package org.tpl.business.model.fantasy;

public class FantasyCupMatch extends FantasyMatch{

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    FantasyCupMatch(FantasyMatch fantasyMatch, String alias){
        this.setId(fantasyMatch.getId());
        this.setFantasyCup(fantasyMatch.getFantasyCup());
        this.setAwaygoals(fantasyMatch.getAwaygoals());
        this.setAwayTeam(fantasyMatch.getAwayTeam());
        this.setHomegoals(fantasyMatch.getHomegoals());
        this.setHomeTeam(fantasyMatch.getHomeTeam());
        this.setFantasyRound(fantasyMatch.getFantasyRound());
        this.setPlayed(fantasyMatch.isPlayed());
        this.alias = alias;
    }
}
