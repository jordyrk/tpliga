package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Match;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;
import org.tpl.presentation.control.propertyeditors.DateEditor;
import org.tpl.presentation.control.propertyeditors.LeagueRoundPropertyEditor;
import org.tpl.presentation.control.propertyeditors.TeamPropertyEditor;
import org.tpl.business.model.validator.MatchValidator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/match/edit")
public class MatchFormController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    MatchValidator matchValidator;

    private DateFormat dateFormat = new SimpleDateFormat("HH.mm.dd.MM.yyyy");

    @ModelAttribute(value="match")
    public Match getMatch(@RequestParam(required = false, value = "matchId") Integer matchId){
        if (matchId == null || matchId < 1){
            return new Match();
        }
        else{
            return leagueService.getMatchById(matchId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){

        List<Team> teams = leagueService.getAllTeams();
        List<LeagueRound> leagueRounds = leagueService.getLeagueRoundsBySeasonId(leagueService.getDefaultSeason().getSeasonId()); 
        model.put("leagueRounds",leagueRounds);
        model.put("teams", teams);

        return "admin/match/edit";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("match") Match match, BindingResult bindingResult, ModelMap model){
        matchValidator.validate(match, bindingResult);

        if(bindingResult.hasErrors()){
            return setupForm(model);
        }
        leagueService.saveOrUpdateMatch(match);


        return "/admin/match/matchoverview=?seasonId="+ match.getLeagueRound().getSeason().getSeasonId() + "&leagueRoundId=" + match.getLeagueRound().getLeagueRoundId();
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class, new DateEditor(dateFormat, true));
        dataBinder.registerCustomEditor(Team.class, new TeamPropertyEditor(leagueService));
        dataBinder.registerCustomEditor(LeagueRound.class, new LeagueRoundPropertyEditor(leagueService));
    }
}
