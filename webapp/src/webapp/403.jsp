<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>

<kantega:section id="title">
    <kantega:label key="http.403.title" bundle="site"/>
</kantega:section>

<kantega:section id="bodyclass">http403</kantega:section>

<kantega:section id="content">

    <h1><kantega:label key="http.403.title" bundle="site"/></h1>

    <div class="running text">
        <kantega:label key="http.403.text" bundle="site"/>
    </div>

</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
