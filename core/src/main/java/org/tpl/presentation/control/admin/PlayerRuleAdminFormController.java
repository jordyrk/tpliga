package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.PlayerRulePosition;
import org.tpl.business.model.fantasy.rule.PlayerRuleType;
import org.tpl.business.model.validator.PlayerRuleValidator;
import org.tpl.business.service.fantasy.RuleService;
import org.tpl.presentation.control.admin.util.RuleControllerUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/fantasyrule/editplayerrule")
public class PlayerRuleAdminFormController {

    @Autowired
    RuleService ruleService;

    @Autowired
    PlayerRuleValidator playerRuleValidator;

    @Autowired
    RuleControllerUtil ruleControllerUtil;

    @ModelAttribute(value="playerrule")
    public PlayerRule getPlayerRule(@RequestParam(required = false, value = "ruleId") Integer ruleId){

        if (ruleId == null || ruleId < 1) {

            return new PlayerRule(PlayerRuleType.GOAL, 0,0,"", PlayerRulePosition.ALL);
        }
        else {
            return ruleService.getPlayerRuleById(ruleId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        model.put("ruletypes", PlayerRuleType.values());
        model.put("positions", PlayerRulePosition.values());
        return "admin/fantasyrule/editplayerrule";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("playerrule")PlayerRule playerRule,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        playerRuleValidator.validate(playerRule,bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/fantasyrule/editplayerrule";
        }
        ruleService.saveOrUpdatePlayerRule(playerRule);
        model.put("message", "Rule was stored: " + playerRule .getName());
        ruleControllerUtil.viewRules(model);
        return "admin/fantasyrule/viewfantasyrules";
    }

}
