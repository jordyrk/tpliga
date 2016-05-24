package org.tpl.integration.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.League;
import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Season;
import org.tpl.business.model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 16.okt.2010

*/

public class JDBCSeasonDao extends SimpleJdbcDaoSupport implements SeasonDao{

    private SeasonRowMapper seasonRowMapper= new SeasonRowMapper();
    private LeagueRoundRowMapper leagueRoundRowMapper = new LeagueRoundRowMapper();
    public List<Season> getSeasonsByLeagueId(int leagueId) {
        return getSimpleJdbcTemplate().query("SELECT * FROM season  WHERE leagueid = ?", seasonRowMapper, leagueId);
    }

    public List<Season> getAllSeasons() {
        return  getSimpleJdbcTemplate().query("SELECT * FROM season where id > ? ", seasonRowMapper, -1);
    }

    public void saveOrUpdateSeason(Season season) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startdate", season.getStartDate());
        params.put("enddate", season.getEndDate());
        params.put("leagueid", season.getLeague() == null ? (-1) :season.getLeague().getLeagueId());
        params.put("numberofteams",  season.getNumberOfTeams());
        if(season.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("season");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            season.setSeasonId(number.intValue());

        }else{
            params.put("id", season.getSeasonId());
            String sql = "UPDATE season SET leagueid=:leagueid,startdate=:startdate,enddate=:enddate,numberofteams=:numberofteams WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
        saveTeamSeason(season);
    }

    public Season getSeasonById(int id) {
        List<Season> list = getSimpleJdbcTemplate().query("SELECT * FROM season  WHERE id = ?", seasonRowMapper, id);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public Season getDefaultSeason() {
        int seasonId = getDefaultSeasonId();
        if(seasonId < 1) return null;
        return this.getSeasonById(seasonId);

    }

    public void setDefaultSeason(Season season) {
        String insertSql = "INSERT INTO default_season VALUES(?)";
        String deleteSql = "DELETE FROM default_season ";
        getSimpleJdbcTemplate().update(deleteSql);
        getSimpleJdbcTemplate().update(insertSql,season.getSeasonId());


    }

    private int getDefaultSeasonId(){
        int seasonId;
        try{
            seasonId = getSimpleJdbcTemplate().queryForInt("SELECT seasonId FROM default_season");
        }catch (DataAccessException e){
            return -1;
        }
        return seasonId;

    }

    private void saveTeamSeason(Season season){
        if (season.isNew()) return;
        getSimpleJdbcTemplate().update("DELETE FROM team_season WHERE seasonid = ?", season.getSeasonId());
        for (Team team: season.getTeams()){
            if (! team.isNew()) {
                String sql = "INSERT INTO team_season (seasonid, teamid) VALUES(?,?)";
                getSimpleJdbcTemplate().update(sql, season.getSeasonId(), team.getTeamId());
            }
        }
    }

    public void saveOrUpdateLeagueRound(LeagueRound leagueRound) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("seasonid", leagueRound.getSeason().getSeasonId());
        params.put("roundnumber", leagueRound.getRound());

        if(leagueRound.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("league_round");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            leagueRound.setLeagueRoundId(number.intValue());

        }else{
            params.put("id", leagueRound.getLeagueRoundId());
            String sql = "UPDATE league_round SET seasonid=:seasonid,roundnumber=:roundnumber WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public List<LeagueRound> getAllLeagueRounds() {
        return getSimpleJdbcTemplate().query("SELECT * FROM league_round where id > ? ", leagueRoundRowMapper, -1);

    }

    public LeagueRound getLeagueRoundById(int leagueRoundId) {
        List<LeagueRound> list = getSimpleJdbcTemplate().query("SELECT * FROM league_round  WHERE id = ?", leagueRoundRowMapper, leagueRoundId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<LeagueRound> getLeagueRoundBySeasonId(int seasonId) {
        return getSimpleJdbcTemplate().query("SELECT * FROM league_round where seasonid = ? order by roundnumber asc", leagueRoundRowMapper, seasonId);
    }

    public LeagueRound getLeagueRoundByRoundAndSeason(Season season, int numberofround) {
        List<LeagueRound> list = getSimpleJdbcTemplate().query("SELECT * FROM league_round WHERE roundnumber = ? and seasonId = ?", leagueRoundRowMapper, numberofround, season.getSeasonId());

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public LeagueRound getFirstLeagueRoundForSeason(int seasonId){
        List<LeagueRound> list = getSimpleJdbcTemplate().query("select * from league_round where seasonId = ? and roundnumber = (select min(roundnumber) from league_round where seasonId = ?)", leagueRoundRowMapper, seasonId, seasonId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    private class SeasonRowMapper implements ParameterizedRowMapper<Season> {
        public Season mapRow(ResultSet rs, int i) throws SQLException {
            Season season = new Season();
            season.setSeasonId(rs.getInt("id"));
            League league = new League();
            league.setLeagueId(rs.getInt("leagueid"));
            season.setLeague(league);
            season.setNumberOfTeams(rs.getInt("numberofteams"));
            season.setStartDate(rs.getDate("startdate"));
            season.setEndDate(rs.getDate("enddate"));
            return season;
        }
    }

    private class LeagueRoundRowMapper implements ParameterizedRowMapper<LeagueRound> {
        public LeagueRound mapRow(ResultSet rs, int i) throws SQLException {
            LeagueRound leagueRound = new LeagueRound();
            leagueRound.setLeagueRoundId(rs.getInt("id"));
            leagueRound.setRound(rs.getInt("roundnumber"));
            Season season = new Season();
            season.setSeasonId(rs.getInt("seasonId"));
            leagueRound.setSeason(season);

            return leagueRound;
        }
    }

}

