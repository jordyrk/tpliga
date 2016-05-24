package org.tpl.integration.dao;

import no.kantega.commons.log.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.tpl.business.model.*;
import org.tpl.business.model.SimplePlayerStats;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.model.search.SimplePlayerStatsSearchResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 06.nov.2010

*/

public class JDBCPlayerStatsDao extends SimpleJdbcDaoSupport implements PlayerStatsDao{
    private PlayerStatsRowMapper playerStatsRowMapper = new PlayerStatsRowMapper();
    private SimplePlayerStatsRowMapper simplePlayerStatsRowMapper = new SimplePlayerStatsRowMapper();

    public void saveOrUpdatePlayerStats(PlayerStats playerStats) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("goals", playerStats.getGoals());
        params.put("assists", playerStats.getAssists());
        params.put("owngoal", playerStats.getOwnGoal());
        params.put("missedpenalty", playerStats.getMissedPenalty());
        params.put("savedpenalty", playerStats.getSavedPenalty());

        params.put("yellowcard", playerStats.getYellowCard());
        params.put("redcard", playerStats.isRedCard()? 1 : 0);
        params.put("wholegame", playerStats.isWholeGame()? 1 : 0);
        params.put("startedgame", playerStats.isStartedGame()? 1 : 0);
        params.put("playedMinutes", playerStats.getPlayedMinutes());
        params.put("points", playerStats.getPoints());


        params.put("shots", playerStats.getShots());
        params.put("shotsOnGoal", playerStats.getShotsOnGoal());
        params.put("offsides", playerStats.getOffsides());
        params.put("foulsCommited", playerStats.getFoulsCommited());
        params.put("foulsDrawn", playerStats.getFoulsDrawn());
        params.put("saves", playerStats.getSaves());

        params.put("matchid", playerStats.getMatch().getMatchId());
        params.put("playerid", playerStats.getPlayer().getPlayerId());
        params.put("manofthematch", playerStats.isManOfTheMatch()? 1 : 0);
        params.put("easportsppi", playerStats.getEaSportsPPI());
        params.put("teamid", playerStats.getTeam().getTeamId());


