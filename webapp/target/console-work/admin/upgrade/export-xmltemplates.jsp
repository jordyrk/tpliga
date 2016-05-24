<?xml version="1.0" encoding="iso-8859-1"?><%@ page contentType="text/xml;charset=iso-8859-1" language="java" pageEncoding="utf-8" %><%@ page import="com.thoughtworks.xstream.XStream" %><%@ page import="no.kantega.publishing.common.data.TemplateConfiguration" %><%@ page import="no.kantega.publishing.common.util.templates.TemplateConfigurationExportHelper" %><%@ page import="no.kantega.publishing.common.util.templates.XStreamTemplateHelper" %>
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
    TemplateConfiguration tc = TemplateConfigurationExportHelper.getConfigururationFromDatabase();
    XStream xstream = XStreamTemplateHelper.getXStream();
    xstream.toXML(tc, out);
%>