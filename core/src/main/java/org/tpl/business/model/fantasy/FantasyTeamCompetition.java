package org.tpl.business.model.fantasy;
/*
Created by jordyr, 02.des.2010
TODO: Should class be removed?

*/

public class FantasyTeamCompetition {
    FantasyCompetition fantasyCompetition;
    FantasyTeam fantasyTeam;
    int sumPoints;

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public int getSumPoints() {
        return sumPoints;
    }

    public void setSumPoints(int sumPoints) {
        this.sumPoints = sumPoints;
    }

    public FantasyCompetition getFantasyCompetition() {
        return fantasyCompetition;
    }

    public void setFantasyCompetition(FantasyCompetition fantasyCompetition) {
        this.fantasyCompetition = fantasyCompetition;
    }
}
