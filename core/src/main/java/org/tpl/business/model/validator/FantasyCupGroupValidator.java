package org.tpl.business.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.fantasy.FantasyCupGroup;

public class FantasyCupGroupValidator implements Validator {

    public boolean supports(Class type) {
        return FantasyCupGroup.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "fantasycupgroup.name.empty");
        FantasyCupGroup fantasyCupGroup = (FantasyCupGroup) o ;
        if(fantasyCupGroup.getFantasyRoundList() == null ||fantasyCupGroup.getFantasyRoundList().isEmpty()){
            errors.rejectValue("fantasyCupGroup.runder", "er tom");
        }
        if(fantasyCupGroup.getFantasyTeamList() == null || fantasyCupGroup.getFantasyTeamList().isEmpty()){
            errors.rejectValue("fantasyCupGroups.teams","er tom");
        }

    }
}
