package org.tpl.business.model.comparator;
/*
Created by jordyr, 04.02.11

*/

import org.tpl.business.model.fantasy.FantasyCompetitionStanding;
import org.tpl.business.model.fantasy.FantasyLeagueStanding;

import java.util.Comparator;

public class FantasyLeagueStandingComparator implements Comparator<FantasyLeagueStanding>{

    public int compare(FantasyLeagueStanding o1, FantasyLeagueStanding o2) {
        if(o1.getPoints() < o2.getPoints())return 1;
        else if(o1.getPoints() > o2.getPoints()) return -1;
        else if( (o1.getGoalsScored() - o1.getGoalsAgainst()) < (o2.getGoalsScored() - o2.getGoalsAgainst()) ) return 1;
        else if( (o1.getGoalsScored() - o1.getGoalsAgainst()) > (o2.getGoalsScored() - o2.getGoalsAgainst()) ) return -1;
        else return 0;
    }
}
