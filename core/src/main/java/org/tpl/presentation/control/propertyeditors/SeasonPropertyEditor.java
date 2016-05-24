package org.tpl.presentation.control.propertyeditors;

import org.tpl.business.model.Season;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;

import java.beans.PropertyEditorSupport;/*
Created by jordyr, 10.okt.2010

*/

public class SeasonPropertyEditor extends PropertyEditorSupport {
     LeagueService leagueService;

    public SeasonPropertyEditor(LeagueService leagueService) {
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
            Season season= leagueService.getSeasonById(Integer.parseInt(text));
            setValue(season);
        } else {
            setValue(null);
        }
    }
}
