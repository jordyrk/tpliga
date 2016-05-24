package org.tpl.business.service.fantasy;

import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyCupRound;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;


import java.util.List;

public interface FantasyCupService {

    void saveOrUpdateCup(FantasyCup fantasyCup);

    FantasyCup getFantasyCupById(int id);

    List<FantasyCup> getAllFantasyCups();

    List<FantasyCup> getAllFantasyCups(int fantasySeasonId);

    List<FantasyCup> getFantasyCupByTeamId(int teamId);

    List<FantasyCup> getFantasyCupForFantasyTeamInCurrentSeason(int teamId);

    void deleteFantasyCup(Integer fantasyCupId);

    boolean hasFixtures(int fantasyCupId);

    boolean hasFixtures(int fantasyCupId, int fantasyRoundId);

    List<FantasyCupRound> getFantasyCupRounds(int fantasyCupId);

    void createFixtureList(int fantasyCupId) throws FixtureListCreationException;

    void createFixtureList() throws FixtureListCreationException;
}
