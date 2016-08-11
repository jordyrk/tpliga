<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>Kamp oversikt</h1>'
<p>${message}</p>
<h3>${match.homeTeam.shortName} (${match.homeGoals}) - (${match.awayGoals}) ${match.awayTeam.shortName}</h3>
<input id="MatchId" type="hidden" value="${match.matchId}"/>

<div class="playerstats">
    <div>
        <a href="${match.matchUrl}" target="_blank">Data hentes fra</a>
    </div>
    <c:if test="${not isSaved}">
        <div>
            <a id="SavePlayerStats" href="${pageContext.request.contextPath}/tpl/admin/match/savePlayerStats">Lagre playerstats</a>
            <div id="SavePlayerStatsMessage">

            </div>
        </div>

        <table>

        <c:forEach items="${playerStats}" var="playerStat" varStatus="status">
            <c:if test="${status.first}">
                <tr>
                    <th>Navn for spiller ikke funnet</th>
                    <th>Velg spiller</th>
                    <th>Lagre</th>
                </tr>
            </c:if>

            <c:if test="${playerStat.player.new}">

            <tr id="PlayerStatRow${playerStat.temporarlyId}">
                <td>
                        ${playerStat.player.lastName}
                </td> <td>${playerStat.player.team.fullName}</td>
                <td>
                    <select id="PlayerSelect${playerStat.temporarlyId}">
                        <c:forEach items="${players}" var="player">
                            <option value="${player.playerId}">${player.lastName} - ${player.firstName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <a class="button updateplayerstats" href="${pageContext.request.contextPath}/tpl/admin/match/addplayertoplayerstat" data-id="${playerStat.temporarlyId}">Oppdater stats</a>
                </td>
            </tr>

            </c:if>
        </c:forEach>
        </table>
    </c:if>

    <table>
        <thead>
        <tr>
            <th><spring:message code="playerstats.name"/></th>
            <th title="<spring:message code="playerstats.goals"/>"><spring:message code="playerstats.goals.short"/></th>
            <th title="<spring:message code="playerstats.assists"/>"><spring:message code="playerstats.assists.short"/></th>
            <th title="<spring:message code="playerstats.ownGoal"/>"><spring:message code="playerstats.ownGoal.short"/></th>
            <th title="<spring:message code="playerstats.missedPenalty"/>"><spring:message code="playerstats.missedPenalty.short"/></th>
            <th title="<spring:message code="playerstats.savedPenalty"/>"><spring:message code="playerstats.savedPenalty.short"/></th>
            <th title="<spring:message code="playerstats.yellowcard"/>"><spring:message code="playerstats.yellowcard.short"/></th>
            <th title="<spring:message code="playerstats.redCard"/>"><spring:message code="playerstats.redCard.short"/></th>
            <th title="<spring:message code="playerstats.playedMinutes"/>"><spring:message code="playerstats.playedMinutes.short"/></th>
            <th title="<spring:message code="playerstats.manofthematch"/>"><spring:message code="playerstats.manofthematch.short"/></th>
            <c:if test="${isSaved}">
                <th title="<spring:message code="playerstats.points"/>"><spring:message code="playerstats.points"/></th>
                <th><spring:message code="playerstats.edit"/></th>

            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${playerStats}" var="playerStat">

            <tr id="${playerStat.temporarlyId}">
                <td>${playerStat.player.firstName} - ${playerStat.player.lastName}</td>
                <td>${playerStat.goals}</td>
                <td>${playerStat.assists}</td>
                <td>${playerStat.ownGoal}</td>
                <td>${playerStat.missedPenalty}</td>
                <td>${playerStat.savedPenalty}</td>
                <td>${playerStat.yellowCard}</td>
                <td>
                    <c:choose>
                        <c:when test="${playerStat.redCard}">1</c:when>
                        <c:otherwise>0</c:otherwise>
                    </c:choose>
                </td>
                <td>${playerStat.playedMinutes}</td>
                <td>${playerStat.manOfTheMatch}</td>
                <c:if test="${isSaved}">
                    <td>${playerStat.points}</td>
                    <td><a href="${pageContext.request.contextPath}/tpl/admin/playerstats/edit?playerId=${playerStat.player.playerId}&matchId=${match.matchId}" class="editPlayerStats edit">&nbsp;</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>





