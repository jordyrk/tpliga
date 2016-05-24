package org.tpl.business.service;

import org.tpl.business.model.fantasy.FantasyRound;

import java.util.List;

public abstract class AbstractService {

     protected int[] createIntArray(List<FantasyRound> fantasyRoundList){
        int[] array= new int[fantasyRoundList.size()];
        for(int i = 0 ; i < fantasyRoundList.size(); i++){
            array[i] = fantasyRoundList.get(i).getFantasyRoundId();
        }
        return array;
    }
}
