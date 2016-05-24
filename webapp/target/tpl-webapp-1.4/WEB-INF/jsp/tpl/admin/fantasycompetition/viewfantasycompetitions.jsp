<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<a href="${pageContext.request.contextPath}/tpl/admin/fantasycompetition/editfantasycompetition" class="adminLink"><spring:message code="fantasycompetition.create"/></a>
<table>
    <thead>
    <tr>
        <th><spring:message code="fantasycompetition.name"/></th>
        <th><spring:message code="fantasycompetition.numberOfTeams"/></th>
        <th><spring:message code="fantasycompetition.season"/></th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="fantasycompetition" items="${fantasycompetitions}">
        <tr>
            <td><c:out value="${fantasycompetition.name}"/></td>
            <td><c:out value="${fantasycompetition.numberOfTeams}"/></td>
            <td><c:out value="${fantasycompetition.fantasySeason.name}"/></td>
            <td>
                <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasycompetition/editfantasycompetition?fantasyCompetitionId=${fantasycompetition.fantasyCompetitionId}" title="<spring:message code='fantasycompetition.edit'/> ${fantasycompetition.name}">&nbsp;</a>
            </td>

        </tr>
    </c:forEach>
    </tbody>

</table>


