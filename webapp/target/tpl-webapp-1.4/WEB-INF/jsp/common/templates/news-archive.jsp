<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>

<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">article</kantega:section>

<kantega:section id="content">
    <h1><aksess:getattribute name="title"/></h1>
     <aksess:exists name="lead paragraph">
        <p class="leadParagraph">
            <aksess:getattribute name="lead paragraph"/>
        </p>
    </aksess:exists>

    <div class="runningText">

            <aksess:getcollection var="entry" name="News archive" descending="true" orderby="priority">
                <h3> <aksess:getattribute name="title" collection="News archive"/></h3>
                <div><aksess:getattribute collection="News archive" name="lead paragraph"/></div>
                <aksess:link collection="News archive">Se bilder</aksess:link>
            </aksess:getcollection>

        <aksess:exists name="image">
            <div class="image">
                <aksess:getattribute name="image" width="200" height="200"/>
                <aksess:exists name="caption">
                    <div class="caption">
                        <aksess:getattribute name="caption"/>
                    </div>
                </aksess:exists>
            </div>
        </aksess:exists>
        <aksess:getattribute name="running text"/>
    </div>
</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/standardarchive.jsp"/>
