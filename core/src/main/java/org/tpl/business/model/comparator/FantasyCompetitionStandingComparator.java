package org.tpl.business.model.comparator;
/*
Created by jordyr, 04.02.11

*/

import org.tpl.business.model.fantasy.FantasyCompetitionStanding;

import java.util.Comparator;

public class FantasyCompetitionStandingComparator implements Comparator<FantasyCompetitionStanding>{

    public int compare(FantasyCompetitionStanding o1, FantasyCompetitionStanding o2) {
        return ((Integer)o2.getAccumulatedPoints()).compareTo((Integer)o1.getAccumulatedPoints());
    }
}
