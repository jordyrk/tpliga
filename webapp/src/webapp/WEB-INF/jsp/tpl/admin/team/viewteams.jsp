<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a href="${pageContext.request.contextPath}/tpl/admin/team/edit" class="adminLink"><spring:message code="team.create"/></a>

<c:set var="editTeams" value="true" scope="request"/>
<%@include file="teamlist.jspf"%>


