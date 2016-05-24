<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<kantega:section id="title">TPL</kantega:section>
<kantega:section id="head">

    <script type="text/javascript" src="<aksess:geturl url='/js$SITE/json.jjs'/>"></script>
    <script type="text/javascript" src="<aksess:geturl url='/js$SITE/fantasycup.jjs'/>"></script>
    <script type="text/javascript" src="<aksess:geturl url='/js$SITE/chat.jjs'/>"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            fantasyCup.init();
            chat.init();
        });
    </script>

</kantega:section>

<kantega:section id="bodyclass">fantasyleague</kantega:section>

<kantega:section id="content">
    <div class="line">
        <div class="unit size2of3">
            <select id="FantasyCupSelector">
                <c:forEach items="${fantasyCupList}" var="fantasyCup">
                    <option value="${fantasyCup.id}">${fantasyCup.name}</option>
                </c:forEach>

            </select>

        </div>
        <div class="txtR size1of3 lastUnit">
            <form id="SeasonForm" method="GET" action="fantasycup">
                <select id="SeasonSelect" name="seasonId">
                    <c:forEach items="${fantasySeasonsList}" var="season" >
                        <option <c:if test="${fantasySeason.fantasySeasonId == season.fantasySeasonId}">selected="selected"</c:if> value="${season.fantasySeasonId}">${season.name}</option>
                    </c:forEach>
                </select>
            </form>
        </div>
    </div>


    <div id="FantasyCup" class="fantasyCup"></div>
    <%@ include file="chat/messagewrapper.jsp"%>

</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/frontpage.jsp"/>

