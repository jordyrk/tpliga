<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a class="adminLink" href="admin/fantasycup/viewfantasycups">FantasyCup admin</a>

<table>
    <thead>
    <tr>
        <th>Hjemmelag</th>
        <th>Bortelag</th>
        <th>Runde</th>
        <th>Hjemmem&aring;l</th>
        <th>Bortem&aring;l</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="fantasyMatch" items="${fantasyMatchList}">
            <tr>
                <td><c:out value="${fantasyMatch.homeTeam.name}"/></td>
                <td><c:out value="${fantasyMatch.awayTeam.name}"/></td>
                <td><c:out value="${fantasyMatch.fantasyRound.fantasyRoundId}"/></td>
                <td><c:out value="${fantasyMatch.homegoals}"/></td>
                <td><c:out value="${fantasyMatch.awaygoals}"/></td>
            </tr>
        </c:forEach>
    </tbody>

</table>


