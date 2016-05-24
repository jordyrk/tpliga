package org.tpl.integration.dao.fantasy;

import no.kantega.commons.log.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasyCupGroupStanding;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasyTeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcFantasyCupGroupStandingDao extends SimpleJdbcDaoSupport implements FantasyCupGroupStandingDao {
    private FantasyCupGroupStandingRowMapper fantasyCupGroupStandingRowMapper= new FantasyCupGroupStandingRowMapper();
    
    public void saveOrUpdateFantasyCupGroupStanding(FantasyCupGroupStanding fantasyCupGroupStanding) {
       boolean alreadySaved = checkIfExists(fantasyCupGroupStanding);
        Map<String, Object> params = createMapOfObject(fantasyCupGroupStanding);

        String sql = " goalsscored=:goalsscored, goalsagainst=:goalsagainst, matcheswon=:matcheswon, matchesdraw=:matchesdraw, matcheslost=:matcheslost, position=:position, points=:points, lastroundposition=:lastroundposition";
        if(alreadySaved){
            sql = "UPDATE fantasy_cup_group_standings SET "+ sql + " WHERE fantasyteamid=:fantasyteamid AND fantasyroundid=:fantasyroundid AND fantasycupgroupid=:fantasycupgroupid";
            getSimpleJdbcTemplate().update(sql,params);

        }else{
            sql += ", fantasyteamid=:fantasyteamid, fantasycupgroupid=:fantasycupgroupid, fantasyroundid=:fantasyroundid";
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_cup_group_standings");
            insert.execute(params);
        }
    }

    public FantasyCupGroupStanding getFantasyCupGroupStandingByIds(int fantasyTeamId, int fantasyCupGroupId, int fantasyRoundId) {
        List<FantasyCupGroupStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group_standings  WHERE fantasyteamid = ? AND fantasycupgroupid = ? AND fantasyroundid = ?", fantasyCupGroupStandingRowMapper, fantasyTeamId, fantasyCupGroupId, fantasyRoundId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasyCupGroupStanding> getFantasyCupGroupStandingByRoundAndCupGroup(int fantasyCupGroupId, int fantasyRoundId) {
         List<FantasyCupGroupStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group_standings  WHERE fantasycupgroupid = ? AND fantasyroundid = ? ORDER BY position ASC",
               fantasyCupGroupStandingRowMapper, fantasyCupGroupId, fantasyRoundId);
        return list;
    }

    public List<FantasyCupGroupStanding> getFantasyCupGroupStandingByTeamAndCupGroup(int fantasyCupGroupId, int fantasyTeamId) {
          List<FantasyCupGroupStanding> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_cup_group_standings  WHERE fantasycupgroupid = ? AND fantasyteamid = ? ORDER BY fantasyroundid ASC",
                 fantasyCupGroupStandingRowMapper, fantasyCupGroupId, fantasyTeamId);
        return list;
    }

    public List<FantasyCupGroupStanding> getLastUpdatedFantasyCupGroupStandingsByCupGroup(int fantasyCupGroupId) {
        int roundId = getLatestUpdatedRoundId(fantasyCupGroupId);
        return getFantasyCupGroupStandingByRoundAndCupGroup(fantasyCupGroupId, roundId);
    }

    public int getLastUpdatedRoundIdForCupGroup(int fantasyCupGroupId) {
        return getLatestUpdatedRoundId(fantasyCupGroupId);
    }

    public int getHighestRoundForGroupId(int fantasyCupGroupId) {

        int highestRoundId = -1;
        try {
            highestRoundId = getSimpleJdbcTemplate().queryForInt("select max(fantasyroundid) from fantasy_cup_group_standings where fantasycupgroupid = ?", fantasyCupGroupId);
        } catch (DataAccessException e) {
            Log.debug(this.getClass().getName(),"No roundid found for cupgroup with id:" + fantasyCupGroupId);
        }
        return highestRoundId;
    }

    private boolean checkIfExists(FantasyCupGroupStanding fantasyCupGroupStanding){
        Object object = getFantasyCupGroupStandingByIds(fantasyCupGroupStanding.getFantasyTeam().getTeamId(), fantasyCupGroupStanding.getFantasyCupGroup().getId(), fantasyCupGroupStanding.getFantasyRound().getFantasyRoundId());
        return object != null;
    }

    private int getLatestUpdatedRoundId(int fantasyCupGroupId){
        String sql = "select max(fantasyroundid) from fantasy_cup_group_standings where fantasycupgroupid = ?";
        int roundId = getJdbcTemplate().queryForInt(sql, fantasyCupGroupId);
        return roundId;
    }

     private Map<String,Object> createMapOfObject(FantasyCupGroupStanding fantasyCupGroupStanding){
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("fantasyteamid", fantasyCupGroupStanding.getFantasyTeam().getTeamId());
        params.put("fantasyroundid", fantasyCupGroupStanding.getFantasyRound().getFantasyRoundId());
        params.put("fantasycupgroupid", fantasyCupGroupStanding.getFantasyCupGroup().getId());

        params.put("goalsscored", fantasyCupGroupStanding.getGoalsScored());
        params.put("goalsagainst", fantasyCupGroupStanding.getGoalsAgainst());
        params.put("matcheswon", fantasyCupGroupStanding.getMatchesWon());
        params.put("matchesdraw", fantasyCupGroupStanding.getMatchesDraw());
        params.put("matcheslost", fantasyCupGroupStanding.getMatchesLost());
        params.put("position", fantasyCupGroupStanding.getPosition());
        params.put("points", fantasyCupGroupStanding.getPoints());
        params.put("lastroundposition", fantasyCupGroupStanding.getLastRoundPosition());
        return params;
    }
    
    private class FantasyCupGroupStandingRowMapper implements ParameterizedRowMapper<FantasyCupGroupStanding> {
        public FantasyCupGroupStanding mapRow(ResultSet rs, int i) throws SQLException {
            FantasyCupGroupStanding fantasyCupGroupStanding = new FantasyCupGroupStanding();
            fantasyCupGroupStanding.setGoalsScored(rs.getInt("goalsscored"));
            fantasyCupGroupStanding.setGoalsAgainst(rs.getInt("goalsagainst"));

            fantasyCupGroupStanding.setMatchesWon(rs.getInt("matcheswon"));
            fantasyCupGroupStanding.setMatchesDraw(rs.getInt("matchesdraw"));
            fantasyCupGroupStanding.setMatchesLost(rs.getInt("matcheslost"));

            fantasyCupGroupStanding.setPosition(rs.getInt("position"));
            fantasyCupGroupStanding.setPoints(rs.getInt("points"));
            fantasyCupGroupStanding.setLastRoundPosition(rs.getInt("lastroundposition"));

            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            fantasyCupGroupStanding.setFantasyTeam(fantasyTeam);

            FantasyCupGroup fantasyCupGroup = new FantasyCupGroup();
            fantasyCupGroup.setId(rs.getInt("fantasycupgroupid"));
            fantasyCupGroupStanding.setFantasyCupGroup(fantasyCupGroup);

            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setFantasyRoundId(rs.getInt("fantasyroundid"));
            fantasyCupGroupStanding.setFantasyRound(fantasyRound);
            return fantasyCupGroupStanding;
        }
    }
}
