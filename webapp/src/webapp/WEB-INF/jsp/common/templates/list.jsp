<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">list</kantega:section>

<kantega:section id="content">

    <h1><aksess:getattribute name="title"/></h1>

    <aksess:exists name="lead paragraph">
        <p class="leadParagraph">
            <aksess:getattribute name="lead paragraph"/>
        </p>
    </aksess:exists>


    <aksess:setvariable attribute="order by" name="orderby"/>
    <aksess:setvariable attribute="display options" name="displayoptions"/>
    <c:choose>
        <c:when test="${orderby == 'publishdate'}">
            <c:set var="descending" value="true"/>
        </c:when>
        <c:otherwise>
            <c:set var="descending" value="false"/>
        </c:otherwise>
    </c:choose>
    <aksess:getcollection name="list" associationcategory="Main column" orderby="${orderby}" descending="${descending}">
        <div class="listelement">
            <h3><aksess:link collection="list"><aksess:getattribute name="title" collection="list"/></aksess:link></h3>
            <aksess:ifequals name="display options" value="leadParagraph">
                <p class="leadParagraph">
                    <aksess:getattribute name="lead paragraph" collection="list"/>
                </p>
            </aksess:ifequals>
            <aksess:ifequals name="display options" value="leadParagraph_image">
                <aksess:exists name="image" collection="list">
                    <div class="image">
                        <aksess:getattribute name="image" collection="list" width="100"/>
                    </div>
                </aksess:exists>
                <p class="leadParagraph">
                    <aksess:getattribute name="lead paragraph" collection="list"/>
                </p>
            </aksess:ifequals>
            <div class="clear"></div>
        </div>
    </aksess:getcollection>

</kantega:section>



<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
