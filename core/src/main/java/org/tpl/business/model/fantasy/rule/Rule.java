package org.tpl.business.model.fantasy.rule;
/*
Created by jordyr, 22.01.11

*/

import org.tpl.business.model.PlayerPosition;

public abstract class Rule {
    private int id = -1;
    private int points;
    private int qualifingNumber;
    private String name;
    private int ruleId;
    protected PlayerRulePosition playerRulePosition;

    protected int calculatePoints(int number){

        if(qualifingNumber <= number){
            return points*(number/qualifingNumber);
        }
        return 0;
    }

     public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getQualifingNumber() {
        return qualifingNumber;
    }

    public void setQualifingNumber(int qualifingNumber) {
        this.qualifingNumber = qualifingNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew(){
        return id == -1;
    }


    public PlayerRulePosition getPlayerRulePosition() {
        return playerRulePosition;
    }

    public void setPlayerRulePosition(PlayerRulePosition playerRulePosition) {
        this.playerRulePosition = playerRulePosition;
    }

    public boolean appliesToAPosition(){
        return playerRulePosition != null;
    }
}
