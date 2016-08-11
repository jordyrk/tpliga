<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aksess" uri="http://www.kantega.no/aksess/tags/aksess" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<div id="EditPlayerSection">
    <form:form commandName="player" action="${pageContext.request.contextPath}/tpl/player/edit" id="EditPlayerForm">
        <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <table class="defaultTable">
        <tr>
            <td><form:label path="playerPosition"><spring:message code="player.position"/>: </form:label></td>
            <td><form:select path="playerPosition" items="${availablePositions}" /></td>
        </tr>
        <tr>
            <td><form:label path="price"><spring:message code="player.price"/>: </form:label></td>
            <td><form:input path="price"/></td>
        </tr>
        <tr>
            <td><form:label path="team"><spring:message code="player.team"/>: </form:label></td>
            <td>
                <form:select path="team" >
                    <option value=""><spring:message code="team.none"/></option>
                    <form:options items="${availableTeams}" itemValue="teamId" itemLabel="shortName"></form:options>
                </form:select>
            </td>
        </tr>
    </table>
    <br>
        <form:hidden path="firstName"/>
        <form:hidden path="lastName"/>
        <form:hidden path="shirtNumber"/>
        <form:hidden path="externalId"/>
        <form:hidden path="playerId"/>
    <br>
    <input type="submit" value="Lagre" class="button">

    <a href='${player.playerId}' class='player' title='Match statisitkk'>Avbryt</a>

    </form:form>


