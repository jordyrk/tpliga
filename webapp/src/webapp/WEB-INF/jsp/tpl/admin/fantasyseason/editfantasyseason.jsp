<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1><spring:message code="fantasyseason.admin"/></h1>
<form:form commandName="fantasyseason" action="${pageContext.request.contextPath}/tpl/admin/fantasyseason/editfantasyseason" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="name"><spring:message code="fantasyseason.name"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="name"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="activeSeason"><spring:message code="fantasyseason.activeSeason"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="activeSeason"/>

        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="registrationActive"><spring:message code="fantasyseason.registrationActive"/>: </form:label>
        </div>
        <div class="input">
            <form:checkbox path="registrationActive"/>

        </div>
    </div>

    <div class="field">
         <div class="label">
             <form:label path="maxTeamPrice"><spring:message code="fantasyseason.maxTeamPrice"/>: </form:label>
         </div>
         <div class="input">
             <form:input path="maxTeamPrice"/>
         </div>
     </div>
    <div class="field">
         <div class="label">
             <form:label path="startingNumberOfTransfers"><spring:message code="fantasyseason.startingNumberOfTransfers"/>: </form:label>
         </div>
         <div class="input">
             <form:input path="startingNumberOfTransfers"/>
         </div>
     </div>
    <div class="field">
         <div class="label">
             <form:label path="numberOfTransfersPerRound"><spring:message code="fantasyseason.numberOfTransfersPerRound"/>: </form:label>
         </div>
         <div class="input">
             <form:input path="numberOfTransfersPerRound"/>
         </div>
     </div>

    <div class="field">
        <form:select path="season" items="${seasons}" itemValue="seasonId" itemLabel="seasonLabel"/>
    </div>
    <form:hidden path="fantasySeasonId"/>
    <input type="submit" value="Lagre">
</form:form>

