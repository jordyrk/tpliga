package org.tpl.business.model.fantasy;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class FantasyCupMatchTypeTest {
    @Test
    public void testGetCorrectTypeWhenCurrentRoundIsZero() {
        FantasyCupMatchType finalType = FantasyCupMatchType.getFantasyCupMatchType(0, 1);
        assertEquals(FantasyCupMatchType.FINAL, finalType);

        FantasyCupMatchType semifinalType = FantasyCupMatchType.getFantasyCupMatchType(0, 2);
        assertEquals(FantasyCupMatchType.SEMIFINAL, semifinalType);

        FantasyCupMatchType thirtyTwoFinal = FantasyCupMatchType.getFantasyCupMatchType(0, 6);
        assertEquals(FantasyCupMatchType.THIRTYTWOFINAL, thirtyTwoFinal);
    }

    @Test
    public void testGetNullWhenCurrentRoundIsToLarge() throws Exception {
        FantasyCupMatchType finalType = FantasyCupMatchType.getFantasyCupMatchType(1, 1);
        assertNull(finalType);

        FantasyCupMatchType semifinalType = FantasyCupMatchType.getFantasyCupMatchType(2, 2);
        assertNull(semifinalType);

        FantasyCupMatchType thirtyTwoFinal = FantasyCupMatchType.getFantasyCupMatchType(6, 6);
        assertNull(thirtyTwoFinal);
    }

    @Test
    public void testGetCorrectTypeWhenCurrentRoundIsLargerThanZero() throws Exception {
        FantasyCupMatchType finalType = FantasyCupMatchType.getFantasyCupMatchType(1, 2);
        assertEquals(FantasyCupMatchType.FINAL, finalType);
        finalType = FantasyCupMatchType.getFantasyCupMatchType(2, 3);
        assertEquals(FantasyCupMatchType.FINAL, finalType);
        finalType = FantasyCupMatchType.getFantasyCupMatchType(3, 4);
        assertEquals(FantasyCupMatchType.FINAL, finalType);
        finalType = FantasyCupMatchType.getFantasyCupMatchType(4, 5);
        assertEquals(FantasyCupMatchType.FINAL, finalType);
        finalType = FantasyCupMatchType.getFantasyCupMatchType(5, 6);
        assertEquals(FantasyCupMatchType.FINAL, finalType);

        FantasyCupMatchType quarterFinal = FantasyCupMatchType.getFantasyCupMatchType(3, 6);
        assertEquals(FantasyCupMatchType.QUARTERFINAL, quarterFinal);

        FantasyCupMatchType semiFinal = FantasyCupMatchType.getFantasyCupMatchType(3, 5);
        assertEquals(FantasyCupMatchType.SEMIFINAL, semiFinal);


    }
}


