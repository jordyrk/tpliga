package org.tpl.business.model.fantasy;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FantasyRoundTest {

    @Test
    public void closedDateShouldBeExceeded() throws Exception {
        FantasyRound fantasyRound = new FantasyRound();
        fantasyRound.setCloseDate(new Date(1L));
        assertTrue(fantasyRound.isDateExceeded());
    }

    @Test
    public void closedDateShouldNotBeExceeded() throws Exception {
        FantasyRound fantasyRound = new FantasyRound();
        Date date = new Date();
        date.setTime(date.getTime() + 100000L);
        fantasyRound.setCloseDate(date);
        assertFalse(fantasyRound.isDateExceeded());
    }
}
