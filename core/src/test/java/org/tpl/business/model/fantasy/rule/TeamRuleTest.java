package org.tpl.business.model.fantasy.rule;
/*
Created by jordyr, 22.01.11

*/

import org.junit.Test;
import org.tpl.business.model.PlayerPosition;

import static junit.framework.Assert.assertEquals;

public class TeamRuleTest {
    @Test
    public void testCalculatePointsForGameOutcome() throws Exception {

        TeamRule victoryRule = new TeamRule(TeamRuleType.VICTORY, 3,20,"Poeng for seier", PlayerRulePosition.ALL);
        assertEquals(victoryRule.getName(), 3,victoryRule.calculatePoints(21, PlayerPosition.DEFENDER));
        assertEquals(victoryRule.getName(), 3,victoryRule.calculatePoints(90, PlayerPosition.DEFENDER));
        assertEquals(victoryRule.getName(), 0,victoryRule.calculatePoints(9, PlayerPosition.DEFENDER) );
    }

    @Test
    public void testCalculateGoalsForTeam() throws Exception {
        TeamRule goalsRule = new TeamRule(TeamRuleType.GOALSCORED, 1, 1, "Lag scoret maal", PlayerRulePosition.ALL);
        assertEquals(goalsRule.getName(), 3, goalsRule.calculatePoints(3, PlayerPosition.DEFENDER) );
        assertEquals(goalsRule.getName(), 3, goalsRule.calculatePoints(3, PlayerPosition.STRIKER) );
        TeamRule lotsOfGoalsRule = new TeamRule(TeamRuleType.GOALSCORED, 2, 5, "Lag scoret over 5 maal", PlayerRulePosition.ALL);
        assertEquals(lotsOfGoalsRule.getName(),2 ,lotsOfGoalsRule.calculatePoints(5, PlayerPosition.STRIKER));

    }

    @Test
    public void testCalculatePointsForZeroGoalsConceded() throws Exception {
        TeamRule zeroGoalsRule = new TeamRule(TeamRuleType.ZEROGOALSCONCEDED, 2, 0, "Null baklengs for keeper", PlayerRulePosition.GOALKEEPER);
         assertEquals(zeroGoalsRule.getName(),2 ,zeroGoalsRule.calculatePoints(0, PlayerPosition.GOALKEEPER));
    }



}
