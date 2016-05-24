package org.tpl.presentation.control;

import no.kantega.commons.log.Log;
import no.kantega.publishing.common.Aksess;
import no.kantega.publishing.security.SecuritySession;
import no.kantega.security.api.common.SystemException;
import no.kantega.security.api.identity.DefaultIdentityResolver;
import no.kantega.security.api.identity.IdentityResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tpl.business.model.ManagerRegistration;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.Team;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.validator.ManagerRegistrationValidator;
import org.tpl.business.service.UserService;
import org.tpl.business.service.fantasy.FantasyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/*
Created by jordyr, 17.nov.2010

*/

@Controller
@RequestMapping("/managerregistration")
public class ManagerRegistrationController {

    @Autowired
    ManagerRegistrationValidator managerRegistrationValidator;

    @Autowired
    UserService userService;

    @Autowired
    FantasyService fantasyService;

    @ModelAttribute(value="managerRegistration")
     public ManagerRegistration newManager(){
        return new ManagerRegistration();
    }



    @RequestMapping(method= RequestMethod.GET)
    public String setupForm(ModelMap model){
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        if(fantasySeason.isRegistrationActive()){
            return "registration/managerregistration";
        }
        else{
            return "redirect:mypage";
        }
    }


    @RequestMapping(method= RequestMethod.POST)
    public String submit(@ModelAttribute("managerRegistration") ManagerRegistration managerRegistration, BindingResult bindingResult, ModelMap model, HttpServletRequest request){
        HttpSession session = request.getSession();
        managerRegistrationValidator.validate(managerRegistration, bindingResult);

        if(bindingResult.hasErrors()){
            return "registration/managerregistration";
        }
        try{
             userService.createManager(managerRegistration,request);
        }catch (SystemException e){
            Log.error(this.getClass().getName(), e);
        }
        IdentityResolver resolver = SecuritySession.getInstance(request).getRealm().getIdentityResolver();

        // Set session logon info
        session.setAttribute(resolver.getAuthenticationContext() + DefaultIdentityResolver.SESSION_IDENTITY_NAME, managerRegistration.getEmail());
        session.setAttribute(resolver.getAuthenticationContext() + DefaultIdentityResolver.SESSION_IDENTITY_DOMAIN, Aksess.getDefaultSecurityDomain());

        // Finish login by getting instance
        SecuritySession.getInstance(request);
        return "redirect:mypage";
    }
}
