<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:length(fantasyCupGroupStandings) > 0}">
    <table id="StandardTable" class="defaultTable">
        <thead>
        <tr>
            <th>Plassering</th>
            <th><spring:message code="fantasyteam.name"/></th>

            <th>Målforskjell</th>
            <th class="center">&nbsp;+&nbsp;&nbsp;&nbsp;</th>
            <th class="center">&nbsp;-&nbsp;&nbsp;&nbsp;</th>
            <th title="Seier" class="center">S</th>
            <th title="Uavgjort" class="center">U</th>
            <th title="TaB" class="center">T</th>

            <th>Poeng&nbsp;</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${fantasyCupGroupStandings}" var="fantasyCupGroupStanding" varStatus="status">
            <c:choose>
                <c:when test="${fantasyCupGroupStanding.lastRoundPosition < fantasyCupGroupStanding.position}">
                    <c:set var="positionMovementClass" value="positionDown"></c:set>
                </c:when>
                <c:when test="${fantasyCupGroupStanding.lastRoundPosition > fantasyCupGroupStanding.position}">
                    <c:set var="positionMovementClass" value="positionUp"></c:set>
                </c:when>
                <c:otherwise><c:set var="positionMovementClass" value=""></c:set></c:otherwise>
            </c:choose>
            <tr>
                <td>${fantasyCupGroupStanding.position}<span class="${positionMovementClass}" title="Siste rundes posisjon: ${fantasyCupGroupStanding.lastRoundPosition}"></span></td>
                <td>${fantasyCupGroupStanding.fantasyTeam.name}<tpl:profileimage multiMediaId="${fantasyCupGroupStanding.fantasyTeam.multiMediaImageId}" height="20" width="20"/></td>

                <td class="center">${fantasyCupGroupStanding.goalsScored-fantasyCupGroupStanding.goalsAgainst}</td>
                <td class="center">${fantasyCupGroupStanding.goalsScored}</td>
                <td class="center">${fantasyCupGroupStanding.goalsAgainst}</td>
                <td class="center">${fantasyCupGroupStanding.matchesWon}</td>
                <td class="center">${fantasyCupGroupStanding.matchesDraw}</td>
                <td class="center">${fantasyCupGroupStanding.matchesLost}</td>
                <td class="center">${fantasyCupGroupStanding.points}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>