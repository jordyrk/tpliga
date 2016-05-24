<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table>
    <thead>
    <tr>
        <th><spring:message code="player.firstName"/></th>
        <th><spring:message code="player.lastName"/></th>
        <th><spring:message code="player.position"/></th>
        <th><spring:message code="player.price"/></th>
        <th><spring:message code="player.shirtNumber"/></th>
        <th><spring:message code="player.team"/></th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="player" items="${players}">
            <tr>
                <td><c:out value="${player.firstName}"/></td>
                <td><c:out value="${player.lastName}"/></td>
                <td><c:out value="${player.playerPosition}"/></td>
                <td><c:out value="${player.price}"/></td>
                <td><c:out value="${player.shirtNumber}"/></td>
                <td><c:out value="${player.team.shortName}"/></td>
                <td>
                    <c:if test="${editPlayer}">
                    <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/player/edit?playerId=${player.playerId}" title="<spring:message code='player.edit'/> ${player.firstName}">&nbsp;</a>
                    </c:if>
                </td>
                <td>
                    <c:if test="${editPlayer}">
                    <a class="delete adminLink" href="${pageContext.request.contextPath}/tpl/admin/player/deleteplayer?playerId=${player.playerId}" title="<spring:message code='player.delete'/> ${player.firstName}">&nbsp;</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>