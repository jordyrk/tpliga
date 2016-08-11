<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<style type="text/css">


</style>
<table id="StandardTable" class="defaultTable tablesorter">
    <thead>
    <tr>
        <th>Plassering</th>
        <th><spring:message code="fantasyteam.name"/></th>
        <th>Målforskjell</th>
        <th>&nbsp;+&nbsp;&nbsp;&nbsp;</th>
        <th>&nbsp;-&nbsp;&nbsp;&nbsp;</th>
        <th class="empty">&nbsp;&nbsp;&nbsp;</th>
        <th title="Seier">S</th>
        <th title="Uavgjort">U</th>
        <th title="TaB">T</th>
        <th>Poeng&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${fantasyLeagueStandingList}" var="fantasyLeagueStanding" varStatus="status">
        
        <c:choose>
            <c:when test="${status.index < fantasyLeague.numberOfTopTeams}">
                <c:set var="positionClass" value="topposition"></c:set>
            </c:when>
            <c:when test="${status.index < (fantasyLeague.numberOfSecondTopTeams+fantasyLeague.numberOfTopTeams)}">
                <c:set var="positionClass" value="secondtopposition"></c:set>
            </c:when>
            <c:when test="${(fantasyLeague.numberOfTeams - status.index) <= (fantasyLeague.numberOfSecondBotttomTeams + fantasyLeague.numberOfBottomTeams) && fantasyLeague.numberOfTeams - status.index > fantasyLeague.numberOfBottomTeams}">
                <c:set var="positionClass" value="secondbottomposition"></c:set>
            </c:when>
            <c:when test="${fantasyLeague.numberOfTeams - status.index <= fantasyLeague.numberOfBottomTeams}">
                <c:set var="positionClass" value="bottomposition"></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="positionClass" value=""></c:set>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${fantasyLeagueStanding.lastRoundPosition < fantasyLeagueStanding.position}">
                <c:set var="positionMovementClass" value="positionDown"></c:set>
            </c:when>
            <c:when test="${fantasyLeagueStanding.lastRoundPosition > fantasyLeagueStanding.position}">
                <c:set var="positionMovementClass" value="positionUp"></c:set>
            </c:when>
            <c:otherwise><c:set var="positionMovementClass" value=""></c:set></c:otherwise>
        </c:choose>
        <tr class="${positionClass}">
            <td>${fantasyLeagueStanding.position}<span class="${positionMovementClass}" title="Siste rundes posisjon: ${fantasyLeagueStanding.lastRoundPosition}"></span></td>
            <td>${fantasyLeagueStanding.fantasyTeam.name}<tpl:profileimage multiMediaId="${fantasyLeagueStanding.fantasyTeam.multiMediaImageId}" height="20" width="20"/></td>
            <td class="center">${fantasyLeagueStanding.goalsScored-fantasyLeagueStanding.goalsAgainst}</td>
            <td class="center">${fantasyLeagueStanding.goalsScored}</td>
            <td class="center">${fantasyLeagueStanding.goalsAgainst}</td>
            <td></td>
            <td class="center">${fantasyLeagueStanding.matchesWon}</td>
            <td class="center">${fantasyLeagueStanding.matchesDraw}</td>
            <td class="center">${fantasyLeagueStanding.matchesLost}</td>
            <td class="center">${fantasyLeagueStanding.points}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>