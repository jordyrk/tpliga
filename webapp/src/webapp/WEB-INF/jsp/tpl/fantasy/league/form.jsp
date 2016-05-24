<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>



    <table id="FormTable" class="defaultTable tablesorter">
        <thead>
        <tr>
            <th>Plassering</th>
            <th><spring:message code="fantasyteam.name"/></th>
            <th>Målforskjell</th>
            <th>&nbsp;+&nbsp;&nbsp;&nbsp;</th>
            <th>&nbsp;-&nbsp;&nbsp;&nbsp;</th>
            <th>Seier</th>
            <th>Uavgjort</th>
            <th>Tap&nbsp;&nbsp;</th>
            <th>Poeng&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${fantasyLeagueStandingList}" var="fantasyCupGroupStanding" varStatus="status">
            <tr>
                <td>${fantasyCupGroupStanding.position}</td>
                <td>${fantasyCupGroupStanding.fantasyTeam.name}<tpl:profileimage multiMediaId="${fantasyCupGroupStanding.fantasyTeam.multiMediaImageId}" height="20" width="20"/></td>
                <td>${fantasyCupGroupStanding.goalsScored-fantasyCupGroupStanding.goalsAgainst}</td>
                <td>${fantasyCupGroupStanding.goalsScored}</td>
                <td>${fantasyCupGroupStanding.goalsAgainst}</td>
                <td>${fantasyCupGroupStanding.matchesWon}</td>
                <td>${fantasyCupGroupStanding.matchesDraw}</td>
                <td>${fantasyCupGroupStanding.matchesLost}</td>
                <td>${fantasyCupGroupStanding.points}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
