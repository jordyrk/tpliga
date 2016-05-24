package org.tpl.integration.dao.fantasy;
/*
Created by jordyr, 03.02.11

*/

import org.tpl.business.model.fantasy.FantasyCompetitionStanding;

import java.util.List;

public interface FantasyCompetitionStandingDao {

    void saveOrUpdateFantasyCompetitionStanding(FantasyCompetitionStanding fantasyCompetitionStanding);

    FantasyCompetitionStanding getFantasyCompetitionStandingByIds(int fantasyTeamId, int fantasyCompetitionId, int fantasyRoundId);

    List<FantasyCompetitionStanding> getFantasyCompetitionStandingByRoundAndCompetition(int fantasyCompetitionId, int fantasyRoundId);

    List<FantasyCompetitionStanding> getFantasyCompetitionStandingByTeamAndCompetition(int fantasyCompetitionId, int fantasyTeamId);

    List<FantasyCompetitionStanding> getLastUpdatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId);

    List<FantasyCompetitionStanding> getAccumulatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId, int[] roundIds);

    int getLastUpdatedRoundIdForCompetition(int fantasyCompetitionId);

    void deleteFantasyCompetitionStandings(int fantasyTeamId);
}
