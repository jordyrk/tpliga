package org.tpl.business.model.cupalgorithm;


import org.junit.Test;
import org.tpl.business.model.comparator.FantasyCupGroupByIdComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.*;

public class TopBottomCupAlgorithmWith7GroupsTest extends AbstractTopBottomCupAlgorithmTest{


    @Test
    public void shouldFindFirstTopRatedTeam() throws Exception {
        List<FantasyCupGroup> fantasyCupGroups = createFantasyCupGroups();
        Collections.sort(fantasyCupGroups, new FantasyCupGroupByIdComparator());
        TopBottomCupAlgorithm algo = new TopBottomCupAlgorithm(createFantasyCup(), fantasyCupGroups, createStandingList());
        FantasyTeam fantasyTeam = algo.findTopRatedTeam();
        assertEquals(1,fantasyTeam.getTeamId());
        fantasyTeam = algo.findTopRatedTeam();
        assertEquals(5,fantasyTeam.getTeamId());
        fantasyTeam = algo.findTopRatedTeam();
        assertEquals(9,fantasyTeam.getTeamId());
        fantasyTeam = algo.findTopRatedTeam();
        assertEquals(13,fantasyTeam.getTeamId());
        fantasyTeam = algo.findTopRatedTeam();
        assertEquals(17,fantasyTeam.getTeamId());
        fantasyTeam = algo.findTopRatedTeam();
        assertEquals(21,fantasyTeam.getTeamId());
        fantasyTeam = algo.findTopRatedTeam();
        assertEquals(25,fantasyTeam.getTeamId());
        fantasyTeam = algo.findTopRatedTeam();
        assertEquals(2,fantasyTeam.getTeamId());

    }

    @Test
    public void shouldFindLastBottomRatedTeam() throws Exception {
        List<FantasyCupGroup> fantasyCupGroups = createFantasyCupGroups();
        Collections.sort(fantasyCupGroups, new FantasyCupGroupByIdComparator());
        TopBottomCupAlgorithm algo = new TopBottomCupAlgorithm(createFantasyCup(), fantasyCupGroups, createStandingList());
        algo.findLowestPositionToBeIncluded();
        FantasyTeam fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(27,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(23,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(26,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(22,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(18,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(14,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(10,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(6,fantasyTeam.getTeamId());
        fantasyTeam = algo.findBottomRatedTeam();
        assertEquals(2,fantasyTeam.getTeamId());

    }

    @Test
    public void testGetAllMatchesForFirstRound() throws Exception {
        List<FantasyMatch> firstRoundMatches = cupAlgorithm.createFirstRound();
        assertEquals(8,firstRoundMatches.size());
    }

    @Test
    public void testFindLowestPositionToBeIncluded() throws Exception {
        cupAlgorithm.findLowestPositionToBeIncluded();
        assertEquals(3,cupAlgorithm.currentBottomRatedPositionIndex);
        assertEquals(2,cupAlgorithm.currentBottomNumberOfTeamsFromPosition);
    }

      @Test
    public void testCreateAliases() throws Exception {
        List<FantasyCupMatchAlias> firstRoundAliases = cupAlgorithm.createFirstRoundAliases();
        assertEquals(8,firstRoundAliases.size());

    }

    protected List<FantasyCupGroupStanding> createStandingList(){
        List<FantasyCupGroupStanding> fantasyCupGroupStandings = new ArrayList<FantasyCupGroupStanding>();
        fantasyCupGroupStandings.add(createStanding(9,3,0,1,1));
        fantasyCupGroupStandings.add(createStanding(6,2,1,2,1));
        fantasyCupGroupStandings.add(createStanding(3,1,2,3,1));
        fantasyCupGroupStandings.add(createStanding(0,0,3,4,1));

        fantasyCupGroupStandings.add(createStanding(9,3,0,5,2));
        fantasyCupGroupStandings.add(createStanding(6,2,1,6,2));
        fantasyCupGroupStandings.add(createStanding(3,1,2,7,2));
        fantasyCupGroupStandings.add(createStanding(0,0,3,8,2));

        fantasyCupGroupStandings.add(createStanding(9,3,0,9,3));
        fantasyCupGroupStandings.add(createStanding(6,2,1,10,3));
        fantasyCupGroupStandings.add(createStanding(3,1,2,11,3));
        fantasyCupGroupStandings.add(createStanding(0,0,3,12,3));

        fantasyCupGroupStandings.add(createStanding(9,3,0,13,4));
        fantasyCupGroupStandings.add(createStanding(6,2,1,14,4));
        fantasyCupGroupStandings.add(createStanding(3,1,2,15,4));
        fantasyCupGroupStandings.add(createStanding(0,0,3,16,4));

        fantasyCupGroupStandings.add(createStanding(9,3,0,17,5));
        fantasyCupGroupStandings.add(createStanding(6,2,1,18,5));
        fantasyCupGroupStandings.add(createStanding(3,1,2,19,5));
        fantasyCupGroupStandings.add(createStanding(1,0,3,20,5));

        fantasyCupGroupStandings.add(createStanding(9,3,0,21,6));
        fantasyCupGroupStandings.add(createStanding(6,2,1,22,6));
        fantasyCupGroupStandings.add(createStanding(4,2,2,23,6));
        fantasyCupGroupStandings.add(createStanding(1,1,3,24,6));

        fantasyCupGroupStandings.add(createStanding(9,3,0,25,7));
        fantasyCupGroupStandings.add(createStanding(6,2,1,26,7));
        fantasyCupGroupStandings.add(createStanding(4,1,2,27,7));
        fantasyCupGroupStandings.add(createStanding(0,0,3,28,7));
        return fantasyCupGroupStandings;
    }

    protected List<FantasyCupGroup> createFantasyCupGroups() {
        List<FantasyCupGroup> fantasyCupGroups = new ArrayList<FantasyCupGroup>();
        fantasyCupGroups.add(createFantasyCupGroup(5));
        fantasyCupGroups.add(createFantasyCupGroup(3));
        fantasyCupGroups.add(createFantasyCupGroup(2));
        fantasyCupGroups.add(createFantasyCupGroup(4));
        fantasyCupGroups.add(createFantasyCupGroup(7));
        fantasyCupGroups.add(createFantasyCupGroup(6));
        fantasyCupGroups.add(createFantasyCupGroup(1));
        return fantasyCupGroups;
    }

    protected FantasyCup createFantasyCup() {
        FantasyCup fantasyCup = new FantasyCup();
        fantasyCup.setId(1);
        fantasyCup.setNumberOfGroups(7);
        fantasyCup.setNumberOfQualifiedTeamsFromGroups(16);

        return fantasyCup;
    }
}
