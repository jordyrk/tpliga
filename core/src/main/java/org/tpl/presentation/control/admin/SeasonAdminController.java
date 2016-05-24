/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tpl.presentation.control.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tpl.business.model.Season;
import org.tpl.business.service.LeagueService;
import org.tpl.presentation.control.admin.util.SeasonControllerUtil;

/**
 *
 * @author Koren
 */
@Controller
public class SeasonAdminController {
    @Autowired
    LeagueService leagueService;

    @Autowired
    SeasonControllerUtil seasonControllerUtil;

    @RequestMapping("/admin/season/viewseasons")
    public String viewSeasons(ModelMap model){
        seasonControllerUtil.viewSeasons(model);
        return "admin/season/viewseasons";
    }

}
