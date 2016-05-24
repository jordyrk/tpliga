<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a href="${pageContext.request.contextPath}/tpl/admin/fantasycup/editfantasycup" class="adminLink"><spring:message code="fantasycup.create"/></a>
<p>${errormessage}</p>
<table>
    <thead>
    <tr>
        <th><spring:message code="fantasycup.name"/></th>
        <th><spring:message code="fantasycup.numberOfTeams"/></th>

        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="fantasyCup" items="${fantasyCups}">
            <tr>
                <td><c:out value="${fantasyCup.name}"/></td>
                <td><c:out value="${fantasyCup.numberOfTeams}"/></td>
                <td>
                    <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasycup/editfantasycup?fantasyCupId=${fantasyCup.id}" title="<spring:message code='fantasycup.edit'/> ${league.shortName}">&nbsp;</a>
                </td>
                <td>
                    <a class="adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasycup/viewfantasycupgroups?fantasyCupId=${fantasyCup.id}">Vis grupper</a>
                </td>
                <td>
                    <a class="delete adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasycup/deletefantasycup?fantasyCupId=${fantasyCup.id}" title="Slett cup">&nbsp;</a>
                </td>

                <td>
                    <a class="adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasycup/createfixtures?fantasyCupId=${fantasyCup.id}">Lag cupoppsett</a>
                </td>

            </tr>
        </c:forEach>
    </tbody>
</table>


