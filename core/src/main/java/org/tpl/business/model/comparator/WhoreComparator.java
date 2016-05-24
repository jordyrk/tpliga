package org.tpl.business.model.comparator;

import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.Whore;

import java.util.Comparator;

public class WhoreComparator implements Comparator<Whore>{

    public int compare(Whore o1, Whore o2) {
        if(o1.getWhoreIndex() == o2.getWhoreIndex()) return 0;
        else if(o1.getWhoreIndex() > o2.getWhoreIndex()) return -1;
        else return 1;
    }
}
