package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.tpl.business.model.fantasy.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcFantasyLeagueStandingDao extends SimpleJdbcDaoSupport implements FantasyLeagueStandingDao {

    private FantasyLeagueStandingRowMapper fantasyLeagueStandingRowMapper = new FantasyLeagueStandingRowMapper();
    public void saveOrUpdateFantasyLeagueStanding(FantasyLeagueStanding fantasyLeagueStanding) {
        boolean alreadySaved = checkIfExists(fantasyLeagueStanding);
        Map<String, Object> params = createMapOfObject(fantasyLeagueStanding);

        String sql = " goalsscored=:goalsscored, goalsagainst=:goalsagainst, matcheswon=:matcheswon, matchesdraw=:matchesdraw, matcheslost=:matcheslost, position=:position, points=:points, lastroundposition=:lastroundposition";
        if(alreadySaved){
            sql = "UPDATE fantasy_league_standings SET "+ sql + " WHERE fantasyteamid=:fantasyteamid AND fantasyroundid=:fantasyroundid AND fantasyleagueid=:fantasyleagueid";
            getSimpleJdbcTemplate().update(sql,params);

        }else{
            sql += ", fantasyteamid=:fantasyteamid, fantasyleagueid=:fantasyleagueid, fantasyroundid=:fantasyroundid";
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_league_standings");
            insert.execute(params);
        }
    }

    public FantasyLeagueStanding getFantasyLeagueStandingByIds(int fantasyTeamId, int fantasyLeagueId, int fantasyRoundId) {
         List<FantasyLeagueStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league_standings  WHERE fantasyteamid = ? AND fantasyleagueid = ? AND fantasyroundid = ?", fantasyLeagueStandingRowMapper, fantasyTeamId, fantasyLeagueId, fantasyRoundId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasyLeagueStanding> getFantasyLeagueStandingByRoundAndLeague(int fantasyLeagueId, int fantasyRoundId) {

       List<FantasyLeagueStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league_standings  WHERE fantasyleagueid = ? AND fantasyroundid = ? ORDER BY position ASC",
               fantasyLeagueStandingRowMapper, fantasyLeagueId, fantasyRoundId);
        return list;
    }

    public List<FantasyLeagueStanding> getFantasyLeagueStandingByTeamAndLeague(int fantasyLeagueId, int fantasyTeamId) {
         List<FantasyLeagueStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_league_standings  WHERE fantasyleagueid = ? AND fantasyteamid = ? ORDER BY fantasyroundid ASC",
                 fantasyLeagueStandingRowMapper, fantasyLeagueId, fantasyTeamId);
        return list;
    }

    public List<FantasyLeagueStanding> getLastUpdatedFantasyLeagueStandingsByLeague(int fantasyLeagueId) {
        int roundId = getLatestUpdatedRoundId(fantasyLeagueId);
        return getFantasyLeagueStandingByRoundAndLeague(fantasyLeagueId, roundId);

    }

    public int getLastUpdatedRoundIdForLeague(int fantasyLeagueId) {
        return getLatestUpdatedRoundId(fantasyLeagueId);
    }

    private boolean checkIfExists(FantasyLeagueStanding fantasyLeagueStanding){
        Object object = getFantasyLeagueStandingByIds(fantasyLeagueStanding.getFantasyTeam().getTeamId(), fantasyLeagueStanding.getFantasyLeague().getId(), fantasyLeagueStanding.getFantasyRound().getFantasyRoundId());
        return object != null;
    }

    private int getLatestUpdatedRoundId(int fantasyLeagueId){
        String sql = "select max(fantasyroundid) from fantasy_league_standings where fantasyleagueid = ?";
        int roundId = getJdbcTemplate().queryForInt(sql, fantasyLeagueId);
        return roundId;
    }

     private Map<String,Object> createMapOfObject(FantasyLeagueStanding fantasyLeagueStanding){
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("fantasyteamid", fantasyLeagueStanding.getFantasyTeam().getTeamId());
        params.put("fantasyroundid", fantasyLeagueStanding.getFantasyRound().getFantasyRoundId());
        params.put("fantasyleagueid", fantasyLeagueStanding.getFantasyLeague().getId());

        params.put("goalsscored", fantasyLeagueStanding.getGoalsScored());
        params.put("goalsagainst", fantasyLeagueStanding.getGoalsAgainst());
        params.put("matcheswon", fantasyLeagueStanding.getMatchesWon());
        params.put("matchesdraw", fantasyLeagueStanding.getMatchesDraw());
        params.put("matcheslost", fantasyLeagueStanding.getMatchesLost());
        params.put("position", fantasyLeagueStanding.getPosition());
        params.put("points", fantasyLeagueStanding.getPoints());
        params.put("lastroundposition", fantasyLeagueStanding.getLastRoundPosition());
        return params;
    }

    private class FantasyLeagueStandingRowMapper implements ParameterizedRowMapper<FantasyLeagueStanding> {
        public FantasyLeagueStanding mapRow(ResultSet rs, int i) throws SQLException {
            FantasyLeagueStanding fantasyLeagueStanding = new FantasyLeagueStanding();
            fantasyLeagueStanding.setGoalsScored(rs.getInt("goalsscored"));
            fantasyLeagueStanding.setGoalsAgainst(rs.getInt("goalsagainst"));

            fantasyLeagueStanding.setMatchesWon(rs.getInt("matcheswon"));
            fantasyLeagueStanding.setMatchesDraw(rs.getInt("matchesdraw"));
            fantasyLeagueStanding.setMatchesLost(rs.getInt("matcheslost"));

            fantasyLeagueStanding.setPosition(rs.getInt("position"));
            fantasyLeagueStanding.setPoints(rs.getInt("points"));
            fantasyLeagueStanding.setLastRoundPosition(rs.getInt("lastroundposition"));

            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            fantasyLeagueStanding.setFantasyTeam(fantasyTeam);

            FantasyLeague fantasyLeague = new FantasyLeague();
            fantasyLeague.setId(rs.getInt("fantasyleagueid"));
            fantasyLeagueStanding.setFantasyLeague(fantasyLeague);

            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setFantasyRoundId(rs.getInt("fantasyroundid"));
            fantasyLeagueStanding.setFantasyRound(fantasyRound);


            return fantasyLeagueStanding;
        }
    }
}
