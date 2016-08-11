<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="cupMatch">
    <div class="top">(${fantasyMatch.alias}) <c:if test="${fantasyMatch.played}"><a class="matchselector" href="${fantasyMatch.id}"></a></c:if></div>
    <div class="middle">
        <div>${fantasyMatch.homeTeam.name}</div>
        <div>
            <c:if test="${fantasyMatch.played}">${fantasyMatch.homegoals}</c:if>
            -
            <c:if test="${fantasyMatch.played}">${fantasyMatch.awaygoals}</c:if>
        </div>
        <div>${fantasyMatch.awayTeam.name}</div>
    </div>
    <div class="bottom"></div>

</div>