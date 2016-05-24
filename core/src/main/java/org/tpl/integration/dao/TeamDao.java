package org.tpl.integration.dao;

import org.tpl.business.model.Season;
import org.tpl.business.model.Team;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.model.search.TeamSearchResult;

import java.util.List;
/*
Created by jordyr, 16.okt.2010

*/

public interface TeamDao {

    void saveOrUpdateTeam(Team team);

    Team getTeamById(int id);

    List<Team> getAllTeams();

    List<Team> getTeamBySeason(int seasonId);

    TeamSearchResult getTeamBySearchTerm(SearchTerm term);

    List<Team> getTeamByAlias(String alias);

}
