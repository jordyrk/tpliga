package org.tpl.presentation.control.propertyeditors;

import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.service.fantasy.FantasyCupService;
import org.tpl.business.service.fantasy.FantasyService;

import java.beans.PropertyEditorSupport;

public class FantasyCupPropertyEditor extends PropertyEditorSupport {
     FantasyCupService fantasyCupService;

    public FantasyCupPropertyEditor(FantasyCupService fantasyCupService) {
        this.fantasyCupService = fantasyCupService;
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
            FantasyCup fantasyCup= fantasyCupService.getFantasyCupById(Integer.parseInt(text));
            setValue(fantasyCup);
        } else {
            setValue(null);
        }
    }
}
