package org.tpl.business.service.fantasy;

import org.tpl.business.model.fantasy.FantasyCompetitionStanding;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasyLeagueStanding;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;

import java.util.List;

public interface FantasyLeagueService {

    void saveOrUpdateLeague(FantasyLeague fantasyLeague);

    FantasyLeague getFantasyLeagueById(int id);

    List<FantasyLeague> getAllFantasyLeagues();

    List<FantasyLeague> getAllFantasyLeagues(int fantasySeasonId);

    void createFixtureList(int fantasyLeagueId) throws FixtureListCreationException;

    boolean hasFixtures(int fantasyLeagueId);

    void saveOrUpdateFantasyLeagueStanding(FantasyLeagueStanding fantasyLeagueStanding);

    FantasyLeagueStanding getFantasyLeagueStandingByIds(int fantasyTeamId, int fantasyLeagueId, int fantasyRoundId);

    List<FantasyLeagueStanding> getFantasyLeagueStandingByRoundAndLeague(int fantasyLeagueId, int fantasyRoundId);

    List<FantasyLeagueStanding> getFantasyLeagueStandingByTeamAndLeague(int fantasyLeagueId, int fantasyTeamId);

    List<FantasyLeagueStanding> getLastUpdatedFantasyLeagueStandingsByLeague(int fantasyLeagueId);

    List<FantasyLeagueStanding> getAccumulatedFantasyLeagueStandingsByLeague(int fantasyLeagueId, int numberOfRounds);

    void updateLeagueStandings(int fantasyRoundId);

    FantasyRound getLastUpdatedFantasyRound(int fantasyLeagueId);

    List<FantasyLeague> getFantasyLeaguesForFantasyTeamInCurrentSeason(int fantasyTeamId);

    List<FantasyLeague> getFantasyLeaguesForFantasyTeam(int fantasyTeamId);

    void deleteFantasyLeague(int fantasyLeagueId);
}
