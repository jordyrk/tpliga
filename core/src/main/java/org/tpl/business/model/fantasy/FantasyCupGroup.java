package org.tpl.business.model.fantasy;

public class FantasyCupGroup extends AbstractFantasyCompetion{
    private boolean homeAndAwayMatches;
    private FantasyCup fantasyCup;

    public boolean isHomeAndAwayMatches() {
        return homeAndAwayMatches;
    }

    public void setHomeAndAwayMatches(boolean homeAndAwayMatches) {
        this.homeAndAwayMatches = homeAndAwayMatches;
    }

    public FantasyCup getFantasyCup() {
        return fantasyCup;
    }

    public void setFantasyCup(FantasyCup fantasyCup) {
        this.fantasyCup = fantasyCup;
    }


}
