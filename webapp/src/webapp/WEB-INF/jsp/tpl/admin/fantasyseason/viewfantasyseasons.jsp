<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table>
    <thead>
    <tr>
        <th><spring:message code="fantasyseason.name"/></th>
        <th><spring:message code="fantasyseason.seasonref"/></th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="fantasyseason" items="${fantasyseasons}">
        <tr>
            <td><c:out value="${fantasyseason.name}"/></td>
            <td><fmt:formatDate value="${fantasyseason.season.startDate}" pattern="yyyy"/> - <fmt:formatDate value="${fantasyseason.season.endDate}" pattern="yyyy"/>, <c:out value="${fantasyseason.season.league.shortName}"/></td>
            <td>
                <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/editfantasyseason?fantasySeasonId=${fantasyseason.fantasySeasonId}" title="<spring:message code='fantasyseason.edit'/> ${fantasyseason.name}">&nbsp;</a>
            </td>
            <td>
                <a class="viewRounds adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/viewallrounds?fantasySeasonId=${fantasyseason.fantasySeasonId}" title="<spring:message code='fantasyseason.rounds'/> ${fantasyseason.name}">&nbsp;</a>
            </td>

        </tr>
    </c:forEach>
    </tbody>

</table>

<a href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/editfantasyseason" class="adminLink button"><spring:message code="fantasyseason.create"/></a>
