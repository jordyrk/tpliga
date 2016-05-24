package org.tpl.presentation.control.propertyeditors;

import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.service.fantasy.FantasyService;

import java.beans.PropertyEditorSupport;

public class FantasyRoundPropertyEditor extends PropertyEditorSupport {
     FantasyService fantasyService;

    public FantasyRoundPropertyEditor(FantasyService fantasyService) {
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
            FantasyRound fantasyRound= fantasyService.getFantasyRoundById(Integer.parseInt(text));
            setValue(fantasyRound);
        } else {
            setValue(null);
        }
    }
}
