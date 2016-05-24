package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.presentation.control.admin.util.PlayerControllerUtil;
import org.tpl.presentation.control.propertyeditors.EnumPropertyEditor;
import org.tpl.presentation.control.propertyeditors.TeamPropertyEditor;
import org.tpl.business.model.validator.PlayerValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27@RequestMapping(value={"/changepassword.html","/admin/changepass.html"})
 */
@Controller
@RequestMapping(value={"/admin/player/edit","/player/edit"})

public class PlayerFormController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerValidator playerValidator;

    @Autowired
    PlayerControllerUtil playerControllerUtil;

    @ModelAttribute(value="player")
    public Player getPlayer(@RequestParam(required = false, value = "playerId") Integer playerId){

        if (playerId == null || playerId < 1){
            return new Player();
        }
        else{
            return playerService.getPlayerById(playerId);
        }

    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request){

        List<Team> availableTeams = leagueService.getAllTeams();
        model.put("availableTeams", availableTeams);
        model.put("availablePositions", PlayerPosition.values());
        if(request.getPathInfo().contains("admin")){
            return "admin/player/edit";
        }
        else{
            return "fantasy/player/editplayer";
        }
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("player") Player player,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        playerValidator.validate(player, bindingResult);

        if(bindingResult.hasErrors()){
            if(request.getPathInfo().contains("admin")){
                return "admin/player/edit";
            }
            else{
                return "fantasy/player/editplayer";
            }
        }
        playerService.saveOrUpdatePlayer(player);
        model.put("player", player);
        if(request.getPathInfo().contains("admin")){
            playerControllerUtil.viewPlayers(model);
            return "admin/player/viewplayers";
        }
        else{
            playerControllerUtil.addPlayerStats(model, player.getPlayerId(), leagueService.getDefaultSeason().getSeasonId());
            return "fantasy/player/playerstats";
        }
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(PlayerPosition.class, new EnumPropertyEditor(PlayerPosition.class));
        dataBinder.registerCustomEditor(Team.class, new TeamPropertyEditor(leagueService));
    }
}
