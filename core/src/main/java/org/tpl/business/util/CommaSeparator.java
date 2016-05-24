package org.tpl.business.util;

import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.fantasy.FantasyRound;

import java.util.List;

public class CommaSeparator {
    public static String createCommaSeparatedString(int[] array){
         String commaString = "";
        for(int i = 0; i < array.length; i++){
            if(i != 0){
                commaString +=",";
            }
            commaString += array[i];
        }
        return commaString;
    }

    public static String createCommaSeparatedString(List<Integer> list) {
        String text = "";
        for(int i = 0; i < list.size(); i++){
            if(i != 0 ){
                text += ",";
            }
            text += list.get(i);
        }
        return text;
    }

     public static String createCommaSeparatedStringFromRounds(List<FantasyRound> fantasyRoundList){
        int[] array= new int[fantasyRoundList.size()];
        for(int i = 0 ; i < fantasyRoundList.size(); i++){
            array[i] = fantasyRoundList.get(i).getFantasyRoundId();
        }
        return createCommaSeparatedString(array);
    }

    public static String createCommaSeparatedStringFromLeagueRounds(List<LeagueRound> rounds) {
        int[] array= new int[rounds.size()];
        for(int i = 0 ; i < rounds.size(); i++){
            array[i] = rounds.get(i).getLeagueRoundId();
        }
        return createCommaSeparatedString(array);
    }
}
