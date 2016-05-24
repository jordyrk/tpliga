package org.tpl.business.service.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.MatchResult;
import org.tpl.business.model.comparator.FantasyLeagueStandingComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreator;
import org.tpl.integration.dao.fantasy.FantasyLeagueDao;
import org.tpl.integration.dao.fantasy.FantasyLeagueStandingDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultFantasyLeagueService implements FantasyLeagueService {

    @Autowired
    FantasyLeagueDao fantasyLeagueDao;

    @Autowired
    FantasyLeagueStandingDao fantasyLeagueStandingDao;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    public void saveOrUpdateLeague(FantasyLeague fantasyLeague) {
        fantasyLeagueDao.saveOrUpdateLeague(fantasyLeague);
    }

    public FantasyLeague getFantasyLeagueById(int id) {
        FantasyLeague fantasyLeague = fantasyLeagueDao.getFantasyLeagueById(id);
        updateDependencies(fantasyLeague);
        return fantasyLeague;

    }

    public List<FantasyLeague> getAllFantasyLeagues() {
        List<FantasyLeague> fantasyLeagueList =  fantasyLeagueDao.getAllFantasyLeagues();
        updateDependencies(fantasyLeagueList);
        return fantasyLeagueList;
    }

    public List<FantasyLeague> getAllFantasyLeagues(int fantasySeasonId) {
        List<FantasyLeague> fantasyLeagueList =  fantasyLeagueDao.getAllFantasyLeagues(fantasySeasonId);
        updateDependencies(fantasyLeagueList);
        return fantasyLeagueList;
    }

    public void createFixtureList(int fantasyLeagueId) throws FixtureListCreationException {
        FantasyLeague fantasyLeague = getFantasyLeagueById(fantasyLeagueId);
        List<FantasyTeam> fantasyTeamList = fantasyLeague.getFantasyTeamList();
        List<FantasyRound> fantasyRoundList = fantasyLeague.getFantasyRoundList();

        validateFantasyLeagueBeforeFixtureListCreation(fantasyLeague, fantasyTeamList, fantasyRoundList);
        FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRoundList, fantasyTeamList,fantasyLeague.isHomeAndAwayMatches());
        List<List<FantasyMatch>> list = fixtureListCreator.createFixtureList();
        saveMatchList(fantasyLeague, list);
    }

    public boolean hasFixtures(int fantasyLeagueId) {
        boolean doesNotHasFixtures = fantasyMatchService.getFantasyMatchByLeagueId(fantasyLeagueId).isEmpty();
        return ! doesNotHasFixtures;
    }

    public void saveOrUpdateFantasyLeagueStanding(FantasyLeagueStanding fantasyLeagueStanding) {
        fantasyLeagueStandingDao.saveOrUpdateFantasyLeagueStanding(fantasyLeagueStanding);
    }

    public FantasyLeagueStanding getFantasyLeagueStandingByIds(int fantasyTeamId, int fantasyLeagueId, int fantasyRoundId) {
        FantasyLeagueStanding fantasyLeagueStanding = fantasyLeagueStandingDao.getFantasyLeagueStandingByIds(fantasyTeamId,fantasyLeagueId,fantasyRoundId);
        if(fantasyLeagueStanding == null) return null;
        updateDependencies(fantasyLeagueStanding);
        return fantasyLeagueStanding;
    }

    public List<FantasyLeagueStanding> getFantasyLeagueStandingByRoundAndLeague(int fantasyLeagueId, int fantasyRoundId) {
        List<FantasyLeagueStanding> list = fantasyLeagueStandingDao.getFantasyLeagueStandingByRoundAndLeague(fantasyLeagueId,fantasyRoundId);
        for(FantasyLeagueStanding fantasyLeagueStanding: list){
            updateDependencies(fantasyLeagueStanding);
        }
        return list;
    }

    public List<FantasyLeagueStanding> getFantasyLeagueStandingByTeamAndLeague(int fantasyLeagueId, int fantasyTeamId) {
        List<FantasyLeagueStanding> list = fantasyLeagueStandingDao.getFantasyLeagueStandingByTeamAndLeague(fantasyLeagueId, fantasyTeamId);
        for(FantasyLeagueStanding fantasyLeagueStanding: list){
            updateDependencies(fantasyLeagueStanding);
        }
        return list;
    }

    public List<FantasyLeagueStanding> getLastUpdatedFantasyLeagueStandingsByLeague(int fantasyLeagueId) {
        FantasyLeague fantasyLeague = this.getFantasyLeagueById(fantasyLeagueId);
        FantasyRound fantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyLeague.getFantasySeason().getFantasySeasonId());
        List<FantasyLeagueStanding> list =  getLastUpdatedFantasyLeagueStandingsByLeague(fantasyLeagueId,fantasyRound.getFantasyRoundId());
        for(FantasyLeagueStanding fantasyLeagueStanding: list){
            updateDependencies(fantasyLeagueStanding);
        }
        return list;
    }

    public List<FantasyLeagueStanding> getAccumulatedFantasyLeagueStandingsByLeague(int fantasyLeagueId, int numberOfRounds) {
        FantasyRound fantasyRound = getLastUpdatedFantasyRound(fantasyLeagueId);
        FantasyLeague fantasyLeague = getFantasyLeagueById(fantasyLeagueId);
        List<FantasyLeagueStanding> fantasyLeagueStandings = getFantasyLeagueStandingByRoundAndLeague(fantasyLeagueId, fantasyRound.getFantasyRoundId());
        FantasyRound fantasyRoundBefore = getPreviousRound(fantasyRound,fantasyLeague,numberOfRounds);
        if(fantasyRoundBefore == null || fantasyRoundBefore.getFantasySeason().getFantasySeasonId() != fantasyRound.getFantasySeason().getFantasySeasonId()){
            return fantasyLeagueStandings;
        }
        else{

            for(FantasyLeagueStanding fantasyLeagueStanding: fantasyLeagueStandings){
                FantasyLeagueStanding prev = fantasyLeagueStandingDao.getFantasyLeagueStandingByIds(fantasyLeagueStanding.getFantasyTeam().getTeamId(), fantasyLeagueId, fantasyRoundBefore.getFantasyRoundId());
                fantasyLeagueStanding.subtractFantasyLeagueStanding(prev);
            }
        }

        Collections.sort(fantasyLeagueStandings,new FantasyLeagueStandingComparator());
        int position = 1;
        for(FantasyLeagueStanding fantasyLeagueStanding: fantasyLeagueStandings){
            fantasyLeagueStanding.setPosition(position);
            position++;
            fantasyLeagueStanding.setFantasyLeague(fantasyLeague);
            fantasyLeagueStanding.setFantasyTeam(fantasyService.getFantasyTeamById(fantasyLeagueStanding.getFantasyTeam().getTeamId()));
        }

        return fantasyLeagueStandings;

    }

    private FantasyRound getPreviousRound(FantasyRound currentRound,FantasyLeague fantasyLeague, int numberOfRounds) {
        FantasyRound fantasyRound = null;
        try{
            int index = fantasyLeague.getFantasyRoundList().indexOf(currentRound)-numberOfRounds;
            fantasyRound = fantasyLeague.getFantasyRoundList().get(index);
        }catch (IndexOutOfBoundsException e){
            //Do nothing return null
        }
        return fantasyRound;
    }

    private List<FantasyLeagueStanding> getLastUpdatedFantasyLeagueStandingsByLeague(int fantasyLeagueId, int fantasyRoundId) {

        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        List<FantasyLeagueStanding> list = fantasyLeagueStandingDao.getFantasyLeagueStandingByRoundAndLeague(fantasyLeagueId,fantasyRound.getFantasyRoundId());
        if(list.size() < 1 && fantasyRound.getRound() != 1){
            return getLastUpdatedFantasyLeagueStandingsByLeague(fantasyLeagueId, fantasyRoundId-1);
        }

        return list;
    }

    private FantasyLeagueStanding getLastUpdatedFantasyLeagueStandingsByLeagueTeamAndRound(int fantasyLeagueId, int fantasyRoundId, int fantasyTeamTeamId) {
        FantasyLeagueStanding fantasyLeagueStanding = fantasyLeagueStandingDao.getFantasyLeagueStandingByIds(fantasyTeamTeamId, fantasyLeagueId, fantasyRoundId);
        if(fantasyLeagueStanding == null && fantasyRoundId > 0){
            return getLastUpdatedFantasyLeagueStandingsByLeagueTeamAndRound(fantasyLeagueId, fantasyRoundId - 1, fantasyTeamTeamId);
        }
        return fantasyLeagueStanding;
    }

    public FantasyRound getLastUpdatedFantasyRound(int fantasyLeagueId) {
        int roundId =  fantasyLeagueStandingDao.getLastUpdatedRoundIdForLeague(fantasyLeagueId);
        return fantasyService.getFantasyRoundById(roundId);
    }

    public List<FantasyLeague> getFantasyLeaguesForFantasyTeamInCurrentSeason(int fantasyTeamId) {
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        List<FantasyLeague> fantasyLeagueList = fantasyLeagueDao.getFantasyLeaguesForFantasyTeam(fantasyTeamId, fantasySeason.getFantasySeasonId());
        updateDependencies(fantasyLeagueList);
        return fantasyLeagueList;
    }

    public List<FantasyLeague> getFantasyLeaguesForFantasyTeam(int fantasyTeamId) {
        return fantasyLeagueDao.getFantasyLeaguesForFantasyTeam(fantasyTeamId);
    }

    public void deleteFantasyLeague(int fantasyLeagueId) {
        fantasyMatchService.deleteFantasyMatchForLeague(fantasyLeagueId);
        fantasyLeagueDao.deleteFantasyLeague(fantasyLeagueId);
    }

    public void updateLeagueStandings(int fantasyRoundId){
        FantasyRound currentRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        List<FantasyLeague> fantasyLeagueList = getAllFantasyLeaguesForFantasyRound(fantasyRoundId);
        for(FantasyLeague fantasyLeague: fantasyLeagueList){
            FantasyRound previousRound = getPreviousRound(currentRound,fantasyLeague,1);
            List<FantasyLeagueStanding> fantasyLeagueStandingList = new ArrayList<FantasyLeagueStanding>();
            List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchByLeagueIdAndRoundId(fantasyLeague.getId(),fantasyRoundId);
            for(FantasyMatch fantasyMatch: fantasyMatchList){
                FantasyTeam homeTeam = fantasyMatch.getHomeTeam();
                MatchResult homematchResult = fantasyMatch.getMatchResultForHomeTeam();

                FantasyTeam awayTeam = fantasyMatch.getAwayTeam();
                MatchResult awayMatchResult = fantasyMatch.getMatchResultForAwayTeam();


                if(previousRound != null){
                    FantasyLeagueStanding homeTeamLeagueStanding = getFantasyLeagueStandingByIds(homeTeam.getTeamId(),fantasyLeague.getId(),previousRound.getFantasyRoundId());
                    FantasyLeagueStanding awayTeamLeagueStanding = getFantasyLeagueStandingByIds(awayTeam.getTeamId(),fantasyLeague.getId(),previousRound.getFantasyRoundId());
                    if(homeTeamLeagueStanding != null){
                        homeTeamLeagueStanding.setLastRoundPosition(homeTeamLeagueStanding.getPosition());
                        updateFantasyLeagueStandingForHomeTeam(currentRound, fantasyMatch, homematchResult, homeTeamLeagueStanding);
                        fantasyLeagueStandingList.add(homeTeamLeagueStanding);
                    }else{
                        homeTeamLeagueStanding = createNewHomeTeamStanding(currentRound, fantasyLeague, fantasyMatch, homeTeam, homematchResult);
                        fantasyLeagueStandingList.add(homeTeamLeagueStanding);
                    }
                    if(awayTeamLeagueStanding != null){
                        awayTeamLeagueStanding.setLastRoundPosition(awayTeamLeagueStanding.getPosition());
                        updateFantasyLeagueStandingForAwayTeam(currentRound,fantasyMatch,awayMatchResult,awayTeamLeagueStanding);
                        fantasyLeagueStandingList.add(awayTeamLeagueStanding);
                    }else{
                        awayTeamLeagueStanding = createNewAwayTeamStanding(currentRound, fantasyLeague, fantasyMatch, awayTeam, awayMatchResult);
                        fantasyLeagueStandingList.add(awayTeamLeagueStanding);
                    }
                }else{
                    FantasyLeagueStanding homeTeamLeagueStanding = createNewHomeTeamStanding(currentRound, fantasyLeague, fantasyMatch, homeTeam, homematchResult);
                    fantasyLeagueStandingList.add(homeTeamLeagueStanding);
                    FantasyLeagueStanding awayTeamLeagueStanding = createNewAwayTeamStanding(currentRound, fantasyLeague, fantasyMatch, awayTeam, awayMatchResult);
                    fantasyLeagueStandingList.add(awayTeamLeagueStanding);
                }
            }
            if(fantasyLeague.getFantasyTeamList().size() != (fantasyMatchList.size() *2)){
                List<FantasyTeam> tempTeamList = new ArrayList<FantasyTeam>();
                tempTeamList.addAll(fantasyLeague.getFantasyTeamList());
                for(FantasyMatch fantasyMatch: fantasyMatchList){
                    tempTeamList.remove(fantasyMatch.getHomeTeam());
                    tempTeamList.remove(fantasyMatch.getAwayTeam());
                }
                for(FantasyTeam fantasyTeam: tempTeamList){
                    FantasyLeagueStanding fantasyLeagueStandings = getLastUpdatedFantasyLeagueStandingsByLeagueTeamAndRound(fantasyLeague.getId(), fantasyRoundId, fantasyTeam.getTeamId());
                    if(fantasyLeagueStandings == null){
                        fantasyLeagueStandings = createTeamStandingWhenNoMatchIsPlayed(currentRound,fantasyLeague,fantasyTeam);
                    }else{
                        updateFantasyLeagueStandingWhenNoMatchIsPlayed(currentRound, fantasyLeagueStandings);
                    }
                    fantasyLeagueStandingList.add(fantasyLeagueStandings);
                }
            }
            Collections.sort(fantasyLeagueStandingList, new FantasyLeagueStandingComparator());
            for(int i = 1; i <= fantasyLeagueStandingList.size(); i++){
                fantasyLeagueStandingList.get(i-1).setPosition(i);
                saveOrUpdateFantasyLeagueStanding(fantasyLeagueStandingList.get(i-1));
            }
        }
    }

    protected List<FantasyLeague> getAllFantasyLeaguesForFantasyRound(int fantasyRoundId) {
        List<FantasyLeague> fantasyLeagueList = fantasyLeagueDao.getAllFantasyLeaguesForFantasyRound(fantasyRoundId);
        updateDependencies(fantasyLeagueList);
        return fantasyLeagueList;
    }

    private void updateFantasyLeagueStandingWhenNoMatchIsPlayed(FantasyRound currentRound, FantasyLeagueStanding teamLeagueStanding) {
        teamLeagueStanding.setFantasyRound(currentRound);
        teamLeagueStanding.setLastRoundPosition(teamLeagueStanding.getPosition());
    }

    private void updateFantasyLeagueStandingForHomeTeam(FantasyRound currentRound, FantasyMatch fantasyMatch, MatchResult matchResult, FantasyLeagueStanding teamLeagueStanding) {
        teamLeagueStanding.setFantasyRound(currentRound);
        teamLeagueStanding.addGoalsScored(fantasyMatch.getHomegoals());
        teamLeagueStanding.addGoalsAgainst(fantasyMatch.getAwaygoals());
        updateMatchResult(matchResult, teamLeagueStanding);
        teamLeagueStanding.addPoints(matchResult.getPoints());
    }

    private void updateFantasyLeagueStandingForAwayTeam(FantasyRound currentRound, FantasyMatch fantasyMatch, MatchResult matchResult, FantasyLeagueStanding teamLeagueStanding) {
        teamLeagueStanding.setFantasyRound(currentRound);
        teamLeagueStanding.addGoalsScored(fantasyMatch.getAwaygoals());
        teamLeagueStanding.addGoalsAgainst(fantasyMatch.getHomegoals());
        updateMatchResult(matchResult, teamLeagueStanding);
        teamLeagueStanding.addPoints(matchResult.getPoints());
    }

    private void updateMatchResult(MatchResult matchResult, FantasyLeagueStanding teamLeagueStanding) {
        if(matchResult.equals(MatchResult.VICTORY)){
            teamLeagueStanding.increaseMatchesWon();
        }else if(matchResult.equals(MatchResult.DRAW)){
            teamLeagueStanding.increaseMatchesDraw();
        }else {
            teamLeagueStanding.increaseMatchesLost();
        }
    }

    private FantasyLeagueStanding createNewHomeTeamStanding(FantasyRound currentRound, FantasyLeague fantasyLeague, FantasyMatch fantasyMatch, FantasyTeam fantasyTeam, MatchResult matchResult) {
        FantasyLeagueStanding teamLeagueStanding = new FantasyLeagueStanding();
        teamLeagueStanding.setLastRoundPosition(1);
        teamLeagueStanding.setFantasyTeam(fantasyTeam);
        teamLeagueStanding.setFantasyLeague(fantasyLeague);
        updateFantasyLeagueStandingForHomeTeam(currentRound,fantasyMatch,matchResult,teamLeagueStanding);
        return teamLeagueStanding;
    }

    private FantasyLeagueStanding createNewAwayTeamStanding(FantasyRound currentRound, FantasyLeague fantasyLeague, FantasyMatch fantasyMatch, FantasyTeam fantasyTeam, MatchResult matchResult) {
        FantasyLeagueStanding teamLeagueStanding = new FantasyLeagueStanding();
        teamLeagueStanding.setLastRoundPosition(1);
        teamLeagueStanding.setFantasyTeam(fantasyTeam);
        teamLeagueStanding.setFantasyLeague(fantasyLeague);
        updateFantasyLeagueStandingForAwayTeam(currentRound,fantasyMatch,matchResult,teamLeagueStanding);
        return teamLeagueStanding;
    }

    private FantasyLeagueStanding createTeamStandingWhenNoMatchIsPlayed(FantasyRound currentRound, FantasyLeague fantasyLeague, FantasyTeam fantasyTeam) {
        FantasyLeagueStanding teamLeagueStanding = new FantasyLeagueStanding();
        teamLeagueStanding.setLastRoundPosition(1);
        teamLeagueStanding.setFantasyTeam(fantasyTeam);
        teamLeagueStanding.setFantasyLeague(fantasyLeague);
        teamLeagueStanding.setFantasyRound(currentRound);
        return teamLeagueStanding;
    }

    private void saveMatchList(FantasyLeague fantasyLeague, List<List<FantasyMatch>> list) {
        for(List<FantasyMatch> fantasyMatchList: list){
            for(FantasyMatch fantasyMatch: fantasyMatchList){
                fantasyMatch.setFantasyLeague(fantasyLeague);
                fantasyMatchService.saveOrUpdateFantasyMatch(fantasyMatch);
            }
        }
    }

    private void validateFantasyLeagueBeforeFixtureListCreation(FantasyLeague fantasyLeague, List<FantasyTeam> fantasyTeamList, List<FantasyRound> fantasyRoundList) throws FixtureListCreationException {
        if(fantasyTeamList.size() < 3 ){
            throw new FixtureListCreationException("Number of teams must be above 3");
        }
        int numberOfTeams = fantasyTeamList.size();
        if(fantasyTeamList.size() % 2 != 0){
            numberOfTeams = fantasyTeamList.size() +1;
        }
        if(fantasyLeague.isHomeAndAwayMatches() && fantasyRoundList.size() != (2*numberOfTeams-2)){
            throw new FixtureListCreationException("Number of rounds must be: " + (2*numberOfTeams-2)+ ". Add or remove rounds" );
        }
        if( ! fantasyLeague.isHomeAndAwayMatches() && fantasyRoundList.size() != (numberOfTeams-1)){
            throw new FixtureListCreationException("Number of rounds must be: " + (numberOfTeams-1)+ ". Add or remove rounds" );
        }
    }

    private  void updateDependencies(List<FantasyLeague> fantasyLeagueList){
        for(FantasyLeague fantasyLeague : fantasyLeagueList){
            updateDependencies(fantasyLeague);
        }
    }

    private void updateDependencies(FantasyLeagueStanding fantasyLeagueStanding){
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(fantasyLeagueStanding.getFantasyTeam().getTeamId());
        fantasyLeagueStanding.setFantasyTeam(fantasyTeam);
        FantasyLeague fantasyLeague = this.getFantasyLeagueById(fantasyLeagueStanding.getFantasyLeague().getId());
        fantasyLeagueStanding.setFantasyLeague(fantasyLeague);
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyLeagueStanding.getFantasyRound().getFantasyRoundId());
        fantasyLeagueStanding.setFantasyRound(fantasyRound);

    }

    private void updateDependencies(FantasyLeague fantasyLeague){
        List<FantasyRound> fantasyRoundList = fantasyService.getFantasyRoundByFantasyLeagueId(fantasyLeague.getId());
        fantasyLeague.setFantasyRoundList(fantasyRoundList);
        List<FantasyTeam> fantasyTeamList = fantasyService.getFantasyTeamByFantasyLeagueId(fantasyLeague.getId());
        fantasyLeague.setFantasyTeamList(fantasyTeamList);

    }
}
