package org.tpl.integration.dao.fantasy;
/*
Created by jordyr, 03.02.11

*/

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.tpl.business.model.fantasy.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcFantasyCompetitionStandingDao extends SimpleJdbcDaoSupport implements FantasyCompetitionStandingDao {
    private FantasyCompetitionStandingRowMapper fantasyCompetitionStandingRowMapper = new FantasyCompetitionStandingRowMapper();
    private FantasyCompetitionMinimizedStandingRowMapper fantasyCompetitionMinimizedStandingRowMapper = new FantasyCompetitionMinimizedStandingRowMapper();

    public void saveOrUpdateFantasyCompetitionStanding(FantasyCompetitionStanding fantasyCompetitionStanding) {
        boolean alreadySaved = checkIfExists(fantasyCompetitionStanding);
        Map<String, Object> params = createMapOfObject(fantasyCompetitionStanding);

        String sql = " accumulatedpoints=:accumulatedpoints, position=:position, lastroundposition=:lastroundposition, points=:points, avaragepoints=:avaragepoints, minimumpoints=:minimumpoints, maximumpoints=:maximumpoints";
        if(alreadySaved){
            sql = "UPDATE fantasy_competition_standings SET "+ sql + " WHERE fantasyteamid=:fantasyteamid AND fantasyroundid=:fantasyroundid AND fantasycompetitionid=:fantasycompetitionid";
            getSimpleJdbcTemplate().update(sql,params);

        }else{
            sql += ", fantasyteamid=:fantasyteamid, fantasyroundid=:fantasyroundid, fantasycompetitionid=:fantasycompetitionid";
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_competition_standings");
            insert.execute(params);
        }
    }

    public FantasyCompetitionStanding getFantasyCompetitionStandingByIds(int fantasyTeamId, int fantasyCompetitionId, int fantasyRoundId) {
        List<FantasyCompetitionStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition_standings  WHERE fantasyteamid = ? AND fantasycompetitionid = ? AND fantasyroundid = ?", fantasyCompetitionStandingRowMapper, fantasyTeamId, fantasyCompetitionId, fantasyRoundId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasyCompetitionStanding> getFantasyCompetitionStandingByRoundAndCompetition(int fantasyCompetitionId, int fantasyRoundId) {
        List<FantasyCompetitionStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition_standings  WHERE fantasycompetitionid = ? AND fantasyroundid = ? ORDER BY position ASC", fantasyCompetitionStandingRowMapper, fantasyCompetitionId, fantasyRoundId);
        return list;
    }

    public List<FantasyCompetitionStanding> getFantasyCompetitionStandingByTeamAndCompetition(int fantasyCompetitionId, int fantasyTeamId) {
        List<FantasyCompetitionStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_competition_standings  WHERE fantasycompetitionid = ? AND fantasyteamid = ? ORDER BY fantasyroundid ASC", fantasyCompetitionStandingRowMapper, fantasyCompetitionId, fantasyTeamId);
        return list;
    }

    public List<FantasyCompetitionStanding> getLastUpdatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId){
        int roundId = getLatestUpdatedRoundId(fantasyCompetitionId);
        return getFantasyCompetitionStandingByRoundAndCompetition(fantasyCompetitionId,roundId);

    }

    public List<FantasyCompetitionStanding> getAccumulatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId, int[] roundIds) {
        String roundIdRange = " (";
        for(int i = 0; i < roundIds.length; i++){
            if(i != 0){
                roundIdRange +=",";
            }
            roundIdRange += roundIds[i];
        }
        roundIdRange += ") ";
        String sql = "select fantasyteamid, sum(points) as accumulatedpoints, min(points) as minimumpoints, max(points) as maximumpoints, sum(points)/"+ roundIds.length+" as avaragepoints from fantasy_competition_standings where fantasyroundid in "+roundIdRange+" and fantasycompetitionid = ? group by fantasyteamid order by sum(points) desc;";

        List<FantasyCompetitionStanding> list = getSimpleJdbcTemplate().query(sql, fantasyCompetitionMinimizedStandingRowMapper, fantasyCompetitionId);
        return list;
    }

    public int getLastUpdatedRoundIdForCompetition(int fantasyCompetitionId) {
        String sql = "select max(fantasyroundid) from fantasy_competition_standings where fantasycompetitionid = ?";
        int roundId = getJdbcTemplate().queryForInt(sql, fantasyCompetitionId);
        return roundId;
    }

    public void deleteFantasyCompetitionStandings(int fantasyTeamId) {
        String sql = "DELETE FROM fantasy_competition_standings WHERE fantasyteamid = ?";
        getSimpleJdbcTemplate().update(sql,fantasyTeamId);
    }

    private boolean checkIfExists(FantasyCompetitionStanding fantasyCompetitionStanding){
        Object object = getFantasyCompetitionStandingByIds(fantasyCompetitionStanding.getFantasyTeam().getTeamId(), fantasyCompetitionStanding.getFantasyCompetition().getFantasyCompetitionId(), fantasyCompetitionStanding.getFantasyRound().getFantasyRoundId());
        return object != null;
    }

    private int getLatestUpdatedRoundId(int fantasyCompetitionId){
        String sql = "select max(fantasyroundid) from fantasy_competition_standings where fantasycompetitionid = ?";
        int roundId = getJdbcTemplate().queryForInt(sql, fantasyCompetitionId);
        return roundId;
    }



    private Map<String,Object> createMapOfObject(FantasyCompetitionStanding fantasyCompetitionStanding){
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("fantasyteamid", fantasyCompetitionStanding.getFantasyTeam().getTeamId());
        params.put("fantasyroundid", fantasyCompetitionStanding.getFantasyRound().getFantasyRoundId());
        params.put("fantasycompetitionid", fantasyCompetitionStanding.getFantasyCompetition().getFantasyCompetitionId());

        params.put("accumulatedpoints", fantasyCompetitionStanding.getAccumulatedPoints());
        params.put("position", fantasyCompetitionStanding.getPosition());
        params.put("lastroundposition", fantasyCompetitionStanding.getLastRoundPosition());
        params.put("points", fantasyCompetitionStanding.getPoints());
        params.put("avaragepoints", fantasyCompetitionStanding.getAveragepoints());
        params.put("minimumpoints", fantasyCompetitionStanding.getMinimumpoints());
        params.put("maximumpoints", fantasyCompetitionStanding.getMaximumpoints());
        return params;
    }


    private class FantasyCompetitionStandingRowMapper implements ParameterizedRowMapper<FantasyCompetitionStanding> {
        public FantasyCompetitionStanding mapRow(ResultSet rs, int i) throws SQLException {
            FantasyCompetitionStanding fantasyCompetitionStanding = new FantasyCompetitionStanding();

            fantasyCompetitionStanding.setAccumulatedPoints(rs.getInt("accumulatedpoints"));
            fantasyCompetitionStanding.setPosition(rs.getInt("position"));
            fantasyCompetitionStanding.setLastRoundPosition(rs.getInt("lastroundposition"));
            fantasyCompetitionStanding.setPoints(rs.getInt("points"));
            fantasyCompetitionStanding.setAveragepoints(rs.getInt("avaragepoints"));
            fantasyCompetitionStanding.setMinimumpoints(rs.getInt("minimumpoints"));
            fantasyCompetitionStanding.setMaximumpoints(rs.getInt("maximumpoints"));

            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            fantasyCompetitionStanding.setFantasyTeam(fantasyTeam);
            FantasyCompetition fantasyCompetition = new FantasyCompetition();
            fantasyCompetition.setFantasyCompetitionId(rs.getInt("fantasycompetitionid"));
            fantasyCompetitionStanding.setFantasyCompetition(fantasyCompetition);
            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setFantasyRoundId(rs.getInt("fantasyroundid"));
            fantasyCompetitionStanding.setFantasyRound(fantasyRound);


            return fantasyCompetitionStanding;
        }
    }
    private class FantasyCompetitionMinimizedStandingRowMapper implements ParameterizedRowMapper<FantasyCompetitionStanding> {
        public FantasyCompetitionStanding mapRow(ResultSet rs, int i) throws SQLException {
            FantasyCompetitionStanding fantasyCompetitionStanding = new FantasyCompetitionStanding();

            fantasyCompetitionStanding.setAccumulatedPoints(rs.getInt("accumulatedpoints"));
            fantasyCompetitionStanding.setAveragepoints(rs.getInt("avaragepoints"));
            fantasyCompetitionStanding.setMinimumpoints(rs.getInt("minimumpoints"));
            fantasyCompetitionStanding.setMaximumpoints(rs.getInt("maximumpoints"));

            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            fantasyCompetitionStanding.setFantasyTeam(fantasyTeam);


            return fantasyCompetitionStanding;
        }
    }
}
