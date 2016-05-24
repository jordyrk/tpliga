package org.tpl.business.model.fantasy.rule;
/*
Created by jordyr, 22.01.11

*/

import org.junit.Test;
import org.tpl.business.model.PlayerPosition;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PlayerRuleTest {
    @Test
    public void testCalculatePoints() throws Exception {
        PlayerRule playedMinutesRule = new PlayerRule(PlayerRuleType.PLAYEDMINUTES, 1, 90, "Spilte hele kampen", PlayerRulePosition.ALL);
        PlayerRule hattrickRule = new PlayerRule(PlayerRuleType.GOAL, 10, 3, "Hattrick", PlayerRulePosition.ALL);
        PlayerRule assistRule = new PlayerRule(PlayerRuleType.ASSIST, 2, 1, "Assist", PlayerRulePosition.DEFENDER);

        assertEquals(playedMinutesRule.getName(), playedMinutesRule.calculatePoints(60), 0);
        assertEquals(playedMinutesRule.getName(), playedMinutesRule.calculatePoints(90), 1);
        assertEquals(hattrickRule.getName(), hattrickRule.calculatePoints(2), 0);
        assertEquals(hattrickRule.getName(), hattrickRule.calculatePoints(3), 10);
        assertEquals(hattrickRule.getName(), hattrickRule.calculatePoints(6), 20);
        assertEquals(assistRule.getName(), assistRule.calculatePoints(3), 6);
    }
}
