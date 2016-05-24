package org.tpl.business.service;

import org.tpl.business.model.*;
import org.tpl.business.model.search.*;
import org.tpl.business.service.fantasy.exception.PlayerDeleteException;

import java.util.List;

/**
 * User: Koren
 * Date: 21.jun.2010
 * Time: 20:46:18
 */
public interface LeagueService {

    void saveOrUpdateTeam(Team team);

    Team getTeamById(int id);

    List<Team> getTeamByAlias(String alias);

    public Team getTeamByExternalId(int externalId);

    List<Team> getAllTeams();

    TeamSearchResult getTeamBySearchTerm(SearchTerm term);

    void saveOrUpdateLeague(League league);

    void deleteLeague(int id);

    League getLeagueById(int id);

    List<League> getAllLeagues();

    void saveOrUpdateMatch(Match match);

    Match getMatchById(int id);

    Match getMatchByTeamsAndSeason(int seasonId, int homeTeamId, int awayTeamId);

    List<Match> getPlayedMatchesInSeason(int seasonId);

    MatchSearchResult getMatchBySearchTerm(SearchTerm term);

    List<Match> getMatchBySeasonId(int seasonId);

    List<Match> getMatchByLeagueRoundId(int leagueRoundId);

    List<Match> getMatchByIdRange(List<Integer> matchIdList);

    int queryMatchForInt(String field, int matchId );

    void saveOrUpdatePlayerStats(PlayerStats playerStats);

    PlayerStats getPlayerStatsByMatchAndPlayer(int playerId, int matchId);

    List<PlayerStats> getPlayerStatsByMatchId(int matchId);

    List<PlayerStats> getPlayerStatsForPlayerAndSeason(int playerId, int seasonId);

    PlayerStats getPlayerStatsByPlayerAndRound(int playerId, int leagueRoundId);

    int queryPlayerStatsForInt(String field, int playerId, int matchId);

    int getHighestLeagueRoundIdForSeason(int seasonId);

    int getLowestLeagueRoundIdForSeason(int seasonId);

    void saveOrUpdateSeason(Season season);

    List<Season> getSeasonsByLeagueId(int leagueId);

    List<Season> getAllSeasons();

    Season getSeasonById(int id);

    Season getDefaultSeason();

    void saveOrUpdateTeamStats(TeamStats teamStats);

    TeamStats getTeamStatsByTeamAndSeason(int teamId, int seasonId);

    List<Team> findTeamByName(String... teamName);

    void saveOrUpdateLeagueRound(LeagueRound leagueRound);

    List<LeagueRound> getAllLeagueRounds();

    List<LeagueRound> getLeagueRoundsBySeasonId(int seasonId);

    LeagueRound getLeagueRoundById(int leagueRoundId);

    LeagueRound getLeagueRoundByRoundAndSeason(Season season, int round);

    LeagueRound getFirstLeagueRoundForSeason(int seasonId);

    List<SimplePlayerStats> getSimplePlayerStatsBySearchTerm(SimplePlayerStatsAttribute simplePlayerStatsAttribute, SearchTerm term, boolean best, int number, String roundIdArray) ;

    List<Team> getTeamsInSeason(int seasonId);



}
