<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="playerstats.admin"/> </h1>
<form:form commandName="playerStats" action="${pageContext.request.contextPath}/tpl/admin/playerstats/edit" cssClass="playerStatsEdit">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="player"><spring:message code="playerstats.player"/>: </form:label>
        </div>
        <div class="input">
            <form:select path="player" items="${availablePlayers}" itemValue="playerId" itemLabel="lastName"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="team"><spring:message code="playerstats.team"/>: </form:label>
        </div>
        <div class="input">
            <form:select path="team" items="${availableTeams}" itemValue="teamId" itemLabel="shortName"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="goals"><spring:message code="playerstats.goals"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="goals" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="assists"><spring:message code="playerstats.assists"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="assists" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="ownGoal"><spring:message code="playerstats.ownGoal"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="ownGoal" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="missedPenalty"><spring:message code="playerstats.missedPenalty"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="missedPenalty" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="savedPenalty"><spring:message code="playerstats.savedPenalty"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="savedPenalty" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="yellowCard"><spring:message code="playerstats.yellowcard"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="yellowCard" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="redCard"><spring:message code="playerstats.redCard"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="redCard" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="wholeGame"><spring:message code="playerstats.wholeGame"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="wholeGame" />
        </div>
    </div>
     <div class="field">
        <div class="label">
            <form:label path="startedGame"><spring:message code="playerstats.startedGame"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="startedGame" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="playedMinutes"><spring:message code="playerstats.playedMinutes"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="playedMinutes" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="points"><spring:message code="playerstats.points"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="points" />
        </div>
    </div>
    <form:hidden path="match"/>
    <input type="submit" value="Lagre">
</form:form>

