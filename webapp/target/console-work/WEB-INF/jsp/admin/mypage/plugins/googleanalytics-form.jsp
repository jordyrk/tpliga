<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kantega" uri="http://www.kantega.no/aksess/tags/commons" %>
<%--
  ~ Copyright 2009 Kantega AS
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<script type="text/javascript">
    function widgetLoaded() {
        // Reload statistics initially and when a new profile is selected
        $("#profile_selector").change(updateStats).change();
        // Redraw chart when widget is moved
        $(".widgetcolumn").bind('sortstop', function() {
            drawPerMonthStatsChart();
        });
    }

    function updateStats() {
        var tableId = $("#profile_selector option:selected").val();
        if (tableId != undefined) {
            $.post("${pageContext.request.contextPath}/admin/mypage/plugins/GoogleAnalytics.action", { tableId: tableId }, function(html) {
                $("#googleAnalyticsResult").html(html);
                drawPerMonthStatsChart();
            }, "html");
        }
    }

    var monthNames = [
        '<kantega:label key="aksess.googleanalytics.january" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.february" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.march" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.april" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.may" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.june" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.july" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.august" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.september" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.october" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.november" escapeJavascript="true"/>',
        '<kantega:label key="aksess.googleanalytics.december" escapeJavascript="true"/>'
    ];

    function getMonthName(month) {
        var monthIdx = Number(month);
        return monthNames[monthIdx-1];
    }
</script>

<div class="heading"><strong><kantega:label key="aksess.googleanalytics.profile"/></strong></div>
<div class="inputs">
    <select id="profile_selector">
        <c:forEach var="profile" items="${profiles}">
            <option value="${profile.tableId}">${profile.name}</option>
        </c:forEach>
    </select>
</div>

<div id="googleAnalyticsResult"></div>
