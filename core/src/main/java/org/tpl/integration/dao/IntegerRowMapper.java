package org.tpl.integration.dao;
/*
Created by jordyr, 22.01.11

*/

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerRowMapper implements ParameterizedRowMapper<Integer> {
     private String columnName;
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return new Integer(rs.getInt(columnName));
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
}
