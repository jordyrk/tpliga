<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>${playerstats.player.firstName} ${playerstats.player.lastName}</h1>

<spring:message code="playerstats.goals"/>${playerstats.goals}
<spring:message code="playerstats.assists"/>${playerstats.assists}
<spring:message code="playerstats.ownGoal"/>${playerstats.ownGoal}
<spring:message code="playerstats.missedPenalty"/>${playerstats.missedPenalty}
<spring:message code="playerstats.yellowcard"/>${playerstats.yellowcard}
<spring:message code="playerstats.redCard"/>${playerstats.redCard}
<spring:message code="playerstats.playedMinutes"/>${playerstats.playedMinutes}



