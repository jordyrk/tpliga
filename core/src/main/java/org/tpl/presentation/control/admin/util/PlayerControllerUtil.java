package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.model.SumPlayerStats;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;

import java.util.List;/*
Created by jordyr, 09.okt.2010

*/

public class PlayerControllerUtil {

    @Autowired
    LeagueService leagueService;

    public void viewPlayers(ModelMap model){
        addAllTeams(model);

    }

    private void addAllTeams(ModelMap model) {
        List<Team> teams = leagueService.getAllTeams();
        model.put("teams", teams);

    }

    public void addPlayerStats(ModelMap model, int playerId, int seasonId){
        List<PlayerStats> playerStats = leagueService.getPlayerStatsForPlayerAndSeason(playerId, seasonId);
        SumPlayerStats sumPlayerStats = new SumPlayerStats();
        sumPlayerStats.add(playerStats);

        model.put("sumPlayerStats", sumPlayerStats);
        model.put("playerStats", playerStats);
    }
}

