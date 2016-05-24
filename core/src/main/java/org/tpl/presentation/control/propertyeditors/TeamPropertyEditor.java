package org.tpl.presentation.control.propertyeditors;

import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;

import java.beans.PropertyEditorSupport;/*
Created by jordyr, 10.okt.2010

*/

public class TeamPropertyEditor extends PropertyEditorSupport {
     LeagueService leagueService;

    public TeamPropertyEditor(LeagueService leagueService) {
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
            Team team= leagueService.getTeamById(Integer.parseInt(text));
            setValue(team);
        } else {
            setValue(null);
        }
    }
}
