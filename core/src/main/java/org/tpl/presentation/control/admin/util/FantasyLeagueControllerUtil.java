package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.League;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyLeagueService;

import java.util.List;

public class FantasyLeagueControllerUtil {

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    public void viewFantasyLeagues(ModelMap model){
        List<FantasyLeague> fantasyLeagues = fantasyLeagueService.getAllFantasyLeagues();
        model.put("fantasyLeagues", fantasyLeagues);
    }
}