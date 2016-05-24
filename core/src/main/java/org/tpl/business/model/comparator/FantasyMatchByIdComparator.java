package org.tpl.business.model.comparator;

import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasyMatch;

import java.util.Comparator;

public class FantasyMatchByIdComparator implements Comparator<FantasyMatch> {

    public int compare(FantasyMatch o1, FantasyMatch o2) {
        return ((Integer)o1.getId()).compareTo(o2.getId());
    }
}
