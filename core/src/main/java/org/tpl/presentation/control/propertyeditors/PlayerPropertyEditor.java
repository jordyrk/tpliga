package org.tpl.presentation.control.propertyeditors;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Player;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;

import java.beans.PropertyEditorSupport;/*
Created by jordyr, 10.okt.2010

*/

public class PlayerPropertyEditor extends PropertyEditorSupport {

    PlayerService playerService;

    public PlayerPropertyEditor(PlayerService playerService) {
        this.playerService = playerService;
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
            Player player = playerService.getPlayerById(Integer.parseInt(text));
            setValue(player);
        } else {
            setValue(null);
        }
    }
}
