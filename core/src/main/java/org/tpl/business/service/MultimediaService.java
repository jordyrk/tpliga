package org.tpl.business.service;

import no.kantega.publishing.security.SecuritySession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MultimediaService {

    int saveOrUpdateTeamImage(MultipartFile file, SecuritySession securitySession, int fantasyTeamId) throws IOException;
}
