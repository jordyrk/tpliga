<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<style type="text/css">


</style>
<table id="StandardTable" class="defaultTable tablesorter">
    <thead>
    <tr>
        <th>Navn</th>
        <th>Horeindex</th>
        <th>Tabkonk poeng</th>
        <th>Liga poeng</th>
        <th>Kostnad</th>
        <th>TK/Liga</th>
        <th>Ligaplass</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${whoreList}" var="whore" varStatus="status">
        
        <tr>
            <td>${whore.fantasyTeam.name}</td>
            <td class="center">${whore.whoreIndex}</td>
            <td>${whore.competitionPoints}</td>
            <td>${whore.leaguePoints}</td>
            <td>${whore.costOfPoints}</td>
            <td>${whore.competitionPositionWithinLeague}</td>
            <td>${whore.leaguePosition}</td>

        </tr>
    </c:forEach>
    </tbody>
</table>