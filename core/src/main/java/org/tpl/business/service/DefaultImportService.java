package org.tpl.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.*;
import org.tpl.business.model.statsfc.DataHolder;
import org.tpl.business.model.statsfc.MonthEnum;
import org.tpl.business.service.fantasy.exception.PlayerStatsCreationException;
import org.tpl.integration.parser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Koren
 * Date: 11.aug.2010
 * Time: 18:32:05
 */
public class DefaultImportService implements ImportService {


    @Autowired
    MatchConstructor matchConstructor;

    @Autowired
    PlayerStatsConstructor playerStatsConstructor;

    @Autowired
    LeagueService leagueService;


    @Autowired
    StatsFcPlayerStatsJsonImpl statsFcPlayerStatsJson;

    public List<Match> importMatches(Season season) throws MatchImportException {
        List<Match> matches = matchConstructor.getMatches();
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            match.setLeagueRound(leagueService.getLeagueRoundByRoundAndSeason(season, match.getLeagueRound().getRound()));

            //Matchdata and score may be updated
            Match storedMatch = leagueService.getMatchByTeamsAndSeason(season.getSeasonId(), match.getHomeTeam().getTeamId(), match.getAwayTeam().getTeamId());
            if (storedMatch != null) {
                storedMatch.setHomeGoals(match.getHomeGoals());
                storedMatch.setAwayGoals(match.getAwayGoals());
                storedMatch.setMatchDate(match.getMatchDate());
                storedMatch.setLeagueRound(match.getLeagueRound());
                match = storedMatch;
            }
            leagueService.saveOrUpdateMatch(match);
        }
        return matches;
    }

    public void readDataForMonth(Season season, MonthEnum month) throws MatchImportException {
        DataHolder.clear();
        List<Map<String, Object>> matches = statsFcPlayerStatsJson.getMatches(month);
        for(Map<String,Object> map : matches){
            Match match = getMatch(season, map);
            DataHolder.add(match.getMatchId(),map);
        }


    }

    private Match getMatch(Season season, Map<String, Object> map) throws MatchImportException {
        Map teams = (Map) map.get("teams");
        Map home = (Map) teams.get("home");
        String homeName = (String) home.get("name");
        List<Team> homeTeamByAlias = leagueService.getTeamByAlias(homeName);
        Team homeTeam = null;
        if(homeTeamByAlias != null && homeTeamByAlias.size() >= 0){
            homeTeam = homeTeamByAlias.get(0);
        }else {
            throw new MatchImportException("No team found for :  " + homeName);
        }
        Map away = (Map) teams.get("away");
        String awayName = (String) away.get("name");
        List<Team> awayTeamByAlias = leagueService.getTeamByAlias(awayName);
        Team awayTeam = null;
        if(awayTeamByAlias != null && awayTeamByAlias.size() >= 0){
            awayTeam = awayTeamByAlias.get(0);
        }else {
            throw new MatchImportException("No team found for :  " + awayName);
        }

        Match match = leagueService.getMatchByTeamsAndSeason(season.getSeasonId(),homeTeam.getTeamId(),awayTeam.getTeamId());


        return match;
    }


    private void validateTeam(String name, Team team) throws MatchImportException {
        if (team == null) {
            throw new MatchImportException("Alias does not exist for: " + name);
        }
    }

    public List<PlayerStats> importPlayerStats(Match match) {
        List<PlayerStats> playerStatsList = playerStatsConstructor.getPlayerStats(match);
        return playerStatsList;
    }
}
