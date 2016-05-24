package org.tpl.integration.dao.fantasy;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 26.nov.2010

*/

public class JDBCFantasyTeamDao extends SimpleJdbcDaoSupport implements FantasyTeamDao{

    private FantasyTeamRowMapper fantasyTeamRowMapper = new FantasyTeamRowMapper();
    private FantasyTeamRoundRowMapper fantasyTeamRoundRowMapper = new FantasyTeamRoundRowMapper();

    public void saveOrUpdateFantasyTeam(FantasyTeam fantasyTeam){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", fantasyTeam.getName());
        params.put("stadium", fantasyTeam.getStadium());
        params.put("teamSpirit", fantasyTeam.getTeamSpirit());
        params.put("supporterClub", fantasyTeam.getSupporterClub());
        params.put("latestNews", fantasyTeam.getLatestNews());
        params.put("multiMediaImageId", fantasyTeam.getMultiMediaImageId());
        params.put("managerid", fantasyTeam.getFantasyManager().getManagerId());
        if(fantasyTeam.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_team");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyTeam.setTeamId(number.intValue());

        }else{
            params.put("id", fantasyTeam.getTeamId());
            String sql = "UPDATE fantasy_team SET name=:name,stadium=:stadium, teamSpirit=:teamSpirit, supporterClub=:supporterClub, latestNews=:latestNews, multiMediaImageId=:multiMediaImageId, managerid=:managerid WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public FantasyTeam getFantasyTeamById(int teamId) {
        List<FantasyTeam> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team  WHERE id = ?", fantasyTeamRowMapper, teamId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasyTeam> getAllFantasyTeams() {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team", fantasyTeamRowMapper);
    }

    public List<FantasyTeam> getFantasyTeamByManagerId(int managerId) {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team  WHERE managerid = ?", fantasyTeamRowMapper, managerId);
    }

    public List<FantasyTeam> getFantasyTeamByFantasyLeagueId(int leagueId) {
        String sql = "SELECT fantasy_team.* FROM fantasy_team LEFT OUTER JOIN fantasy_league_team ON fantasy_league_team.fantasyteamid = fantasy_team.id WHERE fantasyleagueid = ?";
        List<FantasyTeam> teams = getSimpleJdbcTemplate().query(sql,fantasyTeamRowMapper,leagueId);
        return teams;
    }

    public List<FantasyTeam> getFantasyTeamByFantasyCupId(int cupId) {
        String sql = "SELECT fantasy_team.* FROM fantasy_team LEFT OUTER JOIN fantasy_cup_team ON fantasy_cup_team.fantasyteamid = fantasy_team.id WHERE fantasycupid = ?";
        List<FantasyTeam> teams = getSimpleJdbcTemplate().query(sql,fantasyTeamRowMapper,cupId);
        return teams;
    }

    public List<FantasyTeam> getFantasyTeamByFantasyCupGroupId(int cupGroupId) {
        String sql = "SELECT fantasy_team.* FROM fantasy_team LEFT OUTER JOIN fantasy_cup_group_team ON fantasy_cup_group_team.fantasyteamid = fantasy_team.id WHERE fantasycupgroupid = ?";
        List<FantasyTeam> teams = getSimpleJdbcTemplate().query(sql,fantasyTeamRowMapper,cupGroupId);
        return teams;
    }

    private boolean isFantasyTeamRoundSaved(int fantasyTeamId, int fantasyRoundId){
        int value = -1;
        try{
            value = getJdbcTemplate().queryForInt("SELECT fantasyteamid FROM fantasy_team_round WHERE fantasyteamid = ? and fantasyroundid = ?", fantasyTeamId, fantasyRoundId);
        }catch(DataAccessException e){

        }
        return value != -1;
    }

    public void saveOrUpdateFantasyTeamRound(FantasyTeamRound fantasyTeamRound) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("points", fantasyTeamRound.getPoints());
        params.put("official", fantasyTeamRound.isOfficial() ? 1 : 0);
        params.put("officialwhenroundisclosed", fantasyTeamRound.isOfficialWhenRoundIsClosed() ? 1 : 0);
        params.put("fantasyteamid", fantasyTeamRound.getFantasyTeam().getTeamId());
        params.put("fantasyroundid", fantasyTeamRound.getFantasyRound().getFantasyRoundId());
        params.put("formation", fantasyTeamRound.getFormation().toString());
        if(fantasyTeamRound.getPlayerList() != null){
            for(int i = 0; i < fantasyTeamRound.getPlayerList().size(); i++){
                Player player = fantasyTeamRound.getPlayerList().get(i);
                if(player != null && !player.isNew()){
                    params.put("player_" + (i+1) + "_id", player.getPlayerId());
                }else{
                    params.put("player_" + (i+1) + "_id", -1);
                }
            }
        }

