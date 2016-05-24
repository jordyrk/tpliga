package org.tpl.business.service.fantasy;

import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasyCupGroupStanding;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;

import java.util.List;

public interface FantasyCupGroupService {
    void saveOrUpdateCupGroup(FantasyCupGroup fantasyCupGroup);

    FantasyCupGroup getFantasyCupGroupById(int id);

    List<FantasyCupGroup> getAllFantasyCupGroups();

    List<FantasyCupGroup> getAllFantasyCupGroups(int fantasyCupId);

    List<FantasyCupGroup> getAllFantasyCupGroupsInSeason(int fantasySeasonId);

    List<FantasyCupGroup> getAllFantasyCupGroupsForFantasyRound(int fantasyRoundId);

    void deleteFantasyCupGroups(int fantasyCupId);

    boolean hasFixtures(int fantasyCupGroupId);

    void createFixtureList(int fantasyCupGroupId) throws FixtureListCreationException;

    void saveOrUpdateFantasyCupGroupStanding(FantasyCupGroupStanding fantasyCupGroupStanding);

    FantasyCupGroupStanding getFantasyCupGroupStandingByIds(int fantasyTeamId, int fantasyCupGroupId, int fantasyRoundId);

    List<FantasyCupGroupStanding> getFantasyCupGroupStandingByRoundAndCupGroup(int fantasyCupGroupId, int fantasyRoundId);

    List<FantasyCupGroupStanding> getFantasyCupGroupStandingByTeamAndCupGroup(int fantasyCupGroupId, int fantasyTeamId);

    List<FantasyCupGroupStanding> getLastUpdatedFantasyCupGroupStandingsByCupGroup(int fantasyCupGroupId);

    void updateCupGroupStandings(int fantasyRoundId);

    FantasyRound getLastUpdatedFantasyRound(int fantasyCupGroupId);

    boolean isFantasyCupGroupsFinished(List<FantasyCupGroup> fantasyCupGroups);

}
