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

public class JdbcFantasyCupGroupDao extends SimpleJdbcDaoSupport implements FantasyCupGroupDao {
    private FantasyCupGroupRowMapper fantasyCupGroupRowMapper = new FantasyCupGroupRowMapper();
    private FantasyCupGroupIdRowMapper fantasyCupGroupIdRowMapper = new FantasyCupGroupIdRowMapper();

    public void saveOrUpdateCupGroup(FantasyCupGroup fantasyCupGroup) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", fantasyCupGroup.getName());
        params.put("numberofteams", fantasyCupGroup.getNumberOfTeams());
        params.put("homeandawaymatches", fantasyCupGroup.isHomeAndAwayMatches() ? 1 : 0 );
        params.put("fantasycupid", fantasyCupGroup.getFantasyCup().getId());

        if(fantasyCupGroup.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_cup_group");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyCupGroup.setId(number.intValue());

        }else{
            params.put("id", fantasyCupGroup.getId());
            String sql = "UPDATE fantasy_cup_group SET name=:name, numberofteams=:numberofteams, homeandawaymatches=:homeandawaymatches, fantasycupid=:fantasycupid WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
        addTeamsToCup(fantasyCupGroup);
        addRoundsToCupGroup(fantasyCupGroup);
    }

    public FantasyCupGroup getFantasyCupGroupById(int id) {
        List<FantasyCupGroup> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group  WHERE id = ?", fantasyCupGroupRowMapper, id);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasyCupGroup> getAllFantasyCupGroups() {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group  ORDER BY name ASC", fantasyCupGroupRowMapper);
    }

    public List<FantasyCupGroup> getAllFantasyCupGroups(int fantasyCupId) {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group WHERE fantasycupid = ?  ORDER BY name ASC", fantasyCupGroupRowMapper,fantasyCupId);
    }

    public void deleteFantasyCupGroup(int id) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_group WHERE id = ?", id);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_group_rounds WHERE fantasycupgroupid = ?", id);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_group_team WHERE fantasycupgroupid = ?", id);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_group_standings WHERE fantasycupgroupid = ?", id);

    }

    public List<FantasyCupGroup> getAllFantasyCupGroupsForFantasyRound(int fantasyRoundId) {
        String cupGroupIdSql = "SELECT fantasycupgroupid FROM fantasy_cup_group_rounds WHERE fantasyroundid = ?";
        List<Integer> cupGroupIdList =  getSimpleJdbcTemplate().query(cupGroupIdSql, fantasyCupGroupIdRowMapper, fantasyRoundId);
        if(cupGroupIdList.isEmpty()){
            return Collections.emptyList();
        }
        List<FantasyCupGroup> fantasyCupGroupList = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group WHERE id IN (" + createStringFromList(cupGroupIdList) + ")  ORDER BY name ASC", fantasyCupGroupRowMapper);
        return fantasyCupGroupList;
    }

    public int getPreviousRoundId(int fantasyCupGroupId, int fantasyRoundId) {
        String sql = "select max(fantasyroundid) from fantasy_cup_group_rounds where fantasycupgroupid = ? and fantasyroundid < ?";
        return getJdbcTemplate().queryForInt(sql,fantasyCupGroupId,fantasyRoundId);
    }

    private void addTeamsToCup(FantasyCupGroup fantasyCupGroup) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_group_team WHERE fantasycupgroupid = ?", fantasyCupGroup.getId());
        for(FantasyTeam fantasyTeam : fantasyCupGroup.getFantasyTeamList()){
            if (! fantasyTeam.isNew()) {
                String sql = "INSERT INTO fantasy_cup_group_team (fantasycupgroupid, fantasyteamid) VALUES(?,?)";
                getSimpleJdbcTemplate().update(sql, fantasyCupGroup.getId(), fantasyTeam.getTeamId());
            }
        }
    }

    private void addRoundsToCupGroup(FantasyCupGroup fantasyCupGroup) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_group_rounds WHERE fantasycupgroupid = ?", fantasyCupGroup.getId());
        for(FantasyRound fantasyRound : fantasyCupGroup.getFantasyRoundList()){
            if (! fantasyRound.isNew()) {
                String sql = "INSERT INTO fantasy_cup_group_rounds (fantasycupgroupid, fantasyroundid) VALUES(?,?)";
                getSimpleJdbcTemplate().update(sql, fantasyCupGroup.getId(), fantasyRound.getFantasyRoundId());
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

    private class FantasyCupGroupRowMapper implements ParameterizedRowMapper<FantasyCupGroup> {
        public FantasyCupGroup mapRow(ResultSet rs, int i) throws SQLException {
            FantasyCupGroup fantasyCupGroup = new FantasyCupGroup();
            fantasyCupGroup.setId(rs.getInt("id"));
            fantasyCupGroup.setName(rs.getString("name"));
            fantasyCupGroup.setNumberOfTeams(rs.getInt("numberofteams"));
            fantasyCupGroup.setHomeAndAwayMatches(rs.getInt("homeandawaymatches") == 1);

            FantasyCup fantasyCup = new FantasyCup();
            fantasyCup.setId(rs.getInt("fantasycupid"));
            fantasyCupGroup.setFantasyCup(fantasyCup);
            return fantasyCupGroup;
        }
    }

    private class FantasyCupGroupIdRowMapper implements ParameterizedRowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("fantasycupgroupid");
        }
    }
}
