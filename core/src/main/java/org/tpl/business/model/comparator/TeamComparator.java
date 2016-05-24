package org.tpl.business.model.comparator;
/*
Created by jordyr, 04.02.11

*/

import org.tpl.business.model.Team;
import org.tpl.business.model.fantasy.FantasyCompetitionStanding;

import java.util.Comparator;

public class TeamComparator implements Comparator<Team>{

    public int compare(Team o1, Team o2) {
        return ((String)o1.getShortName()).compareTo((String)o2.getShortName());
    }
}
