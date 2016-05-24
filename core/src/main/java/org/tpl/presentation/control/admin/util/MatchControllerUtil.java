package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.*;
import org.tpl.business.model.comparator.PlayerComparator;
import org.tpl.business.service.ImportService;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;

import org.tpl.presentation.control.admin.MatchAdminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchControllerUtil {

    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ImportService importService;

    public void fixtureList(ModelMap model, int leagueRoundId){
        List<Match> matches = leagueService.getMatchByLeagueRoundId(leagueRoundId);
        model.put("leagueRoundId", leagueRoundId);
        model.put("matches", matches);
    }

    public void matchStats(ModelMap model, HttpServletRequest request, int matchId){
        HttpSession session = request.getSession();

        Match match = leagueService.getMatchById(matchId);

        List<PlayerStats> playerStats = leagueService.getPlayerStatsByMatchId(matchId);
        boolean isSaved = true;
        if(playerStats == null || playerStats.isEmpty()){
            isSaved = false;
            playerStats = importService.importPlayerStats(match);

        }
        session.setAttribute(MatchAdminController.PLAYERSTATS, playerStats);
        List<Player> players = playerService.getPlayersByTeamId(match.getHomeTeam().getTeamId());
        players.addAll(playerService.getPlayersByTeamId(match.getAwayTeam().getTeamId()));
        Collections.sort(players, new PlayerComparator());
        model.put("match", match);
        model.put("isSaved", isSaved);
        model.put("players", players);
        model.put("playerStats", playerStats);

    }

    private List<Player> findNewPlayers(List<PlayerStats> playerStatsList){
        List<Player> players = new ArrayList<Player>();
        for(PlayerStats playerStats: playerStatsList){
            if(playerStats.getPlayer().isNew()){
                players.add(playerStats.getPlayer());
            }
        }
        return players;
    }


    public void viewAllMatches(Integer seasonId, ModelMap model) {
        List<Match> matches = leagueService.getMatchBySeasonId(seasonId);
        model.put("matches",matches);
    }
}

