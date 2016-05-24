package org.tpl.business.model.fantasy.rule;
/*
Created by jordyr, 22.01.11

*/

import org.tpl.business.model.PlayerPosition;

public class TeamRule  extends Rule{

    private TeamRuleType ruleType;

    public TeamRule(TeamRuleType ruleType, int points, int qualifingNumber, String name, PlayerRulePosition playerRulePosition) {
        this.ruleType = ruleType;
        this.setPoints(points);
        this.setQualifingNumber(qualifingNumber);
        this.setName(name);
        this.playerRulePosition = playerRulePosition;
    }

    public TeamRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(TeamRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public int calculatePoints(int number, PlayerPosition playerPosition) {
        int calculatedPoints = 0;
         if(this.getPlayerRulePosition().equals(PlayerRulePosition.ALL) || playerPosition.toString().equals(this.getPlayerRulePosition().toString())){
            if(ruleType.isPlayerRelatedRuleType()){
                calculatedPoints = calculatePlayerPoints(number);
            }else if(ruleType.equals(TeamRuleType.ZEROGOALSCONCEDED)){
                    calculatedPoints = calculateZeroGoalsPoints(number);
            }
            else{
                calculatedPoints = calculatePoints(number);
            }
        }
        return calculatedPoints;
    }

    private int calculatePlayerPoints(int number){
        int calculatedPoints = 0;
        if(this.getQualifingNumber() <= number){
            calculatedPoints  = this.getPoints();
        }
        return calculatedPoints;
    }
    private int calculateZeroGoalsPoints(int number){
        int calculatedPoints = 0;
        if(this.getQualifingNumber() == number){
            calculatedPoints  = this.getPoints();
        }
        return calculatedPoints;
    }
}
