<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
 <script type="text/javascript">
        $(document).ready(function(){

            fantasyLeague.setMaxRoundNumber(${numberOfRoundsPlayed});
            fantasyLeague.updateForm(${startingRoundIndex});
        });
    </script>
<h1>${fantasyLeague.name}</h1>
<div id="FantasyLeagueTabs">
    <ul>
        <li><a href="#league-1">Tabell</a></li>
        <li><a href="#league-2">Spilte kamper</a></li>
        <li><a href="#league-3">Terminliste</a></li>
        <li><a href="#league-4">Form</a></li>
        <li><a href="#league-5">Horetabell</a></li>

    </ul>
    <div id="league-1"></div>
    <div id="league-2"></div>
    <div id="league-3"></div>
    <div id="league-4">

        <c:if test="${startingRoundIndex > 0}">
            <h3><span id="FormRoundNumber"></span><a href="#" id="RoundUp"></a><a href="#" id="RoundDown"></a></h3>
            <div id="FormSelector">
                <c:forEach var="roundNumber" begin="1" end="${numberOfRoundsPlayed}">
                    <input type="hidden" value="${roundNumber}"<c:if test="${roundNumber == startingRoundIndex}">selected="selected"</c:if> >
                </c:forEach>
            </div>
            <span id="RoundNumber"></span>
            <div id="FormTableWrapper">

            </div>
        </c:if>
    </div>
    <div id="league-5"></div>
</div>