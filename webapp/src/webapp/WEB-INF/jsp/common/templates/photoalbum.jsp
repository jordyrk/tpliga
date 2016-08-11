<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/photo" prefix="photo" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">photoalbum</kantega:section>

<kantega:section id="head">
    <style type="text/css">
        .group{
            margin: 5px;
        }
        .group img{
            border: 1px solid #dfdfdf;
            padding:5px;

            -moz-box-shadow: 0 0 1em #A9A9A9;
            -webkit-box-shadow: 0 0 1em #A9A9A9;
        }
    </style>
</kantega:section>

<kantega:section id="content">

    <h1><aksess:getattribute name="title"/></h1>

    <aksess:exists name="lead paragraph">
        <p class="leadParagraph">
            <aksess:getattribute name="lead paragraph"/>
        </p>
    </aksess:exists>

    <aksess:setvariable name="photoalbum" attribute="mediafolder"/>


    <div id="Photos">
        <photo:index width="200" height="85" cssclass="bilde" space="4" />
    </div>
    <script type="text/javascript">

        var removeUrlAttrs = function(url){
            var newUrl = url.substring(0,url.indexOf("?"));
            return newUrl;
        }
        $("#Photos a").each(function(){
            var $a = this;
            $($a).attr("rel","photogroup");
            $($a).addClass("group");
            var imageUrl = $($a).find("img").attr("src");
            $($a).attr("href", removeUrlAttrs(imageUrl));
        })



    </script>
    <script type="text/javascript">
        $(document).ready(function() {
            $(".group").fancybox({
                'transitionIn'	:	'elastic',
                'transitionOut'	:	'elastic',
                'overlayOpacity':   0.6,
                'speedIn'		:	200,
                'speedOut'		:	200,
                'autoScale'     :   true,
                'title'         :   this.title,
                'titleShow'     :   true,
                'titlePosition' :   'inside',
                'type'          :   'image',
                'showNavArrows' :   true
            });
        });
    </script>
</kantega:section>

<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
