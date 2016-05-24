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
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.*;

import java.util.List;

/**
 *
 * @author Koren
 */
@Controller
public class FantasyManagerAdminController {


    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyCupService fantasyCupService;

    @RequestMapping("/admin/fantasymanager/viewfantasymanager")
    public String viewFantasyManagers(ModelMap model){
        List<FantasyManager> fantasyManagerList = fantasyService.getAllFantasyManagers();
        model.put("fantasyManagerList", fantasyManagerList);
        return "admin/fantasymanager/viewfantasymanager";
    }

    @RequestMapping("/admin/fantasymanager/deletefantasymanager")
    public String deleteFantasyManagers(@RequestParam(required = true) int fantasyManagerId, ModelMap model){
        List<FantasyTeam> fantasyTeamList = fantasyService.getFantasyTeamByManagerId(fantasyManagerId);
        for(FantasyTeam fantasyTeam:fantasyTeamList){
            List<FantasyLeague> fantasyLeaguesForFantasyTeam = fantasyLeagueService.getFantasyLeaguesForFantasyTeam(fantasyTeam.getTeamId());
            if(! fantasyLeaguesForFantasyTeam.isEmpty()){
                model.put("message", "Team is in league, cannot be deleted");
                return viewFantasyManagers(model);
            }
            List<FantasyCup> fantasyCupList = fantasyCupService.getFantasyCupByTeamId(fantasyTeam.getTeamId());
            if( ! fantasyCupList.isEmpty()){
                model.put("message", "Team is in cup, cannot be deleted");
                return viewFantasyManagers(model);
            }
        }
        fantasyService.deleteFantasyManager(fantasyManagerId);
        model.put("message", "Manager is deleted");
        return viewFantasyManagers(model);
    }

    @RequestMapping("/admin/fantasymanager/updateactivestatus")
    public String updateActiveStatus(@RequestParam(required = true) int fantasyManagerId, @RequestParam(required = true) Boolean active, ModelMap model){
        FantasyManager fantasyManager = fantasyService.getFantasyManagerById(fantasyManagerId);
        fantasyManager.setActive(active);
        fantasyService.saveOrUpdateFantasyManager(fantasyManager);
        model.put("message", "Managerstatus is updated for "  + fantasyManager.getManagerName());
        return viewFantasyManagers(model);
    }

}
