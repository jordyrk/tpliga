package org.tpl.business.model.comparator;

import org.tpl.business.model.fantasy.FantasyMatch;

import java.util.Comparator;

public class FantasyMatchByRoundIdReverseComparator implements Comparator<FantasyMatch> {

    public int compare(FantasyMatch o1, FantasyMatch o2) {
        return ((Integer)o2.getFantasyRound().getFantasyRoundId()).compareTo(o1.getFantasyRound().getFantasyRoundId());
    }
}
