package org.tpl.business.model.validator;
/*
Created by jordyr, 23.01.11

*/

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.Match;
import org.tpl.business.model.fantasy.rule.PlayerRule;

public class PlayerRuleValidator implements Validator {

    public boolean supports(Class type) {
        return PlayerRule.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "rule.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "points", "rule.points.empty");
        ValidationUtils.rejectIfEmpty(errors, "qualifingNumber", "rule.qualifingNumber.empty");
        ValidationUtils.rejectIfEmpty(errors, "ruleType", "rule.ruleType.empty");


    }
}
