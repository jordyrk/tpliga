package org.tpl.presentation.control.propertyeditors;

import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.service.fantasy.FantasyService;

import java.beans.PropertyEditorSupport;/*
Created by jordyr, 10.okt.2010

*/

public class FantasyTeamPropertyEditor extends PropertyEditorSupport {
     FantasyService fantasyService;

    public FantasyTeamPropertyEditor(FantasyService fantasyService) {
        this.fantasyService = fantasyService;
    }

    @Override
    public String getAsText() {
        if(getValue() != null) {
            return getValue().toString();
        }
        return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null && text.length() > 0) {
            FantasyTeam fantasyTeam= fantasyService.getFantasyTeamById(Integer.parseInt(text));
            setValue(fantasyTeam);
        } else {
            setValue(null);
        }
    }
}
