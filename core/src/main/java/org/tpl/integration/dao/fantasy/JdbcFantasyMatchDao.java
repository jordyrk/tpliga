package org.tpl.integration.dao.fantasy;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.search.FantasyMatchSearchResult;
import org.tpl.business.model.search.SearchTerm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcFantasyMatchDao extends SimpleJdbcDaoSupport implements FantasyMatchDao {

    FantasyMatchRowMapper fantasyMatchRowMapper = new FantasyMatchRowMapper();

    public void saveOrUpdateFantasyMatch(FantasyMatch fantasyMatch) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("homegoals", fantasyMatch.getHomegoals());
        params.put("awaygoals", fantasyMatch.getAwaygoals());
        params.put("fantasyroundid", fantasyMatch.getFantasyRound().getFantasyRoundId());
        params.put("hometeamid", fantasyMatch.getHomeTeam().getTeamId());
        params.put("awayteamid", fantasyMatch.getAwayTeam().getTeamId());
        params.put("played", fantasyMatch.isPlayed()? 1 : 0);

        if(fantasyMatch.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_match");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyMatch.setId(number.intValue());

        }else{
            params.put("id", fantasyMatch.getId());
            String sql = "UPDATE fantasy_match SET homegoals=:homegoals, awaygoals=:awaygoals, played=:played, hometeamid=:hometeamid, awayteamid=:awayteamid WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
        saveFantasyMatchType(fantasyMatch);
    }


    public FantasyMatch getFantasyMatchById(int fantasyMatchId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_match  WHERE id = ?", fantasyMatchRowMapper, fantasyMatchId);
        if(list.size() > 0 ){

            return addCompetition(list.get(0));
        }
        else {
            return null;
        }
    }

    public List<FantasyMatch> getMatchByRoundId(int fantasyRoundId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_match  WHERE fantasyroundid = ?", fantasyMatchRowMapper, fantasyRoundId);
        for(FantasyMatch fantasyMatch: list){

            fantasyMatch = addCompetition(fantasyMatch);
        }

        return list;
    }

    public List<FantasyMatch> getMatchByLeagueId(int fantasyLeagueId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league_match_view  WHERE fantasyleagueid = ?", fantasyMatchRowMapper, fantasyLeagueId);
        for(FantasyMatch fantasyMatch: list){

            fantasyMatch = addCompetition(fantasyMatch);
        }

        return list;
    }

    public List<FantasyMatch> getMatchByLeagueIdAndRoundId(int fantasyLeagueId, int fantasyRoundId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league_match_view  WHERE fantasyleagueid = ? AND fantasyroundid = ?", fantasyMatchRowMapper, fantasyLeagueId, fantasyRoundId);
        for(FantasyMatch fantasyMatch: list){

            fantasyMatch = addCompetition(fantasyMatch);
        }

        return list;
    }

    public List<FantasyMatch> getMatchByCupId(int fantasyCupId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_match_view  WHERE fantasycupid = ?", fantasyMatchRowMapper, fantasyCupId);
        for(FantasyMatch fantasyMatch: list){

            fantasyMatch = addCompetition(fantasyMatch);
        }

        return list;
    }

    public List<FantasyMatch> getMatchByCupIdAndRoundId(int fantasyCupId, int fantasyRoundId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_match_view  WHERE fantasycupid = ? AND fantasyroundid = ?", fantasyMatchRowMapper, fantasyCupId, fantasyRoundId);
        for(FantasyMatch fantasyMatch: list){

            fantasyMatch = addCompetition(fantasyMatch);
        }

        return list;
    }

    public List<FantasyMatch> getMatchByCupGroupId(int fantasyCupGroupId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group_match_view  WHERE fantasycupgroupid = ?", fantasyMatchRowMapper, fantasyCupGroupId);
        for(FantasyMatch fantasyMatch: list){

            fantasyMatch = addCompetition(fantasyMatch);
        }

        return list;
    }

    public List<FantasyMatch> getMatchByCupGroupIdAndRoundId(int fantasyCupGroupId, int fantasyRoundId) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group_match_view  WHERE fantasycupgroupid = ? AND fantasyroundid = ?", fantasyMatchRowMapper, fantasyCupGroupId, fantasyRoundId);
        for(FantasyMatch fantasyMatch: list){

            fantasyMatch = addCompetition(fantasyMatch);
        }

        return list;
    }

    public FantasyMatchSearchResult getFantasyMatchBySearchTerm(SearchTerm term, FantasyMatchType fantasyMatchType) {
        FantasyMatchSearchResult result = new FantasyMatchSearchResult();
        String query = "select * from " + fantasyMatchType.getTable() + "_view";
        String countQuery = "select count(*) from " + fantasyMatchType.getTable() + "_view";
        if (term == null) {
            result.setResults(getSimpleJdbcTemplate().query(query, fantasyMatchRowMapper));
            result.setTotalNumberOfResults(getJdbcTemplate().queryForInt(countQuery));
        } else {
            query += " where " + term.getQuery();
            countQuery += " where " + term.getQuery();
            List<FantasyMatch> fantasyMatchList =  getSimpleJdbcTemplate().query(query, fantasyMatchRowMapper, term.getParameters().toArray());
            for(FantasyMatch fantasyMatch: fantasyMatchList){
                fantasyMatch = addCompetition(fantasyMatch);
            }
            result.setResults(fantasyMatchList);
            result.setTotalNumberOfResults(getJdbcTemplate().queryForInt(countQuery, term.getParameters().toArray()));
        }
        return result;
    }

    public boolean fantasyCupHasMatches(int fantasyCupId) {
        String sql = "SELECT count(*) FROM fantasy_cup_match WHERE fantasycupid = ?";
        int numberOfMatches = getJdbcTemplate().queryForInt(sql, fantasyCupId);
        return numberOfMatches > 0;
    }

    public boolean fantasyLeagueHasMatches(int fantasyLeagueId) {
        String sql =  "SELECT count(*) FROM fantasy_league_match WHERE fantasyleagueid = ?";
        int numberOfMatches = getJdbcTemplate().queryForInt(sql, fantasyLeagueId);
        return numberOfMatches > 0;
    }

    public boolean fantasyCupGroupHasMatches(int fantasyCupGroupId) {
        String sql = "SELECT count(*) FROM fantasy_cup_group_match WHERE fantasycupgroupid = ?";
        int numberOfMatches = getJdbcTemplate().queryForInt(sql, fantasyCupGroupId);
        return numberOfMatches > 0;
    }

    public int getMaxRoundIdForCupInMatch(int fantasyCupId) {
        String sql = "SELECT max(fantasyroundid) FROM fantasy_cup_match_view WHERE fantasycupid = ?";
        int roundId = -1;
        try{

            roundId = getJdbcTemplate().queryForInt(sql, fantasyCupId);

        }catch (DataAccessException e){

        }
        return roundId;

    }

    public void deleteFantasyMatch(int id) {
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_match WHERE id = ?", id);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_match WHERE fantasymatchid = ?", id);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_cup_group_match WHERE fantasymatchid = ?", id);
        getSimpleJdbcTemplate().update("DELETE FROM fantasy_league_match WHERE fantasymatchid = ?", id);

    }

    public  List<FantasyMatch> getFantasyMatchByRoundsAndIsPlayed(String commaSeparatedStringFromRounds, boolean played) {
        List<FantasyMatch> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_match  WHERE fantasyroundid in  (" + commaSeparatedStringFromRounds + ") AND played = ?", fantasyMatchRowMapper, played ? 1 : 0);
        return list;
    }

    private FantasyMatch addCompetition(FantasyMatch fantasyMatch){

        String sqlLeague = "SELECT fantasyleagueid FROM fantasy_league_match WHERE fantasymatchid = ?";
        String sqlCup = "SELECT fantasycupid FROM fantasy_cup_match WHERE fantasymatchid = ?";
        String sqlCupGroup = "SELECT fantasycupgroupid FROM fantasy_cup_group_match WHERE fantasymatchid = ?";
        try{
            int value = getJdbcTemplate().queryForInt(sqlLeague, fantasyMatch.getId());
            FantasyLeague fantasyLeague = new FantasyLeague();
            fantasyLeague.setId(value);
            fantasyMatch.setFantasyLeague(fantasyLeague);
            return fantasyMatch;
        }catch (DataAccessException e){

        }
        try{
            int value = getJdbcTemplate().queryForInt(sqlCup, fantasyMatch.getId());
            FantasyCup fantasyCup = new FantasyCup();
            fantasyCup.setId(value);
            fantasyMatch.setFantasyCup(fantasyCup);
            return fantasyMatch;

        }catch (DataAccessException e){

        }
        try{
            int value = getJdbcTemplate().queryForInt(sqlCupGroup, fantasyMatch.getId());
            FantasyCupGroup fantasyCupGroup = new FantasyCupGroup();
            fantasyCupGroup.setId(value);
            fantasyMatch.setFantasyCupGroup(fantasyCupGroup);
            return fantasyMatch;

        }catch (DataAccessException e){

        }

        return fantasyMatch;
    }

    private void saveFantasyMatchType(FantasyMatch fantasyMatch){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fantasymatchid", fantasyMatch.getId());
        FantasyMatchType fantasyMatchType = fantasyMatch.getMatchType();
        String databaseTable = fantasyMatchType.getTable();
        String field = fantasyMatchType.getField();
        int id = 0;
        if(fantasyMatchType.equals(FantasyMatchType.CUP)){
            id = fantasyMatch.getFantasyCup().getId();
        }
        else if(fantasyMatchType.equals(FantasyMatchType.LEAGUE)){
            id = fantasyMatch.getFantasyLeague().getId();
        }
        else if(fantasyMatchType.equals(FantasyMatchType.CUPGROUP)){
            id = fantasyMatch.getFantasyCupGroup().getId();
        }
        params.put(field, id);
        if( ! fantasyMatchType.equals(FantasyMatchType.UNKNOWN)){
            if( ! exists(fantasyMatch.getId(),databaseTable,field, id)){
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
                insert.setTableName(databaseTable);
                insert.execute(params);
            }
        }
    }

    private boolean exists(int fantasyMatchId, String databaseTable, String field, int id){
        int value = -1;
        try{
            value = getJdbcTemplate().queryForInt("SELECT fantasymatchid FROM " + databaseTable + " WHERE fantasymatchid = ? AND " + field + " = ?", fantasyMatchId,id);
        }catch (DataAccessException s){
        }
        return value != -1;
    }

    private class FantasyMatchRowMapper implements ParameterizedRowMapper<FantasyMatch> {
        public FantasyMatch mapRow(ResultSet rs, int i) throws SQLException {
            FantasyMatch fantasyMatch = new FantasyMatch();
            fantasyMatch.setId(rs.getInt("id"));
            fantasyMatch.setHomegoals(rs.getInt("homegoals"));
            fantasyMatch.setAwaygoals(rs.getInt("awaygoals"));
            FantasyTeam homeTeam = new FantasyTeam();
            homeTeam.setTeamId(rs.getInt("hometeamid"));
            fantasyMatch.setHomeTeam(homeTeam);
            fantasyMatch.setPlayed(rs.getInt("played") == 1 ? true : false);

            FantasyTeam awayTeam = new FantasyTeam();
            awayTeam.setTeamId(rs.getInt("awayteamid"));
            fantasyMatch.setAwayTeam(awayTeam);

            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setFantasyRoundId(rs.getInt("fantasyroundid"));
            fantasyMatch.setFantasyRound(fantasyRound);

            return fantasyMatch;
        }
    }
}

