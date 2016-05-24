package org.tpl.presentation.control;
/*
Created by jordyr, 26.01.11

*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.business.service.fantasy.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RuleDisplayController {
    @Autowired
    RuleService ruleService;

    @Autowired
    FantasyService fantasyService;

    @RequestMapping(value="/rules")
    public String viewRules(HttpServletRequest request, ModelMap model){
        List<PlayerRule> playerRuleList = ruleService.getAllPlayerRules();
        List<TeamRule> teamRuleList = ruleService.getAllTeamRules();
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        model.put("maxteamprice", fantasySeason.getMaxTeamPrice());
        model.put("teamrules", teamRuleList);
        model.put("playerrules", playerRuleList);


        return "viewrules";
    }
}
