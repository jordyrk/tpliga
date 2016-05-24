<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aksess" uri="http://www.kantega.no/aksess/tags/aksess" %>
<table class="defaultTable">
    <tbody>
    <c:forEach items="${statsList}" var="simplePlayerStat">
        <tr>
            <td><span class="clublogo ${fn:toLowerCase(fn:replace(simplePlayerStat.team.shortName,' ' ,'' ))}"><a class="player" href="${simplePlayerStat.player.playerId}">${simplePlayerStat.player.firstName} ${simplePlayerStat.player.lastName}</a></span></td>
            <td>${simplePlayerStat.value}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>