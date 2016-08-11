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
<h1><spring:message code="team.admin"/> </h1>
<form:form commandName="team" action="${pageContext.request.contextPath}/tpl/admin/team/edit" cssClass="adminForm">
    <form:errors delimiter="<br>" cssClass="errors" id="Errors" path="*" htmlEscape="false"/>
    <div class="field">
        <div class="label">
            <form:label path="shortName"><spring:message code="team.shortName"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="shortName"/>
        </div>
    </div>
    <div class="field">
        <div class="label">
            <form:label path="fullName"><spring:message code="team.fullName"/>: </form:label>
        </div>
        <div class="input">
            <form:input path="fullName"/>
        </div>
    </div>

    <c:set var="max" value="${fn:length(team.aliases)}"/>
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
                            <form:label path="aliases[${rad.index}]"><spring:message code="team.alias"/>: </form:label>
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

    <form:hidden path="teamId"/>
    <input type="submit" value="Lagre">
</form:form>

