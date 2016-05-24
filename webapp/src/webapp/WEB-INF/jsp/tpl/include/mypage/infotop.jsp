<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>

<script type="text/javascript">
    $(document).ready(function(){
        $("#TotalPrice").html(utils.formatterTall($("#TotalPrice").html()));
        $("#AmountAvailable").html(utils.formatterTall($("#AmountAvailable").html()));
        $("#ChangeImage").click(function(event){
            $(".changeimage").removeClass("hidden");
            $(".profileimage").addClass("hidden");
        });
        $("input[value=Avbryt]").click(function(event){
            $(".changeimage").addClass("hidden");
            $(".profileimage").removeClass("hidden");
        });
    })
</script>
<div class="info">
    <div class="dateflipperwrapper">
        <div class="dateflipper">
            <div class="roundnumber">${fantasyRound.round}</div>
            <div class="subtext">Runde</div>
        </div>
        <span id="PreviousFantasyRound"></span>
        <span id="NextFantasyRound"></span>
    </div>
    <div class="infosection">
        <span id="FantasyTeamName"><a href="${pageContext.request.contextPath}/tpl/fantasyteam/viewteam?teamId=${fantasyTeam.teamId}" title="Vis lagsiden">${fantasyTeam.name}</a></span><br>
        <span>Sum : <span id="TotalPrice">${fantasyTeamRound.totalPrice}</span></span>
        <br>
        <span>Rest : <span id="AmountAvailable">${fantasySeason.maxTeamPrice-fantasyTeamRound.totalPrice}</span></span>
        <br>
    </div>
    <div class="statssection">
        <div>Neste kamper:</div>
        <c:forEach items="${nextLeagueMatches}" var="fantasyMatch">

            <span><aksess:abbreviate maxsize="14">${fantasyMatch.homeTeam.name}</aksess:abbreviate> - <aksess:abbreviate maxsize="14">${fantasyMatch.awayTeam.name}</aksess:abbreviate> </span><a class="matchselector" href="${fantasyMatch.id}"></a>
        </c:forEach>
    </div>
    <div id="FantasyMatchView" class="hidden"></div>
    <div id="CountDown" class="countdowntimer">
            <span id="Day" class="timer">
                <span class="number"></span>
                <span class="description"></span>
            </span>
            <span id="Hour" class="timer">
                <span class="number"></span>
                <span class="description"></span>
            </span>
            <span id="Minute" class="timer">
                <span class="number"></span>
                <span class="description"></span>
            </span>

    </div>

    <div class="imagesection">

        <div class="profileimage">
            <a id="ChangeImage" title="Endre bilde"></a>
            <tpl:profileimage multiMediaId="${fantasyTeam.multiMediaImageId}" height="100" width="100"/>

        </div>
        <div class="changeimage <c:if test="${fantasyTeam.multiMediaImageId > 0}">hidden</c:if>">
            <form action="${pageContext.request.contextPath}/tpl/team/updateteamimage" method="post" enctype="multipart/form-data">
                <label for="teamImage">Endre bilde</label>
                <input type="file" name="teamImage" id="teamImage" /><br>
                <input type="submit" value="Last opp bilde">
                <input type="button" class="" value="Avbryt">
                <input type="hidden" name="fantasyTeamId" value="${fantasyTeam.teamId}">
                <p>Bildet b&oslash;r v&aelig;re p&aring; 100 x 100 pixler</p>
            </form>
        </div>
    </div>

    <div id="Info"></div>
</div>
