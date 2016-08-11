<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<kantega:section id="title">Regler</kantega:section>
<kantega:section id="head">


    <script type="text/javascript">
        $(document).ready(function(){
            $("#StandardTable").tablesorter();
        });
    </script>

</kantega:section>
<kantega:section id="bodyclass">competition</kantega:section>

<kantega:section id="content">
    <h1>Regler</h1>
    <aksess:getattribute name="lead paragraph" contentid="/ruletext/"/>
    <aksess:setvariable attribute="id" name="parentId" contentid="/ruletext/"/>
    <br><br>
    <table id="StandardTable" class="defaultTable tablesorter">
        <thead>
        <tr>
            <th>Navn</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${playerrules}" var="playerrule">
            <tr>
                <td>${playerrule.name}</td>
            </tr>
        </c:forEach>
        <c:forEach items="${teamrules}" var="teamrule">
            <tr>
                <td>${teamrule.name}</td>
            </tr>
        </c:forEach>

        </tbody>

    </table>
</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/frontpage.jsp"/>

