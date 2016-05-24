package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyCupGroup;

import java.util.List;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 21:33:52
 */
public interface FantasyCupGroupDao {
    void saveOrUpdateCupGroup(FantasyCupGroup fantasyCupGroup);

    FantasyCupGroup getFantasyCupGroupById(int id);

    List<FantasyCupGroup> getAllFantasyCupGroups();

    List<FantasyCupGroup> getAllFantasyCupGroups(int fantasyCupId);

    void deleteFantasyCupGroup(int id);
    
    List<FantasyCupGroup> getAllFantasyCupGroupsForFantasyRound(int fantasyRoundId);

    int getPreviousRoundId(int fantasyCupGroupId, int fantasyRoundId);
}


