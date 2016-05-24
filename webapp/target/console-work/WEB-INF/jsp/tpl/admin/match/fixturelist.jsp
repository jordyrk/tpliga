<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table>
    <thead>
    <tr>
        <th>Hjemmelag</th>
        <th>Bortelag</th>
        <th>Kampdata</th>
        <th>Runde</th>
        <th>Fantasy PL id</th>
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
            <td><c:out value="${match.fantasyPremierLeagueId}"/></td>

            <td>
                <a class="edit editMatch" href="${pageContext.request.contextPath}/tpl/admin/match/edit?matchId=${match.matchId}" title="<spring:message code='match.edit'/> ${match.homeTeam.shortName} - ${match.awayTeam.shortName}">&nbsp;</a>
            </td>
            <td>
                <c:if test="${match.fantasyPremierLeagueId>0}">
                    <a class="url" href="http://fantasy.premierleague.com/fixture/${match.fantasyPremierLeagueId}/" target="_blank" title="FPLId: : ${match.fantasyPremierLeagueId}">Matchurl</a>
                </c:if>
            </td>
            <td>
                <c:if test="${match.fantasyPremierLeagueId>0 && ! match.playerStatsUpdated}">
                    <a class="matchstats editMatch" href="${pageContext.request.contextPath}/tpl/admin/match/matchstats?matchId=${match.matchId}" title="<spring:message code='match.admin'/>">&nbsp;</a>
                </c:if>
            </td>
            <td>
                <c:if test="${match.fantasyPremierLeagueId>0 && match.playerStatsUpdated}">
                    <a class="editMatch" href="${pageContext.request.contextPath}/tpl/admin/match/updatepoints?matchId=${match.matchId}" title="Oppdater poeng for spillere">Oppdater poeng</a>
                </c:if>
            </td>


        </tr>
    </c:forEach>
    </tbody>

</table>
