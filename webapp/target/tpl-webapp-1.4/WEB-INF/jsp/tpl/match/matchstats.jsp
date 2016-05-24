<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="aksess" uri="http://www.kantega.no/aksess/tags/aksess" %>
<%@ taglib prefix="tpl" uri="http://www.tpl.org/tags/tpl" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h3>${leaguename}</h3>
<div>
    Statistikken viser antall ganger et resultat har forekommet. Om det er hjemme eller bortekamp er ikke med i statistikken.
    <p>Antall kamper: ${numberOfMathces} </p>
</div>
<table id="StandardTable" class="defaultTable tablesorter">
    <thead>
    <tr>
        <th>Resultat</th>
        <th>Antall ganger</th>
        <th>Prosentvis fordeling</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${matchstats}" var="entry" varStatus="status">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>

            <td><fmt:formatNumber value="${entry.value*100/numberOfMathces}" pattern="#0.00" /> %</td>
        </tr>
    </c:forEach>


    </tbody>
</table>
<script type="text/javascript">
        $(document).ready(function(){
            $("#StandardTable").tablesorter()
            $("#StandardTable th:first").next().trigger("click").trigger("click");
        });
</script>