package org.tpl.integration.dao.fantasy;
/*
Created by jordyr, 03.02.11

*/

import org.tpl.business.model.fantasy.FantasyCompetitionStanding;
import org.tpl.business.model.fantasy.FantasyLeagueStanding;

import java.util.List;

public interface FantasyLeagueStandingDao {

    void saveOrUpdateFantasyLeagueStanding(FantasyLeagueStanding fantasyLeagueStanding);

    FantasyLeagueStanding getFantasyLeagueStandingByIds(int fantasyTeamId, int fantasyLeagueId, int fantasyRoundId);

    List<FantasyLeagueStanding> getFantasyLeagueStandingByRoundAndLeague(int fantasyLeagueId, int fantasyRoundId);

    List<FantasyLeagueStanding> getFantasyLeagueStandingByTeamAndLeague(int fantasyLeagueId, int fantasyTeamId);

    List<FantasyLeagueStanding> getLastUpdatedFantasyLeagueStandingsByLeague(int fantasyLeagueId);

    int getLastUpdatedRoundIdForLeague(int fantasyLeagueId);
}
