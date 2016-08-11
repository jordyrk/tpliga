<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="rule.admin"/></h1>
<form:form commandName="teamrule" action="${pageContext.request.contextPath}/tpl/admin/fantasyrule/editteamrule" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="name"><spring:message code="rule.name"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="name"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="ruleType"><spring:message code="rule.ruleType"/>: </form:label>
        </div>
        <div class="input">
            <form:select path="ruleType" items="${ruletypes}" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="points"><spring:message code="rule.points"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="points"/>
        </div>
    </div>
     <div class="field">
        <div class="label">
            <form:label path="qualifingNumber"><spring:message code="rule.qualifingNumber"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="qualifingNumber"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="playerRulePosition"><spring:message code="rule.position"/>: </form:label>
        </div>
        <div class="input">

            <form:select path="playerRulePosition" items="${positions}" />
        </div>
    </div>
    <form:hidden path="id"/>
    <input type="submit" value="Lagre">
</form:form>

