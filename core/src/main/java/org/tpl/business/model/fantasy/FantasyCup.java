package org.tpl.business.model.fantasy;

import org.tpl.business.model.comparator.FantasyRoundComparator;

import java.util.Collections;
import java.util.List;

public class FantasyCup extends AbstractFantasyCompetion{
    private FantasySeason fantasySeason;
    List<FantasyCupGroup> fantasyCupGroupList;
    private int numberOfQualifiedTeamsFromGroups;
    private int numberOfGroups;

    public FantasySeason getFantasySeason() {
        return fantasySeason;
    }

    public void setFantasySeason(FantasySeason fantasySeason) {
        this.fantasySeason = fantasySeason;
    }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    public List<FantasyCupGroup> getFantasyCupGroupList() {
        return fantasyCupGroupList;
    }

    public void setFantasyCupGroupList(List<FantasyCupGroup> fantasyCupGroupList) {
        this.fantasyCupGroupList = fantasyCupGroupList;
    }

    public int getNumberOfQualifiedTeamsFromGroups() {
        return numberOfQualifiedTeamsFromGroups;
    }

    public void setNumberOfQualifiedTeamsFromGroups(int numberOfQualifiedTeamsFromGroups) {
        this.numberOfQualifiedTeamsFromGroups = numberOfQualifiedTeamsFromGroups;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getNumberOfQualifiedTeams(int position){
        int placesLeft = numberOfQualifiedTeamsFromGroups - (position -1) * numberOfGroups;
        if(placesLeft > numberOfGroups) return numberOfGroups;
        else if( placesLeft < numberOfGroups && placesLeft > 0) return placesLeft;
        else return 0;
        
    }

    public FantasyRound getLastFantasyRound() {
        Collections.sort(fantasyRoundList, new FantasyRoundComparator());
        return fantasyRoundList.get(fantasyRoundList.size() -1);
    }

    public FantasyRound getFirstFantasyRound(){
        Collections.sort(fantasyRoundList, new FantasyRoundComparator());
        return fantasyRoundList.get(0);
    }
}
