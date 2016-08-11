<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<table>
    <thead>
    <tr>
        <th><spring:message code="match.homeTeam"/></th>
        <th><spring:message code="match.awayTeam"/></th>
        <th><spring:message code="match.matchDate"/></th>
        <th><spring:message code="match.round"/></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${fantasyRound.matchList}" var="match">
        <tr>
            <td><c:out value="${match.homeTeam.shortName}"/></td>
            <td><c:out value="${match.awayTeam.shortName}"/></td>
            <td><fmt:formatDate value="${match.matchDate}" pattern="dd.MM.yyyy"/></td>
            <td><c:out value="${match.leagueRound.round}"/></td>
            <td>
                <a class="removeMatch adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/removeMatch?fantasyRoundId=${fantasyRound.fantasyRoundId}&matchId=${match.matchId}" title="<spring:message code='fantasyround.removeMatch'/>">&nbsp;</a>
            </td>
       </tr>
    </c:forEach>
    </tbody>

</table>
<td>
    <a class="addMatch iframe" href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/viewallmatches?seasonId=${season.seasonId}&fantasyRoundId=${fantasyRound.fantasyRoundId}" title="<spring:message code='fantasyround.viewAllMatches'/>">&nbsp; Legg til kamp</a>
</td>
