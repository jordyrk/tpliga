package org.tpl.presentation.control.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.fantasy.FantasyTeamRound;
import org.tpl.business.model.search.*;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyService;

import java.util.List;
/*
Created by jordyr, 07.des.2010

*/

@Controller
public class FantasyTeamRoundController {
    @Autowired
    FantasyService fantasyService;

    @RequestMapping("/fantasy/officialTeamRound")
    public String getPlayer(@RequestParam(required = true) Integer fantasyRoundId, ModelMap model){
        List<FantasyTeamRound> officialTeamRoundList = fantasyService.getOfficialFantasyTeamRounds(fantasyRoundId);
        model.put("officialTeamRoundList", officialTeamRoundList);
        return "fantasy/officialTeamRound";
    }

}


