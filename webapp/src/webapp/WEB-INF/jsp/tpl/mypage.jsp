<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<kantega:section id="title">TPL</kantega:section>
<kantega:section id="head">
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/fantasy.jjs"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/chat.jjs"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/utils.jjs"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/json.jjs"/>"></script>
    <script type="text/javascript">

        $(document).ready(function() {
            $("#Statistics").tabs();
            fantasy.updateOfficialTeams();
            $("#PriceSlider a").first().addClass("first");
            chat.init();
        });

    </script>
</kantega:section>

<kantega:section id="bodyclass">frontPage</kantega:section>

<kantega:section id="content">
    <%@ include file="include/mypage/infotop.jsp"%>
    <div id="MyTeam">
        <div class="formation">
            <span class="currentFormation"><a id="FormationSelector" class="formationSelector"></a><span>&nbsp;&nbsp;</span></span>
            <c:if test="${! fantasyround.openForChange}">
                <a id="ViewFormationSelection" class="dropdown" href="#">&nbsp;</a>
            </c:if>
            <a id="HideFormationSelector"class="dropup hidden" href="#">&nbsp;</a>
            <div id="FormationPicker" class="formationPicker hidden" class="">
                <c:forEach items="${formations}" var="formation">
                    <a class="formationElement" href="${formation}">${formation.description}</a>
                </c:forEach>

            </div>
        </div>
        <div class="viewchanger">
            <a id="ViewPlayerlist" class="toggleView" href="#"></a>
            <a id="ViewField" class="hidden toggleView" href="#"></a>
        </div>

        <div class="blottchanger">
            <a id="BlottTeam" href="#"></a>
            <div id="BlottPopup" class="hidden">
                <div><a href="#" class="close" onclick="$('#BlottPopup').addClass('hidden');"></a></div>
                <div><input type="checkbox" value="true" id="BlottTvert">&nbsp;<label for="BlottTvert">Blott tvert</label></div>
                <div><input type="checkbox" value="true" id="BlottNarRundenstenge">&nbsp;<label for="BlottNarRundenstenge">Blott n&aring;r runden stenge</label></div>
                <div id="OfficalNumberSection" class="wait">
                    <div>
                        Antall blotta lag: <span id="NumberOfOfficalTeams" class="txtR">0</span>
                    </div>
                    <div>
                        Antall som blotte n&aring;r runden stenge: <span id="NumberOfOfficalTeamsWhenTeamIsClosing" class="unitRight">0</span>
                    </div>
                </div>
            </div>
        </div>
        <div id="Field">

            <div id="Keeper" class="keepers"></div>
            <div id="Defender" class="defenders"></div>
            <div id="Midfielder" class="midfielders"></div>
            <div id="Striker" class="strikers"></div>
        </div>
        <div id="PlayerList" class="hidden">

        </div>
    </div>
    <div id="PlayerInfo">

        <div id="PlayerInfoWrapper">
            <div id="PlayerInfoContainer"  style="margin-left:1px;">
                <div id="PlayerView">
                    <%@ include file="mypage/playerstats.jsp"%>
                </div>
                <div id="PlayerStats">

                    <div class="topp">
                        <h2>Stats</h2>
                        <div id="PositionStatsSelector">
                            Position:<br>
                            <a class="statsSelector selected"  href="All" title="Alle">A</a>
                            <c:forEach items="${playerPositions}" var="playerPosition">
                                <a href="${playerPosition}" class="statsSelector" title="${playerPosition}">${fn:substring(playerPosition,0 ,1 )}</a>
                            </c:forEach>

                        </div>
                        <div id="RoundStatsSelector">
                            Antall runder:<br>
                            <a href="All" class="statsSelector selected" title="Alle runder">A</a><a href="3" class="statsSelector" title="3 siste runder">3</a><a href="5" class="statsSelector" title="5 siste runder">5</a><a href="10" class="statsSelector" title="10 siste runder">10</a><br>
                        </div>
                        <div id="AttributeStatsSelector">

                            <a id="MoveAttributesLeft"  href="#">�Forrige</a>
                            <span>
                            <c:forEach items="${statsAttributes}" var="statsAttribute" varStatus="status">
                                <a href="${statsAttribute}"  class="statsSelector<c:if test='${status.first}'> selected</c:if> <c:if test='${not status.first}'> hidden</c:if>" title="${statsAttribute}">${statsAttribute}</a>
                            </c:forEach>
                             </span>
                            <a id="MoveAttributesRight" href="#">Neste�</a>
                        </div>
                            <%--
                            <div id="TeamStatsSelector">
                                <a href="#">Bolton</a><br>
                            </div>
                            --%>
                        <div id="Good_Bad_StatsSelector">
                            <br>
                            <a href="true" class="statsSelector selected">Mest</a><a href="false" class="statsSelector">Minst</a><br>
                        </div>
                        <div id="NumberOfStatsSelector">
                            Antall spillere:<br>
                            <a href="5" class="statsSelector" title="Vis 5 spillere">5</a><a href="10" class="statsSelector selected"  title="Vis 10 spillere">10</a><a href="20" class="statsSelector"  title="Vis 20 spillere">20</a><a href="40" class="statsSelector"  title="Vis 40 spillere">40</a>
                        </div>
                        <div id="TeamSelector">
                            Velg lag:<br>
                            <select id="TeamIdSelector" name="teamid">
                                <option>Velg lag</option>
                                <c:forEach items="${teams}" var="team">
                                    <option value="${team.teamId}">${team.shortName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div id="PlayerStatsResults" class="bottom">

                    </div>
                </div>
            </div>
            <div id="PlayerSelector">
                <div class="topp">
                    <h3>Spillersøk:  <input type="text" name="searchterm" id="SearchTerm"></h3>
                    <div id="PriceSlider">

                    </div>

                    <div id="PriceResult">
                        <span id="MinPrice"></span> - <span id="MaxPrice"></span>
                    </div>
                </div>

                <select name="position" style="display:none;">
                    <c:forEach items="${playerPositions}" var="playerPosition">
                        <option value="${playerPosition}">${playerPosition}</option>
                    </c:forEach>
                </select>

                <div class="bottom">
                    <div id="PlayerSelectorList">

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="Statistics">
        <ul>
            <li><a href="#tab-1">Kamper</a></li>
            <li><a href="#tab-2">Premier League</a></li>
            <li><a href="#tab-3">Blotta lag</a></li>
            <li><a href="#tab-4">Spillerstatistikk</a></li>
            <li><a href="#tab-5">Ymse</a></li>

        </ul>
        <div id="tab-1">
            <div id="NextRound">

                <table class="defaultTable">
                    <thead>
                    <tr>
                        <th>Hjemmelag</th>
                        <th>Bortelag</th>
                        <th>Kampdata</th>
                        <th>Runde</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${fantasyRound.matchList}" var="match" varStatus="status">
                        <c:set var="oddeven" value="${status.index % 2 == 0 ? 'odd':'even'}"/>
                        <tr class="${oddeven}">
                            <td class="hometeam ${fn:toLowerCase(fn:replace(match.homeTeam.shortName,' ' ,'' ))}"><c:out value="${match.homeTeam.shortName}"/></td>
                            <td class="awayteam ${fn:toLowerCase(fn:replace(match.awayTeam.shortName,' ' ,'' ))}"><c:out value="${match.awayTeam.shortName}"/></td>
                            <td class="center">
                                <c:choose>
                                    <c:when test="${match.playerStatsUpdated}">${match.homeGoals} - ${match.awayGoals}</c:when>
                                    <c:otherwise><fmt:formatDate value="${match.matchDate}" pattern="HH:mm dd.MM"/></c:otherwise>
                                </c:choose>
                            </td>
                            <td class="center"><c:out value="${match.leagueRound.round}"/></td>

                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </div>


        </div>
        <div id="tab-2"></div>
        <div id="tab-3"></div>
        <div id="tab-4">
            <h3>Mest brukte spillere</h3>
            <div>
                <a id="MostUsedPlayersButton" class="button" href="#tab-4">Totalt</a>
                <a id="MostUsedPlayersInRoundButton" class="button" href="#tab-4">Denne runden</a>
                <a id="MostUsedPlayersForTeamButton" class="button" href="#tab-4">Av meg</a>
                <a id="MostUsedPlayersAsTeamForTeamButton" class="button" href="#tab-4">Som lag</a>

            </div>
            <div id="FantasyTeamList" >
                <c:forEach items="${allFantasyTeams}" var="currentFantasyTeam">
                    <div class="fantasyTeam">
                        <h3>
                            <a href="${pageContext.request.contextPath}/tpl/fantasyteam/viewteam?teamId=${currentFantasyTeam.teamId}"><tpl:profileimage multiMediaId="${currentFantasyTeam.multiMediaImageId}" width="20" height="20"/><span class="fantasyTeamName">${currentFantasyTeam.name}</span></a>
                        </h3>
                    </div>
                </c:forEach>
            </div>
            <br>
            <div id="MostUsedPlayers"></div>
        </div>
        <div id="tab-5">
            <h3>Div stats</h3>
            <div>
                <a id="FantasyMatchStats" class="button" href="#tab-5">Kampstatistikk TPL</a>
                <a id="MatchStats" class="button" href="#tab-5">Kampstatistikk PL</a>


            </div><br>
            <div id="StatsWrapper"></div>
        </div>


    </div>


    <div id="PlayerPreView" class="hidden">
        <%@ include file="mypage/playerstats.jsp"%>
    </div>
    <%@ include file="chat/messagewrapper.jsp"%>



    <input type="hidden" id="SeasonId" value="${seasonId}">
    <input type="hidden" id="FantasyRoundId" value="${fantasyRound.fantasyRoundId}">
    <input type="hidden" id="MaxSeasonPrice" value="${fantasySeason.maxTeamPrice}">
    <input type="hidden" id="CloseTime" value="${fantasyRound.closeDate.time}">

</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/frontpage.jsp"/>


