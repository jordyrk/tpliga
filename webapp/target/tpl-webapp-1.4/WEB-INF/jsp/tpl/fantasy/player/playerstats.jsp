<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aksess" uri="http://www.kantega.no/aksess/tags/aksess" %>

<span><a class="team ${fn:toLowerCase(fn:replace(player.team.shortName,' ' ,'' ))}" href="${player.team.teamId}">${player.team.shortName}</a></span><br>
<div id="PlayerExtendedView">
    <div class="topp">
        <h1>${player.firstName} ${player.lastName}</h1>
        <img src="../bitmaps/footballcard/playeroutline.png">
        <span class="playerinfo"><span>${player.team.shortName}</span> - <span>${player.playerPosition}</span></span>
        <span class="price">${player.price}</span>
    </div>
    <div class="bottom">
        <table>
            <thead>
            <tr>
                <th></th>
                <th title="Antall m&aring;l">M</th>
                <th title="Antall assists">A</th>
                <th title="M&aring;l i feil bur">Sj&oslash;l</th>
                <th title="Straffebom">Bom</th>
                <th title="Strafferedning">Str red</th>
                <th title="Gul kort">G</th>
                <th title="Raukort">R</th>
                <th title="Spilte minutter">Spi M</th>
                <th title="Startet kampen">Sta</th>
                <th title="Banens beste">BB</th>
                <th title="Antall tabkonk poeng">P</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${playerStats}" var="playerStat">
                <c:choose>
                    <c:when test="${playerStat.match.homeTeam.shortName == playerStat.team.shortName}">
                        <c:set var="againstTeam" value="${playerStat.match.awayTeam}"/>
                        <c:set var="text" value="Hjemme"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="againstTeam" value="${playerStat.match.homeTeam}"/>
                        <c:set var="text" value="Borte"/>
                    </c:otherwise>
                </c:choose>


                <tr>
                    <td title="${text} mot ${againstTeam.shortName} (${playerStat.match.homeGoals} - ${playerStat.match.awayGoals})"><span>${againstTeam.shortName}</span></td>
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
                    <td>
                        <c:choose>
                            <c:when test="${playerStat.startedGame}">1</c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${playerStat.manOfTheMatch ? 'Ja':'Nei' }</td>

                    <td>${playerStat.points}</td>
                </tr>
            </c:forEach>

            <tr>
                <td>Sum</td>
                <td>${sumPlayerStats.goals}</td>
                <td>${sumPlayerStats.assists}</td>
                <td>${sumPlayerStats.ownGoal}</td>
                <td>${sumPlayerStats.missedPenalty}</td>
                <td>${sumPlayerStats.savedPenalty}</td>
                <td>${sumPlayerStats.yellowCard}</td>
                <td>${sumPlayerStats.sumRedCard}</td>
                <td>${sumPlayerStats.playedMinutes}</td>
                <td>${sumPlayerStats.sumStartedGame}</td>
                <td>${sumPlayerStats.sumManOfTheMatch}</td>
                <td>${sumPlayerStats.points}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<br><br>
<aksess:hasrole roles="admin,tpladmin">
    <span id="EditPlayer" class="button">Endre spiller</span>
    <input type="hidden" id="PlayerId" value="${player.playerId}"/>
</aksess:hasrole>
<div><a id='Teamoverview' class="button">Tilbake til lagoversikt</a></div>




