package org.tpl.business.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 20:26:41
 */
public class Season {
    private int seasonId = -1;
    private League league = new League();
    private int numberOfTeams;
    private List<Team> teams = new ArrayList<Team>();

    private Date startDate;
    private Date endDate;
    private boolean defaultSeason;

    private DateFormat dateformat = new SimpleDateFormat("yyyy");


    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public boolean isDefaultSeason() {
        return defaultSeason;
    }

    public void setDefaultSeason(boolean defaultSeason) {
        this.defaultSeason = defaultSeason;
    }
    public boolean isNew(){
        return seasonId == -1;
    }

    public String getSeasonLabel(){
        return dateformat.format(startDate) + " - " + dateformat.format(endDate) + ", League: " + league.getShortName();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Season season = (Season) o;

        if (numberOfTeams != season.numberOfTeams) return false;
        if (!endDate.equals(season.endDate)) return false;
        if (league != null ? !league.equals(season.league) : season.league != null) return false;
        if (!startDate.equals(season.startDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = league != null ? league.hashCode() : 0;
        result = 31 * result + numberOfTeams;
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return seasonId + "";
    }
}
