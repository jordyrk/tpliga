<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" uri="http://www.kantega.no/aksess/tags/admin" %>
<%@ taglib prefix="aksess" uri="http://www.kantega.no/aksess/tags/aksess" %>
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
<kantega:section id="title">
    <kantega:label key="aksess.search.rebuild.title"/>
</kantega:section>

<kantega:section id="content">
    <form action="RebuildIndex.action" name="searchindex" method="POST">
        <admin:box>
            <h1><kantega:label key="aksess.search.title"/></h1>

            <div class="row">
                <input type="checkbox" class="checkbox" checked="true" name="rebuild" id="rebuild"><label for="rebuild" class="checkbox"><kantega:label key="aksess.search.rebuild.rebuild"/></label>
                <div class="clearing"></div>
            </div>
            <div class="row">
                <input type="checkbox" class="checkbox"  checked="true" name="optimize" id="optimize"><label for="optimize" class="checkbox" ><kantega:label key="aksess.search.rebuild.optimize"/></label>
                <div class="clearing"></div>
            </div>
            <div class="row">
                <input type="checkbox" class="checkbox"  checked="true" name="spelling" id="spelling"><label for="spelling" class="checkbox"><kantega:label key="aksess.search.rebuild.spelling"/></label>
                <div class="clearing"></div>
            </div>
            <div class="row">
                <label class="checkbox"><kantega:label key="aksess.search.rebuild.providersToExclude"/></label>
                <c:forEach var="provider" items="${providers}">
                    <input type="checkbox" class="checkbox" name="exclude${provider}r" id="${provider}"><label for="${provider}" class="checkbox">${provider}</label>
                </c:forEach>
                <div class="clearing"></div>
            </div>
            <div class="row">
                <label for="numberOfConcurrentHandlers" class="checkbox"><kantega:label key="aksess.search.rebuild.numberOfConcurrentHandlers"/></label><input type="text" class="text" name="numberOfConcurrentHandlers" id="numberOfConcurrentHandlers" value="1">
                <div class="clearing"></div>
            </div>
            <div class="buttonGroup">
                <a href="#" onclick="document.searchindex.submit()" class="button"><span class="ok"><kantega:label key="aksess.button.start"/></span></a>
            </div>
        </admin:box>
    </form>

</kantega:section>
<%@ include file="../../layout/administrationLayout.jsp" %>
