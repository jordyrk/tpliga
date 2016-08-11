<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="numberOfTeams" value="${fantasyCompetition.numberOfTeams}"/>

<div id="FantasyTeamRoundStats" class="teamStats">
    <h2>Stats</h2>
    <div class="columnwrapper">
        <c:forEach var="fantasyTeamRound" items="${fantasyTeamRounds}">
            <div class="column"><a href="#" title="Antall poeng ${fantasyTeamRound.points}">
                <div class="stats" style="height:${fantasyTeamRound.points*2+10}px;"></div></a>
            </div>
        </c:forEach>
        <c:forEach var="fantasyCompetitionStanding" items="${fantasyCompetitionStandings}">
            <div class="position"><a href="#" title="Posisjon ${fantasyCompetitionStanding.position}">
                <div class="stats" style="height:${fantasyCompetitionStanding.position*8+10}px;"></div></a>
            </div>
        </c:forEach>
    </div>
    <div class="roundnumberwrapper">
        <c:forEach var="fantasyTeamRound" items="${fantasyTeamRounds}">
            <div class="roundnumber">${fantasyTeamRound.fantasyRound.round}</div>
        </c:forEach>
    </div>
</div>