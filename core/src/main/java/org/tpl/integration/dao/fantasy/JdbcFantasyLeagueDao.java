package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.League;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.FantasyTeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcFantasyLeagueDao extends SimpleJdbcDaoSupport implements FantasyLeagueDao {

    private FantasyLeagueRowMapper fantasyLeagueRowMapper = new FantasyLeagueRowMapper();
    private FantasyLeagueIdRowMapper fantasyLeagueIdRowMapper = new FantasyLeagueIdRowMapper();

    public void saveOrUpdateLeague(FantasyLeague fantasyLeague) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", fantasyLeague.getName());
        params.put("numberofteams", fantasyLeague.getNumberOfTeams());
        params.put("numberOfTopTeams", fantasyLeague.getNumberOfTopTeams());
        params.put("numberOfSecondTopTeams", fantasyLeague.getNumberOfSecondTopTeams());
        params.put("numberOfBottomTeams", fantasyLeague.getNumberOfBottomTeams());
        params.put("numberOfSecondBotttomTeams", fantasyLeague.getNumberOfSecondBotttomTeams());
        params.put("styletheme", fantasyLeague.getStyletheme());

        params.put("homeandawaymatches", fantasyLeague.isHomeAndAwayMatches() ? 1 : 0 );
        params.put("fantasyseasonid", fantasyLeague.getFantasySeason().getFantasySeasonId());

        if(fantasyLeague.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_league");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyLeague.setId(number.intValue());

        }else{
            params.put("id", fantasyLeague.getId());
            String sql = "UPDATE fantasy_league SET name=:name, numberofteams=:numberofteams,numberOfTopTeams=:numberOfTopTeams, numberOfSecondTopTeams=:numberOfSecondTopTeams, numberOfBottomTeams=:numberOfBottomTeams, " +
                    "numberOfSecondBotttomTeams=:numberOfSecondBotttomTeams, styletheme=:styletheme, homeandawaymatches=:homeandawaymatches, fantasyseasonid=:fantasyseasonid WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
        addTeamsToLeague(fantasyLeague);
        addRoundsToLeague(fantasyLeague);
    }


    public void deleteFantasyLeague(int fantasyLeagueId) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_league WHERE id = ?", fantasyLeagueId);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_league_rounds WHERE fantasyleagueid = ?", fantasyLeagueId);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_league_standings WHERE fantasyleagueid = ?", fantasyLeagueId);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_league_team WHERE fantasyleagueid = ?", fantasyLeagueId);

    }

    public FantasyLeague getFantasyLeagueById(int id) {
        List<FantasyLeague> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league  WHERE id = ?", fantasyLeagueRowMapper, id);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasyLeague> getAllFantasyLeagues() {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league ORDER BY name ASC", fantasyLeagueRowMapper);
    }

    public List<FantasyLeague> getAllFantasyLeagues(int fantasySeasonId) {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league WHERE fantasyseasonid = ? ORDER BY name DESC", fantasyLeagueRowMapper,fantasySeasonId);
    }

    public List<FantasyLeague> getFantasyLeaguesForFantasyTeam(int fantasyTeamId, int fantasySeasonId) {
        String leagueIdSql = "SELECT fantasyleagueid FROM fantasy_league_team WHERE fantasyteamid = ? AND fantasyleagueid IN (SELECT id FROM fantasy_league WHERE fantasyseasonid = ?)";
        List<Integer> leagueIdList =  getSimpleJdbcTemplate().query(leagueIdSql, fantasyLeagueIdRowMapper, fantasyTeamId, fantasySeasonId);
        if(leagueIdList.isEmpty()){
            return Collections.emptyList();
        }
        List<FantasyLeague> fantasyLeagueList = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league WHERE id IN (" + createStringFromList(leagueIdList) + ")", fantasyLeagueRowMapper);
        return fantasyLeagueList;
    }

    public List<FantasyLeague> getFantasyLeaguesForFantasyTeam(int fantasyTeamId) {
        String leagueIdSql = "SELECT fantasyleagueid FROM fantasy_league_team WHERE fantasyteamid = ?";
        List<Integer> leagueIdList =  getSimpleJdbcTemplate().query(leagueIdSql, fantasyLeagueIdRowMapper, fantasyTeamId);
        if(leagueIdList.isEmpty()){
            return Collections.emptyList();
        }
        List<FantasyLeague> fantasyLeagueList = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league WHERE id IN (" + createStringFromList(leagueIdList) + ")", fantasyLeagueRowMapper);
        return fantasyLeagueList;
    }

    public List<FantasyLeague> getAllFantasyLeaguesForFantasyRound(int fantasyRoundId) {
        String leagueIdSql = "SELECT fantasyleagueid FROM fantasy_league_rounds WHERE fantasyroundid = ?";
        List<Integer> leagueIdList =  getSimpleJdbcTemplate().query(leagueIdSql, fantasyLeagueIdRowMapper, fantasyRoundId);
        if(leagueIdList.isEmpty()){
            return Collections.emptyList();
        }
        List<FantasyLeague> fantasyLeagueList = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league WHERE id IN (" + createStringFromList(leagueIdList) + ")", fantasyLeagueRowMapper);
        return fantasyLeagueList;
    }

    private void addTeamsToLeague(FantasyLeague fantasyLeague) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_league_team WHERE fantasyleagueid = ?", fantasyLeague.getId());
        for(FantasyTeam fantasyTeam : fantasyLeague.getFantasyTeamList()){
            if (! fantasyTeam.isNew()) {
                String sql = "INSERT INTO fantasy_league_team (fantasyleagueid, fantasyteamid) VALUES(?,?)";
                getSimpleJdbcTemplate().update(sql, fantasyLeague.getId(), fantasyTeam.getTeamId());
            }
        }
    }

    private void addRoundsToLeague(FantasyLeague fantasyLeague) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_league_rounds WHERE fantasyleagueid = ?", fantasyLeague.getId());
        for(FantasyRound fantasyRound : fantasyLeague.getFantasyRoundList()){
            if (! fantasyRound.isNew()) {
                String sql = "INSERT INTO fantasy_league_rounds (fantasyleagueid, fantasyroundid) VALUES(?,?)";
                getSimpleJdbcTemplate().update(sql, fantasyLeague.getId(), fantasyRound.getFantasyRoundId());
            }
        }
    }

    private String createStringFromList(List<Integer> list){
        String text = "";
        for(int i = 0; i < list.size(); i++){
            if(i != 0 ){
                text += ",";
            }
            text += list.get(i);
        }

        return text;
    }

    private class FantasyLeagueRowMapper implements ParameterizedRowMapper<FantasyLeague> {
        public FantasyLeague mapRow(ResultSet rs, int i) throws SQLException {
            FantasyLeague fantasyLeague = new FantasyLeague();
            fantasyLeague.setId(rs.getInt("id"));
            fantasyLeague.setName(rs.getString("name"));
            fantasyLeague.setNumberOfTeams(rs.getInt("numberofteams"));
            fantasyLeague.setNumberOfTopTeams(rs.getInt("numberOfTopTeams"));
            fantasyLeague.setNumberOfSecondTopTeams(rs.getInt("numberOfSecondTopTeams"));
            fantasyLeague.setNumberOfBottomTeams(rs.getInt("numberOfBottomTeams"));
            fantasyLeague.setNumberOfSecondBotttomTeams(rs.getInt("numberOfSecondBotttomTeams"));
            fantasyLeague.setStyletheme(rs.getString("styletheme"));

            fantasyLeague.setHomeAndAwayMatches(rs.getInt("homeandawaymatches") == 1);
            FantasySeason fantasySeason = new FantasySeason();
            fantasySeason.setFantasySeasonId(rs.getInt("fantasyseasonid"));
            fantasyLeague.setFantasySeason(fantasySeason);
            return fantasyLeague;
        }
    }

    private class FantasyLeagueIdRowMapper implements ParameterizedRowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("fantasyleagueid");
        }
    }
}
