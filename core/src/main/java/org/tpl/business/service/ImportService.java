package org.tpl.business.service;

import org.tpl.business.model.*;
import org.tpl.business.service.fantasy.exception.PlayerStatsCreationException;
import org.tpl.integration.parser.MatchImportException;

import java.util.List;
import java.util.Map;

/**
 * User: Koren
 * Date: 08.aug.2010
 * Time: 18:22:09
 */
public interface ImportService {
    List<Match> importMatches(Season season) throws MatchImportException;

    List<PlayerStats> importPlayerStats(Match match);

    void updateMatchesWithFantastyPremierLeagueId(Integer seasonId, int round) throws MatchImportException;
}
