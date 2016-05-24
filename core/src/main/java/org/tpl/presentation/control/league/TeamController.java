package org.tpl.presentation.control.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tpl.business.model.Season;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.service.LeagueService;
import org.springframework.stereotype.Controller;
import org.tpl.business.service.fantasy.FantasyService;

@Controller
public class TeamController {

    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyService fantasyService;

    @RequestMapping("/json/team")
    public String getTeam(ModelMap model){
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        Season season = leagueService.getSeasonById(fantasySeason.getSeason().getSeasonId());
        model.put("teams", season.getTeams());
        return "json/team";
    }

}