        if(isFantasyTeamRoundSaved(fantasyTeamRound.getFantasyTeam().getTeamId(), fantasyTeamRound.getFantasyRound().getFantasyRoundId())){
            //Update
            String sql = "UPDATE fantasy_team_round SET points=:points,official=:official,officialwhenroundisclosed=:officialwhenroundisclosed,formation=:formation,player_1_id=:player_1_id, player_2_id=:player_2_id, player_3_id=:player_3_id, player_4_id=:player_4_id, player_5_id=:player_5_id, player_6_id=:player_6_id, player_7_id=:player_7_id, player_8_id=:player_8_id, player_9_id=:player_9_id, player_10_id=:player_10_id, player_11_id=:player_11_id WHERE fantasyteamid=:fantasyteamid AND fantasyroundid=:fantasyroundid ";
            getSimpleJdbcTemplate().update(sql, params);

        }else{
            //Insert
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_team_round");
            insert.execute(params);
        }
    }

    public FantasyTeamRound getFantasyTeamRoundByIds(int fantasyTeamId, int fantasyRoundId) {
        List<FantasyTeamRound> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team_round  WHERE fantasyteamid = ? and fantasyroundid = ?", fantasyTeamRoundRowMapper, fantasyTeamId, fantasyRoundId);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public List<FantasyTeamRound> getFantasyTeamRoundByTeamId(int fantasyTeamId) {
        List<FantasyTeamRound> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team_round  WHERE fantasyteamid= ?", fantasyTeamRoundRowMapper, fantasyTeamId);
        return list;
    }

    public List<FantasyTeamRound> getFantasyTeamRoundByRoundIdRange(String range, int fantasyTeamId, String order) {
        List<FantasyTeamRound> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team_round  WHERE fantasyroundid IN " + range +" AND fantasyteamid= ? ORDER BY fantasyroundid " + order, fantasyTeamRoundRowMapper, fantasyTeamId);
        return list;
    }

    public List<FantasyTeamRound> getFantasyTeamRoundsByFantasyRoundId(int fantasyRoundId) {
         List<FantasyTeamRound> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team_round  WHERE fantasyroundid = ?", fantasyTeamRoundRowMapper, fantasyRoundId);
        return list;
    }

    public List<FantasyTeamRound> getOfficialFantasyTeamRounds(int fantasyRoundId) {
       List<FantasyTeamRound> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_team_round  WHERE fantasyroundid = ? AND official = 1", fantasyTeamRoundRowMapper, fantasyRoundId);
        return list;
    }

    @Override
    public int getNumberOfOfficialTeams(int fantasyRoundId) {
        return getSimpleJdbcTemplate().queryForInt("SELECT count(*) FROM fantasy_team_round WHERE official = 1 AND fantasyroundid = ?", fantasyRoundId);
    }

    public int getNumberOfOfficialWhenRoundIsClosedTeams(int fantasyRoundId){
        return getSimpleJdbcTemplate().queryForInt("SELECT count(*) FROM fantasy_team_round WHERE officialwhenroundisclosed = 1 AND fantasyroundid = ?", fantasyRoundId);
    }

    public void deleteFantasyTeam(int teamId) {
        String sql = "DELETE FROM fantasy_team WHERE id = ?";
        getSimpleJdbcTemplate().update(sql,teamId);
    }

    public void deleteFantasyTeamRound(int teamId) {
        String sql = "DELETE FROM fantasy_team_round WHERE fantasyteamid = ?";
        getSimpleJdbcTemplate().update(sql,teamId);
    }

    /**
     * Precondition: round is closed
     * @param fantasyRoundId
     */
    public void updateFantasyTeamRoundsWhenRoundIsClosed(int fantasyRoundId) {
        getSimpleJdbcTemplate().update("UPDATE fantasy_team_round SET official = 1 WHERE officialwhenroundisclosed = 1 AND fantasyroundid = ?", fantasyRoundId);
    }

    private class FantasyTeamRowMapper implements ParameterizedRowMapper<FantasyTeam> {
        public FantasyTeam mapRow(ResultSet rs, int i) throws SQLException {
            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("id"));
            fantasyTeam.setName(rs.getString("name"));
            fantasyTeam.setStadium(rs.getString("stadium"));
            fantasyTeam.setSupporterClub(rs.getString("supporterClub"));
            fantasyTeam.setLatestNews(rs.getString("latestNews"));
            fantasyTeam.setMultiMediaImageId(rs.getInt("multiMediaImageId"));
            fantasyTeam.setTeamSpirit(rs.getString("teamSpirit"));
            FantasyManager fantasyManager = new FantasyManager();
            fantasyManager.setManagerId(rs.getInt("managerid"));
            fantasyTeam.setFantasyManager(fantasyManager);
            return fantasyTeam;
        }
    }

    private class FantasyTeamRoundRowMapper implements ParameterizedRowMapper<FantasyTeamRound> {
        public FantasyTeamRound mapRow(ResultSet rs, int i) throws SQLException {
            FantasyTeamRound fantasyTeamRound = new FantasyTeamRound();
            fantasyTeamRound.setOfficial(rs.getInt("official") == 1 ? true : false);
            fantasyTeamRound.setOfficialWhenRoundIsClosed(rs.getInt("officialwhenroundisclosed") == 1 ? true : false);
            fantasyTeamRound.setPoints(rs.getInt("points"));
            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setFantasyRoundId(rs.getInt("fantasyroundid"));
            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            fantasyTeamRound.setFantasyRound(fantasyRound);
            fantasyTeamRound.setFantasyTeam(fantasyTeam);
            Formation formation = Formation.valueOf(rs.getString("formation"));
            fantasyTeamRound.setFormation(formation);
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(1),getPlayer("player_1_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(2),getPlayer("player_2_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(3),getPlayer("player_3_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(4),getPlayer("player_4_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(5),getPlayer("player_5_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(6),getPlayer("player_6_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(7),getPlayer("player_7_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(8),getPlayer("player_8_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(9),getPlayer("player_9_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(10),getPlayer("player_10_id", rs));
            fantasyTeamRound.addPlayer(fantasyTeamRound.getFormation().getPosition(11),getPlayer("player_11_id", rs));

            return fantasyTeamRound;

        }

        private Player getPlayer(String field,ResultSet rs )throws SQLException{
            Player player = new Player();
            int id = rs.getInt(field);
            if(id >= 1) {
               player.setPlayerId(id);
            }
            return player;
        }
    }
}
