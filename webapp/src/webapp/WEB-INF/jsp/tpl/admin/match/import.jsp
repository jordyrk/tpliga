<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="errormessage">${errormessage}</div>
<c:if test="${fn:length(matches)>0}">
    <div id="FixtureList">
        <%@include file="fixturelist.jsp"%>
    </div>
</c:if>