package org.tpl.business.model.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.fantasy.FantasyManager;

public class FantasyManagerValidator implements Validator{
    public boolean supports(Class type) {
        return FantasyManager.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "managerName", "fantasymanager.managerName.empty");
        //FantasyTeam fantasyTeam = (FantasyTeam) o;

    }
}