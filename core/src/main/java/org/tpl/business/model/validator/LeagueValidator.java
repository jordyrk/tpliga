/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.business.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.League;

/**
 *
 * @author Koren
 */
public class LeagueValidator implements Validator {

    public boolean supports(Class type) {
        return League.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "shortName", "league.shortName.empty");
        ValidationUtils.rejectIfEmpty(errors, "fullName", "league.fullName.empty");
        League league = (League) o;

    }

}
