<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="defaultTable matchTable">
    <tbody>
    <c:forEach var="fantasyMatch" items="${fantasyMatchList}">
        <c:if test="${fantasyRound != fantasyMatch.fantasyRound.round}">
            <tr><td colspan="6" class="roundHeader">Runde ${fantasyMatch.fantasyRound.round}</td></tr>
        </c:if>
        <tr>
            <td class="hometeam team"><c:out value="${fantasyMatch.homeTeam.name}"/></td>
            <td class="hometeam result"><c:out value="${fantasyMatch.homegoals}"/></td>
            <td class="matchSeparator">-</td>
            <td class="result"><c:out value="${fantasyMatch.awaygoals}"/></td>
            <td class="team"><c:out value="${fantasyMatch.awayTeam.name}"/></td>
            <td><a class="matchselector" href="${fantasyMatch.id}"></a></td>
        </tr>
        <c:set var="fantasyRound" value="${fantasyMatch.fantasyRound.round}"/>
    </c:forEach>
    </tbody>

</table>

<div id="FantasyMatchView" class="hidden"></div>

