<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a href="${pageContext.request.contextPath}/tpl/admin/player/edit" class="adminLink"><spring:message code="player.create"/></a>
<p>${message}</p>
<form action="${pageContext.request.contextPath}/tpl/admin/player/viewplayersForTeam">
<select id="TeamFilter" name="teamId">
    <option disabled="disabled" selected="selected">Velg lag</option>
    <c:forEach var="team" items="${teams}">
        <option value="${team.teamId}">${team.shortName}</option>
    </c:forEach>
</select>
</form>
<c:set var="players" value="${availablePlayers}" scope="request"/>
<c:set var="editPlayer" value="true" scope="request"/>
<br><br>
<form id="PlayerSearch" action="${pageContext.request.contextPath}/tpl/admin/player/search">
    <div class="field">
        <div class="input">
            <input id="PlayerQuery" name="playerQuery" placeholder="S&oslash;k etter spillere"/>
            <input type="submit" value="Søk">
        </div>
    </div>

</form>
<div id="PlayerList">
    
</div>
