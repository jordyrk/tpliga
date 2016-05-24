<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ page import="no.kantega.publishing.common.data.attributes.FormAttribute"%>
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
<%
    FormAttribute attribute = (FormAttribute)request.getAttribute("attribute");
    String formFieldName = (String)request.getAttribute("fieldName");
%>
<div class="inputs">
    <%@include file="listoptions.jsf"%>
</div>
<div class="buttonGroup">
    <a href="#" onclick="editForm(document.myform.<%=formFieldName%>)" class="button" tabindex="<%=attribute.getTabIndex()%>"><span class="edit"><kantega:label key="aksess.button.edit"/></span></a>
</div>
