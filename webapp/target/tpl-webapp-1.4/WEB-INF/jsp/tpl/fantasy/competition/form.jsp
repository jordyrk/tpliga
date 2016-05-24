<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>

<table id="FormTable" class="defaultTable tablesorter">
    <thead>
    <tr>
        <th>Plassering</th>
        <th><spring:message code="fantasyteam.name"/></th>
        <th>Snitt</th>
        <th>Min</th>
        <th>Max</th>
        <th>Sum</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${fantasyCompetitionStandingList}" var="fantasyCompetitionStanding" varStatus="status">
        <tr>
            <td>${fantasyCompetitionStanding.position}</td>
            <td>${fantasyCompetitionStanding.fantasyTeam.name}<tpl:profileimage multiMediaId="${fantasyCompetitionStanding.fantasyTeam.multiMediaImageId}" height="20" width="20"/></td>
            <td>${fantasyCompetitionStanding.averagepoints}</td>
            <td>${fantasyCompetitionStanding.minimumpoints}</td>
            <td>${fantasyCompetitionStanding.maximumpoints}</td>
            <td>${fantasyCompetitionStanding.accumulatedPoints}</td>
        </tr>
    </c:forEach>
    </tbody>

</table>





