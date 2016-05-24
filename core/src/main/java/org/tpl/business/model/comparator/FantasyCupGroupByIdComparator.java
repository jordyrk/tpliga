package org.tpl.business.model.comparator;

import org.tpl.business.model.fantasy.FantasyCupGroup;


import java.util.Comparator;

public class FantasyCupGroupByIdComparator implements Comparator<FantasyCupGroup> {

    public int compare(FantasyCupGroup o1, FantasyCupGroup o2) {
        return ((Integer)o1.getId()).compareTo(o2.getId());
    }
}
