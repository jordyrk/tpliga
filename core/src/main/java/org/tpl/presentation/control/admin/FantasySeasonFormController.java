package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.validator.FantasySeasonValidator;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;
import org.tpl.presentation.control.propertyeditors.*;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 22:19:27
 */
@Controller
@RequestMapping("/admin/fantasyseason/editfantasyseason")
public class FantasySeasonFormController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyUtil fantasyUtil;

    @Autowired
    FantasySeasonValidator fantasySeasonValidator;

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @ModelAttribute(value="fantasyseason")
    public FantasySeason getFantasySeason(@RequestParam(required = false, value = "fantasySeasonId") Integer fantasySeasonId){

        if (fantasySeasonId == null || fantasySeasonId < 1) {

            return new FantasySeason();
        }
        else {
            return fantasyService.getFantasySeasonById(fantasySeasonId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        List<Season> seasonList = leagueService.getAllSeasons();
        model.put("seasons", seasonList);
        
        return "admin/fantasyseason/editfantasyseason";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("fantasyseason")FantasySeason fantasySeason,BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        fantasySeasonValidator.validate(fantasySeason, bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/fantasyseason/editfantasyseason";
        }
        fantasyService.saveOrUpdateFantasySeason(fantasySeason);
        fantasyUtil.viewFantasySeasons(model);
        return "admin/fantasyseason/viewfantasyseasons";
    }
     @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Season.class, new SeasonPropertyEditor(leagueService));

    }

}
