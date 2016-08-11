<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>


<kantega:section id="title">TPL</kantega:section>
<kantega:section id="head">
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/chat.jjs"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/fantasyteam.jjs"/>"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            fantasyTeam.init();
            chat.init();
        });
    </script>
    
</kantega:section>

<kantega:section id="bodyclass">fantasyteam</kantega:section>

<kantega:section id="content">

    <input type="hidden" id="FantasyTeamId" value="${fantasyTeam.teamId}">
    <h1><tpl:profileimage multiMediaId="${fantasyTeam.multiMediaImageId}" width="20" height="20"/>${fantasyTeam.name}<span id="FantasyTeamListIcon" class="fantasyTeamListIcon"></span> </h1>
    <div id="FantasyTeamList" >
        <c:forEach items="${fantasyTeamList}" var="currentFantasyTeam">
            <div class="fantasyTeam">
                <h3>
                <a href="${pageContext.request.contextPath}/tpl/fantasyteam/viewteam?teamId=${currentFantasyTeam.teamId}"><tpl:profileimage multiMediaId="${currentFantasyTeam.multiMediaImageId}" width="20" height="20"/><span class="fantasyTeamName">${currentFantasyTeam.name}</span></a>
                    </h3>
            </div>
        </c:forEach>
    </div>
    <p><select id="FantasyCompetitionSelector">
            <c:forEach items="${fantasyCompetitionList}" var="fantasyCompetition">
                <option value="${fantasyCompetition.fantasyCompetitionId}" label="fantasycompetition">${fantasyCompetition.name}</option>
            </c:forEach>
            <c:forEach items="${fantasyLeagueList}" var="fantasyLeague">
                <option value="${fantasyLeague.id}" label="fantasyleague">${fantasyLeague.name}</option>
            </c:forEach>
           <%-- <c:forEach items="${fantasyCupList}" var="fantasyCup">
                <option value="${fantasyCup.id}" label="fantasycup">${fantasyCup.name}</option>
            </c:forEach>--%>

        </select>
</p>
    <div id="TeamStats">

    </div>

    <div class="matchoverview">

        <input type="hidden" name="numberOfLeagues" value="${fn:length(fantasyLeagueList)}">

        <div id="TeamFixtures"></div>
        <div id="TeamPlayedMatches"></div>
    </div>
    <div id="TeamInfo" class="multipleinfosection">
        <h3 class="center">Laginfo</h3>
        <span class="header center">Manager</span><span class="content center">${fantasyTeam.fantasyManager.managerName}</span>
        <span class="header center">Stadion</span><span class="content center">${fantasyTeam.stadium}</span>
        <span class="header center">Lagånd</span><span class="content">${fantasyTeam.teamSpirit}</span>
        <span class="header center">Supporter klubb</span><span class="content center">${fantasyTeam.supporterClub}</span>
        <span class="header center">Siste nytt</span><span class="content">${fantasyTeam.latestNews}</span>

        <c:if test="${fantasyTeam.teamId == currentFantasyTeam.teamId}">
            <br><br>
            <span class="edit center">
                <a href="${pageContext.request.contextPath}/tpl/fantasy/team/editteam?teamId=${param.teamId}" class="button">Rediger laginfo</a>
                <a href="${pageContext.request.contextPath}/tpl/fantasy/manager/editmanager?managerId=${fantasyTeam.fantasyManager.managerId}" class="button">Rediger manager</a>
            </span>
        </c:if>
    </div>


    <%@ include file="../../chat/messagewrapper.jsp"%>

</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/frontpage.jsp"/>


