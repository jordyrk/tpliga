package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.*;
import org.tpl.business.model.validator.PlayerValidator;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.presentation.control.admin.util.MatchControllerUtil;
import org.tpl.presentation.control.propertyeditors.MatchPropertyEditor;
import org.tpl.presentation.control.propertyeditors.PlayerPropertyEditor;
import org.tpl.presentation.control.propertyeditors.TeamPropertyEditor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/playerstats/edit")
public class PlayerStatsFormController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    MatchControllerUtil matchControllerUtil;

    @Autowired
    PlayerService playerService;


    @ModelAttribute(value="playerStats")
    public PlayerStats getPlayerStats(@RequestParam(required = false, value = "playerId") Integer playerId, @RequestParam(required = false, value = "matchId") Integer matchId){

        if (playerId == null || playerId < 1 || matchId == null || matchId < 1){
            return new PlayerStats();
        }
        else{
            return leagueService.getPlayerStatsByMatchAndPlayer(playerId, matchId);
        }

    }


    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        List<Team> availableTeams = leagueService.getAllTeams();
        PlayerStats playerStats = (PlayerStats) model.get("playerStats");
        List<Player> availablePlayers = playerService.getPlayersByTeamId(playerStats.getPlayer().getTeam().getTeamId());
        model.put("availableTeams", availableTeams);
        model.put("availablePlayers", availablePlayers);
        return "admin/playerstats/edit";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("playerStats") PlayerStats playerStats,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "admin/playerstats/edit";
        }
        leagueService.saveOrUpdatePlayerStats(playerStats);
       matchControllerUtil.matchStats(model,request,playerStats.getMatch().getMatchId());
        return "admin/match/matchstats";
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Player.class, new PlayerPropertyEditor(playerService));
        dataBinder.registerCustomEditor(Match.class, new MatchPropertyEditor(leagueService));
        dataBinder.registerCustomEditor(Team.class, new TeamPropertyEditor(leagueService));
    }


}
