<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>

<kantega:section id="title">TPL - Admin</kantega:section>

<kantega:section id="head">
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/admin.jjs"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/json.jjs"/>"></script>

</kantega:section>

<kantega:section id="bodyclass">admin</kantega:section>

<kantega:section id="content">
    <div id="Sidebar">
        <ul class="sidemenu">
            <li><a href="${pageContext.request.contextPath}/tpl/admin/season/viewseasons">Season admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/league/viewleagues">League admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/team/viewteams">Team admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/player/viewplayers">Player admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/match/matchadmin">Match admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/fantasyseason/viewfantasyseasons">FantasySeason admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/fantasycompetition/overview">FantasyCompetition admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/fantasyleague/viewfantasyleagues">FantasyLeague admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/fantasycup/viewfantasycups">FantasyCup admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/fantasymanager/viewfantasymanager">FantasyManager admin</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/admin/fantasyrule/viewfantasyrules">Rule admin</a></li>
            
        </ul>
    </div>
    <div id="Content">

    </div>

</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/admin.jsp"/>

