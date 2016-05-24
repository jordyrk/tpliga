package org.tpl.presentation.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tpl.business.model.Team;
import org.tpl.business.service.ImportService;
import org.tpl.business.service.LeagueService;
import org.tpl.presentation.control.admin.util.TeamControllerUtil;

import java.util.List;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 18:30:30
 */
@Controller
public class TeamAdminController {

    @Autowired
    ImportService importService;

    @Autowired
    LeagueService leagueService;

    @Autowired
    TeamControllerUtil teamControllerUtil;

    @RequestMapping("/admin/team/viewteams")
    public String viewTeams(ModelMap model){
        teamControllerUtil.viewTeams(model);
        return "admin/team/viewteams";
    }

}
