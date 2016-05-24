/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.business.service.fantasy.RuleService;
import org.tpl.presentation.common.FantasyUtil;
import org.tpl.presentation.control.admin.util.RuleControllerUtil;

import java.util.List;

/**
 *
 * @author Koren
 */
@Controller
public class RuleAdminController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    RuleService ruleService;

    @Autowired
    RuleControllerUtil ruleControllerUtil;

    @RequestMapping("/admin/fantasyrule/viewfantasyrules")
    public String viewFantasyRules(ModelMap model){
        ruleControllerUtil.viewRules(model);
        return "admin/fantasyrule/viewfantasyrules";
    }

    @RequestMapping("/admin/fantasyrule/deleterule")
    public String deletePlayerRule(@RequestParam(required = true, value = "ruleId") Integer ruleId, ModelMap model){
        ruleService.deleteRule(ruleId);
        model.put("message", "Rule is deleted");
        return viewFantasyRules(model);
    }
}
