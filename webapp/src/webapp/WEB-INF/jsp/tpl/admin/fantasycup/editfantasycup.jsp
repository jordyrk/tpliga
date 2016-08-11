<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="fantasycup.admin"/></h1>
<form:form commandName="fantasycup" action="${pageContext.request.contextPath}/tpl/admin/fantasycup/editfantasycup" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="name"><spring:message code="fantasycup.name"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="name"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfTeams"><spring:message code="fantasycup.numberOfTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfTeams"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfQualifiedTeamsFromGroups"><spring:message code="fantasycup.numberOfQualifiedTeamsFromGroups"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfQualifiedTeamsFromGroups"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfGroups"><spring:message code="fantasycup.numberOfGroups"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfGroups"/>
        </div>
    </div>
    <div class="field">
         <form:radiobuttons path="fantasySeason" items="${fantasySeasonList}" itemLabel="name" itemValue="fantasySeasonId" delimiter="<br>"/>
    </div>
    Inkluder lag som skal være med i cup og gruppespill
    <div class="field">
        <form:checkboxes path="fantasyTeamList" items="${availableFantasyTeamList}" itemLabel="name" itemValue="teamId" delimiter="<br>" />
    </div>
    Inkluder runder som skal være med i cup og gruppespill
    <c:if test="${fantasycup.fantasySeason != null }">
    <div class="field">
        <form:checkboxes path="fantasyRoundList" items="${fantasyRoundList}" itemLabel="round" itemValue="fantasyRoundId" delimiter="<br>" />
    </div>
    </c:if>

    <form:hidden path="id"/>
    <input type="submit" value="Lagre">
</form:form>

