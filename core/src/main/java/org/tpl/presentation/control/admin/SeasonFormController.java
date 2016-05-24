package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.League;
import org.tpl.business.model.Season;
import org.tpl.business.model.Team;
import org.tpl.business.model.validator.SeasonValidator;
import org.tpl.business.service.LeagueService;
import org.tpl.presentation.control.admin.util.SeasonControllerUtil;
import org.tpl.presentation.control.propertyeditors.DateEditor;
import org.tpl.presentation.control.propertyeditors.LeaguePropertyEditor;
import org.tpl.presentation.control.propertyeditors.TeamPropertyEditor;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/season/edit")
public class SeasonFormController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    SeasonValidator seasonValidator;

    @Autowired
    SeasonControllerUtil seasonControllerUtil;
    
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @ModelAttribute(value="season")
    public Season getSeason(@RequestParam(required = false, value = "seasonId") Integer seasonId){

        if (seasonId == null || seasonId < 1) {

            return new Season();
        }
        else {
            return leagueService.getSeasonById(seasonId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        List<Team> availableTeams = leagueService.getAllTeams();
        List<League> availableLeagues = leagueService.getAllLeagues();
        model.put("availableTeams", availableTeams);
        model.put("availableLeagues", availableLeagues);
        return "admin/season/edit";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("season")Season season,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        seasonValidator.validate(season, bindingResult);

        if(bindingResult.hasErrors()){
            return "admin/season/edit";
        }
        leagueService.saveOrUpdateSeason(season);
        seasonControllerUtil.viewSeasons(model);
        return "admin/season/viewseasons";
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        Locale locale = new Locale("NO");
        NumberFormat numberFormat = NumberFormat.getIntegerInstance(locale);
        dataBinder.registerCustomEditor(java.util.List.class,new CustomCollectionEditor(java.util.List.class));
        dataBinder.registerCustomEditor(Date.class, new DateEditor(dateFormat, true));
        dataBinder.registerCustomEditor(Team.class, new TeamPropertyEditor(leagueService));
        dataBinder.registerCustomEditor(League.class, new LeaguePropertyEditor(leagueService));


    }
}
