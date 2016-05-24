package org.tpl.business.model.comparator;

import org.tpl.business.model.Match;
import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {

    public int compare(Match o1, Match o2) {
        return ((Integer)o1.getMatchId()).compareTo((Integer)o2.getMatchId());
    }
}
