<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<input type="hidden" id="FantasySeasonId" value="${fantasyseason.fantasySeasonId}">
<div class="unitRight"><a id="ShowFinishedRounds" class="button">Vis alle runder</a></div>
<div id="InfoMessage"></div>
<table class="roundsTable">
    <thead>
    <tr>
        <th><spring:message code="fantasyround.round"/></th>
        <th><spring:message code="fantasyround.currentround"/></th>
        <th><spring:message code="fantasyround.openforchange"/></th>
        <th>&nbsp;</th>

    </tr>
    </thead>
    <tbody>
    <c:set value="hidden" var="rowClass"/>
    <c:forEach var="fantasyRound" items="${fantasyRounds}">
        <c:if test="${fantasyRound.fantasyRoundId == fantasyseason.currentRound.fantasyRoundId}">
            <c:set value="" var="rowClass"/>
        </c:if>
        <tr id="${fantasyRound.fantasyRoundId}" class="${rowClass}">
            <td><c:out value="${fantasyRound.round}"/></td>
            <td><input type="radio" name="runde" class="currentRoundSelect" <c:if test="${fantasyRound.fantasyRoundId == fantasyseason.currentRound.fantasyRoundId}">checked="checked"</c:if>></td>
            <td><input type="checkbox" name="runde" class="openRoundSelect" <c:if test="${fantasyRound.openForChange}">checked="checked"</c:if>></td>
            <td><input type="button" name="runde" class="updateRoundPoints button" value="Oppdater poeng for lag"></td>
            <td><input type="button" name="runde" class="updateCompetiton button" value="Oppdater tabkonk"></td>
            <td><input type="button" name="matches" class="updateMatches button" value="Oppdater kamper"></td>

            <td>
                <a class="viewRounds adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/viewround?fantasySeasonId=${fantasyseason.fantasySeasonId}&fantasyRoundId=${fantasyRound.fantasyRoundId}" title="Legg til fjern eller fjern kamper i runden">&nbsp;</a>
            </td>
             <td>
                <a class="edit adminLink" href="${pageContext.request.contextPath}/tpl/admin/fantasyround/editfantasyround?fantasyRoundId=${fantasyRound.fantasyRoundId}" title="<spring:message code='fantasyround.edit'/> ${fantasyseason.name}">&nbsp;</a>
            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>
