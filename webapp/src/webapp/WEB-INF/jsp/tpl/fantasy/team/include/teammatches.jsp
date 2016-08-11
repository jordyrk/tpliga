<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="StandardTable" class="defaultTable">
    <thead>
    <tr>
        <th>Runde</th>
        <th>Hjemmelag</th>
        <c:if test="${displaymatchscore}"><th></th></c:if>
        <th>Bortelag</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="match" items="${matches}" end="${numberOfMatches}">
        <tr>
            <td>${match.fantasyRound.round}</td>
            <td>${match.homeTeam.name}</td>
            <c:if test="${displaymatchscore}"><td> ${match.homegoals}-${match.awaygoals}</td></c:if>
            <td>${match.awayTeam.name}</td>

        </tr>
    </c:forEach>
    </tbody>
</table>