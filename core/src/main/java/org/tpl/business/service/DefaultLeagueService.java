package org.tpl.business.service;

import org.tpl.business.model.*;
import org.tpl.business.model.comparator.TeamComparator;
import org.tpl.business.model.search.*;
import org.tpl.business.util.CommaSeparator;
import org.tpl.integration.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Koren
 * Date: 21.jun.2010
 * Time: 20:48:48
 */
public class DefaultLeagueService implements LeagueService {

    @Autowired
    LeagueDao leagueDao;

    @Autowired
    MatchDao matchDao;

    @Autowired
    SeasonDao seasonDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    PlayerStatsDao playerStatsDao;


    @Autowired
    PlayerService playerService;
    public void saveOrUpdateTeam(Team team) {
        teamDao.saveOrUpdateTeam(team);
    }

    public Team getTeamById(int id) {
        return teamDao.getTeamById(id);
    }

    public List<Team> getTeamByAlias(String alias) {
        ComparisonTerm aliasTerm = new ComparisonTerm("alias", Operator.LIKE , "%"+alias+"%");
        ComparisonTerm fullNameTerm = new ComparisonTerm("fullname", Operator.LIKE, alias);
        ComparisonTerm shortNameTerm = new ComparisonTerm("shortname", Operator.LIKE, alias);
        OrTerm orTerm = new OrTerm();
        orTerm.addTerm(aliasTerm);
        orTerm.addTerm(fullNameTerm);
        orTerm.addTerm(shortNameTerm);
        return teamDao.getTeamBySearchTerm(orTerm).getResults();
    }

    public Team getTeamByExternalId(int externalId) {
        ComparisonTerm term = new ComparisonTerm("externalId", Operator.EQ, externalId);
        TeamSearchResult teamSearchResult = teamDao.getTeamBySearchTerm(term);
        if(teamSearchResult.getResults().size()> 0)return teamSearchResult.getResults().get(0);
        else return null;
    }

    public List<Team> getAllTeams() {
        return teamDao.getAllTeams();
    }

    public TeamSearchResult getTeamBySearchTerm(SearchTerm term) {
        return teamDao.getTeamBySearchTerm(term);
    }

    public List<Team> findTeamByName(String... teamName) {
        OrTerm outerOrTerm = new OrTerm();
        for(String name : teamName){
            ComparisonTerm term1 = new ComparisonTerm("shortname", Operator.LIKE, name);
            ComparisonTerm term2 = new ComparisonTerm("fullname", Operator.LIKE, name);
            ComparisonTerm term3 = new ComparisonTerm("alias", Operator.LIKE, name);
            OrTerm orTerm = new OrTerm(term1,term2);
            orTerm.addTerm(term3);
            outerOrTerm.addTerm(orTerm);
        }
        return teamDao.getTeamBySearchTerm(outerOrTerm).getResults();
    }

    public void saveOrUpdateLeague(League league) {
        leagueDao.saveOrUpdateLeague(league);
    }

    public void deleteLeague(int id) {
        leagueDao.deleteLeague(id);
    }

    public League getLeagueById(int id) {
        return leagueDao.getLeagueById(id);
    }

    public List<League> getAllLeagues() {
        return leagueDao.getAllLeagues();
    }

    public void saveOrUpdateMatch(Match match) {
        matchDao.saveOrUpdateMatch(match);
    }

    public Match getMatchById(int id) {
        Match match = matchDao.getMatchById(id);
        updateDependencies(match);
        return match;
    }

    public Match getMatchByTeamsAndSeason(int seasonId, int homeTeamId, int awayTeamId) {

        SearchTerm homeTeamSearchTerm = new ComparisonTerm("hometeamid", Operator.EQ, homeTeamId);
        SearchTerm awayTeamSearchTerm = new ComparisonTerm("awayteamid", Operator.EQ, awayTeamId);
        AndTerm andTerm = new AndTerm(homeTeamSearchTerm, awayTeamSearchTerm);
        MatchSearchResult matchSearchResult = this.getMatchBySearchTerm(andTerm);
        if (matchSearchResult.getResults() == null || matchSearchResult.getResults().isEmpty() ){
            return null;
        }
        for(Match match: matchSearchResult.getResults()){
            updateDependencies(match);
            LeagueRound leagueRound = match.getLeagueRound();
            int matchSeasonId = leagueRound.getSeason().getSeasonId();
            if(matchSeasonId == seasonId){
                return match;
            }
        }
        return null;

    }

    public List<Match> getPlayedMatchesInSeason(int seasonId) {
        List<LeagueRound> rounds = getLeagueRoundsBySeasonId(seasonId);
        String commaSeparatedString = CommaSeparator.createCommaSeparatedStringFromLeagueRounds(rounds);
        return matchDao.getMatchesWithRounds(commaSeparatedString,true);
    }

    public MatchSearchResult getMatchBySearchTerm(SearchTerm term) {
        return matchDao.getMatchBySearchTerm(term);
    }

    public List<Match> getMatchBySeasonId(int seasonId) {
        List<LeagueRound> leagueRounds = this.getLeagueRoundsBySeasonId(seasonId);
        List<Match> matches = new ArrayList<Match>();
        for(LeagueRound leagueRound: leagueRounds){
            matches.addAll(this.getMatchByLeagueRoundId(leagueRound.getLeagueRoundId()));
        }
        updateDependenciesForMatches(matches);
        return matches;
    }

    public List<Match> getMatchByLeagueRoundId(int leagueRoundId) {
        ComparisonTerm term1 = new ComparisonTerm("leagueroundid", Operator.LIKE, leagueRoundId);
        List<Match> matches =  matchDao.getMatchBySearchTerm(term1).getResults();
        updateDependenciesForMatches(matches);
        return matches;
    }

    public List<Match> getMatchByIdRange(List<Integer> matchIdList) {
        OrTerm or = new OrTerm();
        for(Integer id: matchIdList){
            ComparisonTerm comparisonTerm = new ComparisonTerm("id", Operator.EQ, id);
            or.addTerm(comparisonTerm);
        }
        List<Match> matches =  matchDao.getMatchBySearchTerm(or).getResults();
        updateDependenciesForMatches(matches);
        return matches;
    }

    public int queryMatchForInt(String field, int matchId) {
        return matchDao.queryForInt(field, matchId);
    }

    public void saveOrUpdatePlayerStats(PlayerStats playerStats) {
        playerStatsDao.saveOrUpdatePlayerStats(playerStats);
    }

    public PlayerStats getPlayerStatsByMatchAndPlayer(int playerId, int matchId) {
        PlayerStats playerStats = playerStatsDao.getPlayerStatsByMatchAndPlayer(playerId, matchId);
        updateDependencies(playerStats);
        return playerStats;
    }

    public List<PlayerStats> getPlayerStatsByMatchId(int matchId) {
        List<PlayerStats> playerStatsList = playerStatsDao.getPlayerStatsByMatchId(matchId);
        for(PlayerStats playerStats: playerStatsList){
            updateDependencies(playerStats);
        }
        return playerStatsList;
    }

    public List<PlayerStats> getPlayerStatsForPlayerAndSeason(int playerId, int seasonId){
        List<PlayerStats> playerStatsList = playerStatsDao.getPlayerStatsForPlayerAndSeason(playerId, seasonId);
        for(PlayerStats playerStats: playerStatsList){
            updateDependencies(playerStats);
        }
        return playerStatsList;
    }

    public PlayerStats getPlayerStatsByPlayerAndRound(int playerId, int leagueRoundId) {
        return playerStatsDao.getPlayerStatsByPlayerAndRound(playerId, leagueRoundId);
    }

    public int queryPlayerStatsForInt(String field, int playerId, int matchId) {
        return playerStatsDao.queryForInt(field, playerId, matchId);
    }

    public int getHighestLeagueRoundIdForSeason(int seasonId) {
        return playerStatsDao.getHighestLeagueRoundIdForSeason(seasonId);
    }

    public int getLowestLeagueRoundIdForSeason(int seasonId) {
        return playerStatsDao.getLowestLeagueRoundIdForSeason(seasonId);
    }

    public List<Season> getSeasonsByLeagueId(int leagueId) {
        List<Season> seasons = seasonDao.getSeasonsByLeagueId(leagueId);
        for(Season season: seasons){
            updateDependencies(season);
        }
        return seasons;
    }

    public List<Season> getAllSeasons() {
        List<Season> seasons = seasonDao.getAllSeasons();
        for(Season season: seasons){
            updateDependencies(season);
        }
        return seasons;
    }

    public void saveOrUpdateSeason(Season season) {
        boolean createLeagueRound = season.isNew();
        seasonDao.saveOrUpdateSeason(season);
        if(season.isDefaultSeason()){
            seasonDao.setDefaultSeason(season);
        }
        if(createLeagueRound && !season.isNew()){
            createAndSaveLeagueRounds(season);
        }
    }

    public Season getSeasonById(int id) {
        Season season =  seasonDao.getSeasonById(id);
        updateDependencies(season);
        return season;
    }

    public Season getDefaultSeason() {
        Season season =  seasonDao.getDefaultSeason();
        if(season != null){
            updateDependencies(season);
        }
        return season;
    }

    public void saveOrUpdateTeamStats(TeamStats teamStats) {
        leagueDao.saveOrUpdateTeamStats(teamStats);
    }

