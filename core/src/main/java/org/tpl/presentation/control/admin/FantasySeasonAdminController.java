/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.presentation.control.admin;

import no.kantega.commons.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.Match;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.*;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;
import org.tpl.presentation.common.FantasyUtil;

import java.util.List;

/**
 *
 * @author Koren
 */
@Controller
public class FantasySeasonAdminController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyUtil fantasyUtil;

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyCupGroupService fantasyCupGroupService;

    @Autowired
    FantasyCupService fantasyCupService;

    @RequestMapping("/admin/fantasyseason/viewfantasyseasons")
    public String viewFantasySeasons(ModelMap model){
        fantasyUtil.viewFantasySeasons(model);
        return "admin/fantasyseason/viewfantasyseasons";
    }

    @RequestMapping("/admin/fantasyseason/viewallrounds")
    public String viewAllFantasyRounds(@RequestParam(required = true, value = "fantasySeasonId") Integer fantasySeasonId,ModelMap model){
        fantasyUtil.viewAllFantasyRounds(model, fantasySeasonId);
        return "admin/fantasyseason/viewallrounds";
    }

    @RequestMapping("/admin/fantasyseason/viewround")
    public String viewFantasyRound(@RequestParam(required = true, value = "fantasySeasonId") Integer fantasySeasonId, @RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId, ModelMap model){
        fantasyUtil.viewFantasyRound(model, fantasySeasonId, fantasyRoundId);


        return "admin/fantasyseason/viewround";
    }

    @RequestMapping("/admin/fantasyseason/removeMatch")
    public String removeMatch(@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId, @RequestParam(required = true, value = "matchId") Integer matchId, ModelMap model){
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        int fantasySeasonId = fantasyRound.getFantasySeason().getFantasySeasonId();
        fantasyService.removeMatchFromRound(matchId, fantasyRoundId);
        fantasyUtil.viewFantasyRound(model, fantasySeasonId, fantasyRoundId);
        return "admin/fantasyseason/viewround";
    }

    @RequestMapping("/admin/fantasyseason/addMatch")
    public String addMatch(@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId, @RequestParam(required = true, value = "matchId") Integer matchId, ModelMap model){
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        int fantasySeasonId = fantasyRound.getFantasySeason().getFantasySeasonId();
        fantasyService.addMatchToRound(matchId, fantasyRoundId);
        fantasyUtil.viewFantasyRound(model, fantasySeasonId, fantasyRoundId);
        return "admin/fantasyseason/viewround";
    }

    @RequestMapping("/admin/fantasyseason/viewallmatches")
    public String viewAllMatches(@RequestParam(required = true,value="seasonId")Integer seasonId,@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId, ModelMap model){
        List<Match> matches = leagueService.getMatchBySeasonId(seasonId);
        model.put("matches", matches);
        model.put("fantasyRoundId", fantasyRoundId);
        return "admin/fantasyseason/viewallmatches";
    }

    @RequestMapping("/admin/fantasyseason/updateRound")
    public String updateRound(@RequestParam(required = true,value="fantasySeasonId")Integer fantasySeasonId,@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId, ModelMap model){
       FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
       FantasySeason fantasySeason = fantasyService.getFantasySeasonById(fantasySeasonId);
       fantasySeason.setCurrentRound(fantasyRound);
       fantasyService.saveOrUpdateFantasySeason(fantasySeason);
        return "admin/fantasyseason/updateRound";
    }
    @RequestMapping("/admin/fantasyseason/openCloseRound")
    public String openCloseRound(@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId,@RequestParam(required = true, value = "open") Boolean open, ModelMap model){
       FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
       fantasyRound.setOpenForChange(open);
       fantasyService.saveOrUpdateFantasyRound(fantasyRound);
        return "admin/fantasyseason/updateRound";
    }

    @RequestMapping("/admin/fantasyseason/updateTeamPoints")
    public String updateTeamPoints(@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId, ModelMap model){
        fantasyService.updateFantasyTeamPoints(fantasyRoundId);
        return "admin/fantasyseason/updateRound";
    }

    @RequestMapping("/admin/fantasyseason/updateCompetitions")
    public String updateCompetitions(@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId, ModelMap model){
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        FantasySeason fantasySeason = fantasyRound.getFantasySeason();
        fantasyCompetitionService.updateCompetitionStandings(fantasyRoundId, fantasySeason.getFantasySeasonId());
        return "admin/fantasyseason/updateRound";
    }

    @RequestMapping("/admin/fantasyseason/updateMatches")
    public String updateMatches(@RequestParam(required = true, value = "fantasyRoundId") Integer fantasyRoundId){
        fantasyMatchService.updateMatchScore(fantasyRoundId);
        fantasyLeagueService.updateLeagueStandings(fantasyRoundId);
        fantasyCupGroupService.updateCupGroupStandings(fantasyRoundId);
        try{
            fantasyCupService.createFixtureList();
        }catch (FixtureListCreationException e){
            Log.error(this.getClass().getName(), "Error creating fixturelist: " + e.getMessage());
        }
        return "admin/fantasyseason/updateRound";
    }

}
