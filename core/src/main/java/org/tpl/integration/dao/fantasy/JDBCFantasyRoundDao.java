package org.tpl.integration.dao.fantasy;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.integration.dao.IntegerRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 02.des.2010

*/

public class JDBCFantasyRoundDao extends SimpleJdbcDaoSupport implements FantasyRoundDao {
    private FantasyRoundRowMapper fantasyRoundRowMapper = new FantasyRoundRowMapper();

    public void saveOrUpdateFantasyRound(FantasyRound fantasyRound) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("round", fantasyRound.getRound());
        params.put("fantasyseasonid", fantasyRound.getFantasySeason().getFantasySeasonId());
        params.put("openforchange", fantasyRound.isOpenForChange() ? 1 : 0);
        params.put("closeDate", fantasyRound.getCloseDate());

        if(fantasyRound.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_round");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyRound.setFantasyRoundId(number.intValue());

        }else{
            params.put("id", fantasyRound.getFantasyRoundId());
            String sql = "UPDATE fantasy_round SET round=:round, fantasyseasonid=:fantasyseasonid, openforchange=:openforchange, closeDate=:closeDate WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public FantasyRound getFantasyRoundById(int fantasyRoundId) {
        List<FantasyRound> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_round  WHERE id = ?", fantasyRoundRowMapper, fantasyRoundId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public boolean addMatchToRound(int leaguematchId, int fantasyRoundId) {
        if(!isMatchInRound(fantasyRoundId, leaguematchId)){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("leaguematchid", leaguematchId);
            params.put("fantasyroundid", fantasyRoundId);
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_round_match");
            int returnValue = insert.execute(params);
            if(returnValue > 0) return true;
        }
        return false;

    }

    public boolean removeMatchFromRound(int leaguematchId, int fantasyRoundId) {
        int value = 0;
        if(isMatchInRound(fantasyRoundId, leaguematchId)){
            String sql = "DELETE FROM fantasy_round_match WHERE fantasyroundid = ? and leaguematchid = ?";
            value = getSimpleJdbcTemplate().update(sql, fantasyRoundId, leaguematchId);
        }
        return value > 0;
    }

    public List<Integer> getMatchIdsForRound(int fantasyRoundId) {
        String sql = "SELECT leaguematchid FROM fantasy_round_match WHERE fantasyroundid = ?";
        IntegerRowMapper integerRowMapper = new IntegerRowMapper();
        integerRowMapper.setColumnName("leaguematchid");
        List<Integer> matchIds =  getSimpleJdbcTemplate().query(sql,integerRowMapper,fantasyRoundId);
        return matchIds;

    }

    public List<FantasyRound> getFantasyRoundByFantasySeasonId(int fantasySeasonId) {
        return getSimpleJdbcTemplate().query("SELECT * FROM fantasy_round  WHERE fantasyseasonid = ? ORDER BY round ASC", fantasyRoundRowMapper, fantasySeasonId);
    }

    public List<FantasyRound> getFantasyRoundByFantasyLeagueId(int fantasyLeagueId) {
        String sql = "SELECT fantasy_round.* FROM fantasy_round LEFT OUTER JOIN fantasy_league_rounds ON fantasy_league_rounds.fantasyroundid = fantasy_round.id WHERE fantasyleagueid = ? ORDER BY fantasy_round.id";
        List<FantasyRound> fantasyRounds = getSimpleJdbcTemplate().query(sql,fantasyRoundRowMapper,fantasyLeagueId);
        return fantasyRounds;
    }

    public List<FantasyRound> getFantasyRoundByFantasyCupId(int fantasyCupId) {
        String sql = "SELECT fantasy_round.* FROM fantasy_round LEFT OUTER JOIN fantasy_cup_rounds ON fantasy_cup_rounds.fantasyroundid = fantasy_round.id WHERE fantasycupid = ? ORDER BY fantasy_round.id";
        List<FantasyRound> fantasyRounds = getSimpleJdbcTemplate().query(sql,fantasyRoundRowMapper,fantasyCupId);
        return fantasyRounds;
    }

    public List<FantasyRound> getFantasyRoundByFantasyCupGroupId(int fantasyCupGroupId) {
        String sql = "SELECT fantasy_round.* FROM fantasy_round LEFT OUTER JOIN fantasy_cup_group_rounds ON fantasy_cup_group_rounds.fantasyroundid = fantasy_round.id WHERE fantasycupgroupid = ? ORDER BY fantasy_round.id" ;
        List<FantasyRound> fantasyRounds = getSimpleJdbcTemplate().query(sql,fantasyRoundRowMapper,fantasyCupGroupId);
        return fantasyRounds;
    }

    private boolean isMatchInRound(int fantasyRoundId, int leagueMatchId){
        int value = -1;
        try{
            value = getJdbcTemplate().queryForInt("SELECT leaguematchid FROM fantasy_round_match WHERE fantasyroundid = ? and leaguematchid = ?", fantasyRoundId, leagueMatchId);
        }catch(DataAccessException e){

        }
        return value != -1;

    }

    public FantasyRound getFantasyRoundByNumberAndSeason(int round, int fantasySeasonId) {
        List<FantasyRound> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_round  WHERE round = ? AND fantasyseasonid = ? ", fantasyRoundRowMapper, round, fantasySeasonId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    private class FantasyRoundRowMapper implements ParameterizedRowMapper<FantasyRound> {
        public FantasyRound mapRow(ResultSet rs, int i) throws SQLException {
            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setFantasyRoundId(rs.getInt("id"));
            fantasyRound.setRound(rs.getInt("round"));
            fantasyRound.setOpenForChange(rs.getInt("openforchange") == 1);
            fantasyRound.setCloseDate(rs.getTimestamp("closeDate"));
            FantasySeason fantasySeason = new FantasySeason();
            fantasySeason.setFantasySeasonId(rs.getInt("fantasyseasonid"));
            fantasyRound.setFantasySeason(fantasySeason);
            return fantasyRound;
        }
    }
}
