package org.tpl.business.model.comparator;

import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasyCupGroupStanding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class FantasyCupGroupByIdComparatorTest {
    @Test
    public void testCompare() throws Exception {

        List<FantasyCupGroup> fantasyCupGroupList = new ArrayList<FantasyCupGroup>();
        fantasyCupGroupList.add(createFantasyCupGroup(12));
        fantasyCupGroupList.add(createFantasyCupGroup(14));
        fantasyCupGroupList.add(createFantasyCupGroup(9));
        fantasyCupGroupList.add(createFantasyCupGroup(1));
        Collections.sort(fantasyCupGroupList, new FantasyCupGroupByIdComparator());
        assertEquals(1,fantasyCupGroupList.get(0).getId());
        assertEquals(9,fantasyCupGroupList.get(1).getId());
        assertEquals(12,fantasyCupGroupList.get(2).getId());
        assertEquals(14,fantasyCupGroupList.get(3).getId());
    }

    private FantasyCupGroup createFantasyCupGroup(int id) {
        FantasyCupGroup group = new FantasyCupGroup();
        group.setId(id);
        return group;
    }

}
