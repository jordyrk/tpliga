<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a href="${pageContext.request.contextPath}/tpl/admin/league/edit" class="adminLink"><spring:message code="league.create"/></a>
<table>
    <thead>
    <tr>
        <th><spring:message code="league.fullName"/></th>
        <th><spring:message code="league.shortName"/></th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="league" items="${leagues}">
            <tr>
                <td><c:out value="${league.fullName}"/></td>
                <td><c:out value="${league.shortName}"/></td>
                <td>
                    <%-- Deleting a league may not be so wise
                    <a class="delete" href="${pageContext.request.contextPath}/tpl/admin/league/delete?leagueId=${league.leagueId}" title="Delete league ${league.shortName}">&nbsp;</a>
                     --%>
                    <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/league/edit?leagueId=${league.leagueId}" title="<spring:message code='league.edit'/> ${league.shortName}">&nbsp;</a>
                </td>

            </tr>
        </c:forEach>
    </tbody>

</table>


