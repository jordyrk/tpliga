<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

{
    "Message":
    {
    "text": "${message}" ,
    "saved": <c:choose><c:when test="${playerbought}">true</c:when><c:otherwise>false</c:otherwise> </c:choose>,
    "removed": <c:choose><c:when test="${removed}">true</c:when><c:otherwise>false</c:otherwise> </c:choose>
    }
}
