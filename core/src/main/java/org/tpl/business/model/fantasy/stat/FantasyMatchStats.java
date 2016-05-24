package org.tpl.business.model.fantasy.stat;

import org.tpl.business.model.fantasy.FantasyMatch;

import java.util.HashMap;
import java.util.List;

public class FantasyMatchStats extends AbstractMatchStats{

    public FantasyMatchStats(List<FantasyMatch> fantasyMatchList) {
        map = new HashMap<String, Integer>();
        for(FantasyMatch fantasyMatch: fantasyMatchList){
            addStats(fantasyMatch);
        }
    }

    private void addStats(FantasyMatch fantasyMatch) {
        String score = convertScore(fantasyMatch.getHomegoals(), fantasyMatch.getAwaygoals());
        Integer integer = map.get(score);
        if(integer == null){
            integer = 0;
        }
        map.put(score,integer+1);
    }
}
