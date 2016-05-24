<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%!
    int maxBarWidth = 150;
    int barHeight = 10;

    public int getWidth(int altNum, HttpServletRequest request) {
        if(request.getAttribute("num_votes") == null
                || request.getAttribute("num_votes").equals("")
                || request.getAttribute("num_votes").equals("0")
                || request.getAttribute("poll_alt" + altNum) == null) {
            return 0;
        }
        double numVotesTotal = ((Integer)request.getAttribute("num_votes")).doubleValue();
        double numVotes = ((Integer) request.getAttribute("poll_alt" + altNum)).doubleValue();

        if (numVotes == 0) {
            return 0;
        }

        return new Double((numVotes / numVotesTotal)*maxBarWidth).intValue();
    }
%>
    <aksess:setvariable attribute="contentid" name="pollid"/>
    <aksess:poll pollid="${pollid}"/>

    <div class="question">
        <aksess:getattribute name="sporsmal"/>
    </div>

    <c:choose>
        <c:when test="${visresultat}">
            <div class="result">
                <p class="alternative">
                    <aksess:getattribute name="alt1"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(1, request) %>" align="middle">
                    ${poll_alt1}
                </p>
                <p class="alternative">
                    <aksess:getattribute name="alt2"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(2, request) %>" align="middle">
                    ${poll_alt2}
                </p>
                <aksess:exists name="alt3">
                    <p class="alternative">
                    <aksess:getattribute name="alt3"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(3, request) %>" align="middle">
                    ${poll_alt3}
                </aksess:exists>

                <aksess:exists name="alt4">
                <p class="alternative">
                    <aksess:getattribute name="alt4"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(4, request) %>" align="middle">
                    ${poll_alt4}
                </p>
                </aksess:exists>

                <aksess:exists name="alt5">
                <p class="alternative">
                    <aksess:getattribute name="alt5"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(5, request) %>" align="middle">
                    ${poll_alt5}
                </p>
                </aksess:exists>

                <aksess:exists name="alt6">
                <p class="alternative">
                    <aksess:getattribute name="alt6"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(6, request) %>" align="middle">
                    ${poll_alt6}
                </p>
                </aksess:exists>

                <aksess:exists name="alt7">
                <p class="alternative">
                    <aksess:getattribute name="alt7"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(7, request) %>" align="middle">
                    ${poll_alt7}
                </p>
                </aksess:exists>

                <aksess:exists name="alt8">
                <p class="alternative">
                    <aksess:getattribute name="alt8"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(8, request) %>" align="middle">
                    ${poll_alt8}
                </p>
                </aksess:exists>

                <aksess:exists name="alt9">
                <p class="alternative">
                    <aksess:getattribute name="alt9"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(9, request) %>" align="middle">
                    ${poll_alt9}
                </p>
                </aksess:exists>

                <aksess:exists name="alt10">
                <p class="alternative">
                    <aksess:getattribute name="alt10"/><br/>
                    <img src="<aksess:geturl url="/bitmaps/common/img_poll.gif"/>" alt="${num_votes}" height="<%=barHeight%>" width="<%= getWidth(10, request) %>" align="middle">
                    ${poll_alt10}
                </p>
                </aksess:exists>

                <p><kantega:label key="poll.result.numberofvotes" bundle="site"/>: ${num_votes}</p>
            </div>

        </c:when>

        <c:otherwise>
            <div class="alternatives">
                <form action="" method="POST">
                    <fieldset>
                        <p class="alternative"><input type="radio" name="alternativ" value="alt1"><aksess:getattribute name="alt1"/></p>
                        <p class="alternative"><input type="radio" name="alternativ" value="alt2"><aksess:getattribute name="alt2"/></p>
                        <aksess:exists name="alt3">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt3"><aksess:getattribute name="alt3"/></p>
                        </aksess:exists>
                        <aksess:exists name="alt4">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt4"><aksess:getattribute name="alt4"/></p>
                        </aksess:exists>
                        <aksess:exists name="alt5">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt5"><aksess:getattribute name="alt5"/></p>
                        </aksess:exists>
                        <aksess:exists name="alt6">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt6"><aksess:getattribute name="alt6"/></p>
                        </aksess:exists>
                        <aksess:exists name="alt7">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt7"><aksess:getattribute name="alt7"/></p>
                        </aksess:exists>
                        <aksess:exists name="alt8">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt8"><aksess:getattribute name="alt8"/></p>
                        </aksess:exists>
                        <aksess:exists name="alt9">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt9"><aksess:getattribute name="alt9"/></p>
                        </aksess:exists>
                        <aksess:exists name="alt10">
                            <p class="alternative"><input type="radio" name="alternativ" value="alt10"><aksess:getattribute name="alt10"/></p>
                        </aksess:exists>
                        <p><input type="submit" name="pollsubmit_<aksess:getattribute name="contentid"/>" class="button" value="<kantega:label key="poll.submit" bundle="site"/>"></p>
                    </fieldset>
                </form>

                <aksess:exists name="linktext">
                  <p><a href="<aksess:getattribute name="url"/>&amp;visresultat=true"><aksess:getattribute name="linktext"/></a></p>
                </aksess:exists>

            </div>
        </c:otherwise>
    </c:choose>
