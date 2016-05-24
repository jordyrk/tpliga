package org.tpl.business.model.fantasy.rule;
/*
Created by jordyr, 22.01.11

*/

public enum TeamRuleType {
    GOALSCORED("homegoals", "awaygoals"),
    GOALSCONCEDED("awaygoals", "homegoals"),
    ZEROGOALSCONCEDED("awaygoals", "homegoals"),
    VICTORY("playedMinutes"),
    DRAW("playedMinutes"),
    LOSS("playedMinutes");

    private String homeField;
    private String awayField;
    private String playerField;



    TeamRuleType(String homeField, String awayField) {
        this.homeField = homeField;
        this.awayField = awayField;
    }

    TeamRuleType(String playerField) {
        this.playerField = playerField;
    }

    public String getHomeField() {
        return homeField;
    }

    public String getAwayField() {
        return awayField;
    }

    public String getPlayerField() {
        return playerField;
    }

    public boolean isPlayerRelatedRuleType(){
        if(playerField == null || playerField.length() < 1){
            return false;
        }
        else{
            return true;
        }
    }


}

