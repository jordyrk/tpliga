package org.tpl.integration.dao;

import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.model.SimplePlayerStatsAttribute;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.model.search.SimplePlayerStatsSearchResult;

import java.util.List;
/*
Created by jordyr, 06.nov.2010

*/

public interface PlayerStatsDao {

    void saveOrUpdatePlayerStats(PlayerStats playerStats);

    PlayerStats getPlayerStatsByMatchAndPlayer(int playerId, int matchId);

    List<PlayerStats> getPlayerStatsByMatchId(int matchId);

    List<PlayerStats> getPlayerStatsForPlayerAndSeason(int playerId, int seasonId);

    PlayerStats getPlayerStatsByPlayerAndRound(int playerId, int leagueRoundId);

    int queryForInt(String field, int playerId, int matchId);

    int getHighestLeagueRoundIdForSeason(int seasonId);

    int getLowestLeagueRoundIdForSeason(int seasonId);

    SimplePlayerStatsSearchResult getSimplePlayerStatsBySearchTerm(SimplePlayerStatsAttribute simplePlayerStatsAttribute, SearchTerm term, boolean best, int number, String roundIdArray) ;

    boolean playerHasStats(int playerId);
}
