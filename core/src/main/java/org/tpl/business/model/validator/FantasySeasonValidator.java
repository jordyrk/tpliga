package org.tpl.business.model.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.fantasy.FantasySeason;

public class FantasySeasonValidator implements Validator{
    public boolean supports(Class type) {
        return FantasySeason.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "fantasyseason.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "season", "fantasyseason.season.empty");
        //FantasyTeam fantasyTeam = (FantasyTeam) o;

    }
}