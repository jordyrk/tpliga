<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
    <table>
        <thead>
        <tr>
            <th>Navn</th>
            <th>Pris</th>
            <th>Lag</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="player" items="${fantasyTeamRound.playerInFormationList}">
            <%@include file="playerlist.jsp"%>
        </c:forEach>
        </tbody>
    </table>
</div>

