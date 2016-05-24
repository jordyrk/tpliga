<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/aksess" prefix="aksess" %>
<%@ taglib uri="http://www.kantega.no/aksess/tags/commons" prefix="kantega" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<kantega:section id="title">
    <aksess:getattribute name="title"/>
</kantega:section>

<kantega:section id="bodyclass">subscription</kantega:section>

<kantega:section id="content">


    <h1><aksess:getattribute name="title"/></h1>

    <c:choose>

        <c:when test="${meldtAv != null}">
            <div class="subscriptionFeedback">
                <aksess:getattribute name="text_subscribe"/>
            </div>
        </c:when>

        <c:when test="${meldtPa != null}">
            <div class="subscriptionFeedback">
                <aksess:getattribute name="text_unsubscribe"/>
            </div>
        </c:when>

        <c:otherwise>

            <aksess:exists name="lead paragraph">
                <p class="leadParagraph">
                    <aksess:getattribute name="lead paragraph"/>
                </p>
            </aksess:exists>


            <form method="post" action="<aksess:getattribute name="url"/>">
                <fieldset class="alternatives">
                    <table>
                        <thead>
                            <tr>
                                <th colspan="2"><kantega:label key="subscription.title" bundle="site"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="options">
                                <td>
                                    <kantega:label key="subscription.email" bundle="site"/>:
                                </td>
                                <td>
                                   <input type="text" name="epost" maxlength="80" title="<kantega:label key="subscription.email" bundle="site"/>" value="<c:out value="${epost}"/>">
                                </td>
                            </tr>
                            <tr class="options">
                                <td><kantega:label key="subscription.notification" bundle="site"/>:</td>
                                <td>
                                    <input type="radio" name="interval" value="immediate"> <kantega:label key="subscription.notification.interval.immediately" bundle="site"/><br>
                                    <input type="radio" name="interval" value="daily" checked> <kantega:label key="subscription.notification.interval.daily" bundle="site"/><br>
                                    <input type="radio" name="interval" value="weekly"> <kantega:label key="subscription.notification.interval.weekly" bundle="site"/><br>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>

                <fieldset class="categories">
                    <table class="list">
                        <thead>
                            <tr>
                                <th><kantega:label key="subscription.category" bundle="site"/></th>
                                <th><kantega:label key="subscription.subscribe" bundle="site"/></th>
                                <th><kantega:label key="subscription.unsubscribe" bundle="site"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <aksess:getcollection name="categories" contentlist="categories" orderby="title" varStatus="status">
                            <tr class="listrow${status.index%2}">
                                <td><aksess:getattribute name="title" collection="categories"/></td>
                                <td><input type="radio" id="kategori_pa_${status.index}" name="<aksess:getattribute name="id" collection="categories"/>" value="pa"></td>
                                <td><input type="radio" id="kategori_av_${status.index}" name="<aksess:getattribute name="id" collection="categories"/>" value="av"></td>
                            </tr>
                            </aksess:getcollection>
                        </tbody>
                    </table>

                    <p><input type="submit" class="button" value="<kantega:label key="subscription.submit" bundle="site"/>"/></p>
                </fieldset>
            </form>
        </c:otherwise>
    </c:choose>


</kantega:section>



<aksess:include url="/WEB-INF/jsp$SITE/include/design/standard.jsp"/>
