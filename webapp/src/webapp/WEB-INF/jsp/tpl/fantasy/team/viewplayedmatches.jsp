<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="displaymatchscore" value="true"/>
<c:set var="matches" value="${playedMatches}"/>
<%-- zero based index--%>
<c:set var="numberOfMatches" value="4"/>
<h3>Siste 5 kamper</h3>
<%@include file="include/teammatches.jsp"%>