        PlayerStats tempPlayerStats = getPlayerStatsByMatchAndPlayer(playerStats.getPlayer().getPlayerId(),playerStats.getMatch().getMatchId());
        if(tempPlayerStats == null){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("player_stats");
            insert.execute(params);
        }else{
            String sql = "UPDATE player_stats SET goals=:goals,assists=:assists ,owngoal=:owngoal, missedpenalty=:missedpenalty, savedpenalty=:savedpenalty, yellowcard=:yellowcard, redcard=:redcard, " +
                    "wholegame=:wholegame, startedgame=:startedgame, playedMinutes=:playedMinutes, points=:points, shots=:shots, shotsOnGoal=:shotsOnGoal, offsides=:offsides, foulsCommited=:foulsCommited," +
                    "foulsDrawn=:foulsDrawn, saves=:saves,manofthematch=:manofthematch,easportsppi=:easportsppi, teamid=:teamid WHERE playerid=:playerid and matchid=:matchid";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public PlayerStats getPlayerStatsByMatchAndPlayer(int playerId, int matchId) {
        List<PlayerStats> list = getSimpleJdbcTemplate().query("SELECT * FROM player_stats  WHERE matchid = ? and playerid = ?", playerStatsRowMapper, matchId, playerId);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<PlayerStats> getPlayerStatsByMatchId(int matchId) {
        List<PlayerStats> list = getSimpleJdbcTemplate().query("SELECT * FROM player_stats  WHERE matchid = ?", playerStatsRowMapper, matchId);
        return list;
    }

    public List<PlayerStats> getPlayerStatsForPlayerAndSeason(int playerId, int seasonId) {
        List<PlayerStats> list = getSimpleJdbcTemplate().query("SELECT * FROM player_stats_season_view  WHERE playerid = ? and seasonid = ?", playerStatsRowMapper, playerId, seasonId);
        return list;
    }

    public PlayerStats getPlayerStatsByPlayerAndRound(int playerId, int leagueRoundId) {
        List<PlayerStats> list = getSimpleJdbcTemplate().query("SELECT * FROM player_stats_season_view  WHERE  playerid = ? and leagueroundid = ?", playerStatsRowMapper,  playerId, leagueRoundId);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public int queryForInt(String field, int playerId, int matchId ){
        int value = 0;
        try{
            String sql = "SELECT " + field + " FROM player_stats WHERE playerid = ? AND matchid = ?";
            value = getSimpleJdbcTemplate().queryForInt(sql,playerId,matchId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public int getHighestLeagueRoundIdForSeason(int seasonId) {
        int value = -1;
        try{
            String sql = "SELECT max(leagueroundid) FROM player_stats_season_view WHERE seasonid = ? ";
            value = getSimpleJdbcTemplate().queryForInt(sql,seasonId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public int getLowestLeagueRoundIdForSeason(int seasonId) {
        int value = -1;
        try{
            String sql = "SELECT min(leagueroundid) FROM player_stats_season_view WHERE seasonid = ? ";
            value = getSimpleJdbcTemplate().queryForInt(sql,seasonId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public SimplePlayerStatsSearchResult getSimplePlayerStatsBySearchTerm(SimplePlayerStatsAttribute simplePlayerStatsAttribute, SearchTerm term, boolean best, int number, String roundIdArray) {
        SimplePlayerStatsSearchResult result = new SimplePlayerStatsSearchResult();
        String query = "SELECT " +simplePlayerStatsAttribute.getField() + " as "+simplePlayerStatsAttribute.getAlias() + " , playerId, teamid  FROM player_stats_season_view";

        if(term != null){
            query += " where " + term.getQuery();
        }

        if(term == null && roundIdArray != null){
            query += " where leagueroundid IN" + roundIdArray;
        }

        else if(term != null && roundIdArray != null){
            query += " AND leagueroundid IN " + roundIdArray;
        }

        query += " group by playerid, teamId ";
        query += " order by " +simplePlayerStatsAttribute.getField();
        if(best){
            query += " desc ";
        }
        else{
            query += " asc ";
        }
        query += " limit " + number;
        Log.debug("DEBUG","Query for simpleplayerstats:" + query);
        List<SimplePlayerStats> simplePlayerStatsList;
        if(term != null){
            simplePlayerStatsList =getSimpleJdbcTemplate().query(query, simplePlayerStatsRowMapper, term.getParameters().toArray());
        }
        else{
            simplePlayerStatsList =getSimpleJdbcTemplate().query(query, simplePlayerStatsRowMapper);
        }

        result.setResults(simplePlayerStatsList);


        return result;
    }

    public boolean playerHasStats(int playerId) {
        boolean playerHasStats = false;
        try{
            int numberOfStats = getSimpleJdbcTemplate().queryForInt("select count(*) from player_stats where playerid=?", playerId);
            if(numberOfStats > 0){
                playerHasStats = true;
            }
        }catch (DataAccessException e){
            //player has no stats, do nothing
        }
        return playerHasStats;

    }

    private class PlayerStatsRowMapper implements ParameterizedRowMapper<PlayerStats> {
        public PlayerStats mapRow(ResultSet rs, int i) throws SQLException {
            PlayerStats playerStats = new PlayerStats();
            Match match = new Match();
            match.setMatchId(rs.getInt("matchid"));
            playerStats.setMatch(match);

            Player player = new Player();
            player.setPlayerId(rs.getInt("playerid"));
            playerStats.setPlayer(player);

            Team team = new Team();
            team.setTeamId(rs.getInt("teamid"));
            playerStats.setTeam(team);

            playerStats.setGoals(rs.getInt("goals"));
            playerStats.setAssists(rs.getInt("assists"));
            playerStats.setOwnGoal(rs.getInt("owngoal"));
            playerStats.setMissedPenalty(rs.getInt("missedpenalty"));
            playerStats.setSavedPenalty(rs.getInt("savedpenalty"));
            playerStats.setYellowCard(rs.getInt("yellowcard"));
            playerStats.setRedCard(rs.getInt("redcard")==1 ? true : false);
            playerStats.setWholeGame(rs.getInt("wholegame") == 1 ? true : false );
            playerStats.setStartedGame(rs.getInt("startedgame") == 1 ? true : false);
            playerStats.setPlayedMinutes(rs.getInt("playedMinutes"));
            playerStats.setPoints(rs.getInt("points"));
            playerStats.setShots(rs.getInt("shots"));
            playerStats.setShotsOnGoal(rs.getInt("shotsOnGoal"));
            playerStats.setOffsides(rs.getInt("offsides"));
            playerStats.setFoulsCommited(rs.getInt("foulsCommited"));
            playerStats.setFoulsDrawn(rs.getInt("foulsDrawn"));
            playerStats.setSaves(rs.getInt("saves"));
            playerStats.setManOfTheMatch(rs.getInt("manofthematch") == 1 ? true : false);
            playerStats.setEaSportsPPI(rs.getInt("easportsppi"));

            return playerStats;
        }

    }

    private class SimplePlayerStatsRowMapper implements ParameterizedRowMapper<SimplePlayerStats> {
        public SimplePlayerStats mapRow(ResultSet rs, int i) throws SQLException {
            SimplePlayerStats simplePlayerStats = new SimplePlayerStats();
            simplePlayerStats.setValue(rs.getInt(1));
            Player player = new Player();
            player.setPlayerId(rs.getInt("playerId"));
            simplePlayerStats.setPlayer(player);
            Team team = new Team();
            team.setTeamId(rs.getInt("teamId"));
            simplePlayerStats.setTeam(team);
            return simplePlayerStats;
        }

    }

}
