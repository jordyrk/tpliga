package org.tpl.business.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
/*
Created by jordyr, 26.okt.2010

*/

public class MatchTest extends TestCase{
    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Test
    public void testIsMatchPlayed(){
        Match match = new Match();
        match.setMatchDate(new Date());
        assertFalse("match.isMatchPlayed == false", match.isMatchPlayed());
    }

    public void testGetMatchResultForHomeTeam(){
        Match match = new Match();
        match.setHomeGoals(2);
        match.setAwayGoals(1);
        MatchResult matchResult = match.getMatchResultForHomeTeam();
        assertEquals("matchresult is victory", matchResult, MatchResult.VICTORY);

        match.setAwayGoals(2);
        matchResult = match.getMatchResultForHomeTeam();
        assertEquals("matchresult is draw", matchResult, MatchResult.DRAW);

        match.setAwayGoals(9);
        matchResult = match.getMatchResultForHomeTeam();
        assertEquals("matchresult is loss", matchResult, MatchResult.LOSS);
    }
    public void testGetMatchResultForAwayTeam(){
        Match match = new Match();
        match.setHomeGoals(2);
        match.setAwayGoals(2);
        MatchResult matchResult = match.getMatchResultForAwayTeam();
        assertEquals("matchresult is draw", matchResult, MatchResult.DRAW);

        match.setHomeGoals(1);
        matchResult = match.getMatchResultForAwayTeam();
        assertEquals("matchresult is victory", matchResult, MatchResult.VICTORY);

        match.setHomeGoals(3);
        matchResult = match.getMatchResultForAwayTeam();
        assertEquals("matchresult is victory", matchResult, MatchResult.LOSS);
    }

    
}
