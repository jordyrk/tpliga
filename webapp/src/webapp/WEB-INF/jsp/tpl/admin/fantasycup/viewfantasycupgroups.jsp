<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p>${errormessage}</p>
<table>
    <thead>
    <tr>
        <th><spring:message code="fantasycupgroup.name"/></th>
        <th><spring:message code="fantasycupgroup.numberOfTeams"/></th>
        <th><spring:message code="fantasycupgroup.homeAndAwayMatches"/></th>

        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="fantasyCupGroup" items="${fantasyCupGroups}">
            <tr>
                <td><c:out value="${fantasyCupGroup.name}"/></td>
                <td><c:out value="${fantasyCupGroup.numberOfTeams}"/></td>
                <td><c:out value="${fantasyCupGroup.homeAndAwayMatches}"/></td>
                <td>
                    <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasycup/editfantasycupgroup?fantasyCupGroupId=${fantasyCupGroup.id}" title="<spring:message code='fantasycupgroup.edit'/> ${league.shortName}">&nbsp;</a>
                </td>
                <td>
                    <a class="adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasycupgroup/createfixtures?fantasyCupGroupId=${fantasyCupGroup.id}" >Lag terminliste</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>


