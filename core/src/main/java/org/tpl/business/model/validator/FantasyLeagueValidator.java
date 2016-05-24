/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.business.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.League;
import org.tpl.business.model.fantasy.FantasyLeague;

/**
 *
 * @author Koren
 */
public class FantasyLeagueValidator implements Validator {

    public boolean supports(Class type) {
        return FantasyLeague.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "fantasyleague.name.empty");
    }

}
