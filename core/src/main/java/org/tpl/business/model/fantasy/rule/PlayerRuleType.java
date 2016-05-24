package org.tpl.business.model.fantasy.rule;
/*
Created by jordyr, 22.01.11

*/

public enum PlayerRuleType {

    GOAL("goals"),
    ASSIST("assists"),
    OWNGOAL("owngoal"),
    MISSEDPENALTY("missedpenalty"),
    SAVEDPENALTY("savedpenalty"),
    YELLOWCARD("yellowcard"),
    REDCARD("redcard"),
    WHOLEGAME("wholegame"),
    STARTEDGAME("startedgame"),
    PLAYEDMINUTES("playedMinutes"),
    SHOTS("shots"),
    SHOTSONGOAL("shotsOnGoal"),
    OFFSIDES("offsides"),
    FOULSCOMMITED("foulsCommited"),
    FOULSDRAWN("foulsDrawn"),
    SAVES("saves"),
    MANOFTHEMATCH("manofthematch");


    private String field;

    PlayerRuleType(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
