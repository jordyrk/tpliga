<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="team.season"/></h1>
<p>Ved &aring; opprette en ny sesong s&aring; vil det opprettes et antall runder tilsvarende antall lag * 2 -2. </p>
<form:form commandName="season" action="${pageContext.request.contextPath}/tpl/admin/season/edit" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="numberOfTeams"><spring:message code="season.numberOfTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfTeams"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="startDate"><spring:message code="season.startDate"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="startDate"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="endDate"><spring:message code="season.endDate"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="endDate"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="defaultSeason"><spring:message code="season.defaultSeason"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="defaultSeason"/>
        </div>
    </div>
    <div class="field">
         <form:radiobuttons path="league" items="${availableLeagues}" itemLabel="shortName" itemValue="leagueId" delimiter="<br>"/>
    </div>
    <div class="field">
        <form:checkboxes path="teams" items="${availableTeams}" itemLabel="shortName" itemValue="teamId" delimiter="<br>" />
    </div>


    <form:hidden path="seasonId"/>
    <input type="submit" value="Lagre">
</form:form>

