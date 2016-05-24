package org.tpl.business.model.fantasy.util;
/*
Created by jordyr, 29.01.11

*/

import org.tpl.business.model.fantasy.FantasyRound;

import java.util.List;

public class FantasyRoundUtil {

    public String createIdRange(List<FantasyRound> fantasyRoundList, int currentRoundId){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(int i = 0 ; i < fantasyRoundList.size(); i++ ){
            if(currentRoundId < fantasyRoundList.get(i).getFantasyRoundId()){
                break;
            }
            if(i != 0) {
                sb.append(",");
            }
            sb.append(fantasyRoundList.get(i).getFantasyRoundId());
        }
        sb.append(")");
        return sb.toString();
    }

    public String createIdRange(List<FantasyRound> fantasyRoundList){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(int i = 0 ; i < fantasyRoundList.size(); i++ ){
            if(i != 0) {
                sb.append(",");
            }
            sb.append(fantasyRoundList.get(i).getFantasyRoundId());
        }
        sb.append(")");
        return sb.toString();
    }
}
