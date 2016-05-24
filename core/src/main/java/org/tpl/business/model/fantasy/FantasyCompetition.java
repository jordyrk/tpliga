package org.tpl.business.model.fantasy;

import java.util.List;
/*
Created by jordyr, 25.nov.2010

*/

public class FantasyCompetition {
    private int fantasyCompetitionId = -1;
    //Counter
    private int numberOfTeams = 0;
    private String name;
    private FantasySeason fantasySeason;

    List<FantasyTeamCompetition> fantasyTeamCompetitionList;
    private boolean defaultCompetition;

    public int getFantasyCompetitionId() {
        return fantasyCompetitionId;
    }

    public void setFantasyCompetitionId(int fantasyCompetitionId) {
        this.fantasyCompetitionId = fantasyCompetitionId;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FantasySeason getFantasySeason() {
        return fantasySeason;
    }

    public void setFantasySeason(FantasySeason fantasySeason) {
        this.fantasySeason = fantasySeason;
    }

    public List<FantasyTeamCompetition> getFantasyTeamCompetitionList() {
        return fantasyTeamCompetitionList;
    }

    public void setFantasyTeamCompetitionList(List<FantasyTeamCompetition> fantasyTeamCompetitionList) {
        this.fantasyTeamCompetitionList = fantasyTeamCompetitionList;
    }

    public boolean isDefaultCompetition() {
        return defaultCompetition;
    }

    public void setDefaultCompetition(boolean defaultCompetition) {
        this.defaultCompetition = defaultCompetition;
    }

    public boolean isNew(){
        return fantasyCompetitionId == -1;
    }


}
