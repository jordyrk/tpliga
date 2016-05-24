package org.tpl.business.service;

import no.kantega.commons.exception.NotAuthorizedException;
import no.kantega.security.api.common.SystemException;
import org.tpl.business.model.ManagerRegistration;

import javax.servlet.http.HttpServletRequest;
/*
Created by jordyr, 18.nov.2010

*/

public interface UserService {

    public void createManager(ManagerRegistration managerRegistration, HttpServletRequest request) throws SystemException;
}
