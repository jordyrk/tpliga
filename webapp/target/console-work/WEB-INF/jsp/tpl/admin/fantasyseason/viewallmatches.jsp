<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
</head>

<div id="admin">
<table>
    <thead>
    <tr>
        <th><spring:message code="match.homeTeam"/></th>
        <th><spring:message code="match.awayTeam"/></th>
        <th><spring:message code="match.matchDate"/></th>
        <th><spring:message code="match.round"/></th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="match" items="${matches}">
        <tr>
            <td><c:out value="${match.homeTeam.shortName}"/></td>
            <td><c:out value="${match.awayTeam.shortName}"/></td>
            <td><fmt:formatDate value="${match.matchDate}" pattern="dd.MM.yyyy"/></td>
            <td><c:out value="${match.leagueRound.round}"/></td>
            <td>
                <a class="addMatch" href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/addMatch?matchId=${match.matchId}&fantasyRoundId=${fantasyRoundId}" title="<spring:message code='match.edit'/> ${match.homeTeam.shortName} - ${match.awayTeam.shortName}">Legg till</a>
            </td>

        </tr>
    </c:forEach>
    </tbody>

</table>
</div>