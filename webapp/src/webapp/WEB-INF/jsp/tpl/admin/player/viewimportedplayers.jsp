<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<h1><spring:message code="team.importteams"/></h1>

<c:set var="players" value="${players}" scope="request"/>
<c:set var="editPlayer" value="false" scope="request"/>
<div id="PlayerList">
    <%@include file="playerlist.jsp"%>
</div>