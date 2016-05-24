package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.League;
import org.tpl.business.service.LeagueService;
import org.tpl.presentation.control.admin.util.LeagueControllerUtil;
import org.tpl.business.model.validator.LeagueValidator;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/league/edit")
public class LeagueFormController {
    @Autowired
    LeagueService leagueService;
    
    @Autowired
    LeagueValidator leagueValidator;

      @Autowired
      LeagueControllerUtil leagueControllerUtil;

    @ModelAttribute(value="league")
    public League getLeague(@RequestParam(required = false, value = "leagueId") Integer leagueId){
        
        if (leagueId == null || leagueId < 1){
            return new League();
        }
        else{
            return leagueService.getLeagueById(leagueId);
        }

    }


    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        return "admin/league/edit";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("league")League league,BindingResult bindingResult, ModelMap model){
        leagueValidator.validate(league, bindingResult);

        if(bindingResult.hasErrors()){
            return "admin/league/edit";
        }
        leagueService.saveOrUpdateLeague(league);
        leagueControllerUtil.viewLeagues(model);
        return "admin/league/viewleagues";
    }

  
}
