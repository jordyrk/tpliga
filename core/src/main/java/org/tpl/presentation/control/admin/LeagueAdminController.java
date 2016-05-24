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
import org.tpl.business.service.LeagueService;
import org.tpl.presentation.control.admin.util.LeagueControllerUtil;

/**
 *
 * @author Koren
 */
@Controller
public class LeagueAdminController {

    @Autowired
    LeagueService leagueService;

    @Autowired
    LeagueControllerUtil leagueControllerUtil;

    @RequestMapping("/admin/league/viewleagues")
    public String viewLeagues(ModelMap model) {
        leagueControllerUtil.viewLeagues(model);
        return "admin/league/viewleagues";
    }

    @RequestMapping("/admin/league/delete")
    public String deleteLeague(@RequestParam(required = true, value = "leagueId") Integer leagueId,ModelMap model) {
        if(leagueId > 0){
            leagueService.deleteLeague(leagueId);
        }
        return "admin/league/viewleagues";
    }
}
