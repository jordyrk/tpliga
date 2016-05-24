package org.tpl.presentation.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.search.AndTerm;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.model.search.Operator;
import org.tpl.business.model.search.OrTerm;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.business.service.fantasy.FantasyMatchService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Koren
 * Date: 07.aug.2010
 * Time: 11:18:22
 */
@Controller
public class MypageController {
    @Autowired
    FantasyService fantasyService;

    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyUtil fantasyUtil;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @Autowired
    PlayerService playerService;



    @RequestMapping(value="/mypage")
    public String startPage(HttpServletRequest request, ModelMap model){
        FantasyManager fantasyManager = fantasyUtil.getFantasyManagerFromRequest(request);
        FantasyTeam fantasyTeam = null;

        if(fantasyManager == null){
            return "redirect:fantasy/manager/editmanager";

        }
        List<FantasyTeam> fantasyTeamList = fantasyService.getFantasyTeamByManagerId(fantasyManager.getManagerId());
        if (fantasyTeamList == null || fantasyTeamList.size()<1) {
            return "redirect:fantasy/team/editteam";
        }
        //TODO: Implement multiple teams for a manager
        fantasyTeam = fantasyTeamList.get(0);

        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        FantasyRound fantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasySeason.getFantasySeasonId());
        List<FantasyMatch> nextMatches = fantasyMatchService.getNextMatches(fantasyTeam.getTeamId(), fantasyRound.getFantasyRoundId());
        List<FantasyTeam> allFantasyTeams = fantasyService.getAllFantasyTeams();
        List<Team> teams = leagueService.getTeamsInSeason(leagueService.getDefaultSeason().getSeasonId());
        model.put("allFantasyTeams", allFantasyTeams);
        model.put("nextLeagueMatches",nextMatches);
        model.put("playerPositions", PlayerPosition.values());
        model.put("statsAttributes", SimplePlayerStatsAttribute.values());
        model.put("fantasyTeam", fantasyTeam);
        model.put("formations", Formation.values());
        model.put("fantasyRound", fantasyRound);
        model.put("fantasySeason", fantasySeason);
        model.put("seasonId", fantasySeason.getSeason().getSeasonId());
        model.put("teams",teams);
        return "mypage";
    }

}
