package org.tpl.integration.dao;


import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.Team;
import org.tpl.business.model.fantasy.PlayerUsage;
import org.tpl.business.model.search.PlayerSearchResult;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.util.CommaSeparator;
import org.tpl.business.util.Splitter;
import org.tpl.integration.dao.util.CharacterConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCPlayerDao extends SimpleJdbcDaoSupport implements PlayerDao{

    private PlayerRowMapper playerRowMapper = new PlayerRowMapper();
    private PlayerTeamViewRowMapper playerTeamViewRowMapper = new PlayerTeamViewRowMapper();
    private CharacterConverter characterConverter = new CharacterConverter();
    private PlayerUsageViewRowMapper playerUsageViewRowMapper = new PlayerUsageViewRowMapper();

    public void saveOrUpdatePlayer(Player player) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("firstname", player.getFirstName());
        params.put("lastname", player.getLastName());
        if(player.getAliases() != null && ! player.getAliases().isEmpty()){
            String alias = Splitter.createCommaSeparetedString(player.getAliases());
            params.put("alias", alias);
        }else{
            params.put("alias", "");
        }

        params.put("position", player.getPlayerPosition().toString());
        params.put("price", player.getPrice());
        params.put("shirtnumber", player.getShirtNumber());
        params.put("externalId", player.getExternalId());
        params.put("soccernetId",player.getSoccerNetId());
        //If team is null set team_id to 0. The player will not belong to a team.
        params.put("team_id", (player.getTeam() == null)? 0: player.getTeam().getTeamId());
        if(player.getPlayerId() == -1){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("player");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            player.setPlayerId(number.intValue());

        }else{
            params.put("id", player.getPlayerId());
            String sql = "UPDATE player SET externalId=:externalId, firstname=:firstname,lastname=:lastname,position=:position,price=:price, shirtnumber=:shirtnumber, alias=:alias, soccernetId=:soccernetId, team_id=:team_id WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public Player getPlayerById(int id) {

        List<Player> list = getSimpleJdbcTemplate().query("SELECT * FROM player  WHERE id = ?", playerRowMapper, id);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<Player> getPlayerByAlias(String alias) {
        alias = "%"+  alias + "%";
        return getSimpleJdbcTemplate().query("SELECT * FROM player WHERE alias like ?", playerRowMapper, alias);
    }


    public PlayerSearchResult getPlayerBySearchTerm(SearchTerm term) {
        PlayerSearchResult result = new PlayerSearchResult();
        String query = "select * from player_team_view";
        String countQuery = "select count(*) from player_team_view";
        if (term == null) {
            result.setResults(getSimpleJdbcTemplate().query(query, playerTeamViewRowMapper));
            result.setTotalNumberOfResults(getJdbcTemplate().queryForInt(countQuery));
        } else {
            query += " where " + term.getQuery() +  " ORDER BY shortname";
            countQuery += " where " + term.getQuery();
            Object[] params = convertCharacters(term.getParameters().toArray());
            List<Player> players  =  getSimpleJdbcTemplate().query(query, playerTeamViewRowMapper, params);
            int numberOfResults = getJdbcTemplate().queryForInt(countQuery, params);

            result.setResults(players);
            result.setTotalNumberOfResults(numberOfResults);
        }
        return result;
    }

    private Object[] convertCharacters(Object[] term) {
        for(int i = 0; i < term.length;i++){
            if(term[i] instanceof String){
                String convertedString = characterConverter.convertString((String) term[i]);
                term[i] = convertedString;
            }
        }
        return term;


    }

    public List<Player> getAllPlayers() {
        return getSimpleJdbcTemplate().query("SELECT * FROM player", playerRowMapper);

    }

    public void deletePlayer(int playerId) {
        getSimpleJdbcTemplate().update("DELETE FROM player WHERE id = ?", playerId);
    }

    private class PlayerRowMapper implements ParameterizedRowMapper<Player> {
        public Player mapRow(ResultSet rs, int i) throws SQLException {
            return mapPlayerRow(rs);

        }
    }

    public List<PlayerUsage> getMostUsedPlayers(int limit, int[] fantasyRoundIdArray) {
        String sql = "SELECT id , COUNT(id) AS numberOfUsages FROM player_team_round_view WHERE fantasyRoundId in ("+ CommaSeparator.createCommaSeparatedString(fantasyRoundIdArray) +") GROUP BY id ORDER BY numberOfUsages desc limit "+ limit  ;
        List<PlayerUsage> list = getSimpleJdbcTemplate().query(sql, playerUsageViewRowMapper);
        updateDependencies(list);
        return list;
    }

    public List<PlayerUsage> getMostUsedPlayersForRound(int limit, int fantasyRoundId) {
        String sql = "SELECT id , COUNT(id) AS numberOfUsages FROM player_team_round_view WHERE fantasyRoundId = ? GROUP BY id ORDER BY numberOfUsages desc limit "+ limit;
        List<PlayerUsage> list = getSimpleJdbcTemplate().query(sql, playerUsageViewRowMapper,fantasyRoundId);
        updateDependencies(list);
        return list;
    }

    public List<PlayerUsage> getMostUsedPlayersForTeam(int limit, int fantasyTeamId, int[] fantasyRoundIdArray) {
        String sql = "SELECT id , COUNT(id) AS numberOfUsages FROM player_team_round_view WHERE fantasyRoundId in ("+ CommaSeparator.createCommaSeparatedString(fantasyRoundIdArray) +") AND fantasyteamid = ? GROUP BY id ORDER BY numberOfUsages desc limit "+ limit  ;
        List<PlayerUsage> list = getSimpleJdbcTemplate().query(sql, playerUsageViewRowMapper,fantasyTeamId);
        updateDependencies(list);
        return list;
    }

    private void updateDependencies(List<PlayerUsage> list){
        for(PlayerUsage playerUsage:list){
            updateDependencies(playerUsage);
        }
    }

    private void updateDependencies(PlayerUsage playerUsage){
        
        playerUsage.addPlayer(getPlayerById(playerUsage.getPlayerId()));
    }

    private class PlayerTeamViewRowMapper implements ParameterizedRowMapper<Player> {
        public Player mapRow(ResultSet rs, int i) throws SQLException {
            Player player = mapPlayerRow(rs);
            player.getTeam().setFullName(rs.getString("fullname"));
            player.getTeam().setShortName(rs.getString("shortname"));
            return player;
        }
    }

    private class PlayerUsageViewRowMapper implements ParameterizedRowMapper<PlayerUsage> {
        public PlayerUsage mapRow(ResultSet rs, int i) throws SQLException {
            Player player = new Player();
            player.setPlayerId(rs.getInt("id"));
            PlayerUsage playerUsage = new PlayerUsage();
            playerUsage.addPlayer(player);
            playerUsage.setNumberOfUsages(rs.getInt("numberOfUsages"));
            return playerUsage;
        }
    }

    private Player mapPlayerRow(ResultSet rs)  throws SQLException {
        Player player = new Player();
        player.setPlayerId(rs.getInt("id"));
        player.setExternalId(rs.getInt("externalId"));
        player.setFirstName(rs.getString("firstname"));
        player.setLastName(rs.getString("lastname"));
        String aliases = rs.getString("alias");
        if(aliases != null){
            String[] aliasesArray = aliases.split(",");
            List<String> aliasList = new ArrayList<String>(Arrays.asList(aliasesArray));
            player.setAlias(aliasList);
        }
        player.setPlayerPosition(PlayerPosition.valueOf(rs.getString("position")));
        player.setPrice(rs.getInt("price"));
        player.setShirtNumber(rs.getInt("shirtnumber"));
        player.setSoccerNetId(rs.getInt("soccernetId"));
        Team team = new Team();
        team.setTeamId(rs.getInt("team_id"));
        player.setTeam(team);

        return player;
    }


}
