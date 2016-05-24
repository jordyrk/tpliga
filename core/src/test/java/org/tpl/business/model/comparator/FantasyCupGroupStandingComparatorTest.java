package org.tpl.business.model.comparator;

import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCupGroupStanding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FantasyCupGroupStandingComparatorTest {
    @Test
    public void testCompare() throws Exception {
        List<FantasyCupGroupStanding> fantasyCupGroupStandings = new ArrayList<FantasyCupGroupStanding>();
        fantasyCupGroupStandings.add(createStanding(4,2,0));
        fantasyCupGroupStandings.add(createStanding(2,1,1));
        fantasyCupGroupStandings.add(createStanding(1,1,3));
        Collections.sort(fantasyCupGroupStandings, new FantasyCupGroupStandingComparator());
    }

    private FantasyCupGroupStanding createStanding(int points, int goalsScored, int goalsAgainst){
        FantasyCupGroupStanding fantasyCupGroupStanding = new FantasyCupGroupStanding();
        fantasyCupGroupStanding.setPoints(points);
        fantasyCupGroupStanding.setGoalsScored(goalsScored);
        fantasyCupGroupStanding.setGoalsAgainst(goalsAgainst);
        return fantasyCupGroupStanding;
    }
}
