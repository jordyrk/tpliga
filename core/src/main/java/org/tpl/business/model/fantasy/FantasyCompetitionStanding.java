package org.tpl.business.model.fantasy;
/*
Created by jordyr, 03.02.11

*/

public class FantasyCompetitionStanding {

    private FantasyTeam fantasyTeam;
    private FantasyCompetition fantasyCompetition;
    private FantasyRound fantasyRound;
    private int position;
    private int accumulatedPoints;
    private int points;
    private int lastRoundPosition;
    private int averagepoints;
    private int minimumpoints;
    private int maximumpoints;

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public FantasyCompetition getFantasyCompetition() {
        return fantasyCompetition;
    }

    public void setFantasyCompetition(FantasyCompetition fantasyCompetition) {
        this.fantasyCompetition = fantasyCompetition;
    }

    public FantasyRound getFantasyRound() {
        return fantasyRound;
    }

    public void setFantasyRound(FantasyRound fantasyRound) {
        this.fantasyRound = fantasyRound;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(int accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLastRoundPosition() {
        return lastRoundPosition;
    }

    public void setLastRoundPosition(int lastRoundPosition) {
        this.lastRoundPosition = lastRoundPosition;
    }

    public int getAveragepoints() {
        return averagepoints;
    }

    public void setAveragepoints(int averagepoints) {
        this.averagepoints = averagepoints;
    }

    public int getMinimumpoints() {
        return minimumpoints;
    }

    public void setMinimumpoints(int minimumpoints) {
        this.minimumpoints = minimumpoints;
    }

    public int getMaximumpoints() {
        return maximumpoints;
    }

    public void setMaximumpoints(int maximumpoints) {
        this.maximumpoints = maximumpoints;
    }
}
