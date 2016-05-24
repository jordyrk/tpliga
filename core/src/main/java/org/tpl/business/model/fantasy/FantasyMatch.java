package org.tpl.business.model.fantasy;
import org.tpl.business.model.MatchResult;

public class FantasyMatch {

    private int id = -1;
    private FantasyTeam homeTeam;
    private FantasyTeam awayTeam;
    private int homegoals;
    private int awaygoals;
    private FantasyRound fantasyRound;
    private FantasyCup fantasyCup;
    private FantasyLeague fantasyLeague;
    private FantasyCupGroup fantasyCupGroup;
    private boolean played;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FantasyTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(FantasyTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public FantasyTeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(FantasyTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomegoals() {
        return homegoals;
    }

    public void setHomegoals(int homegoals) {
        this.homegoals = homegoals;
    }

    public int getAwaygoals() {
        return awaygoals;
    }

    public void setAwaygoals(int awaygoals) {
        this.awaygoals = awaygoals;
    }

    public FantasyRound getFantasyRound() {
        return fantasyRound;
    }

    public void setFantasyRound(FantasyRound fantasyRound) {
        this.fantasyRound = fantasyRound;
    }

    public FantasyCup getFantasyCup() {
        return fantasyCup;
    }

    public void setFantasyCup(FantasyCup fantasyCup) {
        this.fantasyCup = fantasyCup;
    }

    public FantasyLeague getFantasyLeague() {
        return fantasyLeague;
    }

    public void setFantasyLeague(FantasyLeague fantasyLeague) {
        this.fantasyLeague = fantasyLeague;
    }

    public FantasyCupGroup getFantasyCupGroup() {
        return fantasyCupGroup;
    }

    public void setFantasyCupGroup(FantasyCupGroup fantasyCupGroup) {
        this.fantasyCupGroup = fantasyCupGroup;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean isNew(){
        return id == -1;
    }

    public FantasyMatchType getMatchType(){
        if(fantasyCup != null)return FantasyMatchType.CUP;
        else if(fantasyLeague != null) return FantasyMatchType.LEAGUE;
        else if(fantasyCupGroup != null) return FantasyMatchType.CUPGROUP;
        else return FantasyMatchType.UNKNOWN;
    }

    public MatchResult getMatchResultForHomeTeam(){
        if(homegoals < awaygoals) return MatchResult.LOSS;
        else if (homegoals == awaygoals) return MatchResult.DRAW;
        else return MatchResult.VICTORY;
    }
    public MatchResult getMatchResultForAwayTeam(){
        if(awaygoals < homegoals) return MatchResult.LOSS;
        else if (homegoals == awaygoals) return MatchResult.DRAW;
        else return MatchResult.VICTORY;
    }

    public FantasyTeam getWinningTeam(){
        if(MatchResult.VICTORY == getMatchResultForHomeTeam()){
            return homeTeam;
        }else if(MatchResult.VICTORY == getMatchResultForAwayTeam()){
            return awayTeam;
        }
        else return null;
    }

    public AbstractFantasyCompetion getFantasyCompetition(){
        if(fantasyLeague != null){
            return fantasyLeague;
        }else if(fantasyCupGroup != null){
            return fantasyCupGroup;
        }else{
            return fantasyCup;
        }
    }
}
