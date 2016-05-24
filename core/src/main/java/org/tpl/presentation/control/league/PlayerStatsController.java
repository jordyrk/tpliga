package org.tpl.presentation.control.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.*;
import org.tpl.business.model.search.AndTerm;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.model.search.Operator;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.presentation.control.admin.util.PlayerControllerUtil;

import java.util.List;
/*
Created by jordyr, 08.jan.2011

*/

@Controller
public class PlayerStatsController {

    @Autowired
    PlayerService playerService;

    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerControllerUtil playerControllerUtil;


    @RequestMapping("/playerstats/sumplayerstats")
    public String getSumPlayerStats(@RequestParam(required = true) Integer playerId, @RequestParam(required = true) Integer seasonId, ModelMap model){
        Player player = playerService.getPlayerById(playerId);
        List<PlayerStats> playerStats = leagueService.getPlayerStatsForPlayerAndSeason(playerId, seasonId);
        SumPlayerStats sumPlayerStats = new SumPlayerStats();
        sumPlayerStats.add(playerStats);

        model.put("sumPlayerStats", sumPlayerStats);
        model.put("player", player);

        return "json/sumplayerstats";
    }

     @RequestMapping("/open/sumplayerstatsincurrentseason")
    public String getSumPlayerStatsInCurrentSeason(@RequestParam(required = true) Integer playerId, ModelMap model){
        Player player = playerService.getPlayerById(playerId);
        List<PlayerStats> playerStats = leagueService.getPlayerStatsForPlayerAndSeason(playerId, leagueService.getDefaultSeason().getSeasonId());
        SumPlayerStats sumPlayerStats = new SumPlayerStats();
        sumPlayerStats.add(playerStats);

        model.put("sumPlayerStats", sumPlayerStats);
        model.put("player", player);

        return "json/openplayerstats";
    }

    @RequestMapping("/playerstats/playerstats")
    public String getPlayerStats(@RequestParam(required = true) Integer playerId, @RequestParam(required = true) Integer seasonId, ModelMap model){
        Player player = playerService.getPlayerById(playerId);
        playerControllerUtil.addPlayerStats(model, playerId, seasonId);
        model.put("player", player);
        return "fantasy/player/playerstats";
    }

    @RequestMapping("/playerstats/simpleplayerstats")
    public String getSimplePlayerStats(@RequestParam(required = true) String attribute,
                                       @RequestParam(required = true) Integer numberOf,
                                       @RequestParam(required = true) boolean best,
                                       @RequestParam(required = false) String position,
                                       @RequestParam(required = false) Integer teamId,
                                       @RequestParam(required = false) Integer numberOfRounds,

                                       ModelMap model){
        Season season = leagueService.getDefaultSeason();

        SimplePlayerStatsAttribute statsAttribute = SimplePlayerStatsAttribute.valueOf(attribute);
        ComparisonTerm seasonTerm = new ComparisonTerm("seasonid", Operator.EQ, season.getSeasonId());
        AndTerm andTerm = new AndTerm();
        andTerm.addTerm(seasonTerm);
        ComparisonTerm positionTerm;
        ComparisonTerm teamTerm;
        String roundArray = null;
        if(position != null){
            PlayerPosition playerPosition = PlayerPosition.valueOf(position);
            positionTerm = new ComparisonTerm("position", Operator.EQ, playerPosition.toString());

            andTerm.addTerm(positionTerm);
        }
        if(teamId != null && teamId >0){
            teamTerm = new ComparisonTerm("teamid", Operator.EQ, teamId);
            andTerm.addTerm(teamTerm);
        }

        if(numberOfRounds!= null && numberOfRounds > 0){
            int hightestRoundId = leagueService.getHighestLeagueRoundIdForSeason(season.getSeasonId());
            int lowestRoundId = leagueService.getLowestLeagueRoundIdForSeason(season.getSeasonId());
            roundArray = getRoundIdAsArray(numberOfRounds,hightestRoundId,lowestRoundId);

        }

        List<SimplePlayerStats> statsList = leagueService.getSimplePlayerStatsBySearchTerm(statsAttribute,andTerm,best,numberOf,roundArray);
        model.put("statsList", statsList);

         return "fantasy/player/simpleplayerstats";
    }


    protected String getRoundIdAsArray(int numberOfId, int maxId,int minId){
        String roundIdArray = "(";
        for(int i = 0; i < numberOfId; i++){
            if(i != 0 && maxId >= minId){
                roundIdArray += ",";
            }
            if(maxId >= minId){
                roundIdArray += maxId;
                maxId--;
            }
        }
        roundIdArray += ") ";
        return roundIdArray;
    }


}
