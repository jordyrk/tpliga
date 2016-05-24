/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.business.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.League;
import org.tpl.business.model.Player;

/**
 *
 * @author Koren
 */
public class PlayerValidator implements Validator {

    public boolean supports(Class type) {
        return Player.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "lastName", "league.fullName.empty");
        Player player = (Player) o;

    }

}
