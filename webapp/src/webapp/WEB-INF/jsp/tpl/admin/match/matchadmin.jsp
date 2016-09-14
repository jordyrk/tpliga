<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="pageMenuWrapper">
     <select id="SeasonFilter" name="seasonId">
        <option disabled="disabled" selected="selected">Velg sesong</option>
        <c:forEach var="season" items="${seasons}">

            <option value="${season.seasonId}"><fmt:formatDate value="${season.startDate}" pattern="MM.yy"/> - <fmt:formatDate value="${season.endDate}" pattern="MM.yy"/></option>
        </c:forEach>
    </select>

    <div id="SeasonAdminView" class="hidden">
        <a class="matchAdmin button" href="${pageContext.request.contextPath}/tpl/admin/match/import">Oppdater kamper</a>
        <select id="MonthSelect" name="montSelect">
            <option disabled="disabled" selected="selected">Velg måned</option>
            <c:forEach var="month" items="${months}">

                <option value="${month}">${month}</option>
            </c:forEach>
        </select>
        <a id="MonthUpdateId" class="button" href="${pageContext.request.contextPath}/tpl/admin/match/updatematchesfantasypremierleague" title="Henter data for en måned fra STATSFC. ">Hent data</a>
        <a class="matchAdmin button" href="${pageContext.request.contextPath}/tpl/admin/match/matchoverview">Terminliste</a>
    </div>


</div>
<div id="MatchContent"></div>