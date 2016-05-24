package org.tpl.business.model.comparator;

import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyLeagueStanding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class FantasyLeagueStandingComparatorTest {

    @Test
    public void shouldOrderListCorrectlyByPoints() throws Exception {
        List<FantasyLeagueStanding> fantasyLeagueStandingList = createStandingsList();
        Collections.sort(fantasyLeagueStandingList,new FantasyLeagueStandingComparator());
        assertEquals(12,fantasyLeagueStandingList.get(0).getPoints());
        assertEquals(10,fantasyLeagueStandingList.get(1).getPoints());

    }

@Test
    public void shouldOrderListCorrectlyByGoals() throws Exception {
    List<FantasyLeagueStanding> fantasyLeagueStandingList = createStandingsList();
        Collections.sort(fantasyLeagueStandingList,new FantasyLeagueStandingComparator());
        assertEquals(2,fantasyLeagueStandingList.get(1).getGoalsScored());
        assertEquals(1,fantasyLeagueStandingList.get(2).getGoalsScored());

    }

    private List<FantasyLeagueStanding> createStandingsList() {
        FantasyLeagueStanding fantasyLeagueStanding1 = new FantasyLeagueStanding();
        fantasyLeagueStanding1.setPoints(10);
        fantasyLeagueStanding1.setGoalsScored(2);

        FantasyLeagueStanding fantasyLeagueStanding2 = new FantasyLeagueStanding();
        fantasyLeagueStanding2.setPoints(10);
        fantasyLeagueStanding2.setGoalsScored(1);


        FantasyLeagueStanding fantasyLeagueStanding3 = new FantasyLeagueStanding();
        fantasyLeagueStanding3.setPoints(9);
        fantasyLeagueStanding3.setGoalsScored(0);

        FantasyLeagueStanding fantasyLeagueStanding4 = new FantasyLeagueStanding();
        fantasyLeagueStanding4.setPoints(12);
        fantasyLeagueStanding4.setGoalsScored(9);


        List<FantasyLeagueStanding> fantasyLeagueStandingList = new ArrayList<FantasyLeagueStanding>();
        fantasyLeagueStandingList.add(fantasyLeagueStanding1);
        fantasyLeagueStandingList.add(fantasyLeagueStanding2);
        fantasyLeagueStandingList.add(fantasyLeagueStanding3);
        fantasyLeagueStandingList.add(fantasyLeagueStanding4);
        return fantasyLeagueStandingList;
    }

}
