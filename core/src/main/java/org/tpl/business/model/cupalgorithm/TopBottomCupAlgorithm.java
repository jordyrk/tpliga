package org.tpl.business.model.cupalgorithm;

import org.tpl.business.model.comparator.FantasyCupGroupByIdComparator;
import org.tpl.business.model.comparator.FantasyCupGroupStandingComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.service.fantasy.fixturelist.FixtureListCreationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopBottomCupAlgorithm implements CupAlgorithm {

    private FantasyCup fantasyCup;
    private List<FantasyCupGroupStanding> fantasyCupGroupStandings;
    private List<FantasyCupGroup> fantasyCupGroups;
    protected int currentTopRatedGroupIndex = 0;
    protected int currentTopRatedPositionIndex = 1;
    protected int currentBottomRatedGroupIndex = 0;
    protected int currentBottomNumberOfTeamsFromPosition = 0;
    protected int currentBottomRatedPositionIndex = -1;


    public TopBottomCupAlgorithm(FantasyCup fantasyCup, List<FantasyCupGroup> fantasyCupGroups, List<FantasyCupGroupStanding> fantasyCupGroupStandings) {
        this.fantasyCup = fantasyCup;
        this.fantasyCupGroups = fantasyCupGroups;
        this.fantasyCupGroupStandings = fantasyCupGroupStandings;
        currentBottomRatedGroupIndex = fantasyCupGroups != null ? fantasyCupGroups.size() -1 : 0;

    }

    public List<FantasyCupMatchAlias> createFirstRoundAliases() throws FixtureListCreationException{
        Collections.sort(fantasyCupGroups,new FantasyCupGroupByIdComparator());
        findLowestPositionToBeIncluded();
        int numberOfMatches = fantasyCup.getNumberOfQualifiedTeamsFromGroups()/2;
        List<FantasyCupMatchAlias> fantasyCupMatchAliases = new ArrayList<FantasyCupMatchAlias>();
        while(fantasyCupMatchAliases.size() < numberOfMatches){
            FantasyCupMatchAlias fantasyCupMatchAlias = new FantasyCupMatchAlias();
            fantasyCupMatchAlias.setHomeTeamAlias(findTopRatedTeamAlias());
            fantasyCupMatchAlias.setAwayTeamAlias(findBottomRatedTeamAlias());
            fantasyCupMatchAliases.add(fantasyCupMatchAlias);
        }
        return fantasyCupMatchAliases;
    }

    public List<FantasyMatch> createFirstRound() throws FixtureListCreationException{
        Collections.sort(fantasyCupGroups,new FantasyCupGroupByIdComparator());
        findLowestPositionToBeIncluded();
        List<FantasyMatch> fantasyMatchList = new ArrayList<FantasyMatch>();
        int numberOfMatches = fantasyCup.getNumberOfQualifiedTeamsFromGroups()/2;
        validateNumberOfMatches(numberOfMatches);

        while(fantasyMatchList.size() < numberOfMatches){
            FantasyTeam homeTeam = findTopRatedTeam();
            FantasyTeam awayTeam = findBottomRatedTeam();
            FantasyMatch fantasyMatch = new FantasyMatch();
            fantasyMatch.setHomeTeam(homeTeam);
            fantasyMatch.setAwayTeam(awayTeam);
            fantasyMatchList.add(fantasyMatch);
        }
        return fantasyMatchList;
    }

    protected void findLowestPositionToBeIncluded() {
        boolean isNotFound = true;
        int position = 1;
        while(isNotFound){
            int numberOfTeams = fantasyCup.getNumberOfQualifiedTeams(position);
            if(numberOfTeams < fantasyCupGroups.size() && numberOfTeams != 0){
                isNotFound = false;
                currentBottomRatedPositionIndex = position;
                currentBottomNumberOfTeamsFromPosition = numberOfTeams;
            }
            else if(numberOfTeams == 0){
                isNotFound = false;
                currentBottomRatedPositionIndex = position;
                currentBottomNumberOfTeamsFromPosition = fantasyCup.getNumberOfQualifiedTeams(position-1);
            }
            position++;
        }
    }

    protected FantasyTeam findBottomRatedTeam() throws FixtureListCreationException{
        FantasyTeam fantasyTeam;
        if(allPositions()){
            FantasyCupGroup fantasyCupGroup = fantasyCupGroups.get(currentBottomRatedGroupIndex);
            List<FantasyCupGroupStanding> standingList = getStandingsFromGroup(fantasyCupGroup.getId());
            Collections.sort(standingList, new FantasyCupGroupStandingComparator());

            fantasyTeam = standingList.get(currentBottomRatedPositionIndex-1).getFantasyTeam();
            if(currentBottomRatedGroupIndex >= 0){
                currentBottomRatedGroupIndex--;
            }
            if(currentBottomRatedGroupIndex == -1 ){
                currentBottomRatedGroupIndex = fantasyCupGroups.size()-1;
                currentBottomRatedPositionIndex--;
            }

        }else{
            List<FantasyCupGroupStanding> standingsFromPosition = getStandingsFromPosition(currentBottomRatedPositionIndex);
            fantasyTeam = standingsFromPosition.get(currentBottomNumberOfTeamsFromPosition-1).getFantasyTeam();
            currentBottomNumberOfTeamsFromPosition--;
            if(currentBottomNumberOfTeamsFromPosition == 0){
                currentBottomRatedGroupIndex = fantasyCupGroups.size()-1;
                currentBottomRatedPositionIndex --;
                currentBottomNumberOfTeamsFromPosition = fantasyCupGroups.size();
            }
        }
        return fantasyTeam;
    }

    protected String findBottomRatedTeamAlias(){
        String alias;
        if(allPositions()){
            FantasyCupGroup fantasyCupGroup = fantasyCupGroups.get(currentBottomRatedGroupIndex);
            alias = "Posisjon " + currentBottomRatedPositionIndex + ", " + fantasyCupGroup.getName();
            if(currentBottomRatedGroupIndex >= 0){
                currentBottomRatedGroupIndex--;
            }
            if(currentBottomRatedGroupIndex == -1 ){
                currentBottomRatedGroupIndex = fantasyCupGroups.size()-1;
                currentBottomRatedPositionIndex--;
            }

        }else{
            alias = "Beste " + currentBottomRatedPositionIndex ;
            currentBottomNumberOfTeamsFromPosition--;
            if(currentBottomNumberOfTeamsFromPosition == 0){
                currentBottomRatedGroupIndex = fantasyCupGroups.size()-1;
                currentBottomRatedPositionIndex --;
                currentBottomNumberOfTeamsFromPosition = fantasyCupGroups.size();
            }
        }
        return alias;
    }

    protected boolean allPositions() {
        return currentBottomNumberOfTeamsFromPosition == fantasyCupGroups.size();
    }

    protected FantasyTeam findTopRatedTeam(){
        FantasyCupGroup fantasyCupGroup = fantasyCupGroups.get(currentTopRatedGroupIndex);
        List<FantasyCupGroupStanding> standingList = getStandingsFromGroup(fantasyCupGroup.getId());
        Collections.sort(standingList, new FantasyCupGroupStandingComparator());
        FantasyTeam fantasyTeam = standingList.get(currentTopRatedPositionIndex-1).getFantasyTeam();

        if(currentTopRatedGroupIndex < fantasyCupGroups.size()+1){
            currentTopRatedGroupIndex++;
        }
        if(fantasyCupGroups.size()  == currentTopRatedGroupIndex){
            currentTopRatedPositionIndex++;
            currentTopRatedGroupIndex = 0;
        }
        return fantasyTeam;
    }

    protected String findTopRatedTeamAlias(){
        FantasyCupGroup fantasyCupGroup = fantasyCupGroups.get(currentTopRatedGroupIndex);
        String alias = "Posisjon " + currentTopRatedPositionIndex + ", " + fantasyCupGroup.getName();
        if(currentTopRatedGroupIndex < fantasyCupGroups.size()+1){
            currentTopRatedGroupIndex++;
        }
        if(fantasyCupGroups.size()  == currentTopRatedGroupIndex){
            currentTopRatedPositionIndex++;
            currentTopRatedGroupIndex = 0;
        }
        return alias;
    }

    private List<FantasyCupGroupStanding> getStandingsFromGroup(int id) {
        List<FantasyCupGroupStanding> standingList = new ArrayList<FantasyCupGroupStanding>();
        for(FantasyCupGroupStanding fantasyCupGroupStanding: fantasyCupGroupStandings){
            if(fantasyCupGroupStanding.getFantasyCupGroup().getId() == id){
                standingList.add(fantasyCupGroupStanding);
            }
        }
        return standingList;
    }

    protected List<FantasyCupGroupStanding> getStandingsFromPosition(int position) throws FixtureListCreationException{
        List<List<FantasyCupGroupStanding>> list= new ArrayList<List<FantasyCupGroupStanding>>();
        for(FantasyCupGroup fantasyCupGroup: fantasyCupGroups){
            List<FantasyCupGroupStanding> standingList = getStandingsFromGroup(fantasyCupGroup.getId());
            Collections.sort(standingList, new FantasyCupGroupStandingComparator());
            list.add(standingList);
        }
        List<FantasyCupGroupStanding> positionList = new ArrayList<FantasyCupGroupStanding>();

        for(List<FantasyCupGroupStanding> standingList: list){
            try{
                positionList.add(standingList.get((position-1)));

            }catch (IndexOutOfBoundsException e){
                throw new FixtureListCreationException("Invalid position: " + position);
            }
        }
        Collections.sort(positionList, new FantasyCupGroupStandingComparator());
        return positionList;
    }

    protected boolean validateNumberOfMatches(int numberOfMatches) throws FixtureListCreationException{
        //Allowed number of matches is 1,2,4,8,16,32,64,128
        for(double i = 0 ; i < 8 ; i++){
            int allowedNumberOfMatches = ((Double)Math.pow(2,i)).intValue();
            if(numberOfMatches == allowedNumberOfMatches){
                return true;
            }
        }
        throw new FixtureListCreationException("Number of matches is invalid, must be 1,2,4,8,16,32,64 or 128, but was: " + numberOfMatches);
    }
}
