<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ page import="no.kantega.commons.util.StringHelper,
                 no.kantega.publishing.common.data.Content,
                 no.kantega.publishing.common.data.attributes.Attribute"%>
<%@ page import="no.kantega.publishing.common.data.enums.ContentProperty"%>
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
    Attribute attribute = (Attribute)request.getAttribute("attribute");
    Content   content   = (Content)request.getAttribute("content");
    String    fieldName = (String)request.getAttribute("fieldName");

    String value = attribute.getValue();
    if (value == null) value = "";
    value = StringHelper.escapeQuotes(value);

    int maxLength = attribute.getMaxLength();
    int rows = (maxLength / 72) + 1;

    if (ContentProperty.TITLE.equalsIgnoreCase(attribute.getField()) && !content.isNew()) {
        value = content.getTitle();
    }
%>
<div class="inputs">
    <%
        if (maxLength <= 255) {
    %>
    <input type="text" onFocus="openaksess.editcontext.setFocusField(this)" onBlur="openaksess.editcontext.blurField()" name="<%=fieldName%>" id="<%=fieldName%>" value="<%=value%>" tabindex="<%=attribute.getTabIndex()%>"
    <%
        if (maxLength <  72) {
            out.write(" size=" + maxLength);
        } else {
            out.write(" class=\"fullWidth\"");
        }
        if (maxLength != -1) {
            out.write(" maxlength=" + maxLength);
        }
    %>><%
        } else {
    %>
    <textarea rows="<%=rows%>" cols="72" class="fullWidth" wrap="soft" onFocus="openaksess.editcontext.setFocusField(this)" onBlur="openaksess.editcontext.blurField()" name="<%=fieldName%>" id="<%=fieldName%>" tabindex="<%=attribute.getTabIndex()%>"><%=value%></textarea>
 <%
    }
 %>
</div>