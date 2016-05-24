package org.tpl.integration.dao.fantasy;
/*
Created by jordyr, 03.02.11

*/

import org.tpl.business.model.fantasy.FantasyCupGroupStanding;
import org.tpl.business.model.fantasy.FantasyLeagueStanding;

import java.util.List;

public interface FantasyCupGroupStandingDao {

    void saveOrUpdateFantasyCupGroupStanding(FantasyCupGroupStanding fantasyCupGroupStanding);

    FantasyCupGroupStanding getFantasyCupGroupStandingByIds(int fantasyTeamId, int fantasyCupGroupId, int fantasyRoundId);

    List<FantasyCupGroupStanding> getFantasyCupGroupStandingByRoundAndCupGroup(int fantasyCupGroupId, int fantasyRoundId);

    List<FantasyCupGroupStanding> getFantasyCupGroupStandingByTeamAndCupGroup(int fantasyCupGroupId, int fantasyTeamId);

    List<FantasyCupGroupStanding> getLastUpdatedFantasyCupGroupStandingsByCupGroup(int fantasyCupGroupId);

    int getLastUpdatedRoundIdForCupGroup(int fantasyCupGroupId);

    int getHighestRoundForGroupId(int fantasyCupGroupId);
}
