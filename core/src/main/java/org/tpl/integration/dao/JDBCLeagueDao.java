package org.tpl.integration.dao;

import org.springframework.dao.DataAccessException;
import org.tpl.business.model.*;
import org.tpl.business.model.search.MatchSearchResult;
import org.tpl.business.model.search.PlayerSearchResult;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.model.search.TeamSearchResult;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;


/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 21:40:07
 */
public class JDBCLeagueDao extends SimpleJdbcDaoSupport implements LeagueDao {
   
    private LeagueRowMapper leagueRowMapper= new LeagueRowMapper();
    private TeamStatsRowMapper teamStatsRowMapper= new TeamStatsRowMapper();


    public void saveOrUpdateLeague(League league) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fullname", league.getFullName());
        params.put("shortname", league.getShortName());

        if(league.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("league");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            league.setLeagueId(number.intValue());

        }else{
            params.put("id", league.getLeagueId());
            String sql = "UPDATE league SET fullname=:fullname,shortname=:shortname WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public void deleteLeague(int id) {
        String sql = "delete from league where id = ?";
        int numberOfRowsAffected = getSimpleJdbcTemplate().update(sql,id);

    }

    public League getLeagueById(int id) {
        List<League> list = getSimpleJdbcTemplate().query("SELECT * FROM league  WHERE id = ?", leagueRowMapper, id);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<League> getAllLeagues() {
        return getSimpleJdbcTemplate().query("SELECT * FROM league", leagueRowMapper);

    }

    public void saveOrUpdateTeamStats(TeamStats teamStats) {
        Map<String, Object> params = new HashMap<String, Object>();
        //seasonid,teamid,position ,gamesplayed ,wins ,draws ,losses ,goalsscored ,goalsagainst ,points ,
        params.put("seasonid", teamStats.getSeason().getSeasonId());
        params.put("teamid", teamStats.getTeam().getTeamId());
        params.put("position", teamStats.getPosition());
        params.put("gamesplayed", teamStats.getGamesPlayed());
        params.put("wins", teamStats.getWins());

        params.put("draws", teamStats.getDraws());
        params.put("losses", teamStats.getLosses());
        params.put("goalsscored", teamStats.getGoalsScored());
        params.put("goalsagainst", teamStats.getGoalsAgainst());
        params.put("points", teamStats.getPoints());

        TeamStats tempTeamStats = getTeamStatsByTeamAndSeason(teamStats.getTeam().getTeamId(),teamStats.getSeason().getSeasonId());
        if(tempTeamStats == null){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("team_season");
            insert.execute(params);
        }else{
            String sql = "UPDATE team_season SET position=:position,gamesplayed=:gamesplayed ,wins=:wins, draws=:draws, losses=:losses, goalsscored=:goalsscored, goalsagainst=:goalsagainst, " +
                    "points=:points,  WHERE seasonid=:seasonid and teamid=:teamid";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public TeamStats getTeamStatsByTeamAndSeason(int teamId, int seasonId) {
        List<TeamStats> list = getSimpleJdbcTemplate().query("SELECT * FROM team_season  WHERE seasonid = ? and teamid = ? ", teamStatsRowMapper, seasonId, teamId);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    private class LeagueRowMapper implements ParameterizedRowMapper<League> {
        public League mapRow(ResultSet rs, int i) throws SQLException {
            League league = new League();
            league.setLeagueId(rs.getInt("id"));
            league.setFullName(rs.getString("fullname"));
            league.setShortName(rs.getString("shortname"));

            return league;
        }
    }

    private class TeamStatsRowMapper implements ParameterizedRowMapper<TeamStats> {
        public TeamStats mapRow(ResultSet rs, int i) throws SQLException {
            TeamStats teamStats = new TeamStats();
            Team team = new Team();
            team.setTeamId(rs.getInt("teamid"));
            teamStats.setTeam(team);

            Season season = new Season();
            season.setSeasonId(rs.getInt("seasonid"));
            teamStats.setSeason(season);

            teamStats.setPosition(rs.getInt("position"));
            teamStats.setGamesPlayed(rs.getInt("gamesplayed"));
            teamStats.setWins(rs.getInt("wins"));
            teamStats.setDraws(rs.getInt("draws"));

            teamStats.setLosses(rs.getInt("losses"));
            teamStats.setGoalsScored(rs.getInt("goalsscored"));
            teamStats.setGoalsAgainst(rs.getInt("goalsagainst"));
            teamStats.setPoints(rs.getInt("points"));

            return teamStats;
        }
    }


}
