package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.service.LeagueService;/*
Created by jordyr, 09.okt.2010

*/

public class TeamControllerUtil {

    @Autowired
    LeagueService leagueService;

    public void viewTeams(ModelMap model){
         addAllTeams(model);
    }

     private void addAllTeams(ModelMap model) {
        model.put("teams", leagueService.getAllTeams());
    }


}

