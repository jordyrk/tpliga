package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.League;
import org.tpl.business.service.LeagueService;

import java.util.List;/*
Created by jordyr, 09.okt.2010

*/

public class LeagueControllerUtil {

    @Autowired
    LeagueService leagueService;

    public void viewLeagues(ModelMap model){
        List<League> leagues = leagueService.getAllLeagues();
        model.put("leagues", leagues);
    }


}

