<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty allFantasyTeams}">
    [
    <c:forEach items="${allFantasyTeams}" var="fantasyTeam" varStatus="status">
        {
        "id": "${fantasyTeam.teamId}",
        "name": "${fantasyTeam.name}",
        "stadium": "${fantasyTeam.stadium}",
        "teamSpirit": "${fantasyTeam.teamSpirit}",
        "supporterClub": "${fantasyTeam.supporterClub}",
        "latestNews": "${fantasyTeam.latestNews}",
        "managerName": "${fantasyTeam.fantasyManager.managerName}",
        "imageUrl": "http://www.tpliga.org${fantasyTeam.imageUrl}"
        }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
    ]
</c:if>





