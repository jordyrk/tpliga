<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<kantega:section id="title">TPL</kantega:section>

<kantega:section id="bodyclass">frontPage</kantega:section>

<kantega:section id="content">
    <h1>Manager registrering</h1>
    <form:form action="${pageContext.request.contextPath}/tpl/managerregistration" method="post" commandName="managerRegistration">
         <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
        <div class="field">
            <div class="label">
                <form:label path="firstname"><spring:message code="managerRegistration.firstname"/>: </form:label>
            </div>
            <div class="input">
                <form:input path="firstname" />
            </div>
        </div>
         <div class="field">
            <div class="label">
                <form:label path="lastname"><spring:message code="managerRegistration.lastname"/>: </form:label>
            </div>
            <div class="input">
                <form:input path="lastname" />
            </div>
        </div>
        <div class="field">
            <div class="label">
                <form:label path="email"><spring:message code="managerRegistration.email"/>: </form:label>
            </div>
            <div class="input">
                <form:input path="email" />
            </div>
        </div>
        <div class="field">
            <div class="label">
                <form:label path="password"><spring:message code="managerRegistration.password"/>: </form:label>
            </div>
            <div class="input">
                <form:password path="password" />
            </div>
        </div>
        <div class="field">
            <div class="label">
                <form:label path="passwordRepeat"><spring:message code="managerRegistration.passwordRepeat"/>: </form:label>
            </div>
            <div class="input">
                <form:password path="passwordRepeat" />
            </div>
        </div>
        <input type="submit" value="Lagre">
    </form:form>


</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>

