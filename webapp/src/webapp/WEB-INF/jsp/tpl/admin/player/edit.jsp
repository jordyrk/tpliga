<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
    $(document).ready(function() {

        $("#FlereAlias a").click(function(event){
            event.preventDefault();
            var $last = $("#Aliaser div.feltgruppe:last");
            $last.clone().appendTo("#Aliaser");
            $last = $("#Aliaser div.feltgruppe:last");
            // Sett ny id for input elementer
            admin.setNextNameAndIdForFormElements($last)
            // Fjern tekster i nye elementer
            $("input", $last).each(function () {
                this.value = "";
            });

        });

    });
</script>

<h1><spring:message code="player.admin"/></h1>
<form:form commandName="player" action="${pageContext.request.contextPath}/tpl/admin/player/edit" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="firstName"><spring:message code="player.firstName"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="firstName"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="lastName"><spring:message code="player.lastName"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="lastName"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="playerPosition"><spring:message code="player.position"/>: </form:label>
        </div>
        <div class="input">
            <form:select path="playerPosition" items="${availablePositions}" />
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="price"><spring:message code="player.price"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="price"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="shirtNumber"><spring:message code="player.shirtNumber"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="shirtNumber"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="externalId"><spring:message code="player.externalId"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="externalId"/>
        </div>
    </div>
    <div class="field">
        <form:select path="team" >
            <option value=""><spring:message code="team.none"/></option>
            <form:options items="${availableTeams}" itemValue="teamId" itemLabel="shortName"></form:options>
        </form:select>

    </div>
    <c:set var="max" value="${fn:length(player.aliases)}"/>
    <c:if test="${max < 1}">
        <c:set var="max" value="1"/>
    </c:if>
    <div id="Aliaser">
        <h2>Aliaser</h2>
        <c:forEach var="i" begin="0" step="1" end="${max-1}" varStatus="rad">
            <div class="feltgruppe">

                <fieldset>
                    <div class="felt">
                        <div class="label">
                            <form:label path="aliases[${rad.index}]"><spring:message code="player.alias"/>: </form:label>
                        </div>

                        <div class="input">
                            <form:input path="aliases[${rad.index}]"/>
                        </div>
                    </div>
                </fieldset>
            </div>
        </c:forEach>
    </div>
    <div id="FlereAlias" ><a href="">Legg til alias</a></div>

    <form:hidden path="playerId"/>
    <input type="submit" value="Lagre">
</form:form>

