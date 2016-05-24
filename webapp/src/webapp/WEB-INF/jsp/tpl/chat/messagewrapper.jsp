<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<div id="Messages">
    <h1 title="Oppdateres hvert 15. sekund">Prat</h1>
    <span id="GetOlderMessages" class="minus" title="Hent gamlere beskjeder"></span>
    <span id="GetNewerMessages" class="plus hidden" title="Hent nyere beskjeder"></span>
    <div id="MessagesWrapper"></div>
    <form id="MessageForm" action="${pageContext.request.contextPath}/tpl/chat/createMessage" >
        <div>
            <textarea rows="2" name="message" placeholder="Skriv beskjed" ></textarea>
            <input type="submit" value="Legg inn melding" class="button">

        </div>
    </form>


</div>