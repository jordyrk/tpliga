package org.tpl.business.model.fantasy.stat;

import org.tpl.business.model.Match;
import org.tpl.business.model.fantasy.FantasyMatch;

import java.util.HashMap;
import java.util.List;

public class MatchStats  extends AbstractMatchStats {

    public MatchStats(List<Match> matchList) {
        map = new HashMap<String, Integer>();
        for(Match match: matchList){
            addStats(match);
        }
    }

    private void addStats(Match match) {
        String score = convertScore(match.getHomeGoals(), match.getAwayGoals());
        Integer integer = map.get(score);
        if(integer == null){
            integer = 0;
        }
        map.put(score,integer+1);
    }

}
