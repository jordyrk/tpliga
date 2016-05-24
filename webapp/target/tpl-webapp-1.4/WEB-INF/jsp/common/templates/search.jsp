<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  ~ Copyright 2009 Kantega AS
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">search</kantega:section>

<kantega:section id="content">

    <h1><aksess:getattribute name="title"/></h1>

    <aksess:exists name="lead paragraph">
        <p class="leadParagraph">
            <aksess:getattribute name="lead paragraph"/>
        </p>
    </aksess:exists>

    <div id="ErrorsContainer" style="display: none;"></div>

    <fieldset>
        <legend><kantega:label key="search.heading" bundle="site"/></legend>
        <form id="SearchForm" action="<aksess:getattribute name="url"/>" method="post" name="search">
            <p>
                <label><kantega:label key="search.query" bundle="site"/></label>
                <input type="text" name="q" size="20" value="<c:out value="${param.q}"/>" title="<kantega:label key="search.query" bundle="site"/>">
            </p>
            <p>
                <input type="submit" class="button" value="<kantega:label key="search.submit" bundle="site"/>">
            </p>
        </form>
    </fieldset>

    <c:if test="${param.q != null && param.q != ''}">
        <div class="searchResult">
            <c:choose>
                <c:when test="${result.searchResult.numberOfHits eq 0}">
                    <div class="hitCount">
                        <kantega:label key="search.results.nohits" bundle="site"/>
                    </div>
                </c:when>
                <c:otherwise>
                <div class="hitCount">
                    <kantega:label key="search.results.numberofhits" bundle="site"/>: ${result.searchResult.numberOfHits}
                </div>
                </c:otherwise>
            </c:choose>


            <c:forEach items="${result.searchHits}" var="searchHit">
                <div class="hit">
                    <a href="${searchHit.url}"><c:out value="${searchHit.title}" escapeXml="false"/></a><br>
                    <c:choose>
                        <c:when test="${searchHit.contextText != ''}">
                            <c:out value="${searchHit.contextText}" escapeXml="false"/>
                        </c:when>
                        <c:otherwise><c:out value="${searchHit.summary}" escapeXml="false"/></c:otherwise>
                    </c:choose>

                    <c:if test="${searchHit.pathElements != null}">
                        <div class="navigationpath">
                            <c:forEach var="pathElement" items="${searchHit.pathElements}">
                                <c:out value="${pathElement.title}" escapeXml="false"/> &gt;
                            </c:forEach>
                            <c:out value="${searchHit.title}" escapeXml="false"/>
                        </div>
                    </c:if>
                </div>
            </c:forEach>

            <c:if test="${result.searchResult.numberOfHits gt 10}">
                <div class="paging">
                    <c:choose>
                        <c:when test="${not empty links.prevPageUrl}">
                            <a class="previous" href="<c:out value="${links.prevPageUrl}"/>"><kantega:label key="search.results.previous" bundle="site"/></a>
                        </c:when>
                        <c:otherwise>
                            <span class="previous disabled"><kantega:label key="search.results.previous" bundle="site"/></span>
                        </c:otherwise>
                    </c:choose>
                    <div class="pagenumbers">
                        <c:forEach items="${links.pageUrls}" varStatus="status">
                            <c:choose>
                                <c:when test="${status.current.key == result.currentPage + 1}">
                                    <a class="currentpage" href="<c:out value="${status.current.value}"/>"><b><c:out value="${status.current.key}"/></b></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:out value="${status.current.value}"/>"><c:out value="${status.current.key}"/></a>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${!status.last}">
                                <span class="seperator">|</span>
                            </c:if>
                        </c:forEach>
                    </div>
                    <c:choose>
                        <c:when test="${not empty links.nextPageUrl}">
                            <a class="next" href="<c:out value="${links.nextPageUrl}"/>"><kantega:label key="search.results.next" bundle="site"/></a>
                        </c:when>
                        <c:otherwise>
                            <span class="next disabled"><kantega:label key="search.results.next" bundle="site"/></span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </c:if>

</kantega:section>

<kantega:section id="menu">
<c:set var="category" value="${result.hitCounts['search.documenttype']}"/>
<c:if test="${not empty category}">
    <h3>
        <kantega:label key="search.documenttype" bundle="site"/>
    </h3>
    <ul>
        <c:forEach items="${category}" var="hitCount">
            <c:if test="${hitCount.hitCount ne 0}">
                <li>
                    <c:set var="hitCountId" value="${hitCount.field}.${hitCount.term}"/>
                    <c:choose>
                        <c:when test="${not empty links.hitcounts[hitCountId]}">
                            <a href="<c:out value="${links.hitcounts[hitCountId]}"/>"><c:out value="${hitCount.termTranslated}"/> <span class="antall">(<c:out value="${hitCount.hitCount}"/>)</span></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${hitCount.termTranslated}"/> <span class="antall">(<c:out value="${hitCount.hitCount}"/>)</span>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</c:if>
<c:set var="category" value="${result.hitCounts['search.parents']}"/>
<c:if test="${not empty category}">
    <h3>
        <kantega:label key="search.parents" bundle="site"/>
    </h3>
    <ul>
        <c:forEach items="${category}" var="hitCount">
            <c:if test="${hitCount.hitCount ne 0}">
                <li>
                    <c:set var="hitCountId" value="${hitCount.field}.${hitCount.term}"/>
                    <c:choose>
                        <c:when test="${not empty links.hitcounts[hitCountId]}">
                            <a href="<c:out value="${links.hitcounts[hitCountId]}"/>"><c:out value="${hitCount.termTranslated}"/> <span class="antall">(<c:out value="${hitCount.hitCount}"/>)</span></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${hitCount.termTranslated}"/> <span class="antall">(<c:out value="${hitCount.hitCount}"/>)</span>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</c:if>
<c:set var="category" value="${result.hitCounts['search.lastmodified']}"/>
<c:if test="${not empty category}">
    <h3>
        <kantega:label key="search.lastmodified" bundle="site"/>
    </h3>
    <ul>
        <c:forEach items="${category}" var="hitCount">
            <c:if test="${hitCount.hitCount ne 0}">
                <li>
                    <c:set var="hitCountId" value="${hitCount.field}.${hitCount.term}"/>
                    <c:choose>
                        <c:when test="${not empty links.hitcounts[hitCountId]}">
                            <a href="<c:out value="${links.hitcounts[hitCountId]}"/>"><kantega:label key="${hitCount.termTranslated}" bundle="site"/> <span class="antall">(<c:out value="${hitCount.hitCount}"/>)</span></a>
                        </c:when>
                        <c:otherwise>
                            <kantega:label key="${hitCount.termTranslated}" bundle="common"/> <span class="antall">(<c:out value="${hitCount.hitCount}"/>)</span>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</c:if>
</kantega:section>


<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
