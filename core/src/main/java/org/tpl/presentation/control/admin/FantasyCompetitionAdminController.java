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
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;

/**
 *
 * @author Koren
 */
@Controller
public class FantasyCompetitionAdminController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyUtil fantasyUtil;

    @RequestMapping("/admin/fantasycompetition/viewfantasycompetitions")
    public String viewFantasyCompetitions(@RequestParam(required = true, value = "fantasySeasonId") Integer fantasySeasonId, ModelMap model){
        fantasyUtil.viewFantasyCompetitions(model, fantasySeasonId);
        return "admin/fantasycompetition/viewfantasycompetitions";
    }

    @RequestMapping("/admin/fantasycompetition/overview")
    public String overview(ModelMap model){
        fantasyUtil.overviewFantasyCompetitions(model);
        return "admin/fantasycompetition/overview";
    }


}
