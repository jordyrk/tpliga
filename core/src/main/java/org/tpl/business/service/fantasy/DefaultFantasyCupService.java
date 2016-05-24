package org.tpl.business.service.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.comparator.FantasyMatchByIdComparator;
import org.tpl.business.model.comparator.FantasyRoundComparator;
import org.tpl.business.model.cupalgorithm.CupAlgorithm;
import org.tpl.business.model.cupalgorithm.TopBottomCupAlgorithm;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreator;
import org.tpl.integration.dao.fantasy.FantasyCupDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultFantasyCupService implements FantasyCupService {
    @Autowired
    FantasyCupDao fantasyCupDao;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyCupGroupService fantasyCupGroupService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    public void saveOrUpdateCup(FantasyCup fantasyCup) {

        boolean isNew = fantasyCup.isNew();
        fantasyCupDao.saveOrUpdateCup(fantasyCup);
        if(isNew){
            for(int i = 0; i < fantasyCup.getNumberOfGroups(); i++){
                FantasyCupGroup fantasyCupGroup = new FantasyCupGroup();
                fantasyCupGroup.setFantasyCup(fantasyCup);
                fantasyCupGroup.setName("Group " + (i +1));
                fantasyCupGroupService.saveOrUpdateCupGroup(fantasyCupGroup);
            }
        }
    }

    public FantasyCup getFantasyCupById(int id) {
        FantasyCup fantasyCup = fantasyCupDao.getFantasyCupById(id);
        updateDependencies(fantasyCup);
        return fantasyCup;
    }

    public List<FantasyCup> getAllFantasyCups() {
        List<FantasyCup> fantasyCupList = fantasyCupDao.getAllFantasyCups();
        updateDependencies(fantasyCupList);
        return fantasyCupList;
    }

    public List<FantasyCup> getAllFantasyCups(int fantasySeasonId) {
        List<FantasyCup> fantasyCupList = fantasyCupDao.getAllFantasyCups(fantasySeasonId);
        updateDependencies(fantasyCupList);
        return fantasyCupList;
    }

    public List<FantasyCup> getFantasyCupByTeamId(int teamId) {
        return fantasyCupDao.getFantasyCupByTeamId(teamId);
    }

    public List<FantasyCup> getFantasyCupForFantasyTeamInCurrentSeason(int teamId) {
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        List<FantasyCup> fantasyCups= fantasyCupDao.getFantasyCupByTeamIdAndSeasonId(fantasySeason.getFantasySeasonId(),teamId);
        updateDependencies(fantasyCups);
        return fantasyCups;
    }

    public void deleteFantasyCup(Integer fantasyCupId) {
        fantasyMatchService.deleteFantasyMatchForCup(fantasyCupId);
        fantasyCupDao.deleteCup(fantasyCupId);
        fantasyCupGroupService.deleteFantasyCupGroups(fantasyCupId);
    }

    public boolean hasFixtures(int fantasyCupId) {
        boolean doesNotHasFixtures = fantasyMatchService.getFantasyMatchByCupId(fantasyCupId).isEmpty();
        return ! doesNotHasFixtures;
    }

    public boolean hasFixtures(int fantasyCupId, int fantasyRoundId) {
        boolean doesNotHasFixtures = fantasyMatchService.getFantasyMatchByCupIdAndRoundId(fantasyCupId, fantasyRoundId).isEmpty();
        return ! doesNotHasFixtures;
    }

    public List<FantasyCupRound> getFantasyCupRounds(int fantasyCupId) {
        FantasyCup fantasyCup = getFantasyCupById(fantasyCupId);
        List<FantasyCupRound> fantasyCupRounds = new ArrayList<FantasyCupRound>();
        List<FantasyCupGroup> fantasyCupGroups = fantasyCupGroupService.getAllFantasyCupGroups(fantasyCupId);
        for(FantasyCupGroup fantasyCupGroup: fantasyCupGroups){
            for(FantasyRound fantasyRound: fantasyCupGroup.getFantasyRoundList()){
                fantasyCup.getFantasyRoundList().remove(fantasyRound);
            }
        }

        for(int i = 0 ; i < fantasyCup.getFantasyRoundList().size(); i ++){
            FantasyCupMatchType fantasyCupMatchType = FantasyCupMatchType.getFantasyCupMatchType(i,fantasyCup.getFantasyRoundList().size());
            FantasyCupMatchType startingFantasyCupMatchType = FantasyCupMatchType.getStarting(fantasyCup.getFantasyRoundList().size());
            FantasyRound fantasyRound = fantasyCup.getFantasyRoundList().get(i);
            FantasyCupRound fantasyCupRound = new FantasyCupRound();
            fantasyCupRound.setFantasyCupMatchType(fantasyCupMatchType);
            fantasyCupRound.setFantasyCup(fantasyCup);
            fantasyCupRound.setFantasyRound(fantasyRound);
            if(hasFixtures(fantasyCupId, fantasyRound.getFantasyRoundId())){
                fantasyCupRound.setFantasyMatchList(fantasyMatchService.getFantasyMatchByCupIdAndRoundId(fantasyCupId, fantasyRound.getFantasyRoundId()),fantasyCupMatchType);
            }else{

                if(fantasyCup.getFantasyCupGroupList().size() > 0 && fantasyCupMatchType == startingFantasyCupMatchType){
                    CupAlgorithm cupAlgorithm = new TopBottomCupAlgorithm(fantasyCup,fantasyCup.getFantasyCupGroupList(),null);
                    List<FantasyCupMatchAlias> firstRoundAliases = null;
                    try {
                        firstRoundAliases = cupAlgorithm.createFirstRoundAliases();
                    } catch (FixtureListCreationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    if(firstRoundAliases != null){
                         for(int j = 0; j < firstRoundAliases.size(); j++){
                            firstRoundAliases.get(j).setAlias(fantasyCupMatchType.getMatchPrefix() + (j +1));

                         }
                    }
                    fantasyCupRound.setFantasyMatchAliasList(firstRoundAliases);
                }
                else{
                    List<FantasyCupMatchAlias> aliasList = new ArrayList<FantasyCupMatchAlias>();
                    int previousMatchCounter = 1;
                    for(int j = 0; j < fantasyCupMatchType.getNumberOfMatches(); j++){
                        FantasyCupMatchAlias fantasyCupMatchAlias = new FantasyCupMatchAlias();
                        String homeTeamAlias = "Winner " + FantasyCupMatchType.getPrevious(fantasyCupMatchType).getMatchPrefix() + previousMatchCounter;
                        fantasyCupMatchAlias.setHomeTeamAlias(homeTeamAlias);;
                        previousMatchCounter ++;
                        String awayTeamAlias= "Winner " + FantasyCupMatchType.getPrevious(fantasyCupMatchType).getMatchPrefix() + previousMatchCounter;
                        fantasyCupMatchAlias.setAwayTeamAlias(awayTeamAlias);
                        previousMatchCounter ++;
                        fantasyCupMatchAlias.setAlias(fantasyCupMatchType.getMatchPrefix() + (j +1));
                        aliasList.add(fantasyCupMatchAlias);
                    }
                    fantasyCupRound.setFantasyMatchAliasList(aliasList);
                }
            }
            fantasyCupRounds.add(fantasyCupRound);
        }
        return fantasyCupRounds;
    }



    public void createFixtureList(int fantasyCupId) throws FixtureListCreationException{
        FantasyCup fantasyCup = getFantasyCupById(fantasyCupId);
        List<FantasyRound> fantasyRoundList = fantasyService.getFantasyRoundByFantasyCupId(fantasyCupId);
        List<FantasyCupGroupStanding> standingList = new ArrayList<FantasyCupGroupStanding>();
        for(FantasyCupGroup fantasyCupGroup: fantasyCup.getFantasyCupGroupList()){
            fantasyRoundList.removeAll(fantasyCupGroup.getFantasyRoundList());
            standingList.addAll(fantasyCupGroupService.getLastUpdatedFantasyCupGroupStandingsByCupGroup(fantasyCupGroup.getId()));
        }

        Collections.sort(fantasyRoundList,new FantasyRoundComparator());
        boolean cupGroupsAreFinished = fantasyCupGroupService.isFantasyCupGroupsFinished(fantasyCup.getFantasyCupGroupList());
        if(cupGroupsAreFinished &&  ! fantasyMatchService.fantasyCupHasMatches(fantasyCupId)&& ! fantasyCup.getFantasyCupGroupList().isEmpty()){
            CupAlgorithm cupAlgorithm = new TopBottomCupAlgorithm(fantasyCup,fantasyCup.getFantasyCupGroupList(),standingList);
            List<FantasyMatch> firstRoundMatches = cupAlgorithm.createFirstRound();
            for(FantasyMatch fantasyMatch: firstRoundMatches){
                fantasyMatch.setFantasyRound(fantasyRoundList.get(0));
                fantasyMatch.setFantasyCup(fantasyCup);
                fantasyMatchService.saveOrUpdateFantasyMatch(fantasyMatch);
            }

        }else if(fantasyCup.getFantasyCupGroupList().isEmpty() && ! fantasyMatchService.fantasyCupHasMatches(fantasyCupId)){

            List<FantasyTeam> fantasyTeamList = fantasyCup.getFantasyTeamList();
            List<FantasyRound> fantasyRounds = new ArrayList<FantasyRound>();
            fantasyRounds.add(fantasyCup.getFirstFantasyRound());
            validateFantasyCupBeforeFixtureListCreation(fantasyCup,fantasyTeamList,fantasyCup.getFantasyRoundList());
            FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRounds, fantasyTeamList, false);
            List<List<FantasyMatch>> fixtureList = fixtureListCreator.createFixtureList();
            List<FantasyMatch> matchList = fixtureList.get(0);
            for(FantasyMatch fantasyMatch: matchList){
                fantasyMatch.setFantasyCup(fantasyCup);
                fantasyMatchService.saveOrUpdateFantasyMatch(fantasyMatch);

            }
        }

        else{
            List<FantasyMatch> lastRoundMatchList = fantasyMatchService.getLastRoundMatchesForCup(fantasyCupId);
            boolean allMatchesArePlayed = isAllMatchesPlayed(lastRoundMatchList);

            if(lastRoundMatchList.size() >= 2 && allMatchesArePlayed){
                Collections.sort(lastRoundMatchList, new FantasyMatchByIdComparator());
                for(int i = 0; i< lastRoundMatchList.size() ; i += 2){
                    FantasyMatch firstMatch = lastRoundMatchList.get(i);
                    FantasyMatch secondMatch = lastRoundMatchList.get(i+1);
                    FantasyTeam firstTeam = firstMatch.getWinningTeam();
                    FantasyTeam secondTeam = secondMatch.getWinningTeam();
                    if(firstTeam == null){
                        firstTeam = fantasyMatchService.determineCupMatchWinner(firstMatch, fantasyCupId);
                    }
                    if(secondTeam == null){
                        secondTeam = fantasyMatchService.determineCupMatchWinner(secondMatch, fantasyCupId);
                    }
                    if(firstTeam != null && secondTeam != null){
                        FantasyMatch fantasyMatch = new FantasyMatch();
                        fantasyMatch.setAwayTeam(firstTeam);
                        fantasyMatch.setHomeTeam(secondTeam);
                        fantasyMatch.setFantasyCup(fantasyCup);
                        fantasyMatch.setFantasyRound(fantasyCup.getNextRound(firstMatch.getFantasyRound()));
                        fantasyMatchService.saveOrUpdateFantasyMatch(fantasyMatch);
                    }
                }
            }
        }
    }

    private boolean isAllMatchesPlayed(List<FantasyMatch> lastRoundMatchList) {
        boolean isAllMatchesPlayed = true;
        if(lastRoundMatchList != null && ! lastRoundMatchList.isEmpty()){
            for(FantasyMatch fantasyMatch: lastRoundMatchList){
                if( ! fantasyMatch.isPlayed()){
                    isAllMatchesPlayed = false;
                }
            }
        }
        return isAllMatchesPlayed;
    }

    public void createFixtureList() throws FixtureListCreationException{
        FantasySeason season = fantasyService.getDefaultFantasySeason();
        List<FantasyCup> fantasyCupList = getAllFantasyCups(season.getFantasySeasonId());
        for(FantasyCup fantasyCup:fantasyCupList){
            boolean cupGroupsAreFinished = fantasyCupGroupService.isFantasyCupGroupsFinished(fantasyCup.getFantasyCupGroupList());
            //TODO: Optimize should query db directly
            FantasyRound lastFantasyRound = fantasyCup.getLastFantasyRound();
            boolean lastRoundHasNoMatches = fantasyMatchService.getFantasyMatchByCupIdAndRoundId(fantasyCup.getId(),lastFantasyRound.getFantasyRoundId()).isEmpty();
            if(cupGroupsAreFinished &&  lastRoundHasNoMatches){
                createFixtureList(fantasyCup.getId());
            }

        }
    }

    private void validateFantasyCupBeforeFixtureListCreation(FantasyCup fantasyCup, List<FantasyTeam> fantasyTeamList, List<FantasyRound> fantasyRoundList) throws FixtureListCreationException {
        if(fantasyTeamList.size() < 2 ){
            throw new FixtureListCreationException("Number of teams must be above or equals 2");
        }
        if(fantasyTeamList.size() % 2 != 0 ){
            throw new FixtureListCreationException("Number of teams must be an even number");
        }

        if(fantasyCup.getFantasyRoundList().isEmpty()){
            throw new FixtureListCreationException("No rounds has been selected");
        }
        if(fantasyTeamList.size() >= 4){
            if(Math.pow(2, fantasyRoundList.size()) != fantasyTeamList.size()){
                throw new FixtureListCreationException("Incorrect number of teams according to number of rounds");
            }
        }
    }


    private  void updateDependencies(List<FantasyCup> fantasyCupList){
        for(FantasyCup fantasyCup : fantasyCupList){
            updateDependencies(fantasyCup);
        }
    }

    private void updateDependencies(FantasyCup fantasyCup){
        List<FantasyRound> fantasyRoundList = fantasyService.getFantasyRoundByFantasyCupId(fantasyCup.getId());
        fantasyCup.setFantasyRoundList(fantasyRoundList);
        List<FantasyTeam> fantasyTeamList = fantasyService.getFantasyTeamByFantasyCupId(fantasyCup.getId());
        fantasyCup.setFantasyTeamList(fantasyTeamList);
        List<FantasyCupGroup> fantasyCupGroups = fantasyCupGroupService.getAllFantasyCupGroups(fantasyCup.getId());
        fantasyCup.setFantasyCupGroupList(fantasyCupGroups);
        fantasyCup.setNumberOfGroups(fantasyCupGroups != null ? fantasyCupGroups.size():0);
        for(FantasyCupGroup fantasyCupGroup: fantasyCupGroups){
            fantasyCupGroup.setFantasyCup(fantasyCup);
        }

    }
}
