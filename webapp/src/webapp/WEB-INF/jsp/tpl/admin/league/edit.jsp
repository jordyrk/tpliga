<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <h1><spring:message code="league.admin"/></h1>
    <form:form commandName="league" action="${pageContext.request.contextPath}/tpl/admin/league/edit" cssClass="adminForm">
        <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
        <div class="field">
            <div class="label">
                <form:label path="shortName"><spring:message code="league.shortName"/>: </form:label>
            </div>
            <div class="input">
                <form:input path="shortName"/>
            </div>
        </div>
        <div class="field">
            <div class="label">
        <form:label path="fullName"><spring:message code="league.fullName"/>: </form:label>
            </div>
            <div class="input">
                <form:input path="fullName"/>
            </div>
        </div>
        <form:hidden path="leagueId"/>
        <input type="submit" value="Lagre">
    </form:form>

