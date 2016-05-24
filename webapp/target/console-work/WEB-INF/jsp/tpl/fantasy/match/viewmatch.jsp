<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aksess" uri="http://www.kantega.no/aksess/tags/aksess" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<c:set value="40" var="imageSize"/>
<div class="topp">
    <div class="fantasyteam">
        <tpl:profileimage multiMediaId="${fantasyMatch.homeTeam.multiMediaImageId}" height="${imageSize}" width="${imageSize}"/>
    </div>
    <div class="matchheader">
        <div>
            ${fantasyMatch.homeTeam.name} -  ${fantasyMatch.awayTeam.name}
        </div>
        <div>
            <c:if test="${fantasyMatch.played}">
                (${fantasyHomeTeamRound.points}) ${fantasyMatch.homegoals}
                -
                ${fantasyMatch.awaygoals} (${fantasyAwayTeamRound.points})
            </c:if>
        </div>
        <br>
        ${fantasyMatch.homeTeam.stadium}
        <br>Runde ${fantasyMatch.fantasyRound.round} i ${fantasyMatch.fantasyCompetition.name}
    </div>
    <div class="fantasyteam">
        <tpl:profileimage multiMediaId="${fantasyMatch.awayTeam.multiMediaImageId}" height="${imageSize}" width="${imageSize}"/>
    </div>
</div>
<div class="bottom" >

    <div class="team home">
        <div>
            <c:if test="${fantasyHomeTeamRound.official ||  isHomeTeam}">
                <c:forEach items="${fantasyHomeTeamRound.playerInFormationList}" var="player">
                    <div class="player ${fn:replace(fn:toLowerCase(player.team.shortName)," ", "")}" title="${player.team.fullName}">${player.firstName} ${player.lastName}</div>
                </c:forEach>
            </c:if>
        </div>
    </div>
    <div class="team away">
        <div>
            <c:if test="${fantasyAwayTeamRound.official ||  isAwayTeam}">
                <c:forEach items="${fantasyAwayTeamRound.playerInFormationList}" var="player">
                    <div class="player ${fn:replace(fn:toLowerCase(player.team.shortName)," ", "")}" title="${player.team.fullName}">${player.firstName} ${player.lastName}</div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>



