package org.tpl.presentation.control.admin;

import no.kantega.commons.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.validator.FantasyCupValidator;
import org.tpl.business.service.fantasy.FantasyCupService;
import org.tpl.business.service.fantasy.FantasyMatchService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;
import org.tpl.presentation.control.admin.util.FantasyCupControllerUtil;
import org.tpl.presentation.control.propertyeditors.FantasyRoundPropertyEditor;
import org.tpl.presentation.control.propertyeditors.FantasySeasonPropertyEditor;
import org.tpl.presentation.control.propertyeditors.FantasyTeamPropertyEditor;

import java.util.List;

@Controller
public class FantasyCupAdminController {
    @Autowired
    FantasyCupControllerUtil fantasyCupControllerUtil;

    @Autowired
    FantasyCupService fantasyCupService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyCupValidator fantasyCupValidator;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @RequestMapping("/admin/fantasycup/viewfantasycups")
    public String viewCups(ModelMap model) {
        fantasyCupControllerUtil.viewFantasyCups(model);
        return "admin/fantasycup/viewfantasycups";
    }

    @ModelAttribute(value="fantasycup")
    public FantasyCup getFantasyCup(@RequestParam(required = false, value = "fantasyCupId") Integer fantasyCupId){
        if (fantasyCupId == null || fantasyCupId < 1){
            return new FantasyCup();
        }
        else{
            return fantasyCupService.getFantasyCupById(fantasyCupId);
        }
    }

    @RequestMapping(value ="/admin/fantasycup/editfantasycup", method= RequestMethod.GET)
    public String setupForm(@ModelAttribute("fantasycup")FantasyCup fantasyCup, ModelMap model){
        List<FantasySeason> fantasySeasonList= fantasyService.getAllFantasySeasons();
        model.put("fantasySeasonList", fantasySeasonList);
        List<FantasyTeam> fantasyTeamList = fantasyService.getAllFantasyTeams();
        model.put("availableFantasyTeamList", fantasyTeamList);
        if(! fantasyCup.isNew() && ! fantasyCup.getFantasySeason().isNew()){
            List<FantasyRound> fantasyRoundList = fantasyService.getFantasyRoundByFantasySeasonId(fantasyCup.getFantasySeason().getFantasySeasonId());
            model.put("fantasyRoundList", fantasyRoundList);
        }
        return "admin/fantasycup/editfantasycup";
    }

    @RequestMapping(value ="/admin/fantasycup/editfantasycup", method= RequestMethod.POST)
    public String submit(@ModelAttribute("fantasycup")FantasyCup fantasyCup,BindingResult bindingResult, ModelMap model){
        fantasyCupValidator.validate(fantasyCup, bindingResult);
        if(bindingResult.hasErrors()){
            return "admin/fantasycup/editfantasycup";
        }
        fantasyCupService.saveOrUpdateCup(fantasyCup);
        fantasyCupControllerUtil.viewFantasyCups(model);
        return "admin/fantasycup/viewfantasycups";
    }

    @RequestMapping(value ="/admin/fantasycup/deletefantasycup")
    public String deleteCup(@RequestParam Integer fantasyCupId, ModelMap model){

        fantasyCupService.deleteFantasyCup(fantasyCupId);
        fantasyCupControllerUtil.viewFantasyCups(model);
        return "admin/fantasycup/viewfantasycups";
    }

    @RequestMapping(value ="/admin/fantasycup/createfixtures")
    public String createFixtures(@RequestParam Integer fantasyCupId, ModelMap model){

         if(fantasyCupId > 0){
             boolean hasFixtures = fantasyCupService.hasFixtures(fantasyCupId);
                if( ! hasFixtures){
                    try {
                        fantasyCupService.createFixtureList(fantasyCupId);
                    } catch (FixtureListCreationException e) {
                        Log.error(this.getClass().getName(),e.getMessage());
                    }
                }
                List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchByCupId(fantasyCupId);
                model.put("fantasyMatchList",fantasyMatchList);
                return "admin/fantasyleague/viewmatchesinleague";
         }
        return "admin/fantasycup/viewmatchesincup";
    }


    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(FantasySeason.class, new FantasySeasonPropertyEditor(fantasyService));
        dataBinder.registerCustomEditor(FantasyRound.class, new FantasyRoundPropertyEditor(fantasyService));
        dataBinder.registerCustomEditor(FantasyTeam.class, new FantasyTeamPropertyEditor(fantasyService));
    }
}
