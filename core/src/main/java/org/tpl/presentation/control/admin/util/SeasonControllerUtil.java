package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.Season;
import org.tpl.business.service.LeagueService;

import java.util.List;/*
Created by jordyr, 09.okt.2010

*/

public class SeasonControllerUtil {

    @Autowired
    LeagueService leagueService;

    public void viewSeasons(ModelMap model){
        addAllSeasons(model);
        addDefaultSeason(model);
    }

    private void addDefaultSeason(ModelMap model){
        Season defaultSeason = leagueService.getDefaultSeason();
        if (defaultSeason == null){
            return;
        }
        model.put("defaultseason", defaultSeason);
    }

    private void addAllSeasons(ModelMap model){
        List<Season> seasons = leagueService.getAllSeasons();
        model.put("seasons", seasons);
    }


}

