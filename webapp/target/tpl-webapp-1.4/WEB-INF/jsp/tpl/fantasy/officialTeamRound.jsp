<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="OfficialTeamRoundList">
    <div id="OfficialTeamRoundWrapper" style="width:${fn:length(officialTeamRoundList)*600}px;">
        <c:forEach var="officialTeamRound" items="${officialTeamRoundList}" varStatus="status">

            <div class="officialTeamRound">
                <div class="buttons">
                    <c:if test="${not status.first}"><span class="PreviousOfficialTeam button">« Forrige</span></c:if>
                    <c:if test="${not status.last}"><span class="NextOfficialTeam button">Neste »</span></c:if>
                </div>
                <h3>${officialTeamRound.fantasyTeam.name}</h3>
                <table>
                    <c:forEach items="${officialTeamRound.playerList}" var="player">
                        <tr>
                        <c:choose>
                            <c:when test="${player.new}">
                                <td></td>
                            </c:when>
                            <c:otherwise>
                                <td><span class="${player.team.shortName}">${player.firstName} ${player.lastName}</span></td>
                                <td>${player.playerPosition}</td>
                                <td>${player.team.shortName}</td>
                            </c:otherwise>
                        </c:choose>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:forEach>
    </div>
</div>