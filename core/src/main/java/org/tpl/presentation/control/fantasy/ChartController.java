package org.tpl.presentation.control.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.fantasy.FantasyCompetitionStanding;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.util.FantasyCompetitionStandingChart;
import org.tpl.business.service.fantasy.FantasyCompetitionService;
import org.tpl.business.service.fantasy.FantasyService;

import java.util.List;

@Controller
public class ChartController {

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    @Autowired
    FantasyService fantasyService;


    @RequestMapping("/chart/roundchart")
    public String getRoundChartForTeam(@RequestParam()Integer teamId ,ModelMap model){
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        List<FantasyCompetitionStanding> fantasyCompetitionStandingListForTeam = fantasyCompetitionService.getFantasyCompetitionStandingByTeamAndCompetition(fantasySeason.getDefaultFantasyCompetition().getFantasyCompetitionId(),teamId);
        FantasyCompetitionStandingChart fantasyCompetitionStandingChart = new FantasyCompetitionStandingChart(fantasyCompetitionStandingListForTeam);
        model.put("fantasyCompetitionStandingChart", fantasyCompetitionStandingChart);
        return "chart/roundlist";
    }
}
