<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<p>${message}</p>
<table>
    <thead>
    <tr>
        <th>Managernavn</th>
        <th>Aktiv</th>
        <th>Slett</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="fantasyManager" items="${fantasyManagerList}">
            <tr>
                <td><c:out value="${fantasyManager.managerName}"/></td>
                <td>
                    <input id="FantasyManager${fantasyManager.managerId}" type="checkbox" <c:if test="${fantasyManager.active}"> checked="checked" </c:if> class="fantasyManagerActive" value="${fantasyManager.managerId}">
                </td>
                <td>
                    <a class="delete adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasymanager/deletefantasymanager?fantasyManagerId=${fantasyManager.managerId}" title="Slett manager">&nbsp;</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>


