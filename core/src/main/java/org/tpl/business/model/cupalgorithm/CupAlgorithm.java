package org.tpl.business.model.cupalgorithm;

import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyCupGroupStanding;
import org.tpl.business.model.fantasy.FantasyCupMatchAlias;
import org.tpl.business.model.fantasy.FantasyMatch;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;

import java.util.List;

public interface CupAlgorithm {

    public List<FantasyMatch> createFirstRound() throws FixtureListCreationException;
    
    List<FantasyCupMatchAlias> createFirstRoundAliases() throws FixtureListCreationException;
}
