package org.tpl.presentation.control.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tpl.business.model.Match;
import org.tpl.business.model.fantasy.stat.MatchStats;
import org.tpl.business.model.search.AndTerm;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.service.LeagueService;

import java.util.List;

@Controller
public class MatchController {

    @Autowired
    LeagueService leagueService;

    @RequestMapping(value = "/league/match/matchstats")
    public String viewMatchStats(ModelMap model){

         List<Match> allMatchesInSeason = leagueService.getPlayedMatchesInSeason(leagueService.getDefaultSeason().getSeasonId());
         MatchStats matchStats = new MatchStats(allMatchesInSeason);
         model.put("leaguename","Premier League");
         model.put("matchstats",matchStats.getMap());
         model.put("numberOfMathces",allMatchesInSeason.size());


        return "match/matchstats";
    }
}
