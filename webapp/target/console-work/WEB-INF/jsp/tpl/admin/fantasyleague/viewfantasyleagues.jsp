<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a href="${pageContext.request.contextPath}/tpl/admin/fantasyleague/editfantasyleague" class="adminLink"><spring:message code="league.create"/></a>
<p>${errormessage}</p>
<p>${message}</p>
<table>
    <thead>
    <tr>
        <th><spring:message code="fantasyleague.name"/></th>
        <th><spring:message code="fantasyleague.numberOfTeams"/></th>
        <th><spring:message code="fantasyleague.homeAndAwayMatches"/></th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="fantasyLeague" items="${fantasyLeagues}">
            <tr>
                <td><c:out value="${fantasyLeague.name}"/></td>
                <td><c:out value="${fantasyLeague.numberOfTeams}"/></td>
                <td><c:out value="${fantasyLeague.homeAndAwayMatches}"/></td>
                <td>

                    <a class="delete adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyleague/delete?fantasyLeagueId=${fantasyLeague.id}" title="Delete league ${league.shortName}">&nbsp;</a>

                    <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyleague/editfantasyleague?fantasyLeagueId=${fantasyLeague.id}" title="<spring:message code='fantasyleague.edit'/> ${league.shortName}">&nbsp;</a>

                </td>
                <td>

                    <a class="adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyleague/createfixtures?fantasyLeagueId=${fantasyLeague.id}" title="<spring:message code='fantasyleague.createfixtures'/> ${league.shortName}">Lag termin</a>

                </td>

            </tr>
        </c:forEach>
    </tbody>

</table>


