package org.tpl.business.model.fantasy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FantasyCupRound {
    FantasyCup fantasyCup;
    FantasyRound fantasyRound;
    List<FantasyCupMatchAlias> fantasyMatchAliasList;
    List<FantasyCupMatch> fantasyMatchList;
    FantasyCupMatchType fantasyCupMatchType;

    public FantasyCup getFantasyCup() {
        return fantasyCup;
    }

    public void setFantasyCup(FantasyCup fantasyCup) {
        this.fantasyCup = fantasyCup;
    }

    public FantasyRound getFantasyRound() {
        return fantasyRound;
    }

    public void setFantasyRound(FantasyRound fantasyRound) {
        this.fantasyRound = fantasyRound;
    }

    public List<FantasyCupMatchAlias> getFantasyMatchAliasList() {
        return fantasyMatchAliasList;
    }

    public void setFantasyMatchAliasList(List<FantasyCupMatchAlias> fantasyMatchAliasList) {
        this.fantasyMatchAliasList = fantasyMatchAliasList;
    }

    public List<FantasyCupMatch> getFantasyMatchList() {
        return fantasyMatchList;
    }

    public void setFantasyMatchList(List<FantasyMatch> fantasyMatchList, FantasyCupMatchType fantasyCupMatchType) {
        this.fantasyMatchList = new ArrayList<FantasyCupMatch>();

        for(int i = 0; i < fantasyMatchList.size(); i++){
            this.fantasyMatchList.add(new FantasyCupMatch(fantasyMatchList.get(i),fantasyCupMatchType.getMatchPrefix() + (i +1)));
        }
    }

    public List<FantasyCupMatch> getFirstHalfMatches(){
        if(fantasyMatchList == null || fantasyMatchList.isEmpty()){
            return Collections.emptyList();
        }

        return fantasyMatchList.subList(0,fantasyMatchList.size()== 1 ? 1 :fantasyMatchList.size()/2 );
    }

    public List<FantasyCupMatch> getSecondHalfMatches(){
        if(fantasyMatchList == null || fantasyMatchList.size() <2 ){
            return Collections.emptyList();
        }

        return fantasyMatchList.subList(fantasyMatchList.size()/2,fantasyMatchList.size());
    }

    public List<FantasyCupMatchAlias> getFirstHalfMatcheAliases(){
        if(fantasyMatchAliasList == null || fantasyMatchAliasList.isEmpty()){
            return Collections.emptyList();
        }

        return fantasyMatchAliasList.subList(0,fantasyMatchAliasList.size()== 1 ? 1 :fantasyMatchAliasList.size()/2 );
    }

    public List<FantasyCupMatchAlias> getSecondHalfMatcheAliases(){
        if(fantasyMatchAliasList == null || fantasyMatchAliasList.size() < 2){
            return Collections.emptyList();
        }
        return fantasyMatchAliasList.subList(fantasyMatchAliasList.size()/2,fantasyMatchAliasList.size());
    }

    public FantasyCupMatchType getFantasyCupMatchType() {
        return fantasyCupMatchType;
    }

    public void setFantasyCupMatchType(FantasyCupMatchType fantasyCupMatchType) {
        this.fantasyCupMatchType = fantasyCupMatchType;
    }
}
