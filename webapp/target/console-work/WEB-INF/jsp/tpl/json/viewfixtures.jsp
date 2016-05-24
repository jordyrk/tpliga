<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
{
"runde": "${round.round}",
"konkurranser":[
{
"konkurranse":"Premier League",
"kamper":[
<c:forEach items="${round.matchList}" var="match" varStatus="status">
    {"kamp":"${match.homeTeam.fullName} - ${match.awayTeam.fullName} "}
    <c:if test="${! status.last}">,</c:if>
</c:forEach>
]
} ,
<c:forEach items="${fixturesMap}" var="entry" varStatus="mapStatus">
    {
    "konkurranse":"${entry.key}",
    "kamper":[
    <c:forEach items="${entry.value}" var="fantasyMatch" varStatus="status">
        {"kamp":"${fantasyMatch.homeTeam.name} - ${fantasyMatch.awayTeam.name} "}
        <c:if test="${! status.last}">,</c:if>
    </c:forEach>
    ]
    }<c:if test="${! mapStatus.last}">,</c:if>
</c:forEach>
]
}
