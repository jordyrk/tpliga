<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="fantasyleague.admin"/></h1>
<form:form commandName="fantasyleague" action="${pageContext.request.contextPath}/tpl/admin/fantasyleague/editfantasyleague" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="name"><spring:message code="fantasyleague.name"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="name"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfTeams"><spring:message code="fantasyleague.numberOfTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfTeams"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfTopTeams"><spring:message code="fantasyleague.numberOfTopTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfTopTeams"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfSecondTopTeams"><spring:message code="fantasyleague.numberOfSecondTopTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfSecondTopTeams"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfBottomTeams"><spring:message code="fantasyleague.numberOfBottomTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfBottomTeams"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfSecondBotttomTeams"><spring:message code="fantasyleague.numberOfSecondBotttomTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfSecondBotttomTeams"/>
        </div>
    </div>

    <div class="field">
        <div class="label">
            <form:label path="styletheme"><spring:message code="fantasyleague.styletheme"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="styletheme"/>
        </div>
    </div>
     <div class="field">
        <div class="label">
            <form:label path="homeAndAwayMatches"><spring:message code="fantasyleague.homeAndAwayMatches"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="homeAndAwayMatches"/>
        </div>
    </div>
    <div class="field">
         <form:radiobuttons path="fantasySeason" items="${fantasySeasonList}" itemLabel="name" itemValue="fantasySeasonId" delimiter="<br>"/>
    </div>

    <div class="field">
        <form:checkboxes path="fantasyTeamList" items="${availableFantasyTeamList}" itemLabel="name" itemValue="teamId" delimiter="<br>" />
    </div>
    <c:if test="${fantasyleague.fantasySeason != null }">
    <div class="field">
        <form:checkboxes path="fantasyRoundList" items="${fantasyRoundList}" itemLabel="round" itemValue="fantasyRoundId" delimiter="<br>" />
    </div>
    </c:if>

    <form:hidden path="id"/>
    <input type="submit" value="Lagre">
</form:form>

