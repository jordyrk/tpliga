package org.tpl.business.model.fantasy.stat;

import java.util.Map;

public class AbstractMatchStats {

    protected Map<String,Integer> map;

     public Map<String, Integer> getMap() {
        return map;
    }

    public int getNumberOfMatches(){
        return map.size();
    }


    protected String convertScore(int homeGoals, int awayGoals) {

        if(homeGoals > awayGoals){
            return homeGoals + "-" + awayGoals;
        }
        else{
            return awayGoals + "-" + homeGoals;
        }
    }
}
