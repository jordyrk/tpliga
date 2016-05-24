package org.tpl.business.service.fantasy;
/*
Created by jordyr, 04.02.11

*/

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.comparator.FantasyCompetitionStandingComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.score.ScoreCalculator;
import org.tpl.integration.dao.fantasy.FantasyCompetitionDao;
import org.tpl.integration.dao.fantasy.FantasyCompetitionStandingDao;
import org.tpl.integration.dao.fantasy.FantasyMatchDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultFantasyCompetitionService implements FantasyCompetitionService {

    @Autowired
    FantasyCompetitionStandingDao fantasyCompetitionStandingDao;

    @Autowired
    FantasyCompetitionDao fantasyCompetitionDao;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    public void saveOrUpdateFantasyCompetitionStanding(FantasyCompetitionStanding fantasyCompetitionStanding) {
        fantasyCompetitionStandingDao.saveOrUpdateFantasyCompetitionStanding(fantasyCompetitionStanding);
    }

    public FantasyCompetitionStanding getFantasyCompetitionStandingByIds(int fantasyTeamId, int fantasyCompetitionId, int fantasyRoundId) {
        FantasyCompetitionStanding fantasyCompetitionStanding = fantasyCompetitionStandingDao.getFantasyCompetitionStandingByIds(fantasyTeamId, fantasyCompetitionId, fantasyRoundId);
        updateDependencies(fantasyCompetitionStanding);
        return fantasyCompetitionStanding;
    }

    public List<FantasyCompetitionStanding> getFantasyCompetitionStandingByRoundAndCompetition(int fantasyCompetitionId, int fantasyRoundId) {
        return fantasyCompetitionStandingDao.getFantasyCompetitionStandingByRoundAndCompetition(fantasyCompetitionId,fantasyRoundId);
    }

    public List<FantasyCompetitionStanding> getFantasyCompetitionStandingByTeamAndCompetition(int fantasyCompetitionId, int fantasyTeamId) {
        return fantasyCompetitionStandingDao.getFantasyCompetitionStandingByTeamAndCompetition(fantasyCompetitionId, fantasyTeamId);
    }

    public List<FantasyCompetitionStanding> getLastUpdatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId) {
        FantasyCompetition fantasyCompetition = this.getFantasyCompetitionById(fantasyCompetitionId);
        FantasyRound fantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyCompetition.getFantasySeason().getFantasySeasonId());

        List<FantasyCompetitionStanding> list = getLastUpdatedFantasyCompetitionStandingsByCompetition(fantasyCompetitionId, fantasyRound.getFantasyRoundId());

        for(FantasyCompetitionStanding fantasyCompetitionStanding: list){
            updateDependencies(fantasyCompetitionStanding);
        }
        return list;
    }

    private List<FantasyCompetitionStanding> getLastUpdatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId, int fantasyRoundId) {

        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        List<FantasyCompetitionStanding> list = fantasyCompetitionStandingDao.getFantasyCompetitionStandingByRoundAndCompetition(fantasyCompetitionId,fantasyRound.getFantasyRoundId());
        if(list.size() < 1 && fantasyRound.getRound() != 1){
            return getLastUpdatedFantasyCompetitionStandingsByCompetition(fantasyCompetitionId, fantasyRoundId-1);
        }

        return list;
    }

    public List<FantasyCompetitionStanding> getAccumulatedFantasyCompetitionStandingsByCompetition(int fantasyCompetitionId, int numberOfRounds) {
        FantasyCompetition fantasyCompetition = this.getFantasyCompetitionById(fantasyCompetitionId);
        FantasyRound fantasyRound = getLastUpdatedFantasyRound(fantasyCompetitionId);
        if(fantasyRound == null){
            return Collections.emptyList();
        }
        int[] roundIdRange = new int[numberOfRounds];
        int fantasyRoundId = fantasyRound.getFantasyRoundId();
        for(int i = 0;i < numberOfRounds; i++){
            roundIdRange[i] = fantasyRoundId--;

        }
        List<FantasyCompetitionStanding> list =  fantasyCompetitionStandingDao.getAccumulatedFantasyCompetitionStandingsByCompetition(fantasyCompetitionId,roundIdRange);
        int position = 1;
        for(FantasyCompetitionStanding fantasyCompetitionStanding: list){
            fantasyCompetitionStanding.setPosition(position);
            position++;
            fantasyCompetitionStanding.setFantasyCompetition(fantasyCompetition);
            updateDependenciesWithoutRound(fantasyCompetitionStanding);
        }

        return list;
    }

    public void saveOrUpdateFantasyCompetition(FantasyCompetition fantasyCompetition) {
        fantasyCompetitionDao.saveOrUpdateFantasyCompetition(fantasyCompetition);
    }

    public FantasyCompetition getFantasyCompetitionById(int fantasyCompetitionId) {
        FantasyCompetition fantasyCompetition = fantasyCompetitionDao.getFantasyCompetitionById(fantasyCompetitionId);
        updateDependencies(fantasyCompetition);
        return fantasyCompetition;
    }

    public FantasyCompetition getDefaultFantasyCompetitionBySeasonId(int fantasySeasonId) {
        FantasyCompetition fantasyCompetition = fantasyCompetitionDao.getDefaultFantasyCompetitionBySeasonId(fantasySeasonId);
        updateDependencies(fantasyCompetition);
        return fantasyCompetition;
    }

    public List<FantasyCompetition> getFantasyCompetitionBySeasonId(int fantasySeasonId) {
        List<FantasyCompetition> fantasyCompetitionList = fantasyCompetitionDao.getFantasyCompetitionBySeasonId(fantasySeasonId);
        for(FantasyCompetition fantasyCompetition: fantasyCompetitionList){
            updateDependencies(fantasyCompetition);
        }
        return fantasyCompetitionList;
    }

    public List<FantasyCompetition> getFantasyCompetitionForFantasyTeamInCurrentSeason(int fantasyTeamId) {
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        return fantasyCompetitionDao.getFantasyCompetitionForFantasyTeamInSeason(fantasyTeamId, fantasySeason.getFantasySeasonId());
    }

    public boolean addTeamToCompetetion(int fantasyCompetitionId, int fantasyTeamId) {
        return fantasyCompetitionDao.addTeamToCompetetion(fantasyCompetitionId,fantasyTeamId);
    }

    public void removeTeamFromCompetitions(int fantasyTeamId) {

        removeTeamFromAllCompetitions(fantasyTeamId);
        deleteFantasyCompetitionStandings(fantasyTeamId);

    }

    private void deleteFantasyCompetitionStandings(int fantasyTeamId) {
        fantasyCompetitionStandingDao.deleteFantasyCompetitionStandings(fantasyTeamId);
    }

    private void removeTeamFromAllCompetitions(int fantasyTeamId) {
        fantasyCompetitionDao.removeTeamFromAllCompetitions(fantasyTeamId);
    }



    public List<FantasyCompetition> getAllFantasyCompetition() {
        List<FantasyCompetition> fantasyTeamCompetitionList = fantasyCompetitionDao.getAllFantasyCompetition();
        return fantasyTeamCompetitionList;
    }

    public FantasyTeamCompetition getFantasyTeamCompetitionByIds(int fantasyCompetitionId, int fantasyTeamId, int fantasySeasonId) {
        return fantasyCompetitionDao.getFantasyTeamCompetitionByIds(fantasyCompetitionId, fantasyTeamId, fantasySeasonId);
    }

    public List<FantasyTeamCompetition> getTeamsForCompetition(int fantasyCompetitionId) {
        return fantasyCompetitionDao.getTeamsForCompetition(fantasyCompetitionId);
    }

    public List<FantasyTeamCompetition> getCompetitonsForTeam(int fantasyTeamId, int fantasySeasonId) {
        return fantasyCompetitionDao.getCompetitonsForTeam(fantasyTeamId, fantasySeasonId);
    }

    public void updateCompetitionStandings(int fantasyRoundId, int fantasySeasonId){
        FantasyRound previousRound = fantasyService.getPreviousFantasyRound(fantasyRoundId,fantasySeasonId);
        FantasyRound currentRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        List<FantasyCompetition> fantasyCompetitionList = getFantasyCompetitionBySeasonId(fantasySeasonId);
        for(FantasyCompetition fantasyCompetition: fantasyCompetitionList){
            if(previousRound != null){
                List<FantasyCompetitionStanding> newStandingList = createUpdatedStandings(currentRound, fantasyCompetition, previousRound);
                setPosition(newStandingList, false);
                saveStandings(newStandingList);
            }
            else{

                List<FantasyCompetitionStanding> newStandingList = createNewStandings(currentRound, fantasyCompetition);
                setPosition(newStandingList, true);
                saveStandings(newStandingList);
            }
        }
    }

    public FantasyRound getLastUpdatedFantasyRound(int fantasyCompetitionId) {
        int roundId = fantasyCompetitionStandingDao.getLastUpdatedRoundIdForCompetition(fantasyCompetitionId);
        return fantasyService.getFantasyRoundById(roundId);
    }

    private List<FantasyCompetitionStanding> createNewStandings(FantasyRound currentRound, FantasyCompetition fantasyCompetition){
        List<FantasyTeamCompetition> fantasyTeamCompetitionList = getTeamsForCompetition(fantasyCompetition.getFantasyCompetitionId());
        List<FantasyCompetitionStanding> newStandingList = new ArrayList<FantasyCompetitionStanding>();
        for(FantasyTeamCompetition fantasyTeamCompetition: fantasyTeamCompetitionList){
            FantasyTeam fantasyTeam = fantasyTeamCompetition.getFantasyTeam();
            FantasyTeamRound fantasyTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyTeam.getTeamId(),currentRound.getFantasyRoundId());

            FantasyCompetitionStanding fantasyCompetitionStanding =  calculatePoints(fantasyCompetition, fantasyTeamRound);
            fantasyCompetitionStanding.setFantasyTeam(fantasyTeam);
            fantasyCompetitionStanding.setFantasyRound(currentRound);
            newStandingList.add(fantasyCompetitionStanding);

        }
        return newStandingList;
    }

    private List<FantasyCompetitionStanding> createUpdatedStandings(FantasyRound currentRound, FantasyCompetition fantasyCompetition, FantasyRound previousRound){
        List<FantasyCompetitionStanding> oldStandingList = getFantasyCompetitionStandingByRoundAndCompetition(fantasyCompetition.getFantasyCompetitionId(), previousRound.getFantasyRoundId());
        if(oldStandingList == null || oldStandingList.size() < 1){
            return createNewStandings(currentRound,fantasyCompetition);
        }
        List<FantasyCompetitionStanding> newStandingList = new ArrayList<FantasyCompetitionStanding>();
        for(FantasyCompetitionStanding oldStanding : oldStandingList){
            FantasyTeam fantasyTeam = oldStanding.getFantasyTeam();
            FantasyTeamRound fantasyTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyTeam.getTeamId(),currentRound.getFantasyRoundId());

            FantasyCompetitionStanding newStanding = calculatePoints(currentRound, oldStanding, fantasyTeamRound);

            newStanding.setFantasyRound(currentRound);
            newStanding.setFantasyTeam(fantasyTeam);
            newStanding.setFantasyCompetition(fantasyCompetition);
            newStandingList.add(newStanding);
        }
        return newStandingList;
    }

    private FantasyCompetitionStanding calculatePoints(FantasyRound currentRound, FantasyCompetitionStanding oldStanding, FantasyTeamRound fantasyTeamRound) {
        FantasyCompetitionStanding newStanding = new FantasyCompetitionStanding();
        int points = fantasyTeamRound.getPoints();
        int accumulatedPoints = oldStanding.getAccumulatedPoints() + points;
        int min = oldStanding.getMinimumpoints();
        int max = oldStanding.getMaximumpoints();

        if(points < min ) min = points;
        if(points > max) max = points;
        int average = (accumulatedPoints/currentRound.getRound());
        newStanding.setPoints(points);
        newStanding.setAccumulatedPoints(accumulatedPoints);
        newStanding.setMinimumpoints(min);
        newStanding.setMaximumpoints(max);
        newStanding.setAveragepoints(average);
        newStanding.setLastRoundPosition(oldStanding.getPosition());
        return newStanding;
    }

    private FantasyCompetitionStanding calculatePoints(FantasyCompetition fantasyCompetition, FantasyTeamRound fantasyTeamRound) {
        FantasyCompetitionStanding fantasyCompetitionStanding = new FantasyCompetitionStanding();

        int points = fantasyTeamRound.getPoints();
        int accumulatedPoints = points;
        int min = points;
        int max = points;
        int average = points;
        fantasyCompetitionStanding.setPoints(points);
        fantasyCompetitionStanding.setAccumulatedPoints(accumulatedPoints);
        fantasyCompetitionStanding.setMinimumpoints(min);
        fantasyCompetitionStanding.setMaximumpoints(max);
        fantasyCompetitionStanding.setAveragepoints(average);
        fantasyCompetitionStanding.setLastRoundPosition(fantasyCompetitionStanding.getPosition());
        fantasyCompetitionStanding.setFantasyCompetition(fantasyCompetition);
        return fantasyCompetitionStanding;

    }

    private void updateDependencies(FantasyCompetition fantasyCompetition){
        if(fantasyCompetition.getFantasyTeamCompetitionList() != null){
            for(FantasyTeamCompetition fantasyTeamCompetition: fantasyCompetition.getFantasyTeamCompetitionList()){
                updateDependencies(fantasyTeamCompetition);
            }
        }

    }

    private void setPosition(List<FantasyCompetitionStanding> fantasyCompetitionStandingList, boolean firstRound){
        Collections.sort(fantasyCompetitionStandingList, new FantasyCompetitionStandingComparator());
        for(int i = 0 ; i <fantasyCompetitionStandingList.size(); i++ ){
            fantasyCompetitionStandingList.get(i).setPosition(i+1);
            if(firstRound){
                fantasyCompetitionStandingList.get(i).setLastRoundPosition(i+1);
            }

        }

    }

    private void saveStandings(List<FantasyCompetitionStanding> fantasyCompetitionStandingList){
        for(FantasyCompetitionStanding fantasyCompetitionStanding: fantasyCompetitionStandingList){
            saveOrUpdateFantasyCompetitionStanding(fantasyCompetitionStanding);
        }
    }

    private void updateDependencies(FantasyTeamCompetition fantasyTeamCompetition){
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(fantasyTeamCompetition.getFantasyTeam().getTeamId());
        fantasyTeamCompetition.setFantasyTeam(fantasyTeam);
    }

    private void updateDependencies(FantasyCompetitionStanding fantasyCompetitionStanding){
        updateDependenciesWithoutRound(fantasyCompetitionStanding);

        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyCompetitionStanding.getFantasyRound().getFantasyRoundId());
        fantasyCompetitionStanding.setFantasyRound(fantasyRound);

    }

    private void updateDependenciesWithoutRound(FantasyCompetitionStanding fantasyCompetitionStanding){
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(fantasyCompetitionStanding.getFantasyTeam().getTeamId());
        fantasyCompetitionStanding.setFantasyTeam(fantasyTeam);

        FantasyCompetition fantasyCompetition = getFantasyCompetitionById(fantasyCompetitionStanding.getFantasyCompetition().getFantasyCompetitionId());
        fantasyCompetitionStanding.setFantasyCompetition(fantasyCompetition);


    }

}
