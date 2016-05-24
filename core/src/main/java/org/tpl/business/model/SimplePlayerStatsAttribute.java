package org.tpl.business.model;

public enum SimplePlayerStatsAttribute {
    POINTS("sum(points)", "sumpoints"),
    ASSIST("sum(assists)", "sumassists"),
    GOALS("sum(goals)", "sumgoals"),
    MISSEDPENALTY("sum(missedpenalty)", "summissedpenalty"),
    OWNGOAL("sum(owngoal)", "sumowngoal"),
    PLAYEDMINUTES("sum(playedMinutes)", "sumplayedMinutes"),
    REDCARD("sum(redcard)", "sumredcard"),
    SAVEDPENALTY("sum(savedpenalty)", "sumsavedpenalty"),
    YELLOWCARD("sum(yellowcard)", "sumyellowcard"),
    PLAYEDMATCHES("count(*)", "sumplayedmatches"),
    SHOTS("sum(shots)","sumshots"),
    SHOTSONGOAL("sum(shotsOnGoal)","sumshotsongoal"),
    OFFSIDES("sum(offsides)","sumoffsides"),
    FOULSCOMMITED("sum(foulsCommited)","sumfoulscommited"),
    FOULSDRAWN("sum(foulsDrawn)","sumfoulsdrawn"),
    SAVES("sum(saves)","sumsaves"),
    WHOLEGAME("sum(wholegame)","sumwholegame"),
    STARTEDGAME("sum(startedgame)","sumwholegame");



    String field;
    String alias;

    SimplePlayerStatsAttribute(String field, String alias) {
        this.field = field;
        this.alias = alias;
    }

    public String getField() {
        return field;
    }

    public String getAlias() {
        return alias;
    }
}
