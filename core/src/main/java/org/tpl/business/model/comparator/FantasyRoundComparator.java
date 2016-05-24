package org.tpl.business.model.comparator;

import org.tpl.business.model.fantasy.FantasyRound;

import java.util.Comparator;

public class FantasyRoundComparator implements Comparator<FantasyRound>{

    public int compare(FantasyRound o1, FantasyRound o2) {
        if(o1.getFantasyRoundId() == o2.getFantasyRoundId()) return 0;
        else if(o1.getFantasyRoundId() > o2.getFantasyRoundId()) return 1;
        else return -1;
    }
}
