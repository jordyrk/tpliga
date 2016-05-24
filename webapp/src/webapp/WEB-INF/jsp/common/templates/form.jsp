<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">form</kantega:section>

<kantega:section id="content">
    <h1><aksess:getattribute name="title"/></h1>

    <c:choose>
        <c:when test="${hasSubmitted}">
            <div><aksess:getattribute name="responsetext" /></div>
        </c:when>
        <c:otherwise>
            <div class="leadParagraph"><aksess:getattribute name="explanationtext" /></div>
            <form:form>
                <form:render/>
                <div style="clear:both">
                <input type="submit" value="Send skjema"></div>
            </form:form>
        </c:otherwise>
    </c:choose>

</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
