<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty players}">
    [
    <c:forEach items="${players}" var="player" varStatus="status">
        {
        "playerId": ${player.playerId},
        "firstName": "${player.firstName}",
        "lastName": "${player.lastName}",
        "playerPosition": "${player.playerPosition}",
        "price": ${player.price},
        "team": "${player.team.shortName}",
        "shirtNumber": "${player.shirtNumber}",
        "externalId": "${player.externalId}"
        <c:if test="${isplayerusage}">,"numberOfUsages": "${player.numberOfUsages}"</c:if>
        <c:if test="${isplayerformation}">,"formationPosition": "${player.formationPosition}"</c:if>
        }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
    ]
</c:if>





