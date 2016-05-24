package org.tpl.integration.dao.fantasy;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.FantasyTeamCompetition;
import org.tpl.business.util.CommaSeparator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
/*
Created by jordyr, 02.des.2010

*/

public class JDBCFantasyCompetitionDao extends SimpleJdbcDaoSupport implements FantasyCompetitionDao {
    private FantasyCompetitionRowMapper fantasyCompetitionRowMapper = new FantasyCompetitionRowMapper();
    private FantasyTeamCompetitionRowMapper fantasyTeamCompetitionRowMapper = new FantasyTeamCompetitionRowMapper();
    private FantasyCompetitionIdRowMapper fantasyCompetitionIdRowMapper = new FantasyCompetitionIdRowMapper();

    public void saveOrUpdateFantasyCompetition(FantasyCompetition fantasyCompetition) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("numberofteams", fantasyCompetition.getNumberOfTeams());
        params.put("name", fantasyCompetition.getName());
        params.put("defaultCompetition", fantasyCompetition.isDefaultCompetition() ? 1 : 0);
        params.put("fantasyseasonid", fantasyCompetition.getFantasySeason().getFantasySeasonId());

        if(fantasyCompetition.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_competition");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyCompetition.setFantasyCompetitionId(number.intValue());

        }else{
            params.put("id", fantasyCompetition.getFantasyCompetitionId());
            String sql = "UPDATE fantasy_competition SET name=:name, defaultCompetition=:defaultCompetition, numberofteams=:numberofteams, fantasyseasonid=:fantasyseasonid WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
        if(fantasyCompetition.isDefaultCompetition()){
            updateDefaultCompetition(fantasyCompetition.getFantasyCompetitionId(), fantasyCompetition.getFantasySeason().getFantasySeasonId());
        }
    }

    public FantasyCompetition getFantasyCompetitionById(int fantasyCompetitionId) {
        List<FantasyCompetition> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition  WHERE id = ?", fantasyCompetitionRowMapper, fantasyCompetitionId);

        if(list.size() > 0 ){
            FantasyCompetition fantasyCompetition = list.get(0);
            fantasyCompetition.setFantasyTeamCompetitionList(getTeamsForCompetition(fantasyCompetitionId));
            return fantasyCompetition;
        }
        else {
            return null;
        }
    }

    public List<FantasyCompetition> getFantasyCompetitionBySeasonId(int fantasySeasonId) {
        List<FantasyCompetition> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition  WHERE fantasyseasonid = ?", fantasyCompetitionRowMapper, fantasySeasonId);
        return list;
    }

    public FantasyCompetition getDefaultFantasyCompetitionBySeasonId(int fantasySeasonId) {
        List<FantasyCompetition> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition  WHERE defaultcompetition = 1 and fantasyseasonid = ?", fantasyCompetitionRowMapper, fantasySeasonId);
        if(list.size() > 0 ){
            FantasyCompetition fantasyCompetition = list.get(0);
            fantasyCompetition.setFantasyTeamCompetitionList(getTeamsForCompetition(fantasyCompetition.getFantasyCompetitionId()));
            return fantasyCompetition;
        }
        else {
            return null;
        }
    }

    public boolean addTeamToCompetetion(int fantasyCompetitionId, int fantasyTeamId) {

        if(!isTeamInCompetition(fantasyCompetitionId, fantasyTeamId)){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("fantasyteamid", fantasyTeamId);
            params.put("fantasycompetitionid", fantasyCompetitionId);
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_competition_team");
            int returnValue = insert.execute(params);
            if(returnValue > 0) return true;
        }
        return false;

    }

    public void removeTeamFromAllCompetitions(int fantasyTeamId) {

        String sql = "DELETE FROM fantasy_competition_team WHERE fantasyteamid = ?";
        getSimpleJdbcTemplate().update(sql,fantasyTeamId);

    }

