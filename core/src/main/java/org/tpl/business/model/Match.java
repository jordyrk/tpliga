package org.tpl.business.model;

import java.util.Comparator;
import java.util.Date;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 20:41:15
 */
public class Match implements Comparator {
    private int matchId = -1;
    private int externalId;
    private Team  homeTeam;
    private Team awayTeam;
    private LeagueRound leagueRound;
    private Date matchDate;

    private int homeGoals;
	private int awayGoals;
	private int homeShots;
	private int awayShots;

    private int homeShotsOnTarget;
	private int awayShotsOnTarget;
	private int homeFouls;
	private int awayFouls;
	private int homeOffside;

    private int awayOffside;
	private int homePossession;
	private int awayPossession;
	private int homeYellow;
	private int awayYellow;

    private int homeRed;
	private int awayRed;
	private int homeSaves;
	private int awaySaves;

    private String matchUrl;
    private String soccerNetId;
    private int fantasyPremierLeagueId;
    private boolean playerStatsUpdated;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
    
    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getHomeShots() {
        return homeShots;
    }

    public void setHomeShots(int homeShots) {
        this.homeShots = homeShots;
    }

    public int getAwayShots() {
        return awayShots;
    }

    public void setAwayShots(int awayShots) {
        this.awayShots = awayShots;
    }

    public int getHomeShotsOnTarget() {
        return homeShotsOnTarget;
    }

    public void setHomeShotsOnTarget(int homeShotsOnTarget) {
        this.homeShotsOnTarget = homeShotsOnTarget;
    }

    public int getAwayShotsOnTarget() {
        return awayShotsOnTarget;
    }

    public void setAwayShotsOnTarget(int awayShotsOnTarget) {
        this.awayShotsOnTarget = awayShotsOnTarget;
    }

    public int getHomeFouls() {
        return homeFouls;
    }

    public void setHomeFouls(int homeFouls) {
        this.homeFouls = homeFouls;
    }

    public int getAwayFouls() {
        return awayFouls;
    }

    public void setAwayFouls(int awayFouls) {
        this.awayFouls = awayFouls;
    }

    public int getHomeOffside() {
        return homeOffside;
    }

    public void setHomeOffside(int homeOffside) {
        this.homeOffside = homeOffside;
    }

    public int getAwayOffside() {
        return awayOffside;
    }

    public void setAwayOffside(int awayOffside) {
        this.awayOffside = awayOffside;
    }

    public int getHomePossession() {
        return homePossession;
    }

    public void setHomePossession(int homePossession) {
        this.homePossession = homePossession;
    }

    public int getAwayPossession() {
        return awayPossession;
    }

    public void setAwayPossession(int awayPossession) {
        this.awayPossession = awayPossession;
    }

    public int getHomeYellow() {
        return homeYellow;
    }

    public void setHomeYellow(int homeYellow) {
        this.homeYellow = homeYellow;
    }

    public int getAwayYellow() {
        return awayYellow;
    }

    public void setAwayYellow(int awayYellow) {
        this.awayYellow = awayYellow;
    }

    public int getHomeRed() {
        return homeRed;
    }

    public void setHomeRed(int homeRed) {
        this.homeRed = homeRed;
    }

    public int getAwayRed() {
        return awayRed;
    }

    public void setAwayRed(int awayRed) {
        this.awayRed = awayRed;
    }

    public int getHomeSaves() {
        return homeSaves;
    }

    public void setHomeSaves(int homeSaves) {
        this.homeSaves = homeSaves;
    }

    public int getAwaySaves() {
        return awaySaves;
    }

    public void setAwaySaves(int awaySaves) {
        this.awaySaves = awaySaves;
    }

    public boolean isNew(){
        if(matchId == -1)return true;
        else return false;
    }

    public String getMatchUrl() {
        return matchUrl;
    }

    public void setMatchUrl(String matchUrl) {
        this.matchUrl = matchUrl;
    }

    public LeagueRound getLeagueRound() {
        return leagueRound;
    }

    public void setLeagueRound(LeagueRound leagueRound) {
        this.leagueRound = leagueRound;
    }

    public int compare(Object o1, Object o2) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isMatchPlayed(){
        if(matchDate == null) return false;
        Date now = new Date();
        if(now.getTime() > (matchDate.getTime() + 3600000*3)){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (awayGoals != match.awayGoals) return false;
        if (externalId != match.externalId) return false;
        if (homeGoals != match.homeGoals) return false;
        if (matchDate != null ? !matchDate.equals(match.matchDate) : match.matchDate != null) return false;
        if (matchUrl != null ? !matchUrl.equals(match.matchUrl) : match.matchUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = externalId;
        result = 31 * result + (matchDate != null ? matchDate.hashCode() : 0);
        result = 31 * result + homeGoals;
        result = 31 * result + awayGoals;
        result = 31 * result + (matchUrl != null ? matchUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return matchId +"";
    }

    public MatchResult getMatchResultForHomeTeam(){
        if(homeGoals < awayGoals) return MatchResult.LOSS;
        else if (homeGoals == awayGoals) return MatchResult.DRAW;
        else return MatchResult.VICTORY;
    }
    public MatchResult getMatchResultForAwayTeam(){
        if(awayGoals < homeGoals) return MatchResult.LOSS;
        else if (homeGoals == awayGoals) return MatchResult.DRAW;
        else return MatchResult.VICTORY;
    }

    public void setSoccerNetId(String soccerNetId) {
        this.soccerNetId = soccerNetId;
    }

    public String getSoccerNetId() {
        return soccerNetId;
    }

    public int getFantasyPremierLeagueId() {
        return fantasyPremierLeagueId;
    }

    public void setFantasyPremierLeagueId(int fantasyPremierLeagueId) {
        this.fantasyPremierLeagueId = fantasyPremierLeagueId;
    }

    public boolean isPlayerStatsUpdated() {
        return playerStatsUpdated;
    }

    public void setPlayerStatsUpdated(boolean playerStatsUpdated) {
        this.playerStatsUpdated = playerStatsUpdated;
    }
}
