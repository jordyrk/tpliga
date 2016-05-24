package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyLeague;

import java.util.List;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 21:33:52
 */
public interface FantasyCupDao {
    void saveOrUpdateCup(FantasyCup fantasyCup);

    FantasyCup getFantasyCupById(int id);

    List<FantasyCup> getAllFantasyCups();

    List<FantasyCup> getAllFantasyCups(int fantasySeasonId);

    void deleteCup(int fantasyCupId);

    List<FantasyCup> getFantasyCupByTeamId(int fantasyTeamId);

    List<FantasyCup> getFantasyCupByTeamIdAndSeasonId(int fantasySeasonId, int fantasyTeamId);
}


