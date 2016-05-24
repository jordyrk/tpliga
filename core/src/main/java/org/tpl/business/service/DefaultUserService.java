package org.tpl.business.service;

import no.kantega.commons.exception.ConfigurationException;
import no.kantega.commons.exception.NotAuthorizedException;
import no.kantega.publishing.common.Aksess;
import no.kantega.publishing.common.cache.ContentIdentifierCache;
import no.kantega.publishing.common.data.Content;
import no.kantega.publishing.common.data.ContentIdentifier;
import no.kantega.publishing.common.data.attributes.Attribute;
import no.kantega.publishing.common.data.enums.AttributeDataType;
import no.kantega.publishing.common.service.ContentManagementService;
import no.kantega.security.api.common.SystemException;
import no.kantega.security.api.identity.DefaultIdentity;
import no.kantega.security.api.password.PasswordManager;
import no.kantega.security.api.profile.DefaultProfile;
import no.kantega.security.api.profile.ProfileManager;
import no.kantega.security.api.profile.ProfileUpdateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.ManagerRegistration;

import javax.servlet.http.HttpServletRequest;
/*
Created by jordyr, 18.nov.2010

*/

public class DefaultUserService implements UserService{
    private ProfileUpdateManager profileUpdateManager;
    private ProfileManager profileManager;
    private PasswordManager passwordManager;

    @Autowired
    SendMailRegistrationService sendMailRegistrationService;

    public void createManager(ManagerRegistration managerRegistration, HttpServletRequest request) throws SystemException {
        DefaultIdentity identity = new DefaultIdentity();
        identity.setDomain(Aksess.getDefaultSecurityDomain());
        identity.setUserId(managerRegistration.getEmail());

        DefaultProfile profile = new DefaultProfile();
        profile.setIdentity(identity);

        profile.setGivenName(managerRegistration.getFirstname());
        profile.setSurname(managerRegistration.getLastname());
        profile.setEmail(managerRegistration.getEmail());
        profile.setDepartment("Manager");


        profileUpdateManager.saveOrUpdateProfile(profile);
        passwordManager.setPassword(identity, managerRegistration.getPassword(), managerRegistration.getPassword());
        try {
            Content content = getRecieptContent(request);
            Attribute attr = content.getAttribute("lead paragraph", AttributeDataType.CONTENT_DATA);
            sendMailRegistrationService.sendRegistrationEmail("noreply@tpliga.org", managerRegistration.getEmail(), content.getTitle(),attr.getValue() + "\n\nDitt brukernavn er: " + managerRegistration.getEmail());
        } catch (ConfigurationException e) {

        }
        catch (NotAuthorizedException e){

        }
    }

    private Content getRecieptContent(HttpServletRequest request) throws ConfigurationException, NotAuthorizedException{
        ContentManagementService cms = new ContentManagementService(request);
        ContentIdentifier cid = ContentIdentifierCache.getContentIdentifierByAlias(1, "/kvittering/");

        Content content = cms.getContent(cid);
        return content;

    }

    public void setProfileUpdateManager(ProfileUpdateManager profileUpdateManager) {
        this.profileUpdateManager = profileUpdateManager;
    }

    public void setProfileManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    public void setPasswordManager(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }
}
