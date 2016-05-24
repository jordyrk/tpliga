package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.League;
import org.tpl.business.model.TeamStats;
import org.tpl.business.model.fantasy.FantasyLeague;

import java.util.List;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 21:33:52
 */
public interface FantasyLeagueDao {
    void saveOrUpdateLeague(FantasyLeague fantasyLeague);

    void deleteFantasyLeague(int fantasyLeagueId);

    FantasyLeague getFantasyLeagueById(int id);

    List<FantasyLeague> getAllFantasyLeagues();

    List<FantasyLeague> getAllFantasyLeagues(int fantasySeasonId);

    List<FantasyLeague> getFantasyLeaguesForFantasyTeam(int fantasyTeamId, int fantasySeasonId);

    List<FantasyLeague> getFantasyLeaguesForFantasyTeam(int fantasyTeamId);

    List<FantasyLeague> getAllFantasyLeaguesForFantasyRound(int fantasyRoundId);

}


