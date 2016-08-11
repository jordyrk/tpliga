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
    <script type="text/javascript" src="<aksess:geturl url='/js$SITE/fantasyleague.jjs'/>"></script>
    <script type="text/javascript" src="<aksess:geturl url='/js$SITE/chat.jjs'/>"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            fantasyLeague.init();
             chat.init();
            standard.setPositionOffset(450);
        });
    </script>
</kantega:section>

<kantega:section id="bodyclass">fantasyleague</kantega:section>

<kantega:section id="content">
    <div class="line">
        <div class="unit size2of3">
            <select id="FantasyLeagueSelector">
                <c:forEach items="${fantasyLeagueList}" var="fantasyLeague">
                    <option value="${fantasyLeague.id}">${fantasyLeague.name}</option>
                </c:forEach>

            </select>
        </div>
        <div class="txtR size1of3 lastUnit">
            <form id="SeasonForm" method="GET" action="fantasyleague">
                <select id="SeasonSelect" name="seasonId">
                    <c:forEach items="${fantasySeasonsList}" var="season" >
                        <option <c:if test="${fantasySeason.fantasySeasonId == season.fantasySeasonId}">selected="selected"</c:if> value="${season.fantasySeasonId}">${season.name}</option>
                    </c:forEach>
                </select>
            </form>
        </div>
    </div>



    <div id="FantasyLeague"></div>
    <%@ include file="chat/messagewrapper.jsp"%>

</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/frontpage.jsp"/>

