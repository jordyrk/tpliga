package org.tpl.presentation.control.fantasy;

import no.kantega.publishing.security.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.fantasy.FantasyManager;
import org.tpl.business.model.validator.FantasyManagerValidator;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;/*
Created by jordyr, 08.okt.2010

*/

@Controller
@RequestMapping("/fantasy/manager/editmanager")
public class FantasyManagerFormController {
    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyUtil fantasyUtil;

    @Autowired
    FantasyManagerValidator fantasyManagerValidator;

    @ModelAttribute(value="fantasymanager")
    public FantasyManager getFantasyManager(@RequestParam(required = false, value = "managerId") Integer managerId, HttpServletRequest request){

        if (managerId == null || managerId < 1) {
            FantasyManager fantasyManager = new FantasyManager();
            User user = fantasyUtil.getUserFromRequest(request);
            fantasyManager.setUserId(user.getId());
            return fantasyManager;
        }
        else {
            return fantasyService.getFantasyManagerById(managerId);
        }
    }

    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        return "fantasy/manager/editmanager";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("fantasymanager")FantasyManager fantasyManager, BindingResult bindingResult, ModelMap model){
        fantasyManagerValidator.validate(fantasyManager, bindingResult);
        if(bindingResult.hasErrors()){
            return "fantasy/manager/editmanager";
        }
        fantasyService.saveOrUpdateFantasyManager(fantasyManager);
        return "redirect:/tpl/mypage";
    }
}
