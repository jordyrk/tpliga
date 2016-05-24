package org.tpl.business.model.cupalgorithm;

import org.junit.Before;
import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasyCupGroupStanding;
import org.tpl.business.model.fantasy.FantasyTeam;

import java.util.List;

public abstract class AbstractTopBottomCupAlgorithmTest {
    protected TopBottomCupAlgorithm cupAlgorithm;
    @Before
    public void setUp() throws Exception {
        cupAlgorithm = new TopBottomCupAlgorithm(createFantasyCup(), createFantasyCupGroups(), createStandingList());
    }

    protected abstract List<FantasyCupGroupStanding> createStandingList();

    protected abstract List<FantasyCupGroup> createFantasyCupGroups();

    protected FantasyCupGroupStanding createStanding(int points, int goalsScored, int goalsAgainst, int teamId, int fantasyCupGroupId){
        FantasyCupGroupStanding fantasyCupGroupStanding = new FantasyCupGroupStanding();
        fantasyCupGroupStanding.setPoints(points);
        fantasyCupGroupStanding.setGoalsScored(goalsScored);
        fantasyCupGroupStanding.setGoalsAgainst(goalsAgainst);
        fantasyCupGroupStanding.setFantasyTeam(new FantasyTeam());
        fantasyCupGroupStanding.getFantasyTeam().setTeamId(teamId);
        fantasyCupGroupStanding.setFantasyCupGroup(createFantasyCupGroup(fantasyCupGroupId));
        return fantasyCupGroupStanding;
    }

    protected FantasyCupGroup createFantasyCupGroup(int id) {
        FantasyCupGroup group = new FantasyCupGroup();
        group.setId(id);
        return group;
    }

    protected abstract FantasyCup createFantasyCup();

}
