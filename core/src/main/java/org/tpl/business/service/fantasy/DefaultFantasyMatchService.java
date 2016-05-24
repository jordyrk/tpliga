package org.tpl.business.service.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.model.SumPlayerStats;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.search.*;
import org.tpl.business.service.AbstractService;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.fantasy.score.ScoreCalculator;
import org.tpl.business.util.CommaSeparator;
import org.tpl.integration.dao.fantasy.FantasyMatchDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultFantasyMatchService extends AbstractService implements FantasyMatchService {

    @Autowired
    FantasyMatchDao fantasyMatchDao;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    LeagueService leagueService;

    @Autowired
    FantasyCupGroupService fantasyCupGroupService;

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyCupService fantasyCupService;

    public void saveOrUpdateFantasyMatch(FantasyMatch fantasyMatch) {
        fantasyMatchDao.saveOrUpdateFantasyMatch(fantasyMatch);
    }

    public FantasyMatch getFantasyMatchById(int fantasyMatchId) {
        FantasyMatch fantasyMatch = fantasyMatchDao.getFantasyMatchById(fantasyMatchId);
        updateDependencies(fantasyMatch);
        updateCompetitonDependencies(fantasyMatch);
        return fantasyMatch;
    }

    public List<FantasyMatch> getFantasyMatchByRoundId(int fantasyRoundId) {
        List<FantasyMatch> list = fantasyMatchDao.getMatchByRoundId(fantasyRoundId);
        updateDependencies(list);
        return list;
    }

    public List<FantasyMatch> getFantasyMatchByLeagueId(int fantasyLeagueId) {
        List<FantasyMatch> list = fantasyMatchDao.getMatchByLeagueId(fantasyLeagueId);
        updateDependencies(list);
        return list;
    }

    public List<FantasyMatch> getFantasyMatchByLeagueIdAndRoundId(int fantasyLeagueId, int fantasyRoundId) {
        List<FantasyMatch> list = fantasyMatchDao.getMatchByLeagueIdAndRoundId(fantasyLeagueId, fantasyRoundId);
        updateDependencies(list);
        return list;
    }

    public List<FantasyMatch> getFantasyMatchByCupId(int fantasyCupId) {
        List<FantasyMatch> list = fantasyMatchDao.getMatchByCupId(fantasyCupId);
        updateDependencies(list);
        return list;
    }

    public List<FantasyMatch> getFantasyMatchByCupIdAndRoundId(int fantasyCupId, int fantasyRoundId) {
        List<FantasyMatch> list = fantasyMatchDao.getMatchByCupIdAndRoundId(fantasyCupId, fantasyRoundId);
        updateDependencies(list);
        return list;
    }

    public List<FantasyMatch> getFantasyMatchByCupGroupId(int fantasyCupGroupId) {
        List<FantasyMatch> list = fantasyMatchDao.getMatchByCupGroupId(fantasyCupGroupId);
        updateDependencies(list);
        return list;
    }

    public List<FantasyMatch> getFantasyMatchByCupGroupIdAndRoundId(int fantasyCupGroupId, int fantasyRoundId) {
        List<FantasyMatch> list = fantasyMatchDao.getMatchByCupGroupIdAndRoundId(fantasyCupGroupId, fantasyRoundId);
        updateDependencies(list);
        return list;
    }


    public void deleteFantasyMatchForLeague(int fantasyLeagueId) {
        List<FantasyMatch> fantasyMatchList = getFantasyMatchByLeagueId(fantasyLeagueId);
        for(FantasyMatch fantasyMatch: fantasyMatchList){
            fantasyMatchDao.deleteFantasyMatch(fantasyMatch.getId());
        }
    }

    public void deleteFantasyMatchForCup(int fantasyCupId) {
        List<FantasyMatch> fantasyMatchList = getFantasyMatchByCupId(fantasyCupId);
        for(FantasyMatch fantasyMatch: fantasyMatchList){
            fantasyMatchDao.deleteFantasyMatch(fantasyMatch.getId());
        }
    }

    public void deleteFantasyMatchForCupGroup(int fantasyCupGroupId) {
        List<FantasyMatch> fantasyMatchList = getFantasyMatchByCupGroupId(fantasyCupGroupId);
        for(FantasyMatch fantasyMatch: fantasyMatchList){
            fantasyMatchDao.deleteFantasyMatch(fantasyMatch.getId());
        }
    }

    public void updateMatchScore(int fantasyRoundId) {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        List<FantasyMatch> list = this.getFantasyMatchByRoundId(fantasyRoundId);
        for(FantasyMatch fantasyMatch: list){
            FantasyTeamRound homeFantasyTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyMatch.getHomeTeam().getTeamId(),fantasyRoundId);
            FantasyTeamRound awayFantasyTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyMatch.getAwayTeam().getTeamId(),fantasyRoundId);
            int[] goals = scoreCalculator.calculateScore(homeFantasyTeamRound.getPoints(), awayFantasyTeamRound.getPoints());
            fantasyMatch.setHomegoals(goals[0]);
            fantasyMatch.setAwaygoals(goals[1]);
            fantasyMatch.setPlayed(true);
            saveOrUpdateFantasyMatch(fantasyMatch);
        }
    }

    public FantasyMatchSearchResult getFantasyMatchBySearchTerm(SearchTerm term, FantasyMatchType fantasyMatchType) {
        FantasyMatchSearchResult fantasyMatchSearchResult = fantasyMatchDao.getFantasyMatchBySearchTerm(term, fantasyMatchType);
        updateDependencies(fantasyMatchSearchResult.getResults());
        return fantasyMatchSearchResult;

    }

    public boolean fantasyCupHasMatches(int fantasyCupId) {
        return fantasyMatchDao.fantasyCupHasMatches(fantasyCupId);
    }

    public boolean fantasyLeagueHasMatches(int fantasyLeagueId) {
        return fantasyMatchDao.fantasyLeagueHasMatches(fantasyLeagueId);
    }

    public boolean fantasyCupGroupHasMatches(int fantasyCupGroupId) {
        return fantasyMatchDao.fantasyCupGroupHasMatches(fantasyCupGroupId);
    }

    public List<FantasyMatch> getLastRoundMatchesForCup(int fantasyCupId) {
        int maxRoundId = fantasyMatchDao.getMaxRoundIdForCupInMatch(fantasyCupId);
        if(maxRoundId < 1){
            return Collections.emptyList();
        }
        List<FantasyMatch> fantasyMatchList = getFantasyMatchByCupIdAndRoundId(fantasyCupId,maxRoundId);
        updateDependencies(fantasyMatchList);
        return fantasyMatchList;
    }

    public FantasyTeam determineCupMatchWinner(FantasyMatch fantasyMatch, int fantasyCupId) {
        FantasyTeam winner = fantasyMatch.getWinningTeam();
        if(winner == null){
            FantasyTeamRound homeTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyMatch.getHomeTeam().getTeamId(),fantasyMatch.getFantasyRound().getFantasyRoundId());
            FantasyTeamRound awayTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyMatch.getAwayTeam().getTeamId(),fantasyMatch.getFantasyRound().getFantasyRoundId());
            if(homeTeamRound.getPoints() > awayTeamRound.getPoints()){
                return fantasyMatch.getHomeTeam();
            }
            else if(homeTeamRound.getPoints() < awayTeamRound.getPoints()){
                return fantasyMatch.getAwayTeam();
            }
            else{
                List<Integer> matchIdList = fantasyService.getMatchIdsForRound(homeTeamRound.getFantasyRound().getFantasyRoundId());
                List<PlayerStats> homeTeamPlayerStats = getPlayerStatsForTeam(homeTeamRound.getPlayerList(), matchIdList);
                List<PlayerStats> awayTeamPlayerStats = getPlayerStatsForTeam(awayTeamRound.getPlayerList(), matchIdList);
                SumPlayerStats homeTeamSumPlayerStats = new SumPlayerStats();
                homeTeamSumPlayerStats.add(homeTeamPlayerStats);
                SumPlayerStats awayTeamSumPlayerStats = new SumPlayerStats();
                awayTeamSumPlayerStats.add(awayTeamPlayerStats);
                if(homeTeamSumPlayerStats.getGoals() > awayTeamSumPlayerStats.getGoals()){
                    return fantasyMatch.getHomeTeam();
                }
                if(homeTeamSumPlayerStats.getGoals() < awayTeamSumPlayerStats.getGoals()){
                    return fantasyMatch.getAwayTeam();
                }
                if(homeTeamSumPlayerStats.getAssists() > awayTeamSumPlayerStats.getAssists()){
                    return fantasyMatch.getHomeTeam();
                }
                if(homeTeamSumPlayerStats.getAssists() < awayTeamSumPlayerStats.getAssists()){
                    return fantasyMatch.getAwayTeam();
                }
                if(homeTeamSumPlayerStats.getSumRedCard() < awayTeamSumPlayerStats.getSumRedCard()){
                    return fantasyMatch.getHomeTeam();
                }
                if(homeTeamSumPlayerStats.getSumRedCard() > awayTeamSumPlayerStats.getSumRedCard()){
                    return fantasyMatch.getAwayTeam();
                }
                if(homeTeamSumPlayerStats.getYellowCard() < awayTeamSumPlayerStats.getYellowCard()){
                    return fantasyMatch.getHomeTeam();
                }
                if(homeTeamSumPlayerStats.getYellowCard() > awayTeamSumPlayerStats.getYellowCard()){
                    return fantasyMatch.getAwayTeam();
                }
                if(homeTeamSumPlayerStats.getPlayedMinutes() > awayTeamSumPlayerStats.getPlayedMinutes()){
                    return fantasyMatch.getHomeTeam();
                }
                if(homeTeamSumPlayerStats.getPlayedMinutes() < awayTeamSumPlayerStats.getPlayedMinutes()){
                    return fantasyMatch.getAwayTeam();
                }
                //Winner cannot be determined, hometeam wins
                return fantasyMatch.getHomeTeam();
            }
        }
        return winner;
    }

    public List<FantasyMatch> getFantasyMatches(int fantasySeasonId, boolean isPlayed) {
        List<FantasyRound> fantasyRounds = fantasyService.getFantasyRoundByFantasySeasonId(fantasySeasonId);
        String commaSeparatedStringFromRounds = CommaSeparator.createCommaSeparatedStringFromRounds(fantasyRounds);
        List<FantasyMatch> fantasyMatchList = fantasyMatchDao.getFantasyMatchByRoundsAndIsPlayed(commaSeparatedStringFromRounds,isPlayed);
        return fantasyMatchList;
    }

    @Override
    public List<FantasyMatch> getNextMatches(int fantasyTeamId, int fantasyRoundId) {
        ComparisonTerm roundTerm = new ComparisonTerm("fantasyroundid", Operator.EQ , fantasyRoundId);
        ComparisonTerm homeTeamTerm = new ComparisonTerm("hometeamid", Operator.EQ , fantasyTeamId);
        ComparisonTerm awayTeamTerm = new ComparisonTerm("awayteamid", Operator.EQ , fantasyTeamId);
        OrTerm orTerm = new OrTerm(homeTeamTerm,awayTeamTerm);
        AndTerm andTerm = new AndTerm(orTerm,roundTerm);
        List<FantasyMatch> nextMatchList = getFantasyMatchBySearchTerm(andTerm,FantasyMatchType.LEAGUE).getResults();
        nextMatchList.addAll(getFantasyMatchBySearchTerm(andTerm,FantasyMatchType.CUP).getResults());
        nextMatchList.addAll(getFantasyMatchBySearchTerm(andTerm,FantasyMatchType.CUPGROUP).getResults());
        return nextMatchList;

    }

    private List<PlayerStats> getPlayerStatsForTeam(List<Player> playerList, List<Integer> matchIdList) {
        List<PlayerStats> playerStatsList = new ArrayList<PlayerStats>();
        for(Player player : playerList){
            for(Integer matchId: matchIdList){
                PlayerStats playerStats = leagueService.getPlayerStatsByMatchAndPlayer(player.getPlayerId(),matchId);
                if(playerStats != null){
                    playerStatsList.add(playerStats);
                }
            }
        }
        return playerStatsList;
    }

    private void updateDependencies(List<FantasyMatch> fantasyMatchList){
        for(FantasyMatch fantasyMatch: fantasyMatchList){
            updateDependencies(fantasyMatch);
        }
    }

    private void updateDependencies(FantasyMatch fantasyMatch){
        FantasyTeam homeTeam = fantasyService.getFantasyTeamById(fantasyMatch.getHomeTeam().getTeamId());
        FantasyTeam awayTeam = fantasyService.getFantasyTeamById(fantasyMatch.getAwayTeam().getTeamId());
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyMatch.getFantasyRound().getFantasyRoundId());
        fantasyMatch.setHomeTeam(homeTeam);
        fantasyMatch.setAwayTeam(awayTeam);
        fantasyMatch.setFantasyRound(fantasyRound);
    }

    private void updateCompetitonDependencies(FantasyMatch fantasyMatch){
        if(fantasyMatch.getFantasyLeague() != null){
            FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyMatch.getFantasyLeague().getId());
            fantasyMatch.setFantasyLeague(fantasyLeague);
        }
        else if(fantasyMatch.getFantasyCup() != null){
            FantasyCup fantasyCup = fantasyCupService.getFantasyCupById(fantasyMatch.getFantasyCup().getId());
            fantasyMatch.setFantasyCup(fantasyCup);
        }else if(fantasyMatch.getFantasyCupGroup() != null){
            FantasyCupGroup fantasyCupGroup = fantasyCupGroupService.getFantasyCupGroupById(fantasyMatch.getFantasyCupGroup().getId());
            fantasyMatch.setFantasyCupGroup(fantasyCupGroup);
        }
    }


    private boolean isRoundUpdated(FantasyRound currentFantasyRound, FantasyRound lastUpdatedFantasyRound) {
        if(lastUpdatedFantasyRound == null) {
            return false;
        }
        return lastUpdatedFantasyRound.getRound() >= currentFantasyRound.getRound();
    }

}
