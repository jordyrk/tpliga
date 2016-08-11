<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<c:forEach var="chatMessage" items="${chatMessageList}" >
    <div class="message">
        <div class="head" title="Lagt inn ">${chatMessage.fantasyManager.managerName} (<fmt:formatDate value="${chatMessage.createdDate}" pattern="dd.MM.yy HH:mm"/>):</div>
        <div class="body">
            ${chatMessage.message}
        </div>
        <br>
    </div>
</c:forEach>

