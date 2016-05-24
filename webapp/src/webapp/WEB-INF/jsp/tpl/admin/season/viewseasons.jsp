<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<a href="${pageContext.request.contextPath}/tpl/admin/season/edit" class="adminLink"><spring:message code="season.create"/></a>

<table>
    <thead>
    <tr>
        <th><spring:message code="season.numberOfTeams"/></th>
        <th><spring:message code="season.startDate"/></th>
        <th><spring:message code="season.endDate"/></th>
        <th><spring:message code="season.league"/></th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="season" items="${seasons}">
            <tr>
                <td><c:out value="${season.numberOfTeams}"/></td>
                <td><fmt:formatDate value="${season.startDate}" pattern="dd.MM.yyyy"/></td>
                <td><fmt:formatDate value="${season.endDate}" pattern="dd.MM.yyyy"/></td>
                <td><c:out value="${season.league.shortName}"/></td>
                <td>
                    <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/season/edit?seasonId=${season.seasonId}" title="<spring:message code='season.edit'/> <fmt:formatDate value='${season.startDate}' pattern='yyyy'/>/<fmt:formatDate value='${season.endDate}' pattern='yyyy'/>">&nbsp;</a>
                </td>

            </tr>
        </c:forEach>
    </tbody>

</table>
