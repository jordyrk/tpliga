<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="defaultTable fixturesTable">
    <tbody>
        <c:forEach var="fantasyMatch" items="${fantasyMatchList}">
            <c:if test="${fantasyRound != fantasyMatch.fantasyRound.round}">
                <tr><td colspan="3" class="roundHeader">Runde ${fantasyMatch.fantasyRound.round}</td></tr>
            </c:if>
            <tr>
                <td class="hometeam team"><c:out value="${fantasyMatch.homeTeam.name}"/></td>
                <td class="matchSeparator">-</td>
                <td class="team"><c:out value="${fantasyMatch.awayTeam.name}"/></td>
            </tr>
            <c:set var="fantasyRound" value="${fantasyMatch.fantasyRound.round}"/>
        </c:forEach>
    </tbody>

</table>


