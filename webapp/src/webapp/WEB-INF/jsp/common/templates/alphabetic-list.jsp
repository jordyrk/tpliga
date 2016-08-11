<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">alphabeticalList</kantega:section>

<kantega:section id="content">
    <h1><aksess:getattribute name="title"/></h1>

     <aksess:exists name="lead paragraph">
         <p class="leadParagraph"><aksess:getattribute name="lead paragraph" /></p>
     </aksess:exists>

     <p>
         <kantega:label key="alphabeticlist.chooseletter" bundle="site"/>:<br />
         <c:forEach items="${letters}" var="letter" varStatus="status">
             <a href="#${letter.key}"><c:out value="${letter.key}"/></a>
             <c:if test="${!status.last}"> | </c:if>
         </c:forEach>
     </p>

     <ul class="alphabeticalList">
         <c:forEach items="${letters}" var="letter" varStatus="outerStatus">
             <li>
                 <a name="${letter.key}" class="letter">${letter.key}</a>
                 <ul>
                    <c:forEach items="${letter.value}" var="content" varStatus="innerStatus">
                        <li><a href="${content.url}">${content.title}</a></li>
                    </c:forEach>
                 </ul>
             </li>
         </c:forEach>
     </ul>
</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
