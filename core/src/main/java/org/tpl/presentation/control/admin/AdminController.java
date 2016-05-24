package org.tpl.presentation.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: Koren
 * Date: 26.sep.2010
 * Time: 18:52:54
 */
@Controller
public class AdminController {


    @RequestMapping("/admin")
    public String admin(){
        return "admin/admin";
    }
}
