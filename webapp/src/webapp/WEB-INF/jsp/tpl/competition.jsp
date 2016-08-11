<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>

<kantega:section id="title">TPL</kantega:section>
<kantega:section id="head">

    <script type="text/javascript" src="<aksess:geturl url='/js$SITE/json.jjs'/>"></script>
    <script type="text/javascript" src="<aksess:geturl url='/js$SITE/fantasycompetition.jjs'/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/chat.jjs"/>"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            chat.init();
            fantasyCompetition.init();
            fantasyCompetition.updateForm(${maxNumberOfRounds});
        });
    </script>

</kantega:section>

<kantega:section id="bodyclass">competition</kantega:section>

<kantega:section id="content">

    <div class="line">
        <div class="unit size2of3"><h1>${fantasyCompetition.name}</h1></div>
        <div class="txtR size1of3 lastUnit ptl">
            <form id="SeasonForm" method="GET" action="competition">
                <select id="SeasonSelect" name="seasonId">
                <c:forEach items="${fantasySeasonsList}" var="season" >
                    <option <c:if test="${fantasySeason.fantasySeasonId == season.fantasySeasonId}">selected="selected"</c:if> value="${season.fantasySeasonId}">${season.name}</option>
                </c:forEach>
            </select>
            </form>
        </div>
    </div>
    <div id="FantasyCompetitionTabs">
        <ul>
            <li><a href="#competition-1">Tabell</a></li>
            <c:if test="${fantasySeason.activeSeason}">
                <li><a href="#competition-2">Form</a></li>
            </c:if>

            <li><a href="#competition-3">Hall of fame</a></li>


        </ul>
        <div id="competition-1">
            <table id="StandardTable" class="defaultTable tablesorter">
                <thead>
                <tr>
                    <th>Plassering</th>
                    <th><spring:message code="fantasyteam.name"/></th>
                    <th>Snitt</th>
                    <th>Min</th>
                    <th>Max</th>
                    <th>Poeng</th>
                    <th>Sum</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${fantasyCompetitionStandingList}" var="fantasyCompetitionStanding" varStatus="status">
                    <c:choose>
                        <c:when test="${fantasyCompetitionStanding.lastRoundPosition < fantasyCompetitionStanding.position}">
                            <c:set var="positionMovementClass" value="positionDown"></c:set>
                        </c:when>
                        <c:when test="${fantasyCompetitionStanding.lastRoundPosition > fantasyCompetitionStanding.position}">
                            <c:set var="positionMovementClass" value="positionUp"></c:set>
                        </c:when>
                        <c:otherwise><c:set var="positionMovementClass" value=""></c:set></c:otherwise>
                    </c:choose>
                    <tr>
                        <td>${fantasyCompetitionStanding.position}<span class="${positionMovementClass}" title="Siste rundes posisjon: ${fantasyCompetitionStanding.lastRoundPosition}"></span></td>
                        <td><a href="${pageContext.request.contextPath}/tpl/fantasyteam/viewteam?teamId=${fantasyCompetitionStanding.fantasyTeam.teamId}">${fantasyCompetitionStanding.fantasyTeam.name}</a><tpl:profileimage multiMediaId="${fantasyCompetitionStanding.fantasyTeam.multiMediaImageId}" height="20" width="20"/></td>
                        <td class="center">${fantasyCompetitionStanding.averagepoints}</td>
                        <td class="center">${fantasyCompetitionStanding.minimumpoints}</td>
                        <td class="center">${fantasyCompetitionStanding.maximumpoints}</td>
                        <td class="center">${fantasyCompetitionStanding.points}</td>
                        <td class="center">${fantasyCompetitionStanding.accumulatedPoints}</td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
            <div id="TeamChart" <c:if test="${! fantasySeason.activeSeason}"> class="hidden"</c:if> >

            </div>
        </div>
        <c:if test="${fantasySeason.activeSeason}">

            <div id="competition-2">
                <c:if test="${maxNumberOfRounds > 0}">
                    <h3><span id="FormRoundNumber"></span><a href="#" id="RoundUp"></a><a href="#" id="RoundDown"></a></h3>
                    <div id="FormSelector">
                        <c:forEach var="roundNumber" begin="1" end="${fantasyRound.round}">
                            <input type="hidden" value="${roundNumber}"<c:if test="${roundNumber == maxNumberOfRounds}">selected="selected"</c:if> >
                        </c:forEach>
                    </div>
                    <span id="RoundNumber"></span>
                    <div id="FormTableWrapper">

                    </div>
                </c:if>
            </div>
        </c:if>
        <div id="competition-3">

            <h3>Hall of fame</h3>
            <table class="defaultTable">
                <thead>
                <tr>
                    <th>Lag</th>
                    <th>Sesong</th>
                    <th>Poeng</th>

                </tr>
                </thead>
                <c:forEach var="hallOfFame" items="${hallOfFameList}">
                    <tr>
                        <td><tpl:profileimage multiMediaId="${hallOfFame.fantasyTeam.multiMediaImageId}" height="20" width="20"/>${hallOfFame.fantasyTeam.name}</td>
                        <td>${hallOfFame.fantasySeason.name}</td>
                        <td>${hallOfFame.points}</td>
                    </tr>
                </c:forEach>
            </table>

        </div>
    </div>
    <input type="hidden" value="${fantasyTeam.teamId}" id="FantasyTeamId">
    <input type="hidden" value="${fantasyRound.round}" id="FormMax">
    <%@ include file="chat/messagewrapper.jsp"%>

</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/frontpage.jsp"/>

