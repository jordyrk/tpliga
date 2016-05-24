package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.validator.FantasyCupGroupValidator;
import org.tpl.business.model.validator.FantasyCupValidator;
import org.tpl.business.service.fantasy.FantasyCupGroupService;
import org.tpl.business.service.fantasy.FantasyCupService;
import org.tpl.business.service.fantasy.FantasyMatchService;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;
import org.tpl.presentation.control.admin.util.FantasyCupControllerUtil;
import org.tpl.presentation.control.propertyeditors.FantasyCupPropertyEditor;
import org.tpl.presentation.control.propertyeditors.FantasyRoundPropertyEditor;
import org.tpl.presentation.control.propertyeditors.FantasySeasonPropertyEditor;
import org.tpl.presentation.control.propertyeditors.FantasyTeamPropertyEditor;

import java.util.List;

@Controller
public class FantasyCupGroupAdminController {
    @Autowired
    FantasyCupControllerUtil fantasyCupControllerUtil;

    @Autowired
    FantasyCupGroupService fantasyCupGroupService;

    @Autowired
    FantasyCupService fantasyCupService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyCupGroupValidator fantasyCupGroupValidator;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @RequestMapping("/admin/fantasycup/viewfantasycupgroups")
    public String viewCupGroups(@RequestParam(required = true) Integer fantasyCupId, ModelMap model) {
        fantasyCupControllerUtil.viewFantasyCupGroups(model, fantasyCupId);
        return "admin/fantasycup/viewfantasycupgroups";
    }

    @ModelAttribute(value="fantasycupgroup")
    public FantasyCupGroup getFantasyCup(@RequestParam(required = false, value = "fantasyCupGroupId") Integer fantasyCupGroupId){

        if (fantasyCupGroupId == null || fantasyCupGroupId < 1){
            return new FantasyCupGroup();
        }
        else{
            return fantasyCupGroupService.getFantasyCupGroupById(fantasyCupGroupId);
        }
    }

    @RequestMapping(value ="/admin/fantasycup/editfantasycupgroup", method= RequestMethod.GET)
    public String setupForm(@ModelAttribute("fantasycupgroup")FantasyCupGroup fantasyCupGroup, ModelMap model){
        addElementsNecesseryForForm(model, fantasyCupGroup);

        return "admin/fantasycup/editfantasycupgroup";
    }

    @RequestMapping(value ="/admin/fantasycup/editfantasycupgroup", method= RequestMethod.POST)
    public String submit(@ModelAttribute("fantasycupgroup")FantasyCupGroup fantasyCupGroup,BindingResult bindingResult, ModelMap model){
        fantasyCupGroupValidator.validate(fantasyCupGroup, bindingResult);
        if(bindingResult.hasErrors()){
            addElementsNecesseryForForm(model, fantasyCupGroup);
            return "admin/fantasycup/editfantasycupgroup";
        }
        fantasyCupGroupService.saveOrUpdateCupGroup(fantasyCupGroup);
        fantasyCupControllerUtil.viewFantasyCupGroups(model,fantasyCupGroup.getFantasyCup().getId());
        return "admin/fantasycup/viewfantasycupgroups";
    }

    @RequestMapping("/admin/fantasycupgroup/createfixtures")
    public String createFixtureList(@RequestParam(required = true, value = "fantasyCupGroupId") Integer fantasyCupGroupId,ModelMap model) {
        FantasyCupGroup  fantasyCupGroup = fantasyCupGroupService.getFantasyCupGroupById(fantasyCupGroupId);
        if(fantasyCupGroupId > 0){
            boolean hasFixtures = fantasyCupGroupService.hasFixtures(fantasyCupGroupId);
            try{
                if( ! hasFixtures){
                    fantasyCupGroupService.createFixtureList(fantasyCupGroupId);
                }
                List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchByCupGroupId(fantasyCupGroupId);
                model.put("fantasyMatchList",fantasyMatchList);
                return "admin/fantasycup/viewmatchesincupgroup";

            }catch (FixtureListCreationException e){
                model.put("errormessage", e.getMessage());
            }
        }
        return viewCupGroups(fantasyCupGroup.getFantasyCup().getId(),model);
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(FantasyRound.class, new FantasyRoundPropertyEditor(fantasyService));
        dataBinder.registerCustomEditor(FantasyTeam.class, new FantasyTeamPropertyEditor(fantasyService));
        dataBinder.registerCustomEditor(FantasyCup.class, new FantasyCupPropertyEditor(fantasyCupService));
    }

    private void addElementsNecesseryForForm(ModelMap model, FantasyCupGroup fantasyCupGroup){


        List<FantasyTeam> fantasyTeamList = fantasyService.getFantasyTeamByFantasyCupId(fantasyCupGroup.getFantasyCup().getId());
        List<FantasyCupGroup> fantasyCupGroupList = fantasyCupGroupService.getAllFantasyCupGroups(fantasyCupGroup.getFantasyCup().getId());
        for(FantasyCupGroup fgCup: fantasyCupGroupList){
            if(fgCup.getId() != fantasyCupGroup.getId()){
                fantasyTeamList.removeAll(fgCup.getFantasyTeamList());
            }

        }
        model.put("availableFantasyTeamList", fantasyTeamList);

        List<FantasyRound> fantasyRoundList = fantasyService.getFantasyRoundByFantasyCupId(fantasyCupGroup.getFantasyCup().getId());
        model.put("fantasyRoundList", fantasyRoundList);
    }
}
