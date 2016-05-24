package org.tpl.integration.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.tpl.business.model.Team;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.model.search.TeamSearchResult;
import org.tpl.business.util.Splitter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
/*
Created by jordyr, 16.okt.2010

*/

public class JDBCTeamDao extends SimpleJdbcDaoSupport implements TeamDao{
    private TeamRowMapper teamRowMapper= new TeamRowMapper();
    private TeamAliasRowMapper teamAliasRowMapper = new TeamAliasRowMapper();

    public void saveOrUpdateTeam(Team team) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fullname", team.getFullName());
        params.put("shortname", team.getShortName());
        params.put("externalId", team.getExternalId());
        if(team.getAliases() != null && ! team.getAliases().isEmpty()){
            String alias = Splitter.createCommaSeparetedString(team.getAliases());
            params.put("alias", alias);
        }else{
            params.put("alias", "");
        }

        if(team.isNew()){
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getJdbcTemplate());
            insert.setTableName("team");
            insert.setGeneratedKeyName("ID");
            KeyHolder keyHolder = insert.executeAndReturnKeyHolder(params);
            Number number = keyHolder.getKey();
            team.setTeamId(number.intValue());

        }else{
            params.put("id", team.getTeamId());
            String sql = "UPDATE team SET externalId=:externalId, fullname=:fullname, shortname=:shortname, alias=:alias WHERE id=:id";
            getSimpleJdbcTemplate().update(sql, params);
        }

    }

    public Team getTeamById(int id) {
        List<Team> list = getSimpleJdbcTemplate().query("SELECT * FROM team  WHERE id = ?", teamRowMapper, id);

        if(list.size() > 0 ){
            return list.get(0);
        }
        else {
            return null;
        }
    }

    public TeamSearchResult getTeamBySearchTerm(SearchTerm term) {
        TeamSearchResult result = new TeamSearchResult();
        String query = "select * from team";
        String countQuery = "select count(*) from team";
        if (term == null) {
            result.setResults(getSimpleJdbcTemplate().query(query, teamRowMapper));
            result.setTotalNumberOfResults(getJdbcTemplate().queryForInt(countQuery));
        } else {
            query += " where " + term.getQuery();
            countQuery += " where " + term.getQuery();
            List<Team> teamList =getSimpleJdbcTemplate().query(query, teamRowMapper, term.getParameters().toArray());
            result.setResults(teamList);
            result.setTotalNumberOfResults(getJdbcTemplate().queryForInt(countQuery, term.getParameters().toArray()));
        }
        return result;
    }

    public List<Team> getTeamByAlias(String alias) {
        alias = "%"+alias+"%";
        return getSimpleJdbcTemplate().query("SELECT * FROM team WHERE alias like ?",teamRowMapper, alias);

    }

    public List<Team> getAllTeams() {
        return getSimpleJdbcTemplate().query("SELECT * FROM team ORDER BY shortname ASC", teamRowMapper);
    }

    public List<Team> getTeamBySeason(int seasonId) {
        String sql = "SELECT team.* FROM team LEFT OUTER JOIN team_season ON team_season.teamid = team.id WHERE seasonid = ? ORDER BY shortname ASC";
        return getSimpleJdbcTemplate().query(sql,teamRowMapper, seasonId);
    }

    private class TeamRowMapper implements ParameterizedRowMapper<Team> {
        public Team mapRow(ResultSet rs, int i) throws SQLException {
            Team team = new Team();
            team.setTeamId(rs.getInt("id"));
            team.setShortName(rs.getString("shortname"));
            team.setFullName(rs.getString("fullname"));
            team.setExternalId(rs.getInt("externalId"));
            String aliases = rs.getString("alias");
            if(aliases != null){
                String[] aliasesArray = aliases.split(",");
                List<String> aliasList = new ArrayList<String>(Arrays.asList(aliasesArray));
                team.setAliases(aliasList);
            }

            return team;
        }
    }

    private class TeamAliasRowMapper implements ParameterizedRowMapper<String> {
        public String mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getString("alias");
        }
    }


}
