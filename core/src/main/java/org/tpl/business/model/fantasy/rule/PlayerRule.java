package org.tpl.business.model.fantasy.rule;
/*
Created by jordyr, 22.01.11

*/

import org.tpl.business.model.PlayerPosition;

public class PlayerRule extends Rule{
    private PlayerRuleType ruleType;

    public PlayerRule(PlayerRuleType ruleType, int points, int qualifingNumber, String name, PlayerRulePosition playerRulePosition) {
        this.ruleType = ruleType;
        this.setPoints(points);
        this.setQualifingNumber(qualifingNumber);
        this.setName(name);
        this.playerRulePosition = playerRulePosition;
    }

    public PlayerRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(PlayerRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public int calculatePoints(int number, PlayerPosition playerPosition){
        if(this.appliesToAPosition() && (this.getPlayerRulePosition().equals(PlayerRulePosition.ALL) || playerPosition.toString().equals(this.getPlayerRulePosition().toString()))){
            return calculatePoints(number);
        }
        return 0;
    }

}
