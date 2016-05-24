<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kantega:section id="title">Manager</kantega:section>

<kantega:section id="bodyclass">fantasyTeam</kantega:section>

<kantega:section id="content">
<h1><spring:message code="fantasyteam.admin"/> </h1>

<form:form commandName="fantasyteam" action="${pageContext.request.contextPath}/tpl/fantasy/team/editteam">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="name"><spring:message code="fantasyteam.name"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="name" maxlength="64"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="stadium"><spring:message code="fantasyteam.stadium"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="stadium" maxlength="255"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="teamSpirit"><spring:message code="fantasyteam.teamSpirit"/>: </form:label>
        </div>
        <div class="input">
            <form:textarea path="teamSpirit" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="supporterClub"><spring:message code="fantasyteam.supporterClub"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="supporterClub" maxlength="255"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="latestNews"><spring:message code="fantasyteam.latestNews"/>: </form:label>
        </div>
        <div class="input">
            <form:textarea path="latestNews"/>
        </div>
    </div>
    
    <form:hidden path="teamId"/>
    <form:hidden path="fantasyManager"/>
    <input type="submit" value="Lagre">
</form:form>
</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>