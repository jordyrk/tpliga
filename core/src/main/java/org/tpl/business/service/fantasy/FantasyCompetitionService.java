package org.tpl.business.service.fantasy;
/*
Created by jordyr, 04.02.11

*/

import org.tpl.business.model.fantasy.*;

import java.util.List;

public interface FantasyCompetitionService {

    void saveOrUpdateFantasyCompetitionStanding(FantasyCompetitionStanding fantasyCompetitionStanding);

    FantasyCompetitionStanding getFantasyCompetitionStandingByIds(int fantasyTeamId, int fantasyCompetitionId, int fantasyRoundId);

    List<FantasyCompetitionStanding> getFantasyCompetitionStandingByRoundAndCompetition(int fantasyCompetitionId, int fantasyRoundId);

    List<FantasyCompetitionStanding> getFantasyCompetitionStandingByTeamAndCompetition(int fantasyCompetitionId, int fantasyTeamId);

    List<FantasyCompetitionStanding> getLastUpdatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId);

    List<FantasyCompetitionStanding> getAccumulatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId, int numberOfRounds);

    void saveOrUpdateFantasyCompetition(FantasyCompetition fantasyCompetition);

    FantasyCompetition getFantasyCompetitionById(int fantasyCompetitionId);

    FantasyCompetition getDefaultFantasyCompetitionBySeasonId(int fantasySeasonId);

    List<FantasyCompetition> getFantasyCompetitionBySeasonId(int fantasySeasonId);

    List<FantasyCompetition> getFantasyCompetitionForFantasyTeamInCurrentSeason(int fantasyTeamId);

    boolean addTeamToCompetetion(int fantasyCompetitionId, int fantasyTeamId);

    void removeTeamFromCompetitions(int fantasyTeamId);

    public List<FantasyCompetition> getAllFantasyCompetition();

    FantasyTeamCompetition getFantasyTeamCompetitionByIds(int fantasyCompetitionId, int fantasyTeamId, int fantasySeasonId);

    List<FantasyTeamCompetition> getTeamsForCompetition(int fantasyCompetitionId);

    List<FantasyTeamCompetition> getCompetitonsForTeam(int fantasyTeamId, int fantasySeasonId);

    void updateCompetitionStandings(int fantasyRoundId, int fantasySeasonId);

    FantasyRound getLastUpdatedFantasyRound(int fantasyCompetitionId);



}
