package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyCompetitionService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/fantasycompetition/editfantasycompetition")
public class FantasyCompetitionFormController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    @Autowired
    FantasyUtil fantasyUtil;



    @ModelAttribute(value="fantasycompetition")
    public FantasyCompetition getFantasyCompetition(@RequestParam(required = false, value = "fantasyCompetitionId") Integer fantasyCompetitionId){

        if (fantasyCompetitionId == null || fantasyCompetitionId < 1) {

            return new FantasyCompetition();
        }
        else {
            return fantasyCompetitionService.getFantasyCompetitionById(fantasyCompetitionId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){

        return "admin/fantasycompetition/editfantasycompetition";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("fantasycompetition")FantasyCompetition fantasyCompetition,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "admin/fantasycompetition/editfantasycompetition";
        }
        fantasyCompetitionService.saveOrUpdateFantasyCompetition(fantasyCompetition);
        
        return "admin/fantasycompetition/viewfantasycompetitions";
    }
     @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {


    }

}
