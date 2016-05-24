package org.tpl.business.service.fantasy;

import no.kantega.commons.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.MatchResult;
import org.tpl.business.model.comparator.FantasyCupGroupStandingComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreator;
import org.tpl.integration.dao.fantasy.FantasyCupGroupDao;
import org.tpl.integration.dao.fantasy.FantasyCupGroupStandingDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultFantasyCupGroupService implements FantasyCupGroupService {
    @Autowired
    FantasyCupGroupDao fantasyCupGroupDao;

    @Autowired
    FantasyCupGroupStandingDao fantasyCupGroupStandingDao;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyCupService fantasyCupService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    public void saveOrUpdateCupGroup(FantasyCupGroup fantasyCupGroup) {
        fantasyCupGroupDao.saveOrUpdateCupGroup(fantasyCupGroup);
    }

    public FantasyCupGroup getFantasyCupGroupById(int id) {
        FantasyCupGroup fantasyCupGroup = fantasyCupGroupDao.getFantasyCupGroupById(id);
        updateDependencies(fantasyCupGroup);
        return fantasyCupGroup;

    }

    public List<FantasyCupGroup> getAllFantasyCupGroups() {
        List<FantasyCupGroup> fantasyCupGroupList = fantasyCupGroupDao.getAllFantasyCupGroups();
        updateDependencies(fantasyCupGroupList);
        return fantasyCupGroupList;
    }

    public List<FantasyCupGroup> getAllFantasyCupGroups(int fantasyCupId) {
        List<FantasyCupGroup> fantasyCupGroupList = fantasyCupGroupDao.getAllFantasyCupGroups(fantasyCupId);
        updateDependencies(fantasyCupGroupList);
        return fantasyCupGroupList;
    }

    public List<FantasyCupGroup> getAllFantasyCupGroupsInSeason(int fantasySeasonId) {
        List<FantasyCup> fantasyCupList = fantasyCupService.getAllFantasyCups(fantasySeasonId);
        List<FantasyCupGroup> fantasyCupGroups = new ArrayList<FantasyCupGroup>();
        for(FantasyCup fantasyCup: fantasyCupList){
            fantasyCupGroups.addAll(fantasyCup.getFantasyCupGroupList());
        }
        return fantasyCupGroups;
    }

    public List<FantasyCupGroup> getAllFantasyCupGroupsForFantasyRound(int fantasyRoundId) {
        List<FantasyCupGroup> fantasyCupGroupList = fantasyCupGroupDao.getAllFantasyCupGroupsForFantasyRound(fantasyRoundId);
        updateDependencies(fantasyCupGroupList);
        return fantasyCupGroupList;

    }

    public void deleteFantasyCupGroups(int fantasyCupId) {
        List<FantasyCupGroup> fantasyCupGroups  = getAllFantasyCupGroups(fantasyCupId);
        for(FantasyCupGroup fantasyCupGroup: fantasyCupGroups){
            fantasyMatchService.deleteFantasyMatchForCupGroup(fantasyCupGroup.getId());
            fantasyCupGroupDao.deleteFantasyCupGroup(fantasyCupGroup.getId());
        }
    }

    public boolean hasFixtures(int fantasyCupGroupId) {
        boolean doesNotHasFixtures = fantasyMatchService.getFantasyMatchByCupGroupId(fantasyCupGroupId).isEmpty();
        return ! doesNotHasFixtures;
    }

    private  void updateDependencies(List<FantasyCupGroup> fantasyCupGroupList){
        for(FantasyCupGroup fantasyCupGroup : fantasyCupGroupList){
            updateDependencies(fantasyCupGroup);
        }
    }

    private void updateDependencies(FantasyCupGroup fantasyCupGroup){
        List<FantasyRound> fantasyRoundList = fantasyService.getFantasyRoundByFantasyCupGroupId(fantasyCupGroup.getId());
        fantasyCupGroup.setFantasyRoundList(fantasyRoundList);
        List<FantasyTeam> fantasyTeamList = fantasyService.getFantasyTeamByFantasyCupGroupId(fantasyCupGroup.getId());
        fantasyCupGroup.setFantasyTeamList(fantasyTeamList);
    }

    public void createFixtureList(int fantasyCupGroupId) throws FixtureListCreationException {
        FantasyCupGroup fantasyCupGroup = getFantasyCupGroupById(fantasyCupGroupId);

        List<FantasyTeam> fantasyTeamList = fantasyCupGroup.getFantasyTeamList();
        List<FantasyRound> fantasyRoundList = fantasyCupGroup.getFantasyRoundList();

        validateFantasyCupGroupBeforeFixtureListCreation(fantasyCupGroup, fantasyTeamList, fantasyRoundList);
        FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRoundList, fantasyTeamList,fantasyCupGroup.isHomeAndAwayMatches());
        List<List<FantasyMatch>> list = fixtureListCreator.createFixtureList();
        saveMatchList(fantasyCupGroup, list);
    }

    public void saveOrUpdateFantasyCupGroupStanding(FantasyCupGroupStanding fantasyCupGroupStanding) {
        fantasyCupGroupStandingDao.saveOrUpdateFantasyCupGroupStanding(fantasyCupGroupStanding);
    }

    public FantasyCupGroupStanding getFantasyCupGroupStandingByIds(int fantasyTeamId, int fantasyCupGroupId, int fantasyRoundId) {
        FantasyCupGroupStanding fantasyCupGroupStanding = fantasyCupGroupStandingDao.getFantasyCupGroupStandingByIds(fantasyTeamId,fantasyCupGroupId,fantasyRoundId);
        if(fantasyCupGroupStanding == null) return null;
        updateDependencies(fantasyCupGroupStanding);
        return fantasyCupGroupStanding;
    }

    public List<FantasyCupGroupStanding> getFantasyCupGroupStandingByRoundAndCupGroup(int fantasyCupGroupId, int fantasyRoundId) {
        List<FantasyCupGroupStanding> list = fantasyCupGroupStandingDao.getFantasyCupGroupStandingByRoundAndCupGroup(fantasyCupGroupId,fantasyRoundId);
        for(FantasyCupGroupStanding fantasyCupGroupStanding: list){
            updateDependencies(fantasyCupGroupStanding);
        }
        return list;
    }

    public List<FantasyCupGroupStanding> getFantasyCupGroupStandingByTeamAndCupGroup(int fantasyCupGroupId, int fantasyTeamId) {
        List<FantasyCupGroupStanding> list = fantasyCupGroupStandingDao.getFantasyCupGroupStandingByTeamAndCupGroup(fantasyCupGroupId, fantasyTeamId);
        for(FantasyCupGroupStanding fantasyCupGroupStanding: list){
            updateDependencies(fantasyCupGroupStanding);
        }
        return list;
    }

    public List<FantasyCupGroupStanding> getLastUpdatedFantasyCupGroupStandingsByCupGroup(int fantasyCupGroupId) {
        FantasyRound lastUpdatedFantasyRound = getLastUpdatedFantasyRound(fantasyCupGroupId);
        if(lastUpdatedFantasyRound == null){
            return Collections.emptyList();
        }
        List<FantasyCupGroupStanding> list =  getLastUpdatedFantasyCupGroupStandingsByCupGroup(fantasyCupGroupId, lastUpdatedFantasyRound.getFantasyRoundId());
        for(FantasyCupGroupStanding fantasyCupGroupStanding: list){
            updateDependencies(fantasyCupGroupStanding);
        }
        return list;
    }

    private FantasyCupGroupStanding getLastUpdatedFantasyCupGroupStandingsByLeagueTeamAndRound(int fantasyCupGroupId, int fantasyRoundId, int fantasyTeamTeamId) {
        FantasyCupGroupStanding fantasyCupGroupStanding = fantasyCupGroupStandingDao.getFantasyCupGroupStandingByIds(fantasyTeamTeamId, fantasyCupGroupId, fantasyRoundId);
        if(fantasyCupGroupStanding == null && fantasyRoundId > 0){
            return getLastUpdatedFantasyCupGroupStandingsByLeagueTeamAndRound(fantasyCupGroupId, fantasyRoundId - 1, fantasyTeamTeamId);
        }
        return fantasyCupGroupStanding;
    }

    public void updateCupGroupStandings(int fantasyRoundId) {
        FantasyRound currentRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        List<FantasyCupGroup> fantasyCupGroupList = getAllFantasyCupGroupsForFantasyRound(fantasyRoundId);
        for(FantasyCupGroup fantasyCupGroup: fantasyCupGroupList){
            FantasyRound previousRound = getPreviousUpdatedFantasyRound(fantasyCupGroup.getId(), currentRound);
            List<FantasyCupGroupStanding> fantasyCupGroupStandingList = new ArrayList<FantasyCupGroupStanding>();
            List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchByCupGroupIdAndRoundId(fantasyCupGroup.getId(), fantasyRoundId);
            for(FantasyMatch fantasyMatch: fantasyMatchList){
                FantasyTeam homeTeam = fantasyMatch.getHomeTeam();
                MatchResult homematchResult = fantasyMatch.getMatchResultForHomeTeam();

                FantasyTeam awayTeam = fantasyMatch.getAwayTeam();
                MatchResult awayMatchResult = fantasyMatch.getMatchResultForAwayTeam();

                if(previousRound != null){
                    FantasyCupGroupStanding homeTeamCupGroupStanding = getFantasyCupGroupStandingByIds(homeTeam.getTeamId(), fantasyCupGroup.getId(), previousRound.getFantasyRoundId());
                    FantasyCupGroupStanding awayTeamCupGroupStanding = getFantasyCupGroupStandingByIds(awayTeam.getTeamId(), fantasyCupGroup.getId(), previousRound.getFantasyRoundId());
                    if(homeTeamCupGroupStanding != null){
                        homeTeamCupGroupStanding.setLastRoundPosition(homeTeamCupGroupStanding.getPosition());
                        updateFantasyCupGroupStandingForHomeTeam(currentRound, fantasyMatch, homematchResult, homeTeamCupGroupStanding);
                        fantasyCupGroupStandingList.add(homeTeamCupGroupStanding);
                    }else{
                        homeTeamCupGroupStanding = createNewHomeTeamStanding(currentRound, fantasyCupGroup, fantasyMatch, homeTeam, homematchResult);
                        fantasyCupGroupStandingList.add(homeTeamCupGroupStanding);
                    }
                    if(awayTeamCupGroupStanding != null){
                        awayTeamCupGroupStanding.setLastRoundPosition(awayTeamCupGroupStanding.getPosition());
                        updateFantasyCupGroupStandingForAwayTeam(currentRound, fantasyMatch, awayMatchResult, awayTeamCupGroupStanding);
                        fantasyCupGroupStandingList.add(awayTeamCupGroupStanding);
                    }else{
                        awayTeamCupGroupStanding = createNewAwayTeamStanding(currentRound, fantasyCupGroup, fantasyMatch, awayTeam, awayMatchResult);
                        fantasyCupGroupStandingList.add(awayTeamCupGroupStanding);
                    }
                }else{
                    FantasyCupGroupStanding homeTeamCupGroupStanding = createNewHomeTeamStanding(currentRound, fantasyCupGroup, fantasyMatch, homeTeam, homematchResult);
                    fantasyCupGroupStandingList.add(homeTeamCupGroupStanding);
                    FantasyCupGroupStanding awayTeamCupGroupStanding = createNewAwayTeamStanding(currentRound, fantasyCupGroup, fantasyMatch, awayTeam, awayMatchResult);
                    fantasyCupGroupStandingList.add(awayTeamCupGroupStanding);
                }
            }
            if(fantasyCupGroup.getFantasyTeamList().size() != (fantasyMatchList.size() *2)){
                List<FantasyTeam> tempTeamList = new ArrayList<FantasyTeam>();
                tempTeamList.addAll(fantasyCupGroup.getFantasyTeamList());
                for(FantasyMatch fantasyMatch: fantasyMatchList){
                    tempTeamList.remove(fantasyMatch.getHomeTeam());
                    tempTeamList.remove(fantasyMatch.getAwayTeam());
                }
                for(FantasyTeam fantasyTeam: tempTeamList){
                    FantasyCupGroupStanding fantasyCupGroupStanding = getLastUpdatedFantasyCupGroupStandingsByLeagueTeamAndRound(fantasyCupGroup.getId(), fantasyRoundId, fantasyTeam.getTeamId());
                    if(fantasyCupGroupStanding == null){
                        fantasyCupGroupStanding = createTeamStandingWhenNoMatchIsPlayed(currentRound,fantasyCupGroup,fantasyTeam);
                    }else{
                        updateFantasyCupGroupStandingWhenNoMatchIsPlayed(currentRound, fantasyCupGroupStanding);
                    }
                    fantasyCupGroupStandingList.add(fantasyCupGroupStanding);
                }
            }
            Collections.sort(fantasyCupGroupStandingList, new FantasyCupGroupStandingComparator());
            for(int i = 1; i <= fantasyCupGroupStandingList.size(); i++){
                fantasyCupGroupStandingList.get(i-1).setPosition(i);
                saveOrUpdateFantasyCupGroupStanding(fantasyCupGroupStandingList.get(i - 1));
            }
        }
    }

    private FantasyRound getPreviousUpdatedFantasyRound(int fantasyCupGroupId, FantasyRound currentRound) {
        FantasyRound lastUpdatedFantasyRound = getLastUpdatedFantasyRound(fantasyCupGroupId);
        if(lastUpdatedFantasyRound != null && lastUpdatedFantasyRound.getFantasyRoundId() != currentRound.getFantasyRoundId()){
            return lastUpdatedFantasyRound;
        }
        FantasyRound fantasyRound = null;
        if(lastUpdatedFantasyRound != null){
            int previousRoundId = fantasyCupGroupDao.getPreviousRoundId(fantasyCupGroupId, lastUpdatedFantasyRound.getFantasyRoundId());
            if(previousRoundId > 0){
                fantasyRound = fantasyService.getFantasyRoundById(previousRoundId);
            }
        }

        return fantasyRound;
    }

    private void updateFantasyCupGroupStandingWhenNoMatchIsPlayed(FantasyRound currentRound, FantasyCupGroupStanding fantasyCupGroupStanding) {
        fantasyCupGroupStanding.setFantasyRound(currentRound);
        fantasyCupGroupStanding.setLastRoundPosition(fantasyCupGroupStanding.getPosition());
    }

    private void updateFantasyCupGroupStandingForHomeTeam(FantasyRound currentRound, FantasyMatch fantasyMatch, MatchResult matchResult, FantasyCupGroupStanding teamCupGroupStanding) {
        teamCupGroupStanding.setFantasyRound(currentRound);
        teamCupGroupStanding.addGoalsScored(fantasyMatch.getHomegoals());
        teamCupGroupStanding.addGoalsAgainst(fantasyMatch.getAwaygoals());
        updateMatchResult(matchResult, teamCupGroupStanding);
        teamCupGroupStanding.addPoints(matchResult.getPoints());
    }

    private void updateFantasyCupGroupStandingForAwayTeam(FantasyRound currentRound, FantasyMatch fantasyMatch, MatchResult matchResult, FantasyCupGroupStanding teamCupGroupStanding) {
        teamCupGroupStanding.setFantasyRound(currentRound);
        teamCupGroupStanding.addGoalsScored(fantasyMatch.getAwaygoals());
        teamCupGroupStanding.addGoalsAgainst(fantasyMatch.getHomegoals());
        updateMatchResult(matchResult, teamCupGroupStanding);
        teamCupGroupStanding.addPoints(matchResult.getPoints());
    }

    private void updateMatchResult(MatchResult matchResult, FantasyCupGroupStanding teamCupGroupStanding) {
        if(matchResult.equals(MatchResult.VICTORY)){
            teamCupGroupStanding.increaseMatchesWon();
        }else if(matchResult.equals(MatchResult.DRAW)){
            teamCupGroupStanding.increaseMatchesDraw();
        }else {
            teamCupGroupStanding.increaseMatchesLost();
        }
    }

    private FantasyCupGroupStanding createNewHomeTeamStanding(FantasyRound currentRound, FantasyCupGroup fantasyCupGroup, FantasyMatch fantasyMatch, FantasyTeam fantasyTeam, MatchResult matchResult) {
        FantasyCupGroupStanding teamCupGroupStanding = new FantasyCupGroupStanding();
        teamCupGroupStanding.setLastRoundPosition(1);
        teamCupGroupStanding.setFantasyTeam(fantasyTeam);
        teamCupGroupStanding.setFantasyCupGroup(fantasyCupGroup);
        updateFantasyCupGroupStandingForHomeTeam(currentRound,fantasyMatch,matchResult,teamCupGroupStanding);
        return teamCupGroupStanding;
    }

    private FantasyCupGroupStanding createNewAwayTeamStanding(FantasyRound currentRound, FantasyCupGroup fantasyCupGroup, FantasyMatch fantasyMatch, FantasyTeam fantasyTeam, MatchResult matchResult) {
        FantasyCupGroupStanding teamCupGroupStanding = new FantasyCupGroupStanding();
        teamCupGroupStanding.setLastRoundPosition(1);
        teamCupGroupStanding.setFantasyTeam(fantasyTeam);
        teamCupGroupStanding.setFantasyCupGroup(fantasyCupGroup);
        updateFantasyCupGroupStandingForAwayTeam(currentRound,fantasyMatch,matchResult,teamCupGroupStanding);
        return teamCupGroupStanding;
    }

    private FantasyCupGroupStanding createTeamStandingWhenNoMatchIsPlayed(FantasyRound currentRound, FantasyCupGroup fantasyCupGroup, FantasyTeam fantasyTeam) {
        FantasyCupGroupStanding teamCupGroupStanding = new FantasyCupGroupStanding();
        teamCupGroupStanding.setLastRoundPosition(1);
        teamCupGroupStanding.setFantasyTeam(fantasyTeam);
        teamCupGroupStanding.setFantasyCupGroup(fantasyCupGroup);
        teamCupGroupStanding.setFantasyRound(currentRound);
        return teamCupGroupStanding;
    }

    public FantasyRound getLastUpdatedFantasyRound(int fantasyCupGroupId) {
        int roundId =  fantasyCupGroupStandingDao.getLastUpdatedRoundIdForCupGroup(fantasyCupGroupId);
        return fantasyService.getFantasyRoundById(roundId);
    }

    public boolean isFantasyCupGroupsFinished(List<FantasyCupGroup> fantasyCupGroups){
        boolean finished = true;
        for(FantasyCupGroup fantasyCupGroup: fantasyCupGroups){
            int highestPossibleRoundId = fantasyCupGroup.getHighestFantasyRoundId();
            int highestPlayedRoundId = fantasyCupGroupStandingDao.getHighestRoundForGroupId(fantasyCupGroup.getId());
            if(highestPlayedRoundId != highestPossibleRoundId){
                return false;
            }
        }
        return finished;
    }

    private List<FantasyCupGroupStanding> getLastUpdatedFantasyCupGroupStandingsByCupGroup(int fantasyCupGroupId, int fantasyRoundId) {
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        List<FantasyCupGroupStanding> list = fantasyCupGroupStandingDao.getFantasyCupGroupStandingByRoundAndCupGroup(fantasyCupGroupId,fantasyRound.getFantasyRoundId());
        if(list.size() < 1 && fantasyRound.getRound() != 1){
            return getLastUpdatedFantasyCupGroupStandingsByCupGroup(fantasyCupGroupId, fantasyRoundId-1);
        }

        return list;
    }

    private void updateDependencies(FantasyCupGroupStanding fantasyCupGroupStanding) {
        FantasyCupGroup fantasyCupGroup = getFantasyCupGroupById(fantasyCupGroupStanding.getFantasyCupGroup().getId());
        fantasyCupGroupStanding.setFantasyCupGroup(fantasyCupGroup);
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(fantasyCupGroupStanding.getFantasyTeam().getTeamId());
        fantasyCupGroupStanding.setFantasyTeam(fantasyTeam);
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyCupGroupStanding.getFantasyRound().getFantasyRoundId());
        fantasyCupGroupStanding.setFantasyRound(fantasyRound);
    }

    private void validateFantasyCupGroupBeforeFixtureListCreation(FantasyCupGroup fantasyCupGroup, List<FantasyTeam> fantasyTeamList, List<FantasyRound> fantasyRoundList) throws FixtureListCreationException {
        if(fantasyTeamList.size() < 3 ){
            throw new FixtureListCreationException("Number of teams must be above or equals 3");
        }
        int numberOfTeams = fantasyTeamList.size();
        if(fantasyTeamList.size() % 2 != 0){
            numberOfTeams = fantasyTeamList.size() +1;
        }
        if(fantasyCupGroup.isHomeAndAwayMatches() && fantasyRoundList.size() != (2*numberOfTeams-2)){
            throw new FixtureListCreationException("Number of rounds must be: " + (2*numberOfTeams-2)+ ". Add or remove rounds" );
        }
        if( ! fantasyCupGroup.isHomeAndAwayMatches() && fantasyRoundList.size() != (numberOfTeams-1)){
            throw new FixtureListCreationException("Number of rounds must be: " + (numberOfTeams-1)+ ". Add or remove rounds" );
        }
    }

    private void saveMatchList(FantasyCupGroup fantasyCupGroup, List<List<FantasyMatch>> list) {
        for(List<FantasyMatch> fantasyMatchList: list){
            for(FantasyMatch fantasyMatch: fantasyMatchList){
                fantasyMatch.setFantasyCupGroup(fantasyCupGroup);
                fantasyMatchService.saveOrUpdateFantasyMatch(fantasyMatch);
            }
        }
    }
}
