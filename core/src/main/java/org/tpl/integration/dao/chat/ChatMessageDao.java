package org.tpl.integration.dao.chat;

import org.tpl.business.model.chat.ChatMessage;

import java.util.List;

public interface ChatMessageDao {

    public void saveOrUpdateChatMessage(ChatMessage chatMessage);

    public ChatMessage getChatMessageById(int chatMessageId);

    public List<ChatMessage> getChatMessages(int number, int offset);

    public List<ChatMessage> getChatMessages(int managerId);

    void deleteChatMessagesForManager(int managerId);
}
