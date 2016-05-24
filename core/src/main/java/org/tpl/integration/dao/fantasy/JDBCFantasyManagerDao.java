package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.fantasy.FantasyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 26.nov.2010

*/

public class JDBCFantasyManagerDao extends SimpleJdbcDaoSupport implements FantasyManagerDao{
    FantasyManagerRowMapper fantasyManagerRowMapper = new FantasyManagerRowMapper();

    public void saveOrUpdateFantasyManager(FantasyManager fantasyManager) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", fantasyManager.getUserId());
        params.put("managerName", fantasyManager.getManagerName());
        params.put("active", fantasyManager.isActive() ? 1 : 0);

        if(fantasyManager.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("fantasy_manager");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            fantasyManager.setManagerId(number.intValue());

        }else{
            params.put("id", fantasyManager.getManagerId());
            String sql = "UPDATE fantasy_manager SET userId=:userId,managerName=:managerName, active=:active WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }
    }

    public FantasyManager getFantasyManagerById(int managerId) {
         List<FantasyManager> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_manager  WHERE id = ?", fantasyManagerRowMapper, managerId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public FantasyManager getFantasyManagerByUserId(String userId) {
         List<FantasyManager> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_manager  WHERE userId = ?", fantasyManagerRowMapper, userId);

        if(list.size() > 0 ){

            return list.get(0);
        }
        else {
            return null;
        }
    }

    public void deleteFantasyManager(String userId) {
        String sql = "DELETE from fantasy_manager where userId = ?";
        getSimpleJdbcTemplate().update(sql,userId);
    }

    public List<FantasyManager> getAllFantasyManagers() {
        List<FantasyManager> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_manager", fantasyManagerRowMapper);
        return list;
    }

    public List<FantasyManager> getAllActiveFantasyManagers() {
        List<FantasyManager> list = getSimpleJdbcTemplate().query("SELECT * FROM fantasy_manager WHERE active = 1", fantasyManagerRowMapper);
        return list;
    }

    private class FantasyManagerRowMapper implements ParameterizedRowMapper<FantasyManager> {
        public FantasyManager mapRow(ResultSet rs, int i) throws SQLException {
            FantasyManager fantasyManager = new FantasyManager();
            fantasyManager.setManagerId(rs.getInt("id"));
            fantasyManager.setUserId(rs.getString("userId"));
            fantasyManager.setManagerName(rs.getString("managerName"));
            fantasyManager.setActive(rs.getInt("active") == 1 ? true: false);
            return fantasyManager;
        }
    }
}
