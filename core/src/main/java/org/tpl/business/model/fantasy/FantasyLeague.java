package org.tpl.business.model.fantasy;

import java.util.ArrayList;
import java.util.List;

public class FantasyLeague extends AbstractFantasyCompetion{
    private boolean homeAndAwayMatches;
    private FantasySeason fantasySeason;
    private int numberOfTopTeams;
    private int numberOfSecondTopTeams;
    private int numberOfBottomTeams;
    private int numberOfSecondBotttomTeams;
    private String styletheme;


    public boolean isHomeAndAwayMatches() {
        return homeAndAwayMatches;
    }

    public void setHomeAndAwayMatches(boolean homeAndAwayMatches) {
        this.homeAndAwayMatches = homeAndAwayMatches;
    }

    public FantasySeason getFantasySeason() {
        return fantasySeason;
    }

    public void setFantasySeason(FantasySeason fantasySeason) {
        this.fantasySeason = fantasySeason;
    }

    public int getNumberOfTopTeams() {
        return numberOfTopTeams;
    }

    public void setNumberOfTopTeams(int numberOfTopTeams) {
        this.numberOfTopTeams = numberOfTopTeams;
    }

    public int getNumberOfSecondTopTeams() {
        return numberOfSecondTopTeams;
    }

    public void setNumberOfSecondTopTeams(int numberOfSecondTopTeams) {
        this.numberOfSecondTopTeams = numberOfSecondTopTeams;
    }

    public int getNumberOfBottomTeams() {
        return numberOfBottomTeams;
    }

    public void setNumberOfBottomTeams(int numberOfBottomTeams) {
        this.numberOfBottomTeams = numberOfBottomTeams;
    }

    public int getNumberOfSecondBotttomTeams() {
        return numberOfSecondBotttomTeams;
    }

    public void setNumberOfSecondBotttomTeams(int numberOfSecondBotttomTeams) {
        this.numberOfSecondBotttomTeams = numberOfSecondBotttomTeams;
    }

    public String getStyletheme() {
        return styletheme;
    }

    public void setStyletheme(String styletheme) {
        this.styletheme = styletheme;
    }
}
