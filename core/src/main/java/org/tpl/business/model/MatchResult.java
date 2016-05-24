package org.tpl.business.model;
/*
Created by jordyr, 24.01.11

*/

public enum MatchResult {
    VICTORY(3),DRAW(1),LOSS(0);

    int points;

    MatchResult(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
