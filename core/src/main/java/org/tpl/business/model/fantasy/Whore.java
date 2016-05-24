package org.tpl.business.model.fantasy;

/**
 * Created by jordyr on 5/4/14.
 */
public class Whore {

    private FantasyTeam fantasyTeam;
    private FantasyLeague fantasyLeague;
    private int leaguePosition;
    private int leaguePoints;
    private int competitionPosition;
    private int competitionPoints;
    private int competitionPositionWithinLeague;
    private int whoreIndex;

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public FantasyLeague getFantasyLeague() {
        return fantasyLeague;
    }

    public void setFantasyLeague(FantasyLeague fantasyLeague) {
        this.fantasyLeague = fantasyLeague;
    }

    public int getCompetitionPositionWithinLeague() {
        return competitionPositionWithinLeague;
    }

    public void setCompetitionPositionWithinLeague(int competitionPositionWithinLeague) {
        this.competitionPositionWithinLeague = competitionPositionWithinLeague;
    }

    public int getLeaguePosition() {
        return leaguePosition;
    }

    public void setLeaguePosition(int leaguePosition) {
        this.leaguePosition = leaguePosition;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public int getCompetitionPosition() {
        return competitionPosition;
    }

    public void setCompetitionPosition(int competitionPosition) {
        this.competitionPosition = competitionPosition;
    }

    public int getCompetitionPoints() {
        return competitionPoints;
    }

    public void setCompetitionPoints(int competitionPoints) {
        this.competitionPoints = competitionPoints;
    }

    public double getCostOfPoints() {
        if(leaguePoints == 0){
            return -1.0;
        }
        return competitionPoints/leaguePoints;
    }

    public int getWhoreIndex() {
        return competitionPositionWithinLeague - leaguePosition;
    }
}
