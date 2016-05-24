package org.tpl.business.model.fantasy;
/*
Created by jordyr, 25.nov.2010

*/

import org.tpl.business.model.Season;

public class FantasySeason {
    private int fantasySeasonId = -1;
    private Season season;
    private String name;
    private FantasyCompetition defaultFantasyCompetition;
    private FantasyRound currentRound;
    private boolean activeSeason;
    private int maxTeamPrice;
    private int startingNumberOfTransfers;
    private int numberOfTransfersPerRound;
    private boolean registrationActive;

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFantasySeasonId() {
        return fantasySeasonId;
    }

    public void setFantasySeasonId(int fantasySeasonId) {
        this.fantasySeasonId = fantasySeasonId;
    }

     public boolean isNew(){
        return fantasySeasonId == -1;
    }

    public FantasyCompetition getDefaultFantasyCompetition() {
        return defaultFantasyCompetition;
    }

    public void setDefaultFantasyCompetition(FantasyCompetition defaultFantasyCompetition) {
        this.defaultFantasyCompetition = defaultFantasyCompetition;
    }

    public boolean isActiveSeason() {
        return activeSeason;
    }

    public void setActiveSeason(boolean activeSeason) {
        this.activeSeason = activeSeason;
    }

    public FantasyRound getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(FantasyRound currentRound) {
        this.currentRound = currentRound;
    }

    public int getMaxTeamPrice() {
        return maxTeamPrice;
    }

    public void setMaxTeamPrice(int maxTeamPrice) {
        this.maxTeamPrice = maxTeamPrice;
    }

    public int getStartingNumberOfTransfers() {
        return startingNumberOfTransfers;
    }

    public void setStartingNumberOfTransfers(int startingNumberOfTransfers) {
        this.startingNumberOfTransfers = startingNumberOfTransfers;
    }

    public int getNumberOfTransfersPerRound() {
        return numberOfTransfersPerRound;
    }

    public void setNumberOfTransfersPerRound(int numberOfTransfersPerRound) {
        this.numberOfTransfersPerRound = numberOfTransfersPerRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FantasySeason that = (FantasySeason) o;

        if (fantasySeasonId != that.fantasySeasonId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fantasySeasonId;
    }

    public boolean isRegistrationActive() {
        return registrationActive;
    }

    public void setRegistrationActive(boolean registrationActive) {
        this.registrationActive = registrationActive;
    }
}
