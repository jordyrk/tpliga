<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>

<html>
<head>
    <title><kantega:label key="http.500.title" bundle="site"/></title>
    <style type="text/css">
        body{
            background:#fff;
            font-size:62.5%;
            color:#000;
        }
        #pageWrapper{
            margin:0 auto;
            width:960px;
            font-size:1.6em;
        }
        h1{
            font-size:2em;
        }
    </style>
</head>
<body>
<div id="pageWrapper">

    <h1><kantega:label key="http.500.title" bundle="site"/></h1>

    <div class="runningText">
        <kantega:label key="http.500.text" bundle="site"/>
    </div>

</div>

</body>
</html>

