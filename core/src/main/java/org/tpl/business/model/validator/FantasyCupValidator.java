package org.tpl.business.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.fantasy.FantasyCup;

public class FantasyCupValidator implements Validator {

    public boolean supports(Class type) {
        return FantasyCup.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "fantasycup.name.empty");
        FantasyCup fantasyCup = (FantasyCup) o ;
    }
}
