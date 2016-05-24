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
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyCompetitionService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.business.service.fantasy.HallOfFameService;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FantasyCompetitionController {
    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    @Autowired
    LeagueService leagueService;

    @Autowired
    HallOfFameService hallOfFameService;

    @Autowired
    FantasyUtil fantasyUtil;

    @RequestMapping(value="/competition")
    public String startPage(@RequestParam(required = false) Integer seasonId, HttpServletRequest request, ModelMap model){
        FantasySeason fantasySeason;
        if(seasonId == null){
            fantasySeason = fantasyService.getDefaultFantasySeason();
        }   else{
            fantasySeason = fantasyService.getFantasySeasonById(seasonId);
        }
        FantasyManager fantasyManager = fantasyUtil.getFantasyManagerFromRequest(request);

        List<FantasySeason> fantasySeasonsList = fantasyService.getAllFantasySeasons();

        List<FantasyTeam> fantasyTeamList = fantasyService.getFantasyTeamByManagerId(fantasyManager.getManagerId());
        FantasyTeam fantasyTeam = fantasyTeamList.get(0);
        List<FantasyTeamRound> fantasyTeamRoundList = fantasyService.getFantasyTeamRoundsByTeamIdUntilCurrentRound(fantasyTeam.getTeamId(), fantasySeason.getFantasySeasonId());

        FantasyCompetition fantasyCompetition = fantasyCompetitionService.getDefaultFantasyCompetitionBySeasonId(fantasySeason.getFantasySeasonId());
        FantasyRound lastUpdatedFantasyRound = fantasyCompetitionService.getLastUpdatedFantasyRound(fantasyCompetition.getFantasyCompetitionId());
        List<FantasyCompetitionStanding> fantasyCompetitionStandingList = fantasyCompetitionService.getLastUpdatedFantasyCompetitionStandingsByCompetition(fantasyCompetition.getFantasyCompetitionId());
        int maxNumberOfRounds = 0;
        if(lastUpdatedFantasyRound !=null){
            maxNumberOfRounds = lastUpdatedFantasyRound.getRound() > 3 ? 3 : lastUpdatedFantasyRound.getRound() ;
        }
        List<HallOfFame> hallOfFameList = hallOfFameService.getHallOfFameList();
        model.put("fantasySeason", fantasySeason);
        model.put("hallOfFameList", hallOfFameList);
        model.put("fantasySeasonsList", fantasySeasonsList);
        model.put("fantasyCompetitionStandingList", fantasyCompetitionStandingList);
        model.put("fantasyTeamRoundList", fantasyTeamRoundList);
        model.put("fantasyCompetition", fantasyCompetition);
        model.put("fantasyTeam", fantasyTeam);
        model.put("fantasyRound", lastUpdatedFantasyRound);
        model.put("maxNumberOfRounds", maxNumberOfRounds);

        return "competition";
    }

    @RequestMapping(value="/fantasy/competition/form")
    public String viewForm(@RequestParam("numberOfRounds") int numberOfRounds,HttpServletRequest request, ModelMap model){
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        FantasyCompetition fantasyCompetition = fantasyCompetitionService.getDefaultFantasyCompetitionBySeasonId(fantasySeason.getFantasySeasonId());
        List<FantasyCompetitionStanding> fantasyCompetitionStandingList = fantasyCompetitionService.getAccumulatedFantasyCompetitionStandingsByCompetition(fantasyCompetition.getFantasyCompetitionId(), numberOfRounds);

        model.put("fantasyCompetitionStandingList", fantasyCompetitionStandingList);
        model.put("fantasyCompetition", fantasyCompetition);
        return "fantasy/competition/form";
    }
}
