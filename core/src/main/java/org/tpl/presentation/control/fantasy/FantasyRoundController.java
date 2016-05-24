package org.tpl.presentation.control.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.service.fantasy.FantasyService;

import java.util.List;

@Controller
public class FantasyRoundController {

    @Autowired
    FantasyService fantasyService;

    @RequestMapping(value = "/fantasyround/getfantasyroundsincurrentseason")
    public String getFantasyRoundsInCurrentSeason(ModelMap model){
        FantasyRound currentFantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        List<FantasyRound> fantasyRounds = fantasyService.getFantasyRoundByFantasySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId());

        model.put("fantasyRounds",fantasyRounds);
        model.put("currentFantasyRound", currentFantasyRound);
        return "json/fantasyrounds";
    }
}
