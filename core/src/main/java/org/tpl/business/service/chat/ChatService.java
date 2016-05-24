package org.tpl.business.service.chat;

import org.tpl.business.model.chat.ChatMessage;

import java.util.List;

public interface ChatService {

    public void saveOrUpdateChatMessage(ChatMessage chatMessage);

    public ChatMessage getChatMessageById(int chatMessageId);

    public List<ChatMessage> getChatMessages(int number, int offset);

    public void deleteChatMessagesForManager(int managerId);
}
