package org.tpl.business.model.fantasy;

/*
Created by jordyr, 25.nov.2010

*/

public class FantasyTeam {
    private int teamId = -1;
    private String name;
    private String stadium;
    private String teamSpirit;
    private String supporterClub;
    private String latestNews;
    private int multiMediaImageId;
    private FantasyManager fantasyManager;
    private String imageUrl;


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getTeamSpirit() {
        return teamSpirit;
    }

    public void setTeamSpirit(String teamSpirit) {
        this.teamSpirit = teamSpirit;
    }

    public String getSupporterClub() {
        return supporterClub;
    }

    public void setSupporterClub(String supporterClub) {
        this.supporterClub = supporterClub;
    }

    public String getLatestNews() {
        return latestNews;
    }

    public void setLatestNews(String latestNews) {
        this.latestNews = latestNews;
    }

    public int getMultiMediaImageId() {
        return multiMediaImageId;
    }

    public void setMultiMediaImageId(int multiMediaImageId) {
        this.multiMediaImageId = multiMediaImageId;
    }

    public FantasyManager getFantasyManager() {
        return fantasyManager;
    }

    public void setFantasyManager(FantasyManager fantasyManager) {
        this.fantasyManager = fantasyManager;
    }

    public boolean isNew(){
        return teamId == -1;
    }

    @Override
    public String toString() {
        return teamId + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FantasyTeam that = (FantasyTeam) o;

        if (teamId != that.teamId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return teamId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
