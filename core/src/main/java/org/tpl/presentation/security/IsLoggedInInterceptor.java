package org.tpl.presentation.security;

import no.kantega.publishing.security.SecuritySession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IsLoggedInInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecuritySession session = SecuritySession.getInstance(request);
        if (!session.isLoggedIn()) {
            session.initiateLogin(request, response);
            return false;
        } else {
            return true;
        }
    }
}