<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">poll</kantega:section>

<kantega:section id="content">


    <h1><aksess:getattribute name="title"/></h1>

    <aksess:exists name="lead paragraph">
    <p class="leadParagraph">
        <aksess:getattribute name="lead paragraph"/>
    </p>
    </aksess:exists>

    <%@ include file="include/poll.jsp" %>

</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
