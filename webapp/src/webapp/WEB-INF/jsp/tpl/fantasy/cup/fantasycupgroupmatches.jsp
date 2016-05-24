<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<table class="defaultTable matchTable">
    <tbody>
    <c:set var="currentRound" value="0"/>
    <c:forEach var="fantasyMatch" items="${fantasyMatchList}">
        <c:if test="${currentRound == 0 || currentRound !=  fantasyMatch.fantasyRound.round}">
            <tr><td class="roundHeader" colspan="6">Runde ${fantasyMatch.fantasyRound.round}</td></tr>
            <c:set var="currentRound" value="${fantasyMatch.fantasyRound.round}"/>
        </c:if>
        <tr>

            <td><c:if test="${fantasyMatch.played}"><a class="matchselector" href="${fantasyMatch.id}"></a></c:if></td>
            <td class="hometeam team">${fantasyMatch.homeTeam.name}</td>
            <td class="hometeam result"><c:if test="${fantasyMatch.played}">${fantasyMatch.homegoals}</c:if></td>
            <td class="matchSeparator" >-</td>
            <td class="result"><c:if test="${fantasyMatch.played}">${fantasyMatch.awaygoals}</c:if></td>
            <td class="team">${fantasyMatch.awayTeam.name}</td>

        </tr>
        <c:set var="fantasyRound" value="${fantasyMatch.fantasyRound.round}"/>
    </c:forEach>
    </tbody>
</table>

