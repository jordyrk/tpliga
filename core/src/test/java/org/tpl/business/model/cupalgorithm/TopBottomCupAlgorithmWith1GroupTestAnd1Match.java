package org.tpl.business.model.cupalgorithm;


import org.junit.Test;
import org.tpl.business.model.comparator.FantasyCupGroupByIdComparator;
import org.tpl.business.model.fantasy.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TopBottomCupAlgorithmWith1GroupTestAnd1Match extends AbstractTopBottomCupAlgorithmTest{


    @Test
    public void shouldFindFirstTopRatedTeam() throws Exception {
        List<FantasyCupGroup> fantasyCupGroups = createFantasyCupGroups();
        Collections.sort(fantasyCupGroups, new FantasyCupGroupByIdComparator());
        TopBottomCupAlgorithm algo = new TopBottomCupAlgorithm(createFantasyCup(), fantasyCupGroups, createStandingList());
        FantasyTeam fantasyTeam = algo.findTopRatedTeam();
        assertEquals(1,fantasyTeam.getTeamId());
    }

    @Test
    public void shouldFindLastBottomRatedTeam() throws Exception {
        List<FantasyCupGroup> fantasyCupGroups = createFantasyCupGroups();
        Collections.sort(fantasyCupGroups, new FantasyCupGroupByIdComparator());
        TopBottomCupAlgorithm algo = new TopBottomCupAlgorithm(createFantasyCup(), fantasyCupGroups, createStandingList());
        algo.findLowestPositionToBeIncluded();
        FantasyTeam fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(2,fantasyTeam.getTeamId());
    }

    @Test
    public void testGetAllMatchesForFirstRound() throws Exception {
        List<FantasyMatch> firstRoundMatches = cupAlgorithm.createFirstRound();
        assertEquals(1,firstRoundMatches.size());
    }

    @Test
    public void testFindLowestPositionToBeIncluded() throws Exception {
        cupAlgorithm.findLowestPositionToBeIncluded();
        assertEquals(2,cupAlgorithm.currentBottomRatedPositionIndex);
        assertEquals(1,cupAlgorithm.currentBottomNumberOfTeamsFromPosition);
    }

    @Test
    public void testCreateAliases() throws Exception {
        List<FantasyCupMatchAlias> firstRoundAliases = cupAlgorithm.createFirstRoundAliases();
        assertEquals(1,firstRoundAliases.size());

    }

    protected List<FantasyCupGroupStanding> createStandingList(){
        List<FantasyCupGroupStanding> fantasyCupGroupStandings = new ArrayList<FantasyCupGroupStanding>();
        fantasyCupGroupStandings.add(createStanding(9,3,0,1,1));
        fantasyCupGroupStandings.add(createStanding(6,2,1,2,1));
        fantasyCupGroupStandings.add(createStanding(3,1,2,3,1));
        fantasyCupGroupStandings.add(createStanding(0,0,3,4,1));
        return fantasyCupGroupStandings;
    }

    protected List<FantasyCupGroup> createFantasyCupGroups() {
        List<FantasyCupGroup> fantasyCupGroups = new ArrayList<FantasyCupGroup>();

        fantasyCupGroups.add(createFantasyCupGroup(1));
        return fantasyCupGroups;
    }

    protected FantasyCup createFantasyCup() {
        FantasyCup fantasyCup = new FantasyCup();
        fantasyCup.setId(1);
        fantasyCup.setNumberOfGroups(1);
        fantasyCup.setNumberOfQualifiedTeamsFromGroups(2);

        return fantasyCup;
    }
}
