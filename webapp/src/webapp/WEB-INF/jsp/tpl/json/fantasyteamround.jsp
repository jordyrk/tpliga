<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{
"official": ${official},
"officialwhenroundisclosed": ${officialwhenroundisclosed},
"formation": "${formation}"
<c:if test="${not empty players}">
    ,"players":[
    <c:forEach items="${players}" var="player" varStatus="status">
        {
        "playerId": ${player.playerId},
        "firstName": "${player.firstName}",
        "lastName": "${player.lastName}",
        "playerPosition": "${player.playerPosition}",
        "price": ${player.price},
        "team": "${player.team.shortName}",
        "shirtNumber": "${player.shirtNumber}",
        "externalId": "${player.externalId}",
        "formationPosition": "${player.formationPosition}"
        }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
    ]
</c:if>
}