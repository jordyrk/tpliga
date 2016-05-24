<%@ taglib prefix="aksess" uri="http://www.kantega.no/aksess/tags/aksess" %>
<div id="MainMenu">
    <ul class="mainnavigation">
        <aksess:isloggedin>
            <li><a href="${pageContext.request.contextPath}/tpl/mypage" class="menyelement mypage">Mitt lag</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/competition" class="menyelement">TabKonk</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/fantasyleague" class="menyelement">Liga</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/fantasycup" class="menyelement">Cup</a></li>
            <li><a href="${pageContext.request.contextPath}/tpl/rules" class="menyelement">Regler</a></li>
            <li><a href="${pageContext.request.contextPath}/tplbilder" class="menyelement">Bilder</a></li>
            <li><a href="http://www.tjokkpess.org" class="menyelement" target="_blank" onclick="_gaq.push(['_trackPageview',this.href]);">Forum</a></li>
        </aksess:isloggedin>
        <aksess:isnotloggedin>
            <li><a href="${pageContext.request.contextPath}/" class="menyelement">Logg inn</a></li>
        </aksess:isnotloggedin>
        <aksess:hasrole roles="admin">
            <li><a href="${pageContext.request.contextPath}/tpl/admin" class="menyelement">Admin</a></li>
        </aksess:hasrole>
        <aksess:isloggedin>
            <li><a href="<aksess:geturl url="Logout.action"/>" class="menyelement loggut">Logg ut (<aksess:getuser name="user"/>${user.givenName})</a></li>
        </aksess:isloggedin>
    </ul>

</div>
