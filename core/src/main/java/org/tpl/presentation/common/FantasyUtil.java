package org.tpl.presentation.common;

import no.kantega.publishing.security.SecuritySession;
import no.kantega.publishing.security.data.Role;
import no.kantega.publishing.security.data.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.FantasyCompetitionService;
import org.tpl.business.service.fantasy.FantasyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
/*
Created by jordyr, 06.des.2010

*/

public class FantasyUtil {
    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    private static final String FANTASY_TEAM_ROUND = "fantasyteamround";

    public FantasyManager getFantasyManagerFromRequest(HttpServletRequest request){
        User currentUser = getUserFromRequest(request);
        return fantasyService.getFantasyManagerByUserId(currentUser.getId());
    }

    public FantasyTeam getFantasyTeamFromRequest(HttpServletRequest request){
        FantasyManager fantasyManager = getFantasyManagerFromRequest(request);
        List<FantasyTeam> fantasyTeams = fantasyService.getFantasyTeamByManagerId(fantasyManager.getManagerId());
        if(fantasyTeams.size() > 0){
            return fantasyTeams.get(0);
        }
        return null;
    }

    public boolean hasRole(SecuritySession securitySession, String... roleArray){
        if(roleArray == null || roleArray.length == 0){
            return false;
        }
        if(securitySession.isLoggedIn()){
            HashMap<String,Role> roles = securitySession.getUser().getRoles();
            for(String key:roles.keySet()){
                for(String role:roleArray ){
                    if(key.equalsIgnoreCase(role)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addFantasyTeamRoundToSession(HttpSession session, FantasyTeamRound fantasyTeamRound){
        session.setAttribute(FANTASY_TEAM_ROUND, fantasyTeamRound);
    }

    public FantasyTeamRound getFantasyTeamRoundFromSession(HttpSession session){
        FantasyTeamRound fantasyTeamRound = (FantasyTeamRound)session.getAttribute(FANTASY_TEAM_ROUND);
        return fantasyTeamRound;
    }

    public User getUserFromRequest(HttpServletRequest request){
        SecuritySession securitySession = SecuritySession.getInstance(request);
        return securitySession.getUser();
    }

    public void overviewFantasyCompetitions(ModelMap model){
        addAllFantasySeasons(model);
    }

    public void viewFantasyCompetitions(ModelMap model, int fantasySeasonId){
        addAllFantasyCompetitions(model, fantasySeasonId);
    }

    public void viewFantasySeasons(ModelMap model){
        addAllFantasySeasons(model);
    }

    public void viewAllFantasyRounds(ModelMap model, int fantasySeasonId){
        addAllFantasyRounds(model,fantasySeasonId);
        addFantasySeason(model, fantasySeasonId);
    }

    public void viewFantasyRound(ModelMap model, int fantasySeasonId, int fantasyRoundId){
        addFantasyRound(model, fantasyRoundId);
        addFantasySeason(model, fantasySeasonId);
        addSeason(model, fantasySeasonId);
    }


    private void addAllFantasySeasons(ModelMap model){
        List<FantasySeason> fantasySeasonList = fantasyService.getAllFantasySeasons();
        model.put("fantasyseasons", fantasySeasonList);
    }

    private void addFantasySeason(ModelMap model, int fantasySeasonId){
        FantasySeason fantasySeason = fantasyService.getFantasySeasonById(fantasySeasonId);
        model.put("fantasyseason", fantasySeason);

    }

    private void addAllFantasyCompetitions(ModelMap model, int fantasySeasonId){
        List<FantasyCompetition> list = fantasyCompetitionService.getFantasyCompetitionBySeasonId(fantasySeasonId);
        model.put("fantasycompetitions", list);
    }

    private void addAllFantasyRounds(ModelMap model, int fantasySeasonId){
        model.put("fantasyRounds",fantasyService.getFantasyRoundByFantasySeasonId(fantasySeasonId));
    }

    private void addFantasyRound(ModelMap model, int fantasyRoundId){
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        model.put("fantasyRound", fantasyRound);
    }

    private void addSeason(ModelMap model, int fantasySeasonId) {
        FantasySeason fantasySeason = fantasyService.getFantasySeasonById(fantasySeasonId);
        Season season = fantasySeason.getSeason();
        model.put("season", season);
    }

}
