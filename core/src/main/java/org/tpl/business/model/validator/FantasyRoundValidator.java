package org.tpl.business.model.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;

public class FantasyRoundValidator implements Validator{
    public boolean supports(Class type) {
        return FantasyRound.class.equals(type);
    }

    public void validate(Object o, Errors errors) {

    }
}