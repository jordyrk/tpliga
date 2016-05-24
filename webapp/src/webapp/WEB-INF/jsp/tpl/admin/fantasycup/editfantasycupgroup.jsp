<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="fantasycupgroup.admin"/></h1>
<form:form commandName="fantasycupgroup" action="${pageContext.request.contextPath}/tpl/admin/fantasycup/editfantasycupgroup" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="name"><spring:message code="fantasycupgroup.name"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="name"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfTeams"><spring:message code="fantasycupgroup.numberOfTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfTeams"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="homeAndAwayMatches"><spring:message code="fantasycupgroup.homeAndAwayMatches"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="homeAndAwayMatches"/>
        </div>
    </div>
    Inkluder lag som skal være med i gruppespill
    <div class="field">
        <form:checkboxes path="fantasyTeamList" items="${availableFantasyTeamList}" itemLabel="name" itemValue="teamId" delimiter="<br>" />
    </div>
    Inkluder runder som skal være med i gruppespill

    <div class="field">
        <form:checkboxes path="fantasyRoundList" items="${fantasyRoundList}" itemLabel="round" itemValue="fantasyRoundId" delimiter="<br>" />
    </div>

    <form:hidden path="id"/>
    <form:hidden path="fantasyCup"/>
    <input type="submit" value="Lagre">
</form:form>

