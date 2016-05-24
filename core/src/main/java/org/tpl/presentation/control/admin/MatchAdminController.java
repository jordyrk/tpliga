package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.*;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.model.search.MatchSearchResult;
import org.tpl.business.model.search.Operator;
import org.tpl.business.model.validator.PlayerStatsValidator;
import org.tpl.business.service.ImportService;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.business.service.fantasy.RuleService;
import org.tpl.integration.parser.MatchImportException;
import org.tpl.presentation.control.admin.util.MatchControllerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MatchAdminController {

    private static final String SEASON = "season";
    public static final String PLAYERSTATS = "playerStats";

    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ImportService importService;

    @Autowired
    MatchControllerUtil matchControllerUtil;

    @Autowired
    RuleService ruleService;

    @RequestMapping("/admin/match/matchadmin")
    public String matchAdmin(ModelMap model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        if(session.getAttribute(SEASON) == null){
            session.setAttribute(SEASON, leagueService.getDefaultSeason());
        }
        List<Season> seasons = leagueService.getAllSeasons();
        model.put("seasons", seasons);
        return "admin/match/matchadmin";
    }

    @RequestMapping("/admin/match/import")
    public String importMatches(@RequestParam(required = true,value="seasonId")Integer seasonId, ModelMap model) {
        try {
            List<Match> matches = importService.importMatches(leagueService.getSeasonById(seasonId));
            model.put("matches", matches);
        } catch (MatchImportException e) {
            model.put("errormessage", e.getMessage());
        }
        return "admin/match/import";
    }

    @RequestMapping("/admin/match/updatematchesfantasypremierleague")
    public String updateMatchesFromSoccernet(@RequestParam(required = true,value="seasonId")Integer seasonId, @RequestParam(required = true,value="round")Integer round, ModelMap model) {
        try {
            importService.updateMatchesWithFantastyPremierLeagueId(seasonId, round);
        } catch (MatchImportException e) {
            model.put("errormessage", e.getMessage());
            return "admin/match/import";
        }
        matchControllerUtil.viewAllMatches(seasonId, model);
        return "admin/match/import";
    }


    @RequestMapping("/admin/match/matchoverview")
    public String getMatchoverview(@RequestParam(required = true,value="seasonId")Integer seasonId, @RequestParam(required = false,value="leagueRoundId")Integer leagueRoundId, ModelMap model){
        LeagueRound currentRound = null;
        List<LeagueRound> leagueRounds = leagueService.getLeagueRoundsBySeasonId(seasonId);
        if(leagueRoundId == null || leagueRoundId < 1){
            currentRound = leagueRounds.get(0);
        }else{
            currentRound = leagueService.getLeagueRoundById(leagueRoundId);
        }

        List<Match> matches = leagueService.getMatchByLeagueRoundId(currentRound.getLeagueRoundId());
        model.put("leagueRoundId", currentRound.getLeagueRoundId());
        model.put("currentRound", currentRound);
        model.put("seasonId", seasonId);
        model.put("matches", matches);
        model.put("leagueRounds", leagueRounds);
        return "admin/match/matchoverview";

    }
    @RequestMapping("/admin/match/fixturelist")
    public String viewFixtureList(@RequestParam(required = true, value="leagueRoundId")Integer leagueRoundId,ModelMap model){
        matchControllerUtil.fixtureList(model, leagueRoundId);
        return "admin/match/fixturelist";
    }

    @RequestMapping("/admin/match/addplayertoplayerstat")
    public String addPlayerToPlayerStats(@RequestParam(required = true, value = "tempstatsid") Integer tempstatsid,@RequestParam(required = true, value = "playerid") Integer playerid, ModelMap model, HttpServletRequest request){
        List<PlayerStats> playerStatsList = (List<PlayerStats>) request.getSession().getAttribute(PLAYERSTATS);
        for(PlayerStats playerStats: playerStatsList){
            if(playerStats.getTemporarlyId() == tempstatsid){
                Player player = playerService.getPlayerById(playerid);
                playerStats.setPlayer(player);
            }
        }
        request.getSession().setAttribute(PLAYERSTATS, playerStatsList);
        model.put("message", "playerstats.ok");
        return "admin/match/message";
    }

    @RequestMapping("/admin/match/matchstats")
    public String getMatchStats(@RequestParam(required = true, value = "matchId") Integer matchId, ModelMap model, HttpServletRequest request){
        matchControllerUtil.matchStats(model,request,matchId);
        return "admin/match/matchstats";
    }

    @RequestMapping("/admin/match/savePlayerStats")
    public String savePlayerStats(ModelMap model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<PlayerStats> playerStatsList = (List<PlayerStats>)session.getAttribute(PLAYERSTATS);
        boolean save = true;
        for(PlayerStats playerStats: playerStatsList){
            if(!playerStats.isSave()){
                model.put("message","playerstats.message.failure");
                return "admin/match/message";
            }
        }
        addManOfTheMatch(playerStatsList);
        for(PlayerStats playerStats: playerStatsList){
            leagueService.saveOrUpdatePlayerStats(playerStats);
        }
        if(playerStatsList != null && playerStatsList.size() > 0 && playerStatsList.get(0)!=null && playerStatsList.get(0).getMatch() != null) {
            Match match = leagueService.getMatchById(playerStatsList.get(0).getMatch().getMatchId());
            match.setPlayerStatsUpdated(true);
            leagueService.saveOrUpdateMatch(match);
        }
        model.put("message","playerstats.message.success");
        session.removeAttribute(PLAYERSTATS);
        return "admin/match/message";

    }

    private void addManOfTheMatch(List<PlayerStats> playerStatsList) {
        //TODO:Finish
        int highestPPI = 0;
        for(PlayerStats playerStats : playerStatsList){
            if(highestPPI < playerStats.getEaSportsPPI()){
                highestPPI = playerStats.getEaSportsPPI();
            }
        }

        for(PlayerStats playerStats : playerStatsList){
            if(highestPPI == playerStats.getEaSportsPPI()){
                playerStats.setManOfTheMatch(true);
            }
        }
    }

    @RequestMapping("/admin/match/deletePlayerStats")
    public void deletePlayerStats(@RequestParam(required = true, value = "tempStatsId") Integer tempStatsId, ModelMap model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<PlayerStats> playerStatsList = (List<PlayerStats>)session.getAttribute(PLAYERSTATS);
        PlayerStats playerStats = findPlayerStatsByTempId(playerStatsList,tempStatsId);
        playerStatsList.remove(playerStats);
        session.setAttribute(PLAYERSTATS,playerStatsList);
    }

    @RequestMapping("/admin/match/updatepoints")
    public String updatePoints(@RequestParam(required = true, value = "matchId") Integer matchId,ModelMap model, HttpServletRequest request ) {
        ruleService.updatePointsForMatch(matchId);
        matchControllerUtil.matchStats(model,request,matchId);
        return "admin/match/matchstats";
    }

    @RequestMapping("/admin/match/updatepointsforround")
    public String updatePointsForRound(@RequestParam(required = true, value = "roundId") Integer roundId,ModelMap model, HttpServletRequest request ) {
        ComparisonTerm comparisonTerm = new ComparisonTerm("leagueroundid", Operator.EQ, roundId);
        List<Match> matches = leagueService.getMatchBySearchTerm(comparisonTerm).getResults();
        for(Match match: matches){
            if(match.isPlayerStatsUpdated()){
                ruleService.updatePointsForMatch(match.getMatchId());
            }
        }
        model.put("message","playerstats.message.allsaved");
        return "admin/match/message";
    }

    private PlayerStats findPlayerStatsByTempId(List<PlayerStats> playerStatsList, int tempId){
        for(PlayerStats playerStats: playerStatsList){
            if(playerStats.getTemporarlyId() == tempId){
                return playerStats;
            }
        }
        return null;
    }
}