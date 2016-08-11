<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
{
"neste runde" : ${fantasyRound.round},
"forrigeRundeId": ${previousFantasyRound.fantasyRoundId},
"ant_blotta_lag": ${fn:length(blottaLagWrapperList)},
"ant_blotta_lag_nar_runden_stenge": ${numberOfOfficialWhenRoundIsClosed},
"runden_stenge": ${fantasyRound.closeDate.time},
"gjeldendeRundeId": ${currentFantasyRound.fantasyRoundId},
"gjeldendeRunde": ${currentFantasyRound.round},

"blottalag":[
<c:if test="${not empty blottaLagWrapperList}">

    <c:forEach items="${blottaLagWrapperList}" var="blottaLagWrapper" varStatus="status">
        {
        "lagnavn": "${blottaLagWrapper.fantasyTeamRound.fantasyTeam.name}",
        "lag_id": "${blottaLagWrapper.fantasyTeamRound.fantasyTeam.teamId}",
        "poeng":  "${blottaLagWrapper.fantasyTeamRound.points}",
        "formasjon":  "${blottaLagWrapper.fantasyTeamRound.formation.description}",
        "motstanderid":"${blottaLagWrapper.oppositionTeamId}",
        "pris":"${blottaLagWrapper.fantasyTeamRound.totalPrice}",
        "lag": {
        <c:forEach var="player" items="${blottaLagWrapper.fantasyTeamRound.playerInFormationList}" varStatus="playerstatus">
            "player${playerstatus.index+1}": {"navn":"<c:if test="${fn:length(player.firstName) > 0}">${fn:substring(player.firstName,0,1)}.</c:if> ${player.lastName}","lag":"${player.team.shortName}"} <c:if test="${!playerstatus.last}">,</c:if>
        </c:forEach>

        }
        }<c:if test="${!status.last}">,</c:if>
    </c:forEach>

</c:if>
]
}




