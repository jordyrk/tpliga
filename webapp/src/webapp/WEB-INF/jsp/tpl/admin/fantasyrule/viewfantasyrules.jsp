<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<a href="${pageContext.request.contextPath}/tpl/admin/fantasyrule/editplayerrule" class="adminLink"><spring:message code="rule.createplayerrule"/></a>
<a href="${pageContext.request.contextPath}/tpl/admin/fantasyrule/editteamrule" class="adminLink"><spring:message code="rule.createteamrule"/></a>
<div class="message">${message}</div>
<table>
    <thead>
    <tr>
        <th><spring:message code="rule.ruleType"/></th>
        <th><spring:message code="rule.name"/></th>
        <th><spring:message code="rule.points"/></th>
        <th><spring:message code="rule.qualifingNumber"/></th>
        <th><spring:message code="rule.position"/></th>

        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="playerRule" items="${playerRuleList}">
        <tr>
            <td><c:out value="${playerRule.ruleType}"/></td>
            <td><c:out value="${playerRule.name}"/></td>
            <td><c:out value="${playerRule.points}"/></td>
            <td><c:out value="${playerRule.qualifingNumber}"/></td>
            <td><c:out value="${playerRule.playerRulePosition}"/></td>

            <td>
                <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyrule/editplayerrule?ruleId=${playerRule.id}" title="<spring:message code='rule.edit'/> ${playerRule.name}">&nbsp;</a>
            </td>
              <td>
                <a class="delete adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyrule/deleterule?ruleId=${playerRule.id}" title="<spring:message code='rule.delete'/> ${playerRule.name}">&nbsp;</a>
            </td>
        </tr>
    </c:forEach>
    <c:forEach var="teamrule" items="${teamRuleList}">
        <tr>
            <td><c:out value="${teamrule.ruleType}"/></td>
            <td><c:out value="${teamrule.name}"/></td>
            <td><c:out value="${teamrule.points}"/></td>
            <td><c:out value="${teamrule.qualifingNumber}"/></td>
            <td><c:out value="${teamrule.playerRulePosition}"/></td>

            <td>
                <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyrule/editteamrule?ruleId=${teamrule.id}" title="<spring:message code='rule.edit'/> ${teamRule.name}">&nbsp;</a>
            </td>
            <td>
                <a class="delete adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyrule/deleterule?ruleId=${teamrule.id}" title="<spring:message code='rule.delete'/> ${playerRule.name}">&nbsp;</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>


