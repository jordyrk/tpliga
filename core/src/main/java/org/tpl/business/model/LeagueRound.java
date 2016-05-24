package org.tpl.business.model;
/*
Created by jordyr, 17.okt.2010

*/

public class LeagueRound {

    private int leagueRoundId = -1;
    private Season season;
    private int round;

    public int getLeagueRoundId() {
        return leagueRoundId;
    }

    public void setLeagueRoundId(int leagueRoundId) {
        this.leagueRoundId = leagueRoundId;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isNew(){
           if(leagueRoundId == -1)return true;
           else return false;
       }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeagueRound that = (LeagueRound) o;

        if (round != that.round) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return round;
    }
}
