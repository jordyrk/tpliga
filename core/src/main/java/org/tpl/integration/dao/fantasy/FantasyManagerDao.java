package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasyManager;

import java.util.List;
/*
Created by jordyr, 26.nov.2010

*/

public interface FantasyManagerDao {

    void saveOrUpdateFantasyManager(FantasyManager fantasyManager);

    FantasyManager getFantasyManagerById(int managerId);

    FantasyManager getFantasyManagerByUserId(String userId);

    void deleteFantasyManager(String userId);

    List<FantasyManager> getAllFantasyManagers();

    List<FantasyManager> getAllActiveFantasyManagers();
}
