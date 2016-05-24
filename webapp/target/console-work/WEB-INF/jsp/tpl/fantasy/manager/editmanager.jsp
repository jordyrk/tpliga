<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<kantega:section id="title">Manager</kantega:section>

<kantega:section id="bodyclass">fantasyTeam</kantega:section>

<kantega:section id="content">
<h1><spring:message code="fantasymanager.admin"/> </h1>

<form:form commandName="fantasymanager" action="${pageContext.request.contextPath}/tpl/fantasy/manager/editmanager">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="managerName"><spring:message code="fantasymanager.managerName"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="managerName"/>
        </div>
    </div>

    <form:hidden path="userId"/>
    <form:hidden path="managerId"/>
    <input type="submit" value="Lagre">
</form:form>

</kantega:section>
<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>