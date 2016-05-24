package org.tpl.presentation.control.propertyeditors;

import org.tpl.business.model.League;
import org.tpl.business.service.LeagueService;

import java.beans.PropertyEditorSupport;/*
Created by jordyr, 10.okt.2010

*/

public class LeaguePropertyEditor extends PropertyEditorSupport {

    LeagueService leagueService;

    public LeaguePropertyEditor(LeagueService leagueService) {
        this.leagueService = leagueService;
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
            League league = leagueService.getLeagueById(Integer.parseInt(text));
            setValue(league);
        } else {
            setValue(null);
        }
    }
}