    public FantasyTeamCompetition getFantasyTeamCompetitionByIds(int fantasyCompetitionId, int fantasyTeamId, int fantasySeasonId) {
        List<FantasyTeamCompetition> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition_standing_view  WHERE fantasycompetitionid = ? and fantasyteamid = ? AND fantasyseasonid = ?", fantasyTeamCompetitionRowMapper, fantasyCompetitionId, fantasyTeamId, fantasySeasonId);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    private boolean isTeamInCompetition(int fantasyCompetitionId, int fantasyTeamId){
        int value = -1;
        try{
            value = getJdbcTemplate().queryForInt("SELECT fantasyteamid FROM fantasy_competition_team WHERE fantasyteamid = ? and fantasycompetitionid = ?", fantasyTeamId, fantasyCompetitionId);
        }catch(DataAccessException e){

        }
        return value != -1;

    }

    public List<FantasyTeamCompetition> getTeamsForCompetition(int fantasyCompetitionId){
        List<FantasyTeamCompetition> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition_standing_view  WHERE fantasycompetitionid= ? ORDER BY sumpoints DESC", fantasyTeamCompetitionRowMapper, fantasyCompetitionId);
        return list;
    }

    public List<FantasyTeamCompetition> getCompetitonsForTeam(int fantasyTeamId, int fantasySeasonId) {
        List<FantasyTeamCompetition> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition_standing_view WHERE fantasyteamid  = ? AND fantasyseasonid = ?", fantasyTeamCompetitionRowMapper, fantasyTeamId, fantasySeasonId);
        return list;
    }

    public List<FantasyCompetition> getAllFantasyCompetition() {
        List<FantasyCompetition> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition", fantasyCompetitionRowMapper);
        return list;
    }

    public List<FantasyCompetition> getFantasyCompetitionForFantasyTeamInSeason(int fantasyTeamId, int fantasySeasonId) {
        String competitionIdSql = "SELECT fantasycompetitionid FROM fantasy_competition_team WHERE fantasyteamid = ?";
        List<Integer> competitionIdList =  getSimpleJdbcTemplate().query(competitionIdSql, fantasyCompetitionIdRowMapper, fantasyTeamId);
        if(competitionIdList.isEmpty()){
            return Collections.emptyList();
        }
        List<FantasyCompetition> competitionList = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition WHERE id IN (" + createStringFromList(competitionIdList) + ") AND fantasyseasonid = ?", fantasyCompetitionRowMapper, fantasySeasonId);
        return competitionList;
    }

    private String createStringFromList(List<Integer> list) {
        return CommaSeparator.createCommaSeparatedString(list);
    }

    private void updateDefaultCompetition(int defaultCompetitionId, int fantasySeasonId){
        String sql = "update fantasy_competition set defaultcompetition = 0 where id != ? and fantasyseasonid = ?";
        getSimpleJdbcTemplate().update(sql, defaultCompetitionId, fantasySeasonId);
    }

    private class FantasyCompetitionRowMapper implements ParameterizedRowMapper<FantasyCompetition> {
        public FantasyCompetition mapRow(ResultSet rs, int i) throws SQLException {
            FantasyCompetition fantasyCompetition = new FantasyCompetition();
            fantasyCompetition.setFantasyCompetitionId(rs.getInt("id"));
            fantasyCompetition.setName(rs.getString("name"));
            fantasyCompetition.setDefaultCompetition(rs.getInt("defaultCompetition") == 1);
            fantasyCompetition.setNumberOfTeams(rs.getInt("numberofteams"));
            FantasySeason fantasySeason = new FantasySeason();
            fantasySeason.setFantasySeasonId(rs.getInt("fantasyseasonid"));
            fantasyCompetition.setFantasySeason(fantasySeason);
            return fantasyCompetition;
        }
    }

    private class FantasyTeamCompetitionRowMapper implements ParameterizedRowMapper<FantasyTeamCompetition> {
        public FantasyTeamCompetition mapRow(ResultSet rs, int i) throws SQLException {
            FantasyTeamCompetition fantasyTeamCompetition = new FantasyTeamCompetition();
            fantasyTeamCompetition.setSumPoints(rs.getInt("sumpoints"));
            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            fantasyTeamCompetition.setFantasyTeam(fantasyTeam);
            FantasyCompetition fantasyCompetition = new FantasyCompetition();
            fantasyCompetition.setFantasyCompetitionId(rs.getInt("fantasycompetitionid"));
            fantasyTeamCompetition.setFantasyCompetition(fantasyCompetition);
            return fantasyTeamCompetition;
        }
    }

    private class FantasyCompetitionIdRowMapper implements ParameterizedRowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("fantasycompetitionid");
        }
    }

}
