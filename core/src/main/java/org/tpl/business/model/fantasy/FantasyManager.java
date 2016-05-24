package org.tpl.business.model.fantasy;

import no.kantega.publishing.security.data.User;
/*
Created by jordyr, 25.nov.2010

*/

public class FantasyManager {
    private int managerId = -1;
    private String userId;
    private String managerName;
    private boolean active;

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
     public boolean isNew(){
        return managerId == -1;
    }

    @Override
    public String toString() {
        return managerId +"";
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
