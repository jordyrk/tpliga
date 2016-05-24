package org.tpl.presentation.control.fantasy;
/*
Created by jordyr, 22.01.11

*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.comparator.FantasyMatchByRoundIdReverseComparator;
import org.tpl.business.model.comparator.FantasyRoundComparator;
import org.tpl.business.model.comparator.WhoreComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.search.AndTerm;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.model.search.Operator;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.*;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Controller
public class FantasyLeagueController {

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @Autowired
    WhoreService whoreService;


    @RequestMapping(value="/fantasyleague")
    public String startPage(@RequestParam(required = false) Integer seasonId, ModelMap model){
        FantasySeason fantasySeason;
        if(seasonId == null){
            fantasySeason = fantasyService.getDefaultFantasySeason();
        }   else{
            fantasySeason = fantasyService.getFantasySeasonById(seasonId);
        }
        List<FantasySeason> fantasySeasonsList = fantasyService.getAllFantasySeasons();
        List<FantasyLeague> fantasyLeagueList = fantasyLeagueService.getAllFantasyLeagues(fantasySeason.getFantasySeasonId());
        model.put("fantasyLeagueList", fantasyLeagueList);
        model.put("fantasySeasonsList", fantasySeasonsList);
        model.put("fantasySeason", fantasySeason);
        return "fantasyleague";
    }

    @RequestMapping(value = "/fantasy/league/fantasyleagueoverview")
    public String getFantasyLeagueOverview(@RequestParam("fantasyLeagueId") int fantasyLeagueId, ModelMap model){
        FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);

        model.put("fantasyLeague", fantasyLeague);
        FantasyRound lastUpdatedFantasyRound = fantasyLeagueService.getLastUpdatedFantasyRound(fantasyLeagueId);
        int startingRoundIndex = fantasyLeague.getNumberOfRoundsBefore(lastUpdatedFantasyRound);
        if(startingRoundIndex > 3 ) startingRoundIndex = 3;

        model.put("startingRoundIndex",startingRoundIndex);
        model.put("numberOfRoundsPlayed", fantasyLeague.getNumberOfRoundsBefore(lastUpdatedFantasyRound));
        return "fantasy/league/fantasyleagueoverview";
    }

    @RequestMapping(value = "/fantasy/league/matches")
    public String viewMatches(@RequestParam("fantasyLeagueId") int fantasyLeagueId,@RequestParam("fantasyRoundId") int fantasyRoundId,HttpServletRequest request, ModelMap model){
        List<FantasyMatch>  fantasyMatchList = fantasyMatchService.getFantasyMatchByLeagueIdAndRoundId(fantasyLeagueId,fantasyRoundId);

        model.put("fantasyMatchList",fantasyMatchList );
        return "fantasy/league/matches";
    }

    @RequestMapping(value = "/fantasy/league/fixtures")
    public String viewFixtures(@RequestParam("fantasyLeagueId") int fantasyLeagueId, ModelMap model){
        FantasyRound fantasyRound  = fantasyLeagueService.getLastUpdatedFantasyRound(fantasyLeagueId);
        ComparisonTerm roundTerm = null;
        if(fantasyRound != null){
            roundTerm = new ComparisonTerm("fantasyroundid", Operator.G,fantasyRound.getFantasyRoundId() );
        }else{
            FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);
            List<FantasyRound> fantasyRoundList = fantasyLeague.getFantasyRoundList();
            Collections.sort(fantasyRoundList, new FantasyRoundComparator());
            if(fantasyRoundList.size() > 0){
                fantasyRound = fantasyRoundList.get(0);
                roundTerm = new ComparisonTerm("fantasyroundid", Operator.GE,fantasyRound.getFantasyRoundId() );
            }
        }
        if(roundTerm != null){
            ComparisonTerm leagueTerm = new ComparisonTerm("fantasyleagueid", Operator.EQ,fantasyLeagueId );
            AndTerm andTerm = new AndTerm(roundTerm,leagueTerm);
            List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchBySearchTerm(andTerm,FantasyMatchType.LEAGUE).getResults();
            model.put("fantasyMatchList",fantasyMatchList );
        }
        return "fantasy/league/fixtures";
    }

    @RequestMapping(value = "/fantasy/league/playedmatches")
    public String viewPlayedMatches(@RequestParam("fantasyLeagueId") int fantasyLeagueId, ModelMap model){
        FantasyRound fantasyRound  = fantasyLeagueService.getLastUpdatedFantasyRound(fantasyLeagueId);
        if(fantasyRound != null){
            ComparisonTerm roundTerm = new ComparisonTerm("fantasyroundid", Operator.LE,fantasyRound.getFantasyRoundId() );
            ComparisonTerm leagueTerm = new ComparisonTerm("fantasyleagueid", Operator.EQ,fantasyLeagueId );
            AndTerm andTerm = new AndTerm(roundTerm,leagueTerm);
            List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchBySearchTerm(andTerm,FantasyMatchType.LEAGUE).getResults();
            Collections.sort(fantasyMatchList, new FantasyMatchByRoundIdReverseComparator());
            model.put("fantasyMatchList",fantasyMatchList );
        }
        return "fantasy/league/playedmatches";
    }

    @RequestMapping(value = "/fantasy/league/standings")
    public String viewStandings(@RequestParam("fantasyLeagueId") int fantasyLeagueId, ModelMap model){
        FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);
        model.put("fantasyLeague", fantasyLeague);
        List<FantasyLeagueStanding> fantasyLeagueStandingList = fantasyLeagueService.getLastUpdatedFantasyLeagueStandingsByLeague(fantasyLeagueId);
        model.put("fantasyLeagueStandingList",fantasyLeagueStandingList );
        return "fantasy/league/standings";
    }

    @RequestMapping(value = "/fantasy/league/whorestandings")
    public String viewWhoreStandings(@RequestParam("fantasyLeagueId") int fantasyLeagueId, ModelMap model){
        FantasyRound fantasyRound  = fantasyLeagueService.getLastUpdatedFantasyRound(fantasyLeagueId);
        List<Whore> whoreList = whoreService.getWhores(fantasyLeagueId, fantasyRound.getFantasyRoundId());
        Collections.sort(whoreList, new WhoreComparator());
        model.put("whoreList",whoreList);
        return "fantasy/league/whorestandings";
    }

    @RequestMapping(value="/fantasy/league/form")
    public String viewForm(@RequestParam("fantasyLeagueId") int fantasyLeagueId,@RequestParam("numberOfRounds") int numberOfRounds,HttpServletRequest request, ModelMap model){
        FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);
        List<FantasyLeagueStanding> fantasyLeagueStandingList = fantasyLeagueService.getAccumulatedFantasyLeagueStandingsByLeague(fantasyLeagueId, numberOfRounds);

        model.put("fantasyLeagueStandingList", fantasyLeagueStandingList);
        model.put("fantasyLeague", fantasyLeague);
        return "fantasy/league/form";
    }

}
