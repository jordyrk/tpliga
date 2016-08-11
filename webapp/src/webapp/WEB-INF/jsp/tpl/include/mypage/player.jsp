<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="viewPlayer">
    <div class="top"><a href="${position}${playernumber}" <c:if test="${player.playerId < 1}"> style="display:none;" </c:if>class="playerdelete" title="Selg spiller">&nbsp;</a></div>
    <div class="middle">
        <c:choose>
            <c:when test="${player.new}">
                <a id="${position}${playernumber}" href="-1" class="buyPlayer"></a>
            </c:when>
            <c:otherwise>
                <span class="logowrapper ${fn:toLowerCase(fn:replace(player.team.shortName,' ' ,'' ))}"></span>
                <a id="${position}${playernumber}" class="playerselect" href="${player.playerId}" title="${player.firstName} ${player.lastName} - ${player.team.shortName}">
                        ${player.lastName}
                </a>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="bottom"></div>
</div>