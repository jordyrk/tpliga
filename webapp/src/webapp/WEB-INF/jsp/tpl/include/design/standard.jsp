<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/menu" prefix="menu" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="include/header.jsp" />
<jsp:include page="include/menu.jsp" />

<div class="container">


    <div class="maincontent">
        <div id="Sidebar">
            <ul class="sidemenu">
                <aksess:getcollection name="newscollection" associatedid="/tplnews/" orderby="priority">
                    <li>
                        <c:set var="lenkeinnhold"><aksess:getattribute  name="link" collection="newscollection"/></c:set>

                        <c:choose>
                            <c:when test="${empty lenkeinnhold}">
                                <aksess:link collection="newscollection"><aksess:getattribute name="title" collection="newscollection"/></aksess:link>
                            </c:when>
                            <c:otherwise>
                                <a href="<aksess:getattribute name="link" collection="newscollection"/>"><aksess:getattribute name="title" collection="newscollection"/></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </aksess:getcollection>
            </ul>
        </div>
        <div id="Content">
            <kantega:getsection id="content" />
        </div>


        <div class="clearboth"></div>
    </div>
</div>

<jsp:include page="include/footer.jsp" />
