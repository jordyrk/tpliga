<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:choose>
    <c:when test="${fn:length(fantasyCupRounds) < 3}">
        <c:set var="semifinalpixel" value="0"/>
        <c:set var="quarterfinalpixel" value="0"/>
    </c:when>
    <c:when test="${fn:length(fantasyCupRounds) == 3}">
        <c:set var="semifinalpixel" value="45"/>
        <c:set var="quarterfinalpixel" value="0"/>
    </c:when>
    <c:when test="${fn:length(fantasyCupRounds) == 4}">
        <c:set var="semifinalpixel" value="158"/>
        <c:set var="quarterfinalpixel" value="107"/>
    </c:when>


</c:choose>
<style type="text/css">
    .final, .semifinal{
        margin-top: ${semifinalpixel}px;
    }
    .quarterfinal{
        margin-top:${quarterfinalpixel}px;
    }
</style>
<h1>${fantasyLeague.name}</h1>
<div id="FantasyCupTabs">
    <ul>
        <li><a href="#cup-1">Cup</a></li>
        <li><a href="#cup-2">Gruppespill</a></li>

    </ul>
    <div id="cup-1">
        <h1>${fantasyCup.name}</h1>
        <c:forEach var="fantasyCupRound" items="${fantasyCupRounds}" varStatus="status">
            <div class="cuproundwrapper left ${fn:toLowerCase(fantasyCupRound.fantasyCupMatchType)}">
                <h5><spring:message code="${fantasyCupRound.fantasyCupMatchType}"/> (Runde: ${fantasyCupRound.fantasyRound.round})</h5>
                <c:forEach var="fantasyMatch" items="${fantasyCupRound.firstHalfMatches}">
                    <%@include file="include/cupmatch.jsp"%>
                </c:forEach>
                <c:forEach var="fantasyMatchAlias" items="${fantasyCupRound.firstHalfMatcheAliases}" varStatus="aliasStatus">
                    <%@include file="include/cupalias.jsp"%>
                </c:forEach>
            </div>
        </c:forEach>
        <c:set var="counter" value="0"></c:set>
        <c:forEach var="fantasyCupRound" items="${reverseFantasyCupRounds}" varStatus="status">
            <c:if test="${fn:length(fantasyCupRound.secondHalfMatches) >= 1 || fn:length(fantasyCupRound.secondHalfMatcheAliases) >= 1}">
                <div class="cuproundwrapper right ${fn:toLowerCase(fantasyCupRound.fantasyCupMatchType)}">
                    <h5><spring:message code="${fantasyCupRound.fantasyCupMatchType}"/> (Runde: ${fantasyCupRound.fantasyRound.round})</h5>
                    <c:forEach var="fantasyMatch" items="${fantasyCupRound.secondHalfMatches}" varStatus="matchStatus">
                        <%@include file="include/cupmatch.jsp"%>
                    </c:forEach>
                    <c:forEach var="fantasyMatchAlias" items="${fantasyCupRound.secondHalfMatcheAliases}" varStatus="aliasStatus">
                        <%@include file="include/cupalias.jsp"%>
                    </c:forEach>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <div id="cup-2">
        <c:forEach var="fantasyCupGroup" items="${fantasyCup.fantasyCupGroupList}">
            <div class="fantasyCupGroup">
                <h2>${fantasyCupGroup.name}</h2>
                <div class="matches" id="${fantasyCupGroup.id}"></div>
                <div class="table"></div>
            </div>
        </c:forEach>

    </div>
</div>
<div id="FantasyMatchView" class="hidden"></div>