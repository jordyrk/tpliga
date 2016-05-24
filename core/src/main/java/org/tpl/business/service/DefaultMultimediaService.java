package org.tpl.business.service;

import no.kantega.publishing.common.data.Multimedia;
import no.kantega.publishing.multimedia.metadata.MultimediaMetadataExtractor;
import no.kantega.publishing.security.SecuritySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.tpl.business.model.Team;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.service.fantasy.FantasyService;

import java.io.IOException;

public class DefaultMultimediaService implements MultimediaService {
    @Autowired
    FantasyService fantasyService;

    private MultimediaMetadataExtractor multimediaMetadataExtractor;

    public DefaultMultimediaService(MultimediaMetadataExtractor multimediaMetadataExtractor) {
        this.multimediaMetadataExtractor = multimediaMetadataExtractor;
    }

    public int saveOrUpdateTeamImage(MultipartFile profileImage, SecuritySession securitySession, int fantasyTeamId) throws IOException {
        no.kantega.publishing.common.service.MultimediaService mms =  new no.kantega.publishing.common.service.MultimediaService(securitySession);
        return saveTeamImage(profileImage, mms, fantasyTeamId);
    }

    private int saveTeamImage(MultipartFile file, no.kantega.publishing.common.service.MultimediaService mms, int fantasyTeamId) throws IOException {
        int id = -1;
        Multimedia teamImage = new Multimedia();

        teamImage.setData(file.getBytes());
        multimediaMetadataExtractor.extractMetadata(teamImage);
        String filename = file.getOriginalFilename();
        if (filename.length() > 255) {
            filename = filename.substring(filename.length() - 255, filename.length());
        }
        teamImage.setFilename(filename);

        if (teamImage.getMimeType().getType().startsWith("image")) {
            FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(fantasyTeamId);
            teamImage.setName(fantasyTeam.getName());
            teamImage.setModifiedBy(fantasyTeam.getFantasyManager().getUserId());
            teamImage.setProfileImageUserId(fantasyTeam.getFantasyManager().getUserId());
            id = mms.setMultimedia(teamImage);

        }
        return id;
    }

}
