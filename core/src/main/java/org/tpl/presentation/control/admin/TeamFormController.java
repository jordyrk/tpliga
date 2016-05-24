package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;
import org.tpl.presentation.control.admin.util.TeamControllerUtil;
import org.tpl.business.model.validator.TeamValidator;/*
Created by jordyr, 08.okt.2010

*/
@Controller
@RequestMapping("/admin/team/edit")
public class TeamFormController {
     @Autowired
     LeagueService leagueService;

    @Autowired
    TeamValidator teamValidator;

    @Autowired
    TeamControllerUtil teamControllerUtil;

    @ModelAttribute(value="team")
    public Team getTeam(@RequestParam(required = false, value = "teamId") Integer teamId){

        if (teamId == null || teamId < 1) {
            Team team = new Team();
            return team;
        }
        else {
            return leagueService.getTeamById(teamId);
        }
    }


    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        return "admin/team/edit";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("team")Team team, BindingResult bindingResult, ModelMap model){
        teamValidator.validate(team, bindingResult);

        if(bindingResult.hasErrors()){
            return "admin/team/edit";
        }
        leagueService.saveOrUpdateTeam(team);
        teamControllerUtil.viewTeams(model);
       return "admin/team/viewteams";
    }
}
