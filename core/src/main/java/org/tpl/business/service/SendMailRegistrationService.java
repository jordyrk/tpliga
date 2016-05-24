package org.tpl.business.service;
/*
Created by jordyr, 01.02.11

*/

import no.kantega.commons.exception.ConfigurationException;

public interface SendMailRegistrationService {

    public void sendRegistrationEmail(String from, String to, String subject, String content)throws ConfigurationException;
}
