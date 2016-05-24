<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1>Rediger runde</h1>
<form:form commandName="fantasyround" action="${pageContext.request.contextPath}/tpl/admin/fantasyround/editfantasyround" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>

    <div class="field">
         <div class="label">
             <form:label path="closeDate">Close date: </form:label>
         </div>
         <div class="input">
             <form:input path="closeDate"/>
         </div>
     </div>

   <form:hidden path="round"/>
    <form:hidden path="openForChange"/>
    <form:hidden path="fantasyRoundId"/>
    <input type="submit" value="Lagre">
</form:form>

