<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ page import="no.kantega.publishing.security.SecuritySession" %> 
<%
SecuritySession securitySession = SecuritySession.getInstance(request);
    if (securitySession.isLoggedIn()) {
        response.sendRedirect(request.getContextPath() + "/tpl/mypage");

    } else {

        response.sendRedirect(request.getContextPath() + "/Login.action");
    }
%>