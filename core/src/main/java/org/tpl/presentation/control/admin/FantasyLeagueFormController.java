package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.League;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.validator.FantasyLeagueValidator;
import org.tpl.business.model.validator.LeagueValidator;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyLeagueService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.control.admin.util.FantasyLeagueControllerUtil;
import org.tpl.presentation.control.admin.util.LeagueControllerUtil;
import org.tpl.presentation.control.propertyeditors.FantasyRoundPropertyEditor;
import org.tpl.presentation.control.propertyeditors.FantasySeasonPropertyEditor;
import org.tpl.presentation.control.propertyeditors.FantasyTeamPropertyEditor;

import java.util.List;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/fantasyleague/editfantasyleague")
public class FantasyLeagueFormController {
    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyLeagueValidator fantasyLeagueValidator;

    @Autowired
    FantasyLeagueControllerUtil fantasyLeagueControllerUtil;


    @ModelAttribute(value="fantasyleague")
    public FantasyLeague getFantasyLeague(@RequestParam(required = false, value = "fantasyLeagueId") Integer fantasyLeagueId){

        if (fantasyLeagueId == null || fantasyLeagueId < 1){
            return new FantasyLeague();
        }
        else{
            return fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(@ModelAttribute("fantasyleague")FantasyLeague fantasyLeague, ModelMap model){
        List<FantasySeason> fantasySeasonList= fantasyService.getAllFantasySeasons();
        model.put("fantasySeasonList", fantasySeasonList);
        List<FantasyTeam> fantasyTeamList = fantasyService.getAllFantasyTeams();
        model.put("availableFantasyTeamList", fantasyTeamList);
        if(! fantasyLeague.isNew() && ! fantasyLeague.getFantasySeason().isNew()){
            List<FantasyRound> fantasyRoundList = fantasyService.getFantasyRoundByFantasySeasonId(fantasyLeague.getFantasySeason().getFantasySeasonId());
            model.put("fantasyRoundList", fantasyRoundList);
        }
        return "admin/fantasyleague/editfantasyleague";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("fantasyleague")FantasyLeague fantasyLeague,BindingResult bindingResult, ModelMap model){
        fantasyLeagueValidator.validate(fantasyLeague, bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/fantasyleague/editfantasyleague";
        }
        fantasyLeagueService.saveOrUpdateLeague(fantasyLeague);
        fantasyLeagueControllerUtil.viewFantasyLeagues(model);
        return "admin/fantasyleague/viewfantasyleagues";
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(FantasySeason.class, new FantasySeasonPropertyEditor(fantasyService));
        dataBinder.registerCustomEditor(FantasyRound.class, new FantasyRoundPropertyEditor(fantasyService));
        dataBinder.registerCustomEditor(FantasyTeam.class, new FantasyTeamPropertyEditor(fantasyService));
    }
}
