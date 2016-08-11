<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form action="${pageContext.request.contextPath}/tpl/admin/match/fixturelist">
    <select id="RoundSelect" name="leagueRoundId">
        <c:forEach items="${leagueRounds}" var="leagueRound">
            <option value="${leagueRound.leagueRoundId}" <c:if test="${leagueRound.round == currentRound.round}">selected="selected"</c:if>>${leagueRound.round}</option>
        </c:forEach>
    </select>
    <input type="hidden" name="seasonId" value="${seasonId}">
</form>
<a id="MatchUpdateAllRounds" href="${pageContext.request.contextPath}/tpl/admin/match/updatepointsforround?roundId=${currentRound.leagueRoundId}" class="button">Oppdater poeng for alle kampene</a>
<div id="MatchUpdateAllRoundsMessage"></div>
<div id="FixtureList">
    <%@include file="fixturelist.jsp"%>
</div>
