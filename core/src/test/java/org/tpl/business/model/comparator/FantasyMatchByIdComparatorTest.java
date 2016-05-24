package org.tpl.business.model.comparator;

import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyMatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class FantasyMatchByIdComparatorTest {
     @Test
    public void testCompare() throws Exception {

        List<FantasyMatch> fantasyMatchList= new ArrayList<FantasyMatch>();
        fantasyMatchList.add(createFantasyMatch(12));
        fantasyMatchList.add(createFantasyMatch(14));
        fantasyMatchList.add(createFantasyMatch(9));
        fantasyMatchList.add(createFantasyMatch(1));
        Collections.sort(fantasyMatchList, new FantasyMatchByIdComparator());
        assertEquals(1,fantasyMatchList.get(0).getId());
        assertEquals(9,fantasyMatchList.get(1).getId());
        assertEquals(12,fantasyMatchList.get(2).getId());
        assertEquals(14,fantasyMatchList.get(3).getId());
    }

    private FantasyMatch createFantasyMatch(int id) {
        FantasyMatch match = new FantasyMatch();
        match.setId(id);
        return match;
    }
}
