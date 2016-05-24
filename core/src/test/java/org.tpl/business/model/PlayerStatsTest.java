package org.tpl.business.model;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PlayerStatsTest {
    @Test
    public void testEquals() throws Exception {
        PlayerStats ps1 = createPlayerStats(1);
        PlayerStats psDuplicate = createPlayerStats(1);
        PlayerStats ps2 = createPlayerStats(2);
        assertTrue(ps1.equals(psDuplicate));
        assertFalse(ps1.equals(ps2));
    }
    private PlayerStats createPlayerStats(int playerId){
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(new Player());
        playerStats.getPlayer().setPlayerId(playerId);
        return playerStats;
    }
}
