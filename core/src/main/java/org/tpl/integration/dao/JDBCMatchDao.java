package org.tpl.integration.dao;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Match;
import org.tpl.business.model.Season;
import org.tpl.business.model.Team;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.model.search.MatchSearchResult;
import org.tpl.business.model.search.SearchTerm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 16.okt.2010

*/

public class JDBCMatchDao extends SimpleJdbcDaoSupport implements MatchDao{
    private MatchRowMapper matchRowMapper= new MatchRowMapper();

    public void saveOrUpdateMatch(Match match) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("externalId", match.getExternalId());
        params.put("matchdate", match.getMatchDate());
        params.put("leagueroundid", match.getLeagueRound().getLeagueRoundId());
        params.put("homegoals", match.getHomeGoals());
        params.put("awaygoals", match.getAwayGoals());
        params.put("homeshots", match.getHomeShots());

        params.put("awayshots", match.getAwayShots());
        params.put("homeshotsontarget", match.getHomeShotsOnTarget());
        params.put("awayshotsontarget", match.getAwayShotsOnTarget());
        params.put("homefouls", match.getHomeFouls());
        params.put("awayfouls", match.getAwayFouls());

        params.put("homeoffside", match.getHomeOffside());
        params.put("awayoffside", match.getAwayOffside());
        params.put("homepossesion", match.getHomePossession());
        params.put("awaypossesion", match.getAwayPossession());
        params.put("homeyellow", match.getHomeYellow());

        params.put("awayyellow", match.getAwayYellow());
        params.put("homered", match.getHomeRed());
        params.put("awayred", match.getAwayRed());
        params.put("homesaves", match.getHomeSaves());
        params.put("awaysaves", match.getAwaySaves());

        params.put("hometeamid", match.getHomeTeam().getTeamId());
        params.put("awayteamid", match.getAwayTeam().getTeamId());
        params.put("matchUrl", match.getMatchUrl());
        params.put("soccerNetId", match.getSoccerNetId());
        params.put("fantasypremierleague_id", match.getFantasyPremierLeagueId());

        params.put("playerStatsUpdated", match.isPlayerStatsUpdated() ? 1 : 0);

        if(match.getMatchId() == -1){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("league_match");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            match.setMatchId(number.intValue());

        }else{
            params.put("id", match.getMatchId());
            String sql = "UPDATE league_match SET externalId=:externalId, matchdate=:matchdate, homegoals=:homegoals, awaygoals=:awaygoals, homeshots=:homeshots, awayshots=:awayshots, homeshotsontarget=:homeshotsontarget, awayshotsontarget=:awayshotsontarget, homefouls=:homefouls, awayfouls=:awayfouls, homeoffside=:homeoffside, awayoffside=:awayoffside, homepossesion=:homepossesion, awaypossesion=:awaypossesion, homeyellow=:homeyellow, awayyellow=:awayyellow, homered=:homered, awayred=:awayred, homesaves=:homesaves, awaysaves=:awaysaves, hometeamid=:hometeamid, awayteamid=:awayteamid, leagueroundid=:leagueroundid, matchUrl=:matchUrl, soccerNetId=:soccerNetId, playerStatsUpdated=:playerStatsUpdated, fantasypremierleague_id=:fantasypremierleague_id WHERE  id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public Match getMatchById(int id) {
        List<Match> list = getSimpleJdbcTemplate().query("SELECT * FROM league_match  WHERE id = ?", matchRowMapper, id);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public MatchSearchResult getMatchBySearchTerm(SearchTerm term) {
        MatchSearchResult result = new MatchSearchResult();
        String query = "select * from league_match";
        String countQuery = "select count(*) from league_match";
        if (term == null) {
            result.setResults(getSimpleJdbcTemplate().query(query, matchRowMapper));
            result.setTotalNumberOfResults(getJdbcTemplate().queryForInt(countQuery));
        } else {
            query += " where " + term.getQuery();
            countQuery += " where " + term.getQuery();
            List<Match> matchList =getSimpleJdbcTemplate().query(query, matchRowMapper, term.getParameters().toArray());
            result.setResults(matchList);
            result.setTotalNumberOfResults(getJdbcTemplate().queryForInt(countQuery, term.getParameters().toArray()));
        }
        return result;
    }


    public int queryForInt(String field, int matchId ){
        int value = 0;
        try{
            String sql = "SELECT " + field + " FROM league_match WHERE id = ?";
            value = getSimpleJdbcTemplate().queryForInt(sql,matchId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public List<Match> getMatchesWithRounds(String commaSeparatedString, boolean isPlayerStatsSaved) {
        List<Match> list = getSimpleJdbcTemplate().query("SELECT * FROM league_match  WHERE leagueroundid in (" + commaSeparatedString + ") and playerStatsUpdated = ?", matchRowMapper, isPlayerStatsSaved ? 1 : 0);
        return list;
    }

    private class MatchRowMapper implements ParameterizedRowMapper<Match> {
        public Match mapRow(ResultSet rs, int i) throws SQLException {
            Match match= new Match();
            match.setExternalId(rs.getInt("externalId"));
            match.setMatchDate(rs.getTimestamp("matchdate"));
            match.setHomeGoals(rs.getInt("homegoals"));
            match.setAwayGoals(rs.getInt("awaygoals"));
            match.setHomeShots(rs.getInt("homeshots"));

            match.setAwayShots(rs.getInt("awayshots"));
            match.setHomeShotsOnTarget(rs.getInt("homeshotsontarget"));
            match.setAwayShotsOnTarget(rs.getInt("awayshotsontarget"));
            match.setHomeFouls(rs.getInt("homefouls"));
            match.setAwayFouls(rs.getInt("awayfouls"));

            match.setHomeOffside(rs.getInt("homeoffside"));
            match.setAwayOffside(rs.getInt("awayoffside"));
            match.setHomePossession(rs.getInt("homepossesion"));
            match.setAwayPossession(rs.getInt("awaypossesion"));
            match.setHomeYellow(rs.getInt("homeyellow"));

            match.setAwayYellow(rs.getInt("awayyellow"));
            match.setHomeRed(rs.getInt("homered"));
            match.setAwayRed(rs.getInt("awayred"));
            match.setHomeSaves(rs.getInt("homesaves"));
            match.setAwaySaves(rs.getInt("awaysaves"));
            match.setMatchUrl(rs.getString("matchUrl"));

            match.setSoccerNetId(rs.getString("soccerNetId"));
            match.setFantasyPremierLeagueId(rs.getInt("fantasypremierleague_id"));
            match.setPlayerStatsUpdated(rs.getInt("playerStatsUpdated") == 1);
            Team homeTeam = new Team();
            homeTeam.setTeamId(rs.getInt("hometeamid"));
            match.setHomeTeam(homeTeam);

            Team awayTeam = new Team();
            awayTeam.setTeamId(rs.getInt("awayteamid"));
            match.setAwayTeam(awayTeam);

            LeagueRound leagueRound = new LeagueRound();
            leagueRound.setLeagueRoundId(rs.getInt("leagueroundid"));
            match.setLeagueRound(leagueRound);

            match.setMatchId(rs.getInt("id"));
            return match;
        }
    }
}

