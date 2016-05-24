package org.tpl.business.model.comparator;

import org.tpl.business.model.chat.ChatMessage;

import java.util.Comparator;

public class ChatMessageComparator implements Comparator<ChatMessage> {

    public int compare(ChatMessage o1, ChatMessage o2) {
        return (o1.getCreatedDate()).compareTo(o2.getCreatedDate());
    }
}