package org.tpl.presentation.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.chat.ChatMessage;
import org.tpl.business.model.fantasy.FantasyManager;
import org.tpl.business.service.chat.ChatService;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ChatMessageController {
    @Autowired
    ChatService chatService;

     @Autowired
     FantasyUtil fantasyUtil;

    @RequestMapping("/chat/getMessages")
    public String getChatMessages(@RequestParam(required = true) int offset,ModelMap model){
        List<ChatMessage> chatMessageList = chatService.getChatMessages(10,offset);
        model.put("chatMessageList", chatMessageList);
        return "chat/messages";

    }
    @RequestMapping("/chat/createMessage")
    public String createChatMessage(@RequestParam String message,ModelMap model, HttpServletRequest request){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        FantasyManager fantasyManager = fantasyUtil.getFantasyManagerFromRequest(request);
        chatMessage.setFantasyManager(fantasyManager);
        chatService.saveOrUpdateChatMessage(chatMessage);
        return getChatMessages(0,model);
    }
}
