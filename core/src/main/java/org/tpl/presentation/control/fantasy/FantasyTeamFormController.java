package org.tpl.presentation.control.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.tpl.business.model.fantasy.FantasyManager;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.validator.FantasyTeamValidator;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;
import org.tpl.presentation.control.propertyeditors.FantasyManagerPropertyEditor;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/fantasy/team/editteam")
public class FantasyTeamFormController {
    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyUtil fantasyUtil;

    @Autowired
    FantasyTeamValidator fantasyTeamValidator;

    @ModelAttribute(value="fantasyteam")
    public FantasyTeam getFantasyTeam(@RequestParam(required = false, value = "teamId") Integer teamId, HttpServletRequest request){

        if (teamId == null || teamId < 1) {
            FantasyTeam fantasyTeam = new FantasyTeam();
            FantasyManager fantasyManager = fantasyUtil.getFantasyManagerFromRequest(request);
            fantasyTeam.setFantasyManager(fantasyManager);
            return fantasyTeam;
        }
        else {
            return fantasyService.getFantasyTeamById(teamId);
        }
    }


    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(@ModelAttribute("team")FantasyTeam fantasyTeam, ModelMap model, HttpServletRequest request){
        FantasyTeam currentFantasyTeam = fantasyUtil.getFantasyTeamFromRequest(request);
        if(currentFantasyTeam != null && !fantasyTeam.equals(currentFantasyTeam)){
            return "redirect:/tpl/mypage";
        }

        return "fantasy/team/editteam";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("team")FantasyTeam fantasyTeam, BindingResult bindingResult, ModelMap model){
        fantasyTeamValidator.validate(fantasyTeam, bindingResult);
        if(bindingResult.hasErrors()){
            return "fantasy/team/editteam";
        }
        fantasyService.saveOrUpdateFantasyTeam(fantasyTeam);
        return "redirect:/tpl/mypage";
    }

    @InitBinder
    @SuppressWarnings("unchecked")
    public void addPropertyEditors(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(FantasyManager.class, new FantasyManagerPropertyEditor(fantasyService));
        
    }

}
