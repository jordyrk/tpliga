package org.tpl.integration.dao;

import java.util.List;
import org.tpl.business.model.*;
import org.tpl.business.model.search.MatchSearchResult;
import org.tpl.business.model.search.PlayerSearchResult;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.model.search.TeamSearchResult;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 21:33:52
 */
public interface LeagueDao {
    void saveOrUpdateLeague(League league);

    void deleteLeague(int id);

    League getLeagueById(int id);

    List<League> getAllLeagues();

    void saveOrUpdateTeamStats(TeamStats teamStats);

    TeamStats getTeamStatsByTeamAndSeason(int teamId, int seasonId);
}
