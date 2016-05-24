package org.tpl.business.model.fantasy;

import org.tpl.business.model.comparator.FantasyRoundComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractFantasyCompetion {
    private int id = -1;
    private String name;
    private int numberOfTeams;

    List<FantasyTeam> fantasyTeamList = new ArrayList<FantasyTeam>();
    List<FantasyRound> fantasyRoundList = new ArrayList<FantasyRound>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public List<FantasyTeam> getFantasyTeamList() {
        return fantasyTeamList;
    }

    public void setFantasyTeamList(List<FantasyTeam> fantasyTeamList) {
        this.fantasyTeamList = fantasyTeamList;
    }

    public List<FantasyRound> getFantasyRoundList() {
        return fantasyRoundList;
    }

    public void setFantasyRoundList(List<FantasyRound> fantasyRoundList) {
        this.fantasyRoundList = fantasyRoundList;
    }

    public boolean isNew(){
        return id == -1;
    }

    @Override
    public String toString() {
        return "" +id;
    }

    public FantasyRound getAnyFantasyRound(){
        if(fantasyRoundList != null && fantasyRoundList.size() > 0){
            return fantasyRoundList.get(0);
        }
        return null;
    }

    public int getHighestFantasyRoundId(){
        if(fantasyRoundList == null || fantasyRoundList.isEmpty()){
            return -1;
        }
        int highest = -1;
        for(FantasyRound fantasyRound: fantasyRoundList){
            if(fantasyRound.getFantasyRoundId() > highest){
                highest = fantasyRound.getFantasyRoundId();
            }
        }
        return highest;
    }

    public FantasyRound getNextRound(FantasyRound fantasyRound){
        if(fantasyRound == null || fantasyRound.isNew()){
            return null;
        }
        Collections.sort(fantasyRoundList, new FantasyRoundComparator());
        int index = fantasyRoundList.indexOf(fantasyRound);
        if(index != fantasyRoundList.size()-1){
            return fantasyRoundList.get(index+1);
        }
        return null;
    }

    public int getNumberOfRoundsBefore(FantasyRound fantasyRound){
        return fantasyRoundList.indexOf(fantasyRound)+1;
    }
}
