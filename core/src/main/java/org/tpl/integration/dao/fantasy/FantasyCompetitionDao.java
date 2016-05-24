package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasyTeamCompetition;

import java.util.List;
/*
Created by jordyr, 26.nov.2010

*/

public interface FantasyCompetitionDao {

    void saveOrUpdateFantasyCompetition(FantasyCompetition fantasyCompetition);

    FantasyCompetition getFantasyCompetitionById(int fantasyCompetitionId);

    FantasyCompetition getDefaultFantasyCompetitionBySeasonId(int fantasySeasonId);

    List<FantasyCompetition> getFantasyCompetitionBySeasonId(int fantasySeasonId);

    boolean addTeamToCompetetion(int fantasyCompetitionId, int fantasyTeamId);

    void removeTeamFromAllCompetitions(int fantasyTeamId);

    FantasyTeamCompetition getFantasyTeamCompetitionByIds(int fantasyCompetitionId, int fantasyTeamId, int fantasySeasonId);

    List<FantasyTeamCompetition> getTeamsForCompetition(int fantasyCompetitionId);

    List<FantasyTeamCompetition> getCompetitonsForTeam(int fantasyTeamId, int fantasySeasonId);

    List<FantasyCompetition> getAllFantasyCompetition();

    List<FantasyCompetition> getFantasyCompetitionForFantasyTeamInSeason(int fantasyTeamId, int fantasySeasonId);
}
