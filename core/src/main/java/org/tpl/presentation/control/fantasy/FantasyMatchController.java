package org.tpl.presentation.control.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.fantasy.FantasyMatch;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.FantasyTeamRound;
import org.tpl.business.model.fantasy.stat.FantasyMatchStats;
import org.tpl.business.service.fantasy.FantasyMatchService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FantasyMatchController {

    @Autowired
    FantasyMatchService fantasyMatchService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyUtil fantasyUtil;


    @RequestMapping(value = "/fantasy/match/viewfantasymatch")
    public String viewFantasyMatch(@RequestParam(required = true) int fantasyMatchId, ModelMap model, HttpServletRequest request){
        FantasyTeam currentFantasyTeam = fantasyUtil.getFantasyTeamFromRequest(request);
        FantasyMatch fantasyMatch = fantasyMatchService.getFantasyMatchById(fantasyMatchId);
        if(currentFantasyTeam.getTeamId() == fantasyMatch.getHomeTeam().getTeamId()){
            model.put("isHomeTeam", true);
        }
        if(currentFantasyTeam.getTeamId() == fantasyMatch.getAwayTeam().getTeamId()){
            model.put("isAwayTeam", true);
        }
        FantasyTeamRound fantasyHomeTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyMatch.getHomeTeam().getTeamId(), fantasyMatch.getFantasyRound().getFantasyRoundId());
        FantasyTeamRound fantasyAwayTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyMatch.getAwayTeam().getTeamId(), fantasyMatch.getFantasyRound().getFantasyRoundId());
        model.put("fantasyMatch",fantasyMatch);
        model.put("fantasyHomeTeamRound",fantasyHomeTeamRound);
        model.put("fantasyAwayTeamRound",fantasyAwayTeamRound);
        return "fantasy/match/viewmatch";
    }

    @RequestMapping(value = "/fantasy/match/matchstats")
    public String viewFantasyMatchStats(ModelMap model){
        List<FantasyMatch> allMatchesInSeason = fantasyMatchService.getFantasyMatches(fantasyService.getDefaultFantasySeason().getFantasySeasonId(), true);
        FantasyMatchStats fantasyMatchStats = new FantasyMatchStats(allMatchesInSeason);
        model.put("leaguename","Tjokkpessligaen + CGO");
        model.put("matchstats",fantasyMatchStats.getMap());
        model.put("numberOfMathces",allMatchesInSeason.size());
        return "match/matchstats";
    }
}
