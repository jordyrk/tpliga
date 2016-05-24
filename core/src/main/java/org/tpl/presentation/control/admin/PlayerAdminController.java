/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tpl.business.model.Match;
import org.tpl.business.model.Player;
import org.tpl.business.service.ImportService;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.business.service.fantasy.exception.PlayerDeleteException;
import org.tpl.presentation.control.admin.util.PlayerControllerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Koren
 */
@Controller
public class PlayerAdminController {

    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ImportService importService;

    @Autowired
    PlayerControllerUtil playerControllerUtil;

    @RequestMapping("/admin/player/viewplayers")
    public String viewPlayers(ModelMap model) {
        playerControllerUtil.viewPlayers(model);
        return "admin/player/viewplayers";
    }

    @RequestMapping("/admin/player/search")
    public String searchForPlayers(@RequestParam String playerQuery,ModelMap model) {
        List<Player> players = playerService.searchForPlayer(playerQuery);
        model.put("players", players);
        model.put("editPlayer", true);
        return "admin/player/playerlist";
    }

    @RequestMapping("/admin/player/deleteplayer")
    public String deletePlayer(@RequestParam(required = true, value = "playerId")int playerId, ModelMap model) {
        try {
            playerService.deletePlayer(playerId);
            model.put("message", "Player with id " + playerId + " is deleted");
        } catch (PlayerDeleteException e) {
            model.put("message", e.getMessage());
        }
        playerControllerUtil.viewPlayers(model);
        return "admin/player/viewplayers";
    }

    @RequestMapping("/admin/player/viewplayersForTeam")
    public String viewPlayers(@RequestParam(required = true, value = "teamId") Integer teamId, ModelMap model) {
        List<Player> players = playerService.getPlayersByTeamId(teamId);
        model.put("players", players);
        model.put("editPlayer", true);
        return "admin/player/playerlist";
    }

    @RequestMapping(value="/admin/player/getplayer", method = RequestMethod.GET)
    public String getJSONPlayer(ModelMap model, @RequestParam Integer playerId){
        Player player = playerService.getPlayerById(playerId);
        List<Player> players = new ArrayList<Player>();
        players.add(player);
        model.put("players", players);
        return "json/player";
    }

    @RequestMapping("/admin/player/randomizePlayerPrice")
    public void randomizePlayerPrice(){
        List<Player> players = playerService.getAllPlayers();

        Random random = new Random();
        for(Player player: players){
            int price = random.nextInt(15)*1000000;
            player.setPrice(price);
            playerService.saveOrUpdatePlayer(player);
        }

    }

}