    public TeamStats getTeamStatsByTeamAndSeason(int teamId, int seasonId) {
        return getTeamStatsByTeamAndSeason(teamId, seasonId);
    }

    public void saveOrUpdateLeagueRound(LeagueRound leagueRound) {
        seasonDao.saveOrUpdateLeagueRound(leagueRound);
    }

    public List<LeagueRound> getAllLeagueRounds() {
        return seasonDao.getAllLeagueRounds();
    }

    public List<LeagueRound> getLeagueRoundsBySeasonId(int seasonId) {
        return seasonDao.getLeagueRoundBySeasonId(seasonId);
    }

    public LeagueRound getLeagueRoundById(int leagueRoundId) {
        return seasonDao.getLeagueRoundById(leagueRoundId);
    }

    public LeagueRound getLeagueRoundByRoundAndSeason(Season season, int round) {
        return seasonDao.getLeagueRoundByRoundAndSeason(season,round);
    }

    public LeagueRound getFirstLeagueRoundForSeason(int seasonId) {
        return seasonDao.getFirstLeagueRoundForSeason(seasonId);
    }

    public List<SimplePlayerStats> getSimplePlayerStatsBySearchTerm(SimplePlayerStatsAttribute simplePlayerStatsAttribute, SearchTerm term, boolean best, int number, String roundIdArray) {
        SimplePlayerStatsSearchResult simplePlayerStatsSearchResult =  playerStatsDao.getSimplePlayerStatsBySearchTerm(simplePlayerStatsAttribute,term,best,number,roundIdArray);
        for(SimplePlayerStats simplePlayerStats: simplePlayerStatsSearchResult.getResults()){
            updateDependencies(simplePlayerStats);
        }

        return simplePlayerStatsSearchResult.getResults();
    }

    public List<Team> getTeamsInSeason(int seasonId) {
        List<Team> teamList = teamDao.getTeamBySeason(seasonId);
        return teamList;
    }


    public void setLeagueDao(LeagueDao leagueDao) {
        this.leagueDao = leagueDao;
    }

    public void setMatchDao(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    public void setSeasonDao(SeasonDao seasonDao) {
        this.seasonDao = seasonDao;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void setTeamDao(TeamDao teamDao) {
        this.teamDao = teamDao;
    }


    private void updateDependencies(SimplePlayerStats simplePlayerStats){
        simplePlayerStats.setPlayer(playerService.getPlayerById(simplePlayerStats.getPlayer().getPlayerId()));
        simplePlayerStats.setTeam(this.getTeamById(simplePlayerStats.getTeam().getTeamId()));
    }

    private void updateDependencies(Season season){
        List<Team> teams = teamDao.getTeamBySeason(season.getSeasonId());
        Collections.sort(teams, new TeamComparator());
        season.setTeams(teams);
        if (season.getLeague() != null) {
            League league = this.getLeagueById(season.getLeague().getLeagueId());
            if (league != null){
                season.setLeague(league);
            }
        }

        Season defaultSeason = seasonDao.getDefaultSeason();
        if(defaultSeason == null){
            season.setDefaultSeason(false);
        }else{
            season.setDefaultSeason(season.getSeasonId() == defaultSeason.getSeasonId());
        }

    }

    private void updateDependenciesForMatches(List<Match> matches){
        for(Match match: matches){
            updateDependencies(match);
        }
    }

    private void updateDependencies(Match match){
        Team homeTeam = this.getTeamById(match.getHomeTeam().getTeamId());
        Team awayTeam = this.getTeamById(match.getAwayTeam().getTeamId());
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        LeagueRound leagueRound = this.getLeagueRoundById(match.getLeagueRound().getLeagueRoundId());
        match.setLeagueRound(leagueRound);
    }

    private void updateDependencies(PlayerStats playerStats){
        if(playerStats == null) return;
        Player player = playerService.getPlayerById(playerStats.getPlayer().getPlayerId());
        Team team = this.getTeamById(playerStats.getTeam().getTeamId());
        playerStats.setPlayer(player);
        playerStats.setTeam(team);
        Match match = this.getMatchById(playerStats.getMatch().getMatchId());
        playerStats.setMatch(match);
    }

    private void createAndSaveLeagueRounds(Season season){
        if(season.getNumberOfTeams()> 0){
            for(int i = 1 ; i <= (season.getNumberOfTeams()*2)-2; i++){
                LeagueRound leageLeagueRound = new LeagueRound();
                leageLeagueRound.setRound(i);
                leageLeagueRound.setSeason(season);
                seasonDao.saveOrUpdateLeagueRound(leageLeagueRound);
            }
        }
    }

}
