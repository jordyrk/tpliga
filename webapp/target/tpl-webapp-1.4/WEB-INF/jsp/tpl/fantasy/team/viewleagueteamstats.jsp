<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="numberOfTeams" value="${fantasyLeague.numberOfTeams}"/>
<div id="FantasyLeagueStats" class="teamStats">
    <h2>Stats</h2>
    <div class="columnwrapper">
        <c:forEach var="fantasyLeagueStanding" items="${fantasyLeagueStandings}">
            <c:choose>
                <c:when test="${fantasyLeagueStanding != null && fantasyLeagueStanding.position >0}">
                    <div class="column"><a href="#" title="Posisjon: ${fantasyLeagueStanding.position}">
                        <div class="stats" style="height:6px;margin-bottom:${(numberOfTeams - fantasyLeagueStanding.position)*10+4}px; "></div></a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="column">
                    </div>
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </div>
    <div class="roundnumberwrapper">
        <c:forEach var="fantasyRound" items="${fantasyRounds}">
            <div class="roundnumber">${fantasyRound.round}</div>
        </c:forEach>
    </div>
    <div class="matchresults">
        <c:forEach var="fantasyMatch" items="${fantasyMatches}">
            <c:choose>
                <c:when test="${fantasyMatch.homeTeam.teamId == fantasyTeam.teamId}">
                    <c:set var="oponentTeam" value="${fantasyMatch.awayTeam}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="oponentTeam" value="${fantasyMatch.homeTeam}"/>
                </c:otherwise>
            </c:choose>
            <div class="matchresult">
                <c:choose>
                    <c:when test="${fantasyMatch== null}">
                        <span class="noresult" title="Ingen kamp spilt">-</span>
                    </c:when>

                    <c:when test="${fantasyMatch.winningTeam.teamId == fantasyTeam.teamId}">
                        <span class="victory" title="Seier mot ${oponentTeam.name} (${fantasyMatch.homegoals}  - ${fantasyMatch.awaygoals} )">W</span>
                    </c:when>
                    <c:when test="${fantasyMatch.homegoals == fantasyMatch.awaygoals}">
                        <span class="draw" title="Uavgjort mot ${oponentTeam.name} (${fantasyMatch.homegoals}  - ${fantasyMatch.awaygoals} )">D</span>
                    </c:when>
                    <c:otherwise>
                        <span class="loss" title="Tap mot ${oponentTeam.name} (${fantasyMatch.homegoals}  - ${fantasyMatch.awaygoals} )">L</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>

</div>|