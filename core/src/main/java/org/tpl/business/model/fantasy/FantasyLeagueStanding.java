package org.tpl.business.model.fantasy;

public class FantasyLeagueStanding {

    private FantasyTeam fantasyTeam;
    private FantasyLeague fantasyLeague;
    private FantasyRound fantasyRound;
    private int goalsScored;
    private int goalsAgainst;
    private int matchesWon;
    private int matchesDraw;
    private int matchesLost;
    private int position;
    private int points;
    private int lastRoundPosition;

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

    public FantasyRound getFantasyRound() {
        return fantasyRound;
    }

    public void setFantasyRound(FantasyRound fantasyRound) {
        this.fantasyRound = fantasyRound;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        this.matchesWon = matchesWon;
    }

    public int getMatchesDraw() {
        return matchesDraw;
    }

    public void setMatchesDraw(int matchesDraw) {
        this.matchesDraw = matchesDraw;
    }

    public int getMatchesLost() {
        return matchesLost;
    }

    public void setMatchesLost(int matchesLost) {
        this.matchesLost = matchesLost;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public void increaseMatchesWon(){
        matchesWon++;
    }

    public void increaseMatchesLost(){
        matchesLost++;
    }

    public void increaseMatchesDraw(){
        matchesDraw++;
    }

    public void addGoalsScored(int number){
        goalsScored += number;
    }

    public void addGoalsAgainst(int number){
        goalsAgainst += number;
    }

    public void addPoints(int number){
        points += number;
    }

    public void subtractFantasyLeagueStanding(FantasyLeagueStanding toBeSubtracted){
        goalsScored = goalsScored - toBeSubtracted.getGoalsScored();
        goalsAgainst = goalsAgainst - toBeSubtracted.getGoalsAgainst();
        matchesWon = matchesWon - toBeSubtracted.getMatchesWon();
        matchesDraw = matchesDraw - toBeSubtracted.getMatchesDraw();
        matchesLost = matchesLost - toBeSubtracted.getMatchesLost();
        points = points -toBeSubtracted.getPoints();
    }
}
