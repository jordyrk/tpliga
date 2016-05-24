/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.business.model.validator;

import no.kantega.commons.exception.RegExpSyntaxException;
import no.kantega.commons.log.Log;
import no.kantega.commons.util.RegExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.League;
import org.tpl.business.model.ManagerRegistration;
import org.tpl.business.service.fantasy.FantasyService;

/**
 *
 * @author Koren
 */
public class ManagerRegistrationValidator implements Validator {
 @Autowired
 FantasyService fantasyService;
    public boolean supports(Class type) {
        return ManagerRegistration.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstname", "managerRegistration.firstname.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastname", "managerRegistration.lastname.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "managerRegistration.email.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "managerRegistration.password.empty");
        ValidationUtils.rejectIfEmpty(errors, "passwordRepeat", "managerRegistration.passwordRepeat.empty");

        ManagerRegistration managerRegistration = (ManagerRegistration) o;
        if (managerRegistration.getPassword().length() < 6){
            errors.rejectValue("password","managerRegistration.password.notLongEnough");
        }
        if (! managerRegistration.getPassword().equals(managerRegistration.getPasswordRepeat())){
            errors.rejectValue("password","managerRegistration.password.not.equal");
        }

        if (!RegExp.isEmail(managerRegistration.getEmail())){
            errors.rejectValue("email","managerRegistration.email.isincorrect");
        }
         if( fantasyService.isUserIdRegistered(managerRegistration.getEmail())){
           errors.rejectValue("email","exists");
        }


    }

}

