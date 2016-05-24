/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.business.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.Season;

/**
 *
 * @author Koren
 */
public class SeasonValidator implements Validator {

    public boolean supports(Class type) {
        return Season.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "startDate", "startDate.empty");
        ValidationUtils.rejectIfEmpty(errors, "endDate", "endDate.empty");
        ValidationUtils.rejectIfEmpty(errors, "numberOfTeams", "numberOfTeams.empty");
        Season season = (Season) o;

    }

}
