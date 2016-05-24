package org.tpl.business.service.chat;


import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.chat.ChatMessage;
import org.tpl.business.model.comparator.ChatMessageComparator;
import org.tpl.business.model.fantasy.FantasyManager;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.integration.dao.chat.ChatMessageDao;

import java.util.Collections;
import java.util.List;

public class DefaultChatService implements ChatService {

    @Autowired
    ChatMessageDao chatMessageDao;

    @Autowired
    FantasyService fantasyService;

    public void saveOrUpdateChatMessage(ChatMessage chatMessage) {
        chatMessageDao.saveOrUpdateChatMessage(chatMessage);
    }

    public ChatMessage getChatMessageById(int chatMessageId) {
        ChatMessage chatMessage = chatMessageDao.getChatMessageById(chatMessageId);
        updateDependencies(chatMessage);
        return chatMessage;

    }

    public List<ChatMessage> getChatMessages(int number, int offset) {
        List<ChatMessage> list = chatMessageDao.getChatMessages(number, offset);
        updateDependencies(list);
        Collections.sort(list, new ChatMessageComparator());
        return list;
    }

    public void deleteChatMessagesForManager(int managerId) {
        chatMessageDao.deleteChatMessagesForManager(managerId);

    }

    private void updateDependencies(List<ChatMessage> chatMessageList){
        for(ChatMessage chatMessage: chatMessageList){
            updateDependencies(chatMessage);
        }
    }
    private void updateDependencies(ChatMessage chatMessage){
        FantasyManager fantasyManager = fantasyService.getFantasyManagerById(chatMessage.getFantasyManager().getManagerId());
        chatMessage.setFantasyManager(fantasyManager);

    }
}
