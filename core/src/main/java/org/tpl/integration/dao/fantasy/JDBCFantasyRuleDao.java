package org.tpl.integration.dao.fantasy;
/*
Created by jordyr, 23.01.11

*/

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.rule.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCFantasyRuleDao extends SimpleJdbcDaoSupport implements FantasyRuleDao{
    private TeamRuleRowMapper teamRuleRowMapper = new TeamRuleRowMapper();
    private PlayerRuleRowMapper playerRuleRowMapper = new PlayerRuleRowMapper();
    public void saveOrUpdatePlayerRule(PlayerRule playerRule) {
        Map<String, Object> params =  paramFromRule(playerRule);
        params.put("ruleType", playerRule.getRuleType().toString());
        if(playerRule.appliesToAPosition()){
            params.put("position", playerRule.getPlayerRulePosition().toString());
        }
        else{
            params.put("position", "");
        }

        if(playerRule.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_rule");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            playerRule.setId(number.intValue());

        }else{
            params.put("id", playerRule.getId());
            String sql = "UPDATE fantasy_rule SET ruleType=:ruleType, points=:points, qualifingNumber=:qualifingNumber, name=:name , position=:position WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public PlayerRule getPlayerRuleById(int playerRuleId) {
        List<PlayerRule> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_rule WHERE id = ?", playerRuleRowMapper, playerRuleId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<PlayerRule> getAllPlayerRules() {
        List<PlayerRule> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_rule", playerRuleRowMapper);
        list.removeAll(Collections.singletonList(null));
        return list;
    }

    public int deleteRule(int ruleId) {
        String sql = "DELETE FROM fantasy_rule WHERE id = ?";
        int numbersOfRowsAffected = getSimpleJdbcTemplate().update(sql, ruleId);
        return numbersOfRowsAffected;
    }

    public void saveOrUpdateTeamRule(TeamRule teamRule) {
        Map<String, Object> params =  paramFromRule(teamRule);
        params.put("ruleType", teamRule.getRuleType().toString());

        if(teamRule.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_rule");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            teamRule.setId(number.intValue());

        }else{
            params.put("id", teamRule.getId());
            String sql = "UPDATE fantasy_rule SET ruleType=:ruleType, points=:points, qualifingNumber=:qualifingNumber, name=:name WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public TeamRule getTeamRuleById(int teamRuleId) {
        List<TeamRule> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_rule WHERE id = ?", teamRuleRowMapper, teamRuleId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public List<TeamRule> getAllTeamRules() {
        List<TeamRule> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_rule", teamRuleRowMapper);
        list.removeAll(Collections.singletonList(null));
        return list;
    }

    private Map<String, Object> paramFromRule(Rule rule){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("points", rule.getPoints());
        map.put("name", rule.getName());
        map.put("qualifingNumber", rule.getQualifingNumber());
        map.put("position", rule.getPlayerRulePosition().toString());
        return map;
    }

    private class TeamRuleRowMapper implements ParameterizedRowMapper<TeamRule> {
        public TeamRule mapRow(ResultSet rs, int i) throws SQLException {
            String name = rs.getString("name");
            int points = rs.getInt("points");
            int qualifingNumber = rs.getInt("qualifingNumber");

            TeamRuleType teamRuleType = null;
            try {
                teamRuleType = TeamRuleType.valueOf(rs.getString("ruleType"));
            } catch (IllegalArgumentException e) {
                return null;
            }
             PlayerRulePosition playerRulePosition = null;
             try {
                playerRulePosition = PlayerRulePosition.valueOf(rs.getString("position"));
            } catch (Exception e) {
                //do nothing, playerposition will be null
            }
            TeamRule teamRule = null;
             if (playerRulePosition != null) {
                teamRule = new TeamRule(teamRuleType,points,qualifingNumber,name, playerRulePosition);
            } else {
                teamRule = new TeamRule(teamRuleType,points,qualifingNumber,name, PlayerRulePosition.ALL);
            }
            teamRule.setId(rs.getInt("id"));
            return teamRule;
        }
    }

    private class PlayerRuleRowMapper implements ParameterizedRowMapper<PlayerRule> {
        public PlayerRule mapRow(ResultSet rs, int i) throws SQLException {
            String name = rs.getString("name");
            int points = rs.getInt("points");
            int qualifingNumber = rs.getInt("qualifingNumber");
            PlayerRuleType playerRuleType = null;
            try {
                playerRuleType = PlayerRuleType.valueOf(rs.getString("ruleType"));
            } catch (IllegalArgumentException e) {
                return null;
            }
            PlayerRulePosition playerRulePosition = null;
            try {
                playerRulePosition = PlayerRulePosition.valueOf(rs.getString("position"));
            } catch (Exception e) {
                //do nothing, playerposition will be null
            }
            PlayerRule playerRule;
            if (playerRulePosition != null) {
                playerRule = new PlayerRule(playerRuleType, points, qualifingNumber, name, playerRulePosition);
            } else {
                playerRule = new PlayerRule(playerRuleType, points, qualifingNumber, name, PlayerRulePosition.ALL);
            }
            playerRule.setId(rs.getInt("id"));
            return playerRule;
        }
    }
}
