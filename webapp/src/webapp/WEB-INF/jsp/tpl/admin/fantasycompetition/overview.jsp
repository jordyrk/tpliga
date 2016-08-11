<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageMenuWrapper">
    <form action="${pageContext.request.contextPath}/tpl/admin/fantasycompetition/viewfantasycompetitions">
        <select id="FantasySeasonFilter" name="fantasySeasonId">
            <option disabled="disabled" selected="selected">Velg sesong</option>
            <c:forEach var="fantasyseason" items="${fantasyseasons}">
                <option value="${fantasyseason.fantasySeasonId}">${fantasyseason.name}</option>
            </c:forEach>
        </select>
    </form>
</div>
<div id="FantasyCompetitionsOverview">

</div>





