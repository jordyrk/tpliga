<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="no.kantega.publishing.common.Aksess" %>
<kantega:section id="title">
    Logg inn!
</kantega:section>
<kantega:section id="head">
    <script type="text/javascript">
        $(document).ready(function(){
            $("#brukernavn").select();
        })
    </script>
</kantega:section>
<kantega:section id="bodyclass">login</kantega:section>
<kantega:section id="content">
    <fieldset id="Login">
    <h1>Logg inn</h1>
    <p><aksess:getattribute name="logintext" contentid="/"/></p>
    <c:set var="display" value="none"/>
     <c:if test="${loginfailed}">
         <c:set var="display" value="block"/>
         <c:set var="feilmelding" value="Feil brukernavn eller passord"/>
     </c:if>

    <div class="skjemafeil" id="Skjemafeilmelding" style="display: ${display};">${feilmelding}</div>
    <form method="post" action="${pageContext.request.contextPath}/Login.action" id="Loginform">
        <p>
            <label for="brukernavn" title="Din e-postadresse">Brukernavn </label>
            <input id="brukernavn" type="text" name="j_username" value="${username}">
        </p>
        <p>
            <label for="passord">Passord </label>
            <input id="passord" type="password" name="j_password">
        </p>
        <p><a href="${pageContext.request.contextPath}/RequestPasswordReset.action">Har du glemt passordet ditt?</a></p>
        <p>
            <input type="hidden" name="j_domain" value="<%=Aksess.getDefaultSecurityDomain()%>">
            <input type="hidden" name="redirect" value="${redirect}">
            <input type="submit" value="Logg inn" class="button">
        </p>
    </form>
</fieldset>
</kantega:section>
<aksess:include url="/WEB-INF/jsp$SITE/include/design/frontpage.jsp"/>

