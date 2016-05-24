package org.tpl.business.model.chat;

import no.kantega.publishing.security.data.User;
import org.tpl.business.model.fantasy.FantasyManager;

import java.util.Date;

public class ChatMessage {

    private int id = -1;
    private String message;
    private Date createdDate;
    private FantasyManager fantasyManager;

    public ChatMessage() {
        createdDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public FantasyManager getFantasyManager() {
        return fantasyManager;
    }

    public void setFantasyManager(FantasyManager fantasyManager) {
        this.fantasyManager = fantasyManager;
    }

    public boolean isNew(){
        return id == -1;
    }
}
