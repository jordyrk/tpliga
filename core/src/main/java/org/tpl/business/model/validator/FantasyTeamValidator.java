package org.tpl.business.model.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.fantasy.FantasyTeam;

public class FantasyTeamValidator implements Validator{
    public boolean supports(Class type) {
        return FantasyTeam.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        //FantasyTeam fantasyTeam = (FantasyTeam) o;

    }
}