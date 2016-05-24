<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty teams}">
    [
    <c:forEach items="${teams}" var="team" varStatus="status">
        {
        "teamId": "${team.teamId}",
        "externalId": "${team.externalId}",
        "shortName": "${team.shortName}",
        "fullName": "${team.fullName}"
        }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
    ]
</c:if>





