package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.fantasy.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcFantasyCupDao extends SimpleJdbcDaoSupport implements FantasyCupDao {

    private FantasyCupRowMapper fantasyCupRowMapper = new FantasyCupRowMapper();
    private FantasyCupIdRowMapper fantasyCupIdRowMapper = new FantasyCupIdRowMapper();

    public void saveOrUpdateCup(FantasyCup fantasyCup) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", fantasyCup.getName());
        params.put("numberofteams", fantasyCup.getNumberOfTeams());
        params.put("fantasyseasonid", fantasyCup.getFantasySeason().getFantasySeasonId());
        params.put("numberOfQualifiedTeamsFromGroups", fantasyCup.getNumberOfQualifiedTeamsFromGroups());

        if(fantasyCup.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_cup");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyCup.setId(number.intValue());

        }else{
            params.put("id", fantasyCup.getId());
            String sql = "UPDATE fantasy_cup SET name=:name, numberofteams=:numberofteams, numberOfQualifiedTeamsFromGroups=:numberOfQualifiedTeamsFromGroups, fantasyseasonid=:fantasyseasonid WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
        addTeamsToCup(fantasyCup);
        addRoundsToCup(fantasyCup);
    }

    public FantasyCup getFantasyCupById(int id) {
        List<FantasyCup> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup  WHERE id = ?", fantasyCupRowMapper, id);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasyCup> getAllFantasyCups() {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup", fantasyCupRowMapper);
    }

    public List<FantasyCup> getAllFantasyCups(int fantasySeasonId) {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup WHERE fantasyseasonid = ?", fantasyCupRowMapper,fantasySeasonId);
    }

    public void deleteCup(int fantasyCupId) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup WHERE id = ?", fantasyCupId);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_match WHERE fantasycupid = ?", fantasyCupId);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_rounds WHERE fantasycupid = ?", fantasyCupId);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_team WHERE fantasycupid = ?", fantasyCupId);

    }

    public List<FantasyCup> getFantasyCupByTeamId(int fantasyTeamId) {
       String cupIdSql = "SELECT fantasycupid FROM fantasy_cup_team WHERE fantasyteamid = ?";
        List<Integer> cupIdList =  getSimpleJdbcTemplate().query(cupIdSql, fantasyCupIdRowMapper, fantasyTeamId);
        if(cupIdList.isEmpty()){
            return Collections.emptyList();
        }
        List<FantasyCup> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup WHERE id IN (" + createStringFromList(cupIdList) + ")", fantasyCupRowMapper);
        return list;

    }

    public List<FantasyCup> getFantasyCupByTeamIdAndSeasonId(int fantasySeasonId, int fantasyTeamId) {
        String cupIdSql = "SELECT fantasycupid FROM fantasy_cup_team WHERE fantasyteamid = ? AND fantasycupid IN (SELECT id FROM fantasy_cup WHERE fantasyseasonid = ?)";
        List<Integer> cupIdList =  getSimpleJdbcTemplate().query(cupIdSql, fantasyCupIdRowMapper, fantasyTeamId, fantasySeasonId);
        if(cupIdList.isEmpty()){
            return Collections.emptyList();
        }
        List<FantasyCup> fantasyCupList = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup WHERE id IN (" + createStringFromList(cupIdList) + ")", fantasyCupRowMapper);
        return fantasyCupList;

    }

    private void addTeamsToCup(FantasyCup fantasyCup) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_team WHERE fantasycupid = ?", fantasyCup.getId());
        for(FantasyTeam fantasyTeam : fantasyCup.getFantasyTeamList()){
            if (! fantasyTeam.isNew()) {
                String sql = "INSERT INTO fantasy_cup_team (fantasycupid, fantasyteamid) VALUES(?,?)";
                getSimpleJdbcTemplate().update(sql, fantasyCup.getId(), fantasyTeam.getTeamId());
            }
        }
    }

    private void addRoundsToCup(FantasyCup fantasyCup) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_rounds WHERE fantasycupid = ?", fantasyCup.getId());
        for(FantasyRound fantasyRound : fantasyCup.getFantasyRoundList()){
            if (! fantasyRound.isNew()) {
                String sql = "INSERT INTO fantasy_cup_rounds (fantasycupid, fantasyroundid) VALUES(?,?)";
                getSimpleJdbcTemplate().update(sql, fantasyCup.getId(), fantasyRound.getFantasyRoundId());
            }
        }
    }

    private class FantasyCupRowMapper implements ParameterizedRowMapper<FantasyCup> {
        public FantasyCup mapRow(ResultSet rs, int i) throws SQLException {
            FantasyCup fantasyCup = new FantasyCup();
            fantasyCup.setId(rs.getInt("id"));
            fantasyCup.setName(rs.getString("name"));
            fantasyCup.setNumberOfTeams(rs.getInt("numberofteams"));
            fantasyCup.setNumberOfQualifiedTeamsFromGroups(rs.getInt("numberOfQualifiedTeamsFromGroups"));
            FantasySeason fantasySeason = new FantasySeason();
            fantasySeason.setFantasySeasonId(rs.getInt("fantasyseasonid"));
            fantasyCup.setFantasySeason(fantasySeason);
            return fantasyCup;
        }
    }

    private class FantasyCupIdRowMapper implements ParameterizedRowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("fantasycupid");
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

}
