package org.tpl.presentation.control.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.service.fantasy.FantasyCupGroupService;
import org.tpl.business.service.fantasy.FantasyCupService;
import org.tpl.business.service.fantasy.FantasyLeagueService;

import java.util.List;

public class FantasyCupControllerUtil {

    @Autowired
    FantasyCupService fantasyCupService;

    @Autowired
    FantasyCupGroupService fantasyCupGroupService;

    public void viewFantasyCups(ModelMap model){
        List<FantasyCup> fantasyCups = fantasyCupService.getAllFantasyCups();
        model.put("fantasyCups", fantasyCups);
    }

    public void viewFantasyCupGroups(ModelMap model, int fantasyCupId){
        List<FantasyCupGroup> fantasyCupGroups = fantasyCupGroupService.getAllFantasyCupGroups(fantasyCupId);
        model.put("fantasyCupGroups", fantasyCupGroups);
    }
}