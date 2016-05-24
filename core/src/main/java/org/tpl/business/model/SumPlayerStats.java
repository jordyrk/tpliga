package org.tpl.business.model;

import java.util.List;
/*
Created by jordyr, 08.jan.2011

*/

public class SumPlayerStats extends AbstractPlayerStats{

    private int sumRedCard;
    private int sumWholeGame;
    private int sumStartedGame;
    private int playedMatches;
    private int manOfTheMatch;

    public void addGoals(int goals) {
        super.setGoals(super.getGoals() + goals);
    }

    public void addAssists(int assists) {
        super.setAssists(super.getAssists() + assists);
    }

    public void addOwnGoal(int ownGoal) {
        super.setOwnGoal(super.getOwnGoal() + ownGoal);
    }

    public void addMissedPenalty(int missedPenalty) {
        super.setMissedPenalty(super.getMissedPenalty() + missedPenalty);
    }

    public void addSavedPenalty(int savedPenalty) {
        super.setSavedPenalty(super.getSavedPenalty() + savedPenalty);
    }

    public void addYellowCard(int yellowCard) {
        super.setYellowCard(super.getYellowCard() + yellowCard);
    }

    public void addRedCard(int redCard) {
        this.sumRedCard += redCard;
    }

    public void addWholeGame(int wholeGame) {
        this.sumWholeGame += wholeGame;
    }

    public void addStartedGame(int startedGame) {
        this.sumStartedGame += startedGame;
    }

    public void addPlayedMinutes(int playedMinutes) {
        super.setPlayedMinutes(super.getPlayedMinutes() + playedMinutes);
    }

    public void addPoints(int points) {
        super.setPoints(super.getPoints() + points);
    }

    public void addPlayedMatches(int playedMatches) {
        setPlayedMatches(getPlayedMatches() + playedMatches);
    }

    public void addManOfTheMatch(int numberOfManOfTheMatch){
        manOfTheMatch += numberOfManOfTheMatch;
    }

    public int  getSumManOfTheMatch(){
        return manOfTheMatch;
    }

    public void  addEaSportsPPI(int eaSportsPPI){
        setEaSportsPPI(getEaSportsPPI()+eaSportsPPI);
    }

    public int getSumRedCard() {
        return sumRedCard;
    }

    public void setSumRedCard(int sumRedCard) {
        this.sumRedCard = sumRedCard;
    }

    public int getSumWholeGame() {
        return sumWholeGame;
    }

    public void setSumWholeGame(int sumWholeGame) {
        this.sumWholeGame = sumWholeGame;
    }

    public int getSumStartedGame() {
        return sumStartedGame;
    }

    public int getPlayedMatches() {
        return playedMatches;
    }

    public void setPlayedMatches(int playedMatches) {
        this.playedMatches = playedMatches;
    }




    public void add(List<PlayerStats> playerStatsList){
        for(PlayerStats playerStats: playerStatsList){
            addGoals(playerStats.getGoals());
            addAssists(playerStats.getAssists());
            addOwnGoal(playerStats.getOwnGoal());
            addMissedPenalty(playerStats.getMissedPenalty());
            addSavedPenalty(playerStats.getSavedPenalty());
            addYellowCard(playerStats.getYellowCard());
            addRedCard(playerStats.isRedCard() ? 1 : 0);
            addWholeGame(playerStats.isWholeGame() ? 1 : 0);
            addStartedGame(playerStats.isStartedGame() ? 1 : 0);
            addPlayedMinutes(playerStats.getPlayedMinutes());
            addPoints(playerStats.getPoints());
            addEaSportsPPI(playerStats.getEaSportsPPI());
            if(playerStats.isManOfTheMatch()){
                addManOfTheMatch(1);
            }
            if(playerStats.isStartedGame() || playerStats.isWholeGame() || playerStats.getPlayedMinutes() > 0){
                addPlayedMatches(1);
            }
        }
    }
}
