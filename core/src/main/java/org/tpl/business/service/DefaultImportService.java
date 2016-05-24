package org.tpl.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.*;
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
public class DefaultImportService implements ImportService{


    @Autowired
    MatchConstructor matchConstructor;

    @Autowired
    PlayerStatsConstructor playerStatsConstructor;

    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyPremierLeagueMatchHtmlParser fantasyPremierLeagueMatchHtmlParser;



    public List<Match> importMatches(Season season) throws MatchImportException {
        List<Match> matches = matchConstructor.getMatches();
        for(int i = 0; i < matches.size(); i++){
            Match match = matches.get(i);
            match.setLeagueRound(leagueService.getLeagueRoundByRoundAndSeason(season, match.getLeagueRound().getRound()));

            //Matchdata and score may be updated
            Match storedMatch = leagueService.getMatchByTeamsAndSeason(season.getSeasonId(), match.getHomeTeam().getTeamId(), match.getAwayTeam().getTeamId());
            if(storedMatch != null){
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

    @Override
    public void updateMatchesWithFantastyPremierLeagueId(Integer seasonId, int round) throws MatchImportException {
        fantasyPremierLeagueMatchHtmlParser.init(round);
        List<Match> matches = fantasyPremierLeagueMatchHtmlParser.getMatches();
        List<Match> storedMatchList= new ArrayList<Match>();
        for(Match match: matches){
            String homeTeamName = match.getHomeTeam().getFullName();
            String awayTeamName = match.getAwayTeam().getFullName();

            Team homeTeam = leagueService.getTeamByAlias(homeTeamName).size() == 0 ? null: leagueService.getTeamByAlias(homeTeamName).get(0);
            validateTeam(homeTeamName ,homeTeam);
            Team awayTeam = leagueService.getTeamByAlias(awayTeamName).size() == 0 ? null: leagueService.getTeamByAlias(awayTeamName).get(0);
            validateTeam(awayTeamName,awayTeam);
            Match storedMatch = leagueService.getMatchByTeamsAndSeason(seasonId, homeTeam.getTeamId(), awayTeam.getTeamId());
            storedMatch.setFantasyPremierLeagueId(match.getFantasyPremierLeagueId());
            storedMatchList.add(storedMatch);
        }
        for(Match storedMatch : storedMatchList){
            leagueService.saveOrUpdateMatch(storedMatch);
        }

    }

    private void validateTeam(String name, Team team) throws MatchImportException{
        if(team == null){
            throw new MatchImportException("Alias does not exist for: " + name);
        }
    }

    public List<PlayerStats> importPlayerStats(Match match) {
        List<PlayerStats> playerStatsList = playerStatsConstructor.getPlayerStats(match);
        return playerStatsList;
    }
}
