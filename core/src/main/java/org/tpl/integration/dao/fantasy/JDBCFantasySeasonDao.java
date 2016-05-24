package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 26.nov.2010

*/

public class JDBCFantasySeasonDao extends SimpleJdbcDaoSupport implements FantasySeasonDao{
    private FantasySeasonRowMapper fantasySeasonRowMapper = new FantasySeasonRowMapper();

    public void saveOrUpdateFantasySeason(FantasySeason fantasySeason) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", fantasySeason.getName());
        params.put("activeSeason", fantasySeason.isActiveSeason() ? 1: 0);
        params.put("registrationActive", fantasySeason.isRegistrationActive() ? 1: 0);
        params.put("maxTeamPrice", fantasySeason.getMaxTeamPrice());
        params.put("startingNumberOfTransfers", fantasySeason.getStartingNumberOfTransfers() );
        params.put("numberOfTransfersPerRound", fantasySeason.getNumberOfTransfersPerRound());
        params.put("seasonid", fantasySeason.getSeason().getSeasonId());
        params.put("defaultfantasycompetitionid", fantasySeason.getDefaultFantasyCompetition().getFantasyCompetitionId());
        params.put("currentroundid", fantasySeason.getCurrentRound().getFantasyRoundId());
        
        if(fantasySeason.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_season");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasySeason.setFantasySeasonId(number.intValue());

        }else{
            params.put("id", fantasySeason.getFantasySeasonId());
            String sql = "UPDATE fantasy_season SET name=:name,activeSeason=:activeSeason , maxTeamPrice=:maxTeamPrice, startingNumberOfTransfers=:startingNumberOfTransfers, numberOfTransfersPerRound=:numberOfTransfersPerRound, seasonid=:seasonid,defaultfantasycompetitionid=:defaultfantasycompetitionid, currentroundid=:currentroundid, registrationActive=:registrationActive WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
        if(fantasySeason.isActiveSeason()){
            updateActiveSeason(fantasySeason.getFantasySeasonId());
        }
    }

    public FantasySeason getFantasySeasonById(int id) {
         List<FantasySeason> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_season  WHERE id = ?", fantasySeasonRowMapper, id);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public FantasySeason getDefaultFantasySeason() {
        List<FantasySeason> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_season  WHERE activeSeason = 1", fantasySeasonRowMapper);
        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<FantasySeason> getAllFantasySeasons(){
         List<FantasySeason> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_season order by id desc", fantasySeasonRowMapper);
         return list;
     }

    private void updateActiveSeason(int activeSeasonId){
        String sql = "update fantasy_season set activeSeason = 0 where id != ?";
        getSimpleJdbcTemplate().update(sql, activeSeasonId);
    }

    

    private class FantasySeasonRowMapper implements ParameterizedRowMapper<FantasySeason> {
        public FantasySeason mapRow(ResultSet rs, int i) throws SQLException {
            FantasySeason fantasySeason = new FantasySeason();
            fantasySeason.setFantasySeasonId(rs.getInt("id"));
            fantasySeason.setActiveSeason(rs.getInt("activeSeason") == 1);
            fantasySeason.setRegistrationActive(rs.getInt("registrationActive") == 1);
            fantasySeason.setMaxTeamPrice(rs.getInt("maxTeamPrice"));
            fantasySeason.setStartingNumberOfTransfers(rs.getInt("startingNumberOfTransfers"));
            fantasySeason.setNumberOfTransfersPerRound(rs.getInt("numberOfTransfersPerRound"));
            Season season= new Season();
            season.setSeasonId(rs.getInt("seasonid"));
            fantasySeason.setSeason(season);
            fantasySeason.setName(rs.getString("name"));
            FantasyCompetition fantasyCompetition = new FantasyCompetition();
            fantasyCompetition.setFantasyCompetitionId(rs.getInt("defaultfantasycompetitionid"));
            fantasyCompetition.setFantasySeason(fantasySeason);
            fantasySeason.setDefaultFantasyCompetition(fantasyCompetition);
            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setFantasyRoundId(rs.getInt("currentroundid"));
            fantasySeason.setCurrentRound(fantasyRound);
            return fantasySeason;
        }
    }
}
