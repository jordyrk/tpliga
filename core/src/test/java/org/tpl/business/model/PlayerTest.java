package org.tpl.business.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void shouldSplitLastNameCorrect() throws Exception {
        Player player = new Player();
        player.setFullName("John Doe");
        assertEquals("Doe", player.getLastName());
        assertEquals("John", player.getFirstName());

        player = new Player();
        player.setLastName("Doe");
        assertEquals("Doe", player.getLastName());
        assertNull(player.getFirstName());

        player = new Player();
        player.setLastName(null);
        assertNull(player.getLastName());
        assertNull(player.getFirstName());

    }
}