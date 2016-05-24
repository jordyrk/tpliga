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
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasyMatch;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyLeagueService;
import org.tpl.business.service.fantasy.FantasyMatchService;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;
import org.tpl.presentation.control.admin.util.FantasyLeagueControllerUtil;
import org.tpl.presentation.control.admin.util.LeagueControllerUtil;

import java.util.List;

/**
 *
 * @author Koren
 */
@Controller
public class FantasyLeagueAdminController {

    @Autowired
    FantasyLeagueControllerUtil fantasyLeagueControllerUtil;

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @RequestMapping("/admin/fantasyleague/viewfantasyleagues")
    public String viewLeagues(ModelMap model) {
        fantasyLeagueControllerUtil.viewFantasyLeagues(model);
        return "admin/fantasyleague/viewfantasyleagues";
    }
    @RequestMapping("/admin/fantasyleague/createfixtures")
    public String createFixtureList(@RequestParam(required = true, value = "fantasyLeagueId") Integer fantasyLeagueId,ModelMap model) {

        if(fantasyLeagueId > 0){
            boolean hasFixtures = fantasyLeagueService.hasFixtures(fantasyLeagueId);
            try{
                if( ! hasFixtures){
                    fantasyLeagueService.createFixtureList(fantasyLeagueId);
                }
                List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchByLeagueId(fantasyLeagueId);
                model.put("fantasyMatchList",fantasyMatchList);
                return "admin/fantasyleague/viewmatchesinleague";

            }catch (FixtureListCreationException e){
                model.put("errormessage", e.getMessage());
            }
        }

        fantasyLeagueControllerUtil.viewFantasyLeagues(model);
        return "admin/fantasyleague/viewfantasyleagues";
    }

    @RequestMapping("/admin/fantasyleague/delete")
    public String deleteFantasyLeague(@RequestParam(required = true, value = "fantasyLeagueId") Integer fantasyLeagueId,ModelMap model) {
        FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);
        fantasyLeagueService.deleteFantasyLeague(fantasyLeagueId);
        model.put("message", "FantasyLeague " + fantasyLeague.getName() + " is deleted");
        return viewLeagues(model);
    }


}
