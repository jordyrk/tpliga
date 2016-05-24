package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.Season;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.validator.FantasyRoundValidator;
import org.tpl.business.model.validator.FantasySeasonValidator;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;
import org.tpl.presentation.control.propertyeditors.DateEditor;
import org.tpl.presentation.control.propertyeditors.SeasonPropertyEditor;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/admin/fantasyround/editfantasyround")
public class FantasyRoundFormController {

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyRoundValidator fantasyRoundValidator;

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @ModelAttribute(value="fantasyround")
    public FantasyRound getFantasyRound(@RequestParam(required = false, value = "fantasyRoundId") Integer fantasyRoundId){

        if (fantasyRoundId == null || fantasyRoundId < 1) {

            return new FantasyRound();
        }
        else {
            return fantasyService.getFantasyRoundById(fantasyRoundId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){

        return "admin/fantasyround/editfantasyround";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("fantasyround")FantasyRound fantasyRound,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        fantasyRoundValidator.validate(fantasyRound, bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/fantasyround/editfantasyround";
        }
        fantasyService.saveOrUpdateFantasyRound(fantasyRound);

        return "admin/fantasyseason/viewfantasyseasons";
    }
     @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class, new DateEditor(dateFormat, true));

    }

}
