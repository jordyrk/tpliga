package org.tpl.presentation.control.fantasy;
/*
Created by jordyr, 22.01.11

*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class FantasyCupController {

    @Autowired
    FantasyCupService fantasyCupService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @Autowired
    FantasyCupGroupService fantasyCupGroupService;


    @RequestMapping(value="/fantasycup")
    public String startPage(@RequestParam(required = false) Integer seasonId, ModelMap model){
        FantasySeason fantasySeason;
        if(seasonId == null){
            fantasySeason = fantasyService.getDefaultFantasySeason();
        }   else{
            fantasySeason = fantasyService.getFantasySeasonById(seasonId);
        }
        List<FantasySeason> fantasySeasonsList = fantasyService.getAllFantasySeasons();
        List<FantasyCup> fantasyCupList = fantasyCupService.getAllFantasyCups(fantasySeason.getFantasySeasonId());
        model.put("fantasyCupList", fantasyCupList);
        model.put("fantasySeasonsList", fantasySeasonsList);
        model.put("fantasySeason", fantasySeason);
        return "fantasycup";
    }

    @RequestMapping(value = "/fantasy/cup/fantasycupoverview")
    public String getFantasyCupOverview(@RequestParam("fantasyCupId") int fantasyCupId, ModelMap model){
        FantasyCup fantasyCup = fantasyCupService.getFantasyCupById(fantasyCupId);
        List<FantasyCupRound> fantasyCupRounds = fantasyCupService.getFantasyCupRounds(fantasyCupId);
        List<FantasyCupRound> reverseFantasyCupRounds = new ArrayList<FantasyCupRound>();
        reverseFantasyCupRounds.addAll(fantasyCupRounds);
        Collections.reverse(reverseFantasyCupRounds);
        model.put("fantasyCupRounds", fantasyCupRounds);
        model.put("reverseFantasyCupRounds", reverseFantasyCupRounds);
        model.put("fantasyCup", fantasyCup);
        return "fantasy/cup/fantasycupoverview";
    }

    @RequestMapping(value = "/fantasy/cup/fantasycupgroupmatches")
    public String getFantasyCupGroupMatches(@RequestParam("fantasyCupGroupId") int fantasyCupGroupId, ModelMap model){
        List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchByCupGroupId(fantasyCupGroupId);
        model.put("fantasyMatchList",fantasyMatchList);
        return "fantasy/cup/fantasycupgroupmatches";
    }

    @RequestMapping(value = "/fantasy/cup/fantasycupgroupstandings")
    public String getFantasyCupGroupStandings(@RequestParam("fantasyCupGroupId") int fantasyCupGroupId, ModelMap model){
        List<FantasyCupGroupStanding> fantasyCupGroupStandings = fantasyCupGroupService.getLastUpdatedFantasyCupGroupStandingsByCupGroup(fantasyCupGroupId);
        model.put("fantasyCupGroupStandings", fantasyCupGroupStandings);
        return "fantasy/cup/fantasycupgroupstandings";
    }
}
