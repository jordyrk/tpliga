package org.tpl.presentation.control.fantasy;

import no.kantega.commons.client.util.RequestParameters;
import no.kantega.publishing.security.SecuritySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.service.MultimediaService;
import org.tpl.business.service.UserService;
import org.tpl.business.service.fantasy.FantasyService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FantasyTeamImageController {

    @Autowired
    MultimediaService multimediaService;

    @Autowired
    FantasyService fantasyService;

    @RequestMapping("/team/updateteamimage")
    public String updateteamimage(HttpServletRequest request, Model model, @RequestParam int fantasyTeamId) throws IOException {
        MultipartFile profileImage = new RequestParameters(request).getFile("teamImage");
        if(profileImage == null){
            return "redirect:/tpl/mypage";
        }
        int multiMediaImageId = multimediaService.saveOrUpdateTeamImage(profileImage, SecuritySession.getInstance(request),fantasyTeamId);
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(fantasyTeamId);
        fantasyTeam.setMultiMediaImageId(multiMediaImageId);
        fantasyService.saveOrUpdateFantasyTeam(fantasyTeam);
        return "redirect:/tpl/mypage";
    }
}
