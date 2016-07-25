<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<!DOCTYPE html>
<html lang="${aksess_locale.language}">
<head>
    <title><kantega:getsection id="title"/></title>
    <meta name="generator" content="OpenAksess">
    <link rel="shortcut icon" href="<aksess:geturl url='/bitmaps/favicon.ico'/>">

    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/reset.css"/>" media="all">
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/base.css"/>" media="all">
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/tablesorter.css"/>" media="all" />
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css$SITE/default.css"/>" media="all">
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css$SITE/print.css"/>" media="print">
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/jquery.fancybox-1.3.4.css"/>" media="all" />
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/jquery-ui-1.8.13.custom.css"/>" media="all" />
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/oocss/grids.css"/>" media="all" />
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/oocss/space.css"/>" media="all" />
    <link rel="stylesheet" type="text/css" href="<aksess:geturl url="/css/common/oocss/table.css"/>" media="all" />
    <script type="text/javascript" src="<aksess:geturl url="/js/common/jquery-1.4.2.min.js"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js/common/jquery.fancybox-1.3.4.pack.js"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js/common/jquery.tablesorter.min.js"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js/common/jquery.cookie.js"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js/common/jquery-ui-1.8.13.custom.min.js"/>"></script>
    <script type="text/javascript" src="<aksess:geturl url="/js$SITE/standard.jjs"/>"></script>
    <kantega:hassection id="head">
        <kantega:getsection id="head"/>
    </kantega:hassection>
    <script type="text/javascript">

        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-21286066-1']);
        _gaq.push(['_trackPageview']);

        (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();

    </script>
</head>
<body<kantega:hassection id="bodyclass"> class="<kantega:getsection id="bodyclass"/>"</kantega:hassection>>
