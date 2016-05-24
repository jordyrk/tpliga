package org.tpl.business.service;
/*
Created by jordyr, 01.02.11

*/

import no.kantega.commons.exception.ConfigurationException;

public class DefaultSendMailRegistrationService implements SendMailRegistrationService{

    public void sendRegistrationEmail(String from, String to, String subject, String content)  throws ConfigurationException{
        no.kantega.publishing.modules.mailsender.MailSender.send(from, to, subject, content);
    }
}
