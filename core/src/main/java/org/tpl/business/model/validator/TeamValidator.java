package org.tpl.business.model.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.Team;

public class TeamValidator implements Validator{
    public boolean supports(Class type) {
        return Team.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
       ValidationUtils.rejectIfEmpty(errors, "shortName", "shortName.empty");
        ValidationUtils.rejectIfEmpty(errors, "fullName", "fullName.empty");
        Team team = (Team) o;

    }
}