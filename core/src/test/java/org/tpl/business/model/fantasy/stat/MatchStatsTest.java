package org.tpl.business.model.fantasy.stat;

import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class MatchStatsTest {
    @Test
    public void testGetMap() throws Exception {
        FantasyMatchStats fantasyMatchStats = new FantasyMatchStats(createMatches());
        Map<String,Integer> map = fantasyMatchStats.getMap();
        Integer integer = map.get("2-1");
        assertEquals(3,integer.intValue());
        integer = map.get("2-2");
        assertEquals(1,integer.intValue());

    }

    private List<FantasyMatch> createMatches(){
        List<FantasyMatch> matches = new ArrayList<FantasyMatch>();
        matches.add(createFantasyMatch(2,1));
        matches.add(createFantasyMatch(1,2));
        matches.add(createFantasyMatch(2,1));
        matches.add(createFantasyMatch(2,2));
        return matches;


    }

    private FantasyMatch createFantasyMatch(int homeGoals, int awayGoals){
        FantasyMatch fantasyMatch = new FantasyMatch();
        fantasyMatch.setHomegoals(homeGoals);
        fantasyMatch.setAwaygoals(awayGoals);
        return fantasyMatch;
    }
}
