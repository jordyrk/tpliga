package org.tpl.presentation.control.league;

import no.kantega.publishing.security.SecuritySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.Season;
import org.tpl.business.model.Team;
import org.tpl.business.model.fantasy.PlayerUsage;
import org.tpl.business.model.search.*;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/*
Created by jordyr, 07.des.2010

*/

@Controller
public class PlayerController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerService playerService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyUtil fantasyUtil;

    @RequestMapping("/fantasy/playerlist")
    public String getPlayer(ModelMap model){
        List<Player> players = playerService.getAllPlayers();
        model.put("players", players);
        return "json/player";
    }
    @RequestMapping("/player/playersearch")
    public String playerSearch(
            ModelMap model,
            @RequestParam(required = false) String playername,
            @RequestParam(required = false) String teamname,
            @RequestParam(required = false) Integer minprice,
            @RequestParam(required = false) Integer maxprice,
            @RequestParam(required = false) String playerposition
    ){
        SearchTerm playerFirstNameTerm = null;
        SearchTerm playerLastNameTerm = null;
        SearchTerm teamFullNameTerm = null;
        SearchTerm teamShortNameTerm = null;
        SearchTerm minPriceTerm = null;
        SearchTerm maxPriceTerm = null;
        SearchTerm positionTerm = null;
        if(playername != null){
            playerFirstNameTerm = new ComparisonTerm("firstname", Operator.LIKE, "%" + playername + "%");
            playerLastNameTerm = new ComparisonTerm("lastname", Operator.LIKE, "%" + playername + "%");
        }
        if(teamname != null){
            teamShortNameTerm = new ComparisonTerm("shortname", Operator.LIKE, "%" + teamname + "%");
            teamFullNameTerm = new ComparisonTerm("fullname", Operator.LIKE, "%" + teamname + "%");
        }
        if(minprice != null){
            minPriceTerm = new ComparisonTerm("price", Operator.GE, minprice);

        }
        if(maxprice != null && maxprice > 0){
            maxPriceTerm = new ComparisonTerm("price", Operator.LE, maxprice);

        }
        if(playerposition != null){
            String position = null;
            try{
                position = PlayerPosition.valueOf(playerposition).toString();
            }catch(Exception e){

            }
            if(position != null){
                positionTerm = new ComparisonTerm("position", Operator.LIKE, position);
            }

        }
        SearchTerm playerTerm = createAndOrTerm(playerFirstNameTerm, playerLastNameTerm, false);
        SearchTerm teamTerm = createAndOrTerm(teamShortNameTerm, teamFullNameTerm, false);
        SearchTerm priceTerm =  createAndOrTerm(minPriceTerm, maxPriceTerm, true);
        SearchTerm playerAndTeamTerm = createAndOrTerm(playerTerm, teamTerm, true);
        SearchTerm priceAndPositionTerm = createAndOrTerm(priceTerm, positionTerm, true);
        SearchTerm finalTerm = createAndOrTerm(playerAndTeamTerm, priceAndPositionTerm, true);
        List<Player> players = playerService.getPlayerBySearchTerm(finalTerm).getResults();
        model.put("players", players);

        return "json/player";

    }

    @RequestMapping("/player/getplayersForPosition")
    public String getJsonPlayersForPosition(ModelMap model, @RequestParam(required = false) String playerposition){
        Season defaultSeason = leagueService.getDefaultSeason();
        List<Team> teams = leagueService.getTeamsInSeason(defaultSeason.getSeasonId());
        OrTerm orTerm = new OrTerm();
        for(Team team: teams){
            ComparisonTerm comparisonTerm = new ComparisonTerm("team_id", Operator.EQ, team.getTeamId());
            orTerm.addTerm(comparisonTerm);
        }
        SearchTerm positionTerm = null;
        if(playerposition != null){
            String position = null;
            try{
                position = PlayerPosition.valueOf(playerposition).toString();
            }catch(Exception e){

            }
            if(position != null){
                positionTerm = new ComparisonTerm("position", Operator.LIKE, position);
            }

        }
        AndTerm andTerm = new AndTerm(positionTerm,orTerm);
        List<Player> players = playerService.getPlayerBySearchTerm(andTerm).getResults();
        model.put("players", players);
        return "json/player";
    }

    @RequestMapping(value={"/player/getplayersForTeam","/open/getplayersForTeam"})
    public String getJsonPlayersForTeam(@RequestParam(required = true, value = "teamId") Integer teamId, ModelMap model, HttpServletRequest request) {
        if(fantasyUtil.hasRole(SecuritySession.getInstance(request),"tpladmin","admin")){
            List<Player> players = playerService.getPlayersByTeamId(teamId);
            model.put("players", players);
        }else{
            ComparisonTerm teamTerm = new ComparisonTerm("team_id",Operator.EQ,teamId);
            ComparisonTerm positionTerm = new ComparisonTerm("position",Operator.NOT,PlayerPosition.UNKNOWN.toString());
            List<Player> players = playerService.getPlayerBySearchTerm(new AndTerm(teamTerm, positionTerm)).getResults();
            model.put("players", players);
        }

        return "json/player";
    }

    @RequestMapping(value={"/open/getplayersForTeamFilterUnkown"})
    public String getJsonPlayersForTeamFilterUnknown(@RequestParam(required = true, value = "teamId") Integer teamId, ModelMap model) {

        ComparisonTerm teamIdTerm = new ComparisonTerm("team_id",Operator.EQ,teamId);
        ComparisonTerm defenderPositionTerm = new ComparisonTerm("position",Operator.LIKE,PlayerPosition.DEFENDER.toString());
        ComparisonTerm goalkeeperPositionTerm = new ComparisonTerm("position",Operator.LIKE,PlayerPosition.GOALKEEPER.toString());
        ComparisonTerm midfielderPositionTerm = new ComparisonTerm("position",Operator.LIKE,PlayerPosition.MIDFIELDER.toString());
        ComparisonTerm strikerPositionTerm = new ComparisonTerm("position",Operator.LIKE,PlayerPosition.STRIKER.toString());
        OrTerm orTerm = new OrTerm();
        orTerm.addTerm(defenderPositionTerm);
        orTerm.addTerm(strikerPositionTerm);
        orTerm.addTerm(midfielderPositionTerm);
        orTerm.addTerm(goalkeeperPositionTerm);
        AndTerm andTerm = new AndTerm(teamIdTerm,orTerm);
        List<Player> players = playerService.getPlayerBySearchTerm(andTerm).getResults();
        model.put("players", players);
        return "json/player";
    }

    @RequestMapping(value={"/player/mostusedplayers"})
    public String getMostUsedPlayers(ModelMap model){
        List<PlayerUsage> mostUsedPlayers = playerService.getMostUsedPlayers(10, fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        model.put("players", mostUsedPlayers);
        model.put("isplayerusage", true);
        return "json/player";
    }

    @RequestMapping(value={"/player/mostusedplayersinround"})
    public String getMostUsedPlayersInRound(ModelMap model){
        List<PlayerUsage> mostUsedPlayersInRound = playerService.getMostUsedPlayersForRound(10, fantasyService.getCurrentFantasyRoundBySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId()).getFantasyRoundId());
        model.put("players", mostUsedPlayersInRound);
        model.put("isplayerusage", true);
        return "json/player";
    }

    @RequestMapping(value={"/player/mostusedplayersforteam"})
    public String getMostUsedPlayersForTeam(HttpServletRequest request,ModelMap model){
        List<PlayerUsage> mostUsedPlayersForTeam = playerService.getMostUsedPlayersForTeam(10, fantasyUtil.getFantasyTeamFromRequest(request).getTeamId(), fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        model.put("players", mostUsedPlayersForTeam);
        model.put("isplayerusage", true);
        return "json/player";
    }

    @RequestMapping(value={"/player/mostusedplayersasteamforteam"})
    public String getMostUsedPlayersAsTeamForTeam(HttpServletRequest request,ModelMap model){
        List<PlayerUsage> mostUsedPlayersForTeam = playerService.getMostUsedPlayersAsTeamForTeam(fantasyUtil.getFantasyTeamFromRequest(request).getTeamId(), fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        model.put("players", mostUsedPlayersForTeam);
        model.put("isplayerusage", true);
        return "json/player";
    }

    private SearchTerm createAndOrTerm(SearchTerm st1,SearchTerm st2 , boolean andTerm){
        if(st1 == null && st2 == null) return null;
        if(st1 == null && st2 != null) return st2;
        if(st1 != null && st2 == null) return st1;
        if(andTerm)return new AndTerm(st1, st2);
        else return new OrTerm(st1,st2);
    }
}


