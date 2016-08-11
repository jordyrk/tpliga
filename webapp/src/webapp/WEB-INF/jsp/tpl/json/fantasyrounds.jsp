<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty fantasyRounds}">
    [
    <c:forEach items="${fantasyRounds}" var="fantasyround" varStatus="status">
        {
        "fantasyRoundId": ${fantasyround.fantasyRoundId},
        "round": ${fantasyround.round},
        "active": <c:choose><c:when test="${fantasyround.fantasyRoundId == currentFantasyRound.fantasyRoundId}">true</c:when><c:otherwise>false</c:otherwise></c:choose>

        }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
    ]
</c:if>





