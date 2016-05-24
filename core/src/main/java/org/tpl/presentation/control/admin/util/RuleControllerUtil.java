package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.service.fantasy.RuleService;

import java.util.List;

public class RuleControllerUtil {

    @Autowired
    RuleService ruleService;

    public void viewRules(ModelMap model){
        List<PlayerRule> playerRuleList = ruleService.getAllPlayerRules();
        model.put("playerRuleList", playerRuleList);
        List<TeamRule> teamRuleList = ruleService.getAllTeamRules();
        model.put("teamRuleList", teamRuleList);


    }
}
