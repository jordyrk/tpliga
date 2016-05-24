package org.tpl.business.model;
/*
Created by jordyr, 08.jan.2011

*/

public class AbstractPlayerStats {
    private int goals;
    private int assists;

    private int ownGoal;
    private int missedPenalty;
    private int savedPenalty;
    private int yellowCard;
    private boolean redCard;

    private boolean wholeGame;
    private boolean startedGame;
    private int playedMinutes;
    private int points;

    private int shots;
    private int shotsOnGoal;
    private int offsides;
    private int foulsCommited;
    private int foulsDrawn;
    private int saves;

    private boolean manOfTheMatch;
    private int eaSportsPPI;

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getOwnGoal() {
        return ownGoal;
    }

    public void setOwnGoal(int ownGoal) {
        this.ownGoal = ownGoal;
    }

    public int getMissedPenalty() {
        return missedPenalty;
    }

    public void setMissedPenalty(int missedPenalty) {
        this.missedPenalty = missedPenalty;
    }

    public int getSavedPenalty() {
        return savedPenalty;
    }

    public void setSavedPenalty(int savedPenalty) {
        this.savedPenalty = savedPenalty;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(int yellowCard) {
        this.yellowCard = yellowCard;
    }

    public boolean isRedCard() {
        return redCard;
    }

    public void setRedCard(boolean redCard) {
        this.redCard = redCard;
    }

    public boolean isWholeGame() {
        return wholeGame;
    }

    public void setWholeGame(boolean wholeGame) {
        this.wholeGame = wholeGame;
    }

    public boolean isStartedGame() {
        return startedGame;
    }

    public void setStartedGame(boolean startedGame) {
        this.startedGame = startedGame;
    }

    public int getPlayedMinutes() {
        return playedMinutes;
    }

    public void setPlayedMinutes(int playedMinutes) {
        this.playedMinutes = playedMinutes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getShotsOnGoal() {
        return shotsOnGoal;
    }

    public void setShotsOnGoal(int shotsOnGoal) {
        this.shotsOnGoal = shotsOnGoal;
    }

    public int getOffsides() {
        return offsides;
    }

    public void setOffsides(int offsides) {
        this.offsides = offsides;
    }

    public int getFoulsCommited() {
        return foulsCommited;
    }

    public void setFoulsCommited(int foulsCommited) {
        this.foulsCommited = foulsCommited;
    }

    public int getFoulsDrawn() {
        return foulsDrawn;
    }

    public void setFoulsDrawn(int foulsDrawn) {
        this.foulsDrawn = foulsDrawn;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public boolean isManOfTheMatch() {
        return manOfTheMatch;
    }

    public void setManOfTheMatch(boolean manOfTheMatch) {
        this.manOfTheMatch = manOfTheMatch;
    }

    public int getEaSportsPPI() {
        return eaSportsPPI;
    }

    public void setEaSportsPPI(int eaSportsPPI) {
        this.eaSportsPPI = eaSportsPPI;
    }
}
