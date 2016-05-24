package org.tpl.presentation.control.propertyeditors;

import org.tpl.business.model.Season;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.FantasyService;

import java.beans.PropertyEditorSupport;

public class FantasySeasonPropertyEditor extends PropertyEditorSupport {
     FantasyService fantasyService;

    public FantasySeasonPropertyEditor(FantasyService fantasyService) {
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
            FantasySeason fantasySeason= fantasyService.getFantasySeasonById(Integer.parseInt(text));
            setValue(fantasySeason);
        } else {
            setValue(null);
        }
    }
}
