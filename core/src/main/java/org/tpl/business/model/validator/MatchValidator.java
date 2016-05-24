package org.tpl.business.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.Match;

public class MatchValidator implements Validator {

    public boolean supports(Class type) {
        return Match.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "homeTeam", "match.homeTeam.empty");
        ValidationUtils.rejectIfEmpty(errors, "awayTeam", "match.awayTeam.empty");
        ValidationUtils.rejectIfEmpty(errors, "matchDate", "match.matchDate.empty");
      

    }

}
