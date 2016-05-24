package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.fantasy.rule.PlayerRulePosition;
import org.tpl.business.model.fantasy.rule.PlayerRuleType;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.model.fantasy.rule.TeamRuleType;
import org.tpl.business.model.validator.TeamRuleValidator;
import org.tpl.business.service.fantasy.RuleService;
import org.tpl.presentation.control.admin.util.RuleControllerUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/fantasyrule/editteamrule")
public class TeamRuleFormController {

    @Autowired
    RuleService ruleService;

    @Autowired
    TeamRuleValidator teamRuleValidator;

    @Autowired
    RuleControllerUtil ruleControllerUtil;

    @ModelAttribute(value="teamrule")
    public TeamRule getTeamRule(@RequestParam(required = false, value = "ruleId") Integer ruleId){

        if (ruleId == null || ruleId < 1) {

            return new TeamRule(TeamRuleType.GOALSCORED, 0, 0, "", PlayerRulePosition.ALL);
        }
        else {
            return ruleService.getTeamRuleById(ruleId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        model.put("ruletypes", TeamRuleType.values());
        model.put("positions", PlayerRulePosition.values());
        return "admin/fantasyrule/editteamrule";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("teamrule")TeamRule teamRule,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        teamRuleValidator.validate(teamRule,bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/fantasyrule/editteamrule";
        }
        ruleService.saveOrUpdateTeamRule(teamRule);
        model.put("message", "Rule was stored: " + teamRule.getName());
        ruleControllerUtil.viewRules(model);
        return "admin/fantasyrule/viewfantasyrules";
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {


    }

}
