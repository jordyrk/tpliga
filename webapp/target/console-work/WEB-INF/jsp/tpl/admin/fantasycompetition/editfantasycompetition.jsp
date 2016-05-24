<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="fantasycompetition.admin"/></h1>
<form:form commandName="fantasycompetition" action="${pageContext.request.contextPath}/tpl/admin/fantasycompetition/editfantasycompetition" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="name"><spring:message code="fantasycompetition.name"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="name"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="numberOfTeams"><spring:message code="fantasycompetition.numberOfTeams"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="numberOfTeams"/>
        </div>
    </div>
     <div class="field">
        <div class="label">
            <form:label path="defaultCompetition"><spring:message code="fantasyseason.defaultCompetition"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="defaultCompetition"/>

        </div>
    </div>

    <form:hidden path="fantasyCompetitionId"/>
    <input type="submit" value="Lagre">
</form:form>

