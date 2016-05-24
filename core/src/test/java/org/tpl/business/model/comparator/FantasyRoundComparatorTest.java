package org.tpl.business.model.comparator;

import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyRound;

import static junit.framework.Assert.assertEquals;

public class FantasyRoundComparatorTest {
    @Test
    public void testLessThan(){
        FantasyRound fR1 = createFantasyRound(1);
        FantasyRound fR2 = createFantasyRound(2);
        assertEquals(new FantasyRoundComparator().compare(fR1, fR2),-1);
    }

    @Test
    public void testEqualsThan(){
        FantasyRound fR1 = createFantasyRound(1);
        FantasyRound fR2 = createFantasyRound(1);
        assertEquals(new FantasyRoundComparator().compare(fR1, fR2),0);
    }

    @Test
    public void testGreaterThan(){
        FantasyRound fR1 = createFantasyRound(3);
        FantasyRound fR2 = createFantasyRound(2);
        assertEquals(new FantasyRoundComparator().compare(fR1, fR2),1);
    }

    private FantasyRound createFantasyRound(int id){
        FantasyRound fantasyRound = new FantasyRound();
        fantasyRound.setFantasyRoundId(id);
        return fantasyRound;
    }
}
