<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="match.admin"/> </h1>
<form:form commandName="match" action="${pageContext.request.contextPath}/tpl/admin/match/edit" cssClass="matchEdit">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="leagueRound"><spring:message code="match.leagueround"/>: </form:label>
        </div>
        <div class="input">
            <form:select path="leagueRound" items="${leagueRounds}" itemValue="leagueRoundId" itemLabel="round"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="homeTeam"><spring:message code="match.homeTeam"/>: </form:label>
        </div>
        <div class="input">
            <form:select path="homeTeam" items="${teams}" itemValue="teamId" itemLabel="shortName"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="awayTeam"><spring:message code="match.awayTeam"/>: </form:label>
        </div>
        <div class="input">
            <form:select path="awayTeam" items="${teams}" itemValue="teamId" itemLabel="shortName"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="matchDate"><spring:message code="match.matchDate"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="matchDate" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="matchUrl"><spring:message code="match.matchUrl"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="matchUrl" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="homeGoals"><spring:message code="match.homeGoals"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="homeGoals" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="awayGoals"><spring:message code="match.awayGoals"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="awayGoals" />
        </div>
    </div>

    <form:hidden path="matchId"/>
    <input type="submit" value="Lagre">
</form:form>

