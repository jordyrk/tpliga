package org.tpl.business.service.fantasy;
/*
Created by jordyr, 23.01.11

*/

import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;

import java.util.List;

public interface RuleService {

    void saveOrUpdatePlayerRule(PlayerRule playerRule);

    PlayerRule getPlayerRuleById(int playerRuleId);

    List<PlayerRule> getAllPlayerRules();

    int deleteRule(int ruleId);

    void saveOrUpdateTeamRule(TeamRule teamRule);

    TeamRule getTeamRuleById(int teamRuleId);

    List<TeamRule> getAllTeamRules();

    void updatePointsForMatch(int matchId);
}
