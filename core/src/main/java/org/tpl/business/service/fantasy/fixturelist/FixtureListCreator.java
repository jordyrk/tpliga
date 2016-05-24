package org.tpl.business.service.fantasy.fixturelist;

import org.tpl.business.model.fantasy.FantasyMatch;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasyTeam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixtureListCreator {

    List<FantasyRound> fantasyRoundList;
    List<FantasyTeam> fantasyTeamList;
    boolean homeAndAwayMatches;
    List<List<FantasyMatch>> fantasyMatchList;
    FantasyTeam[][] fantasyTeamArray ;


    public FixtureListCreator(List<FantasyRound> fantasyRoundList, List<FantasyTeam> fantasyTeamList, boolean homeAndAwayMatches) {
        this.fantasyRoundList = fantasyRoundList;
        this.fantasyTeamList = fantasyTeamList;
        this.homeAndAwayMatches = homeAndAwayMatches;
        createArray();
    }

    public List<List<FantasyMatch>> createFixtureList(){
        if(fantasyTeamList.size() < 2){
            return Collections.emptyList();
        }
        int roundCounter = fantasyRoundList.size();
        if(homeAndAwayMatches){
            roundCounter = fantasyRoundList.size()/2;
        }
        fantasyMatchList = new ArrayList<List<FantasyMatch>>();
        addFirstHalfOfSeason(roundCounter);

        if(homeAndAwayMatches){
            addSecondHalfOfSeason(roundCounter);
        }
        removeMatchesWithDummyTeam();
        populateMatchWithRounds();
        return fantasyMatchList;
    }

    private void removeMatchesWithDummyTeam() {
        int[] indexes = getFirstIndexOfDummyMatch();
        while(indexes != null){
            fantasyMatchList.get(indexes[0]).remove(indexes[1]);
            indexes = getFirstIndexOfDummyMatch();
        }

    }

    private int[] getFirstIndexOfDummyMatch() {
        for(int i = 0; i < fantasyMatchList.size(); i++){
            List<FantasyMatch> fantasyMatches = fantasyMatchList.get(i);
            for(int j = 0; j< fantasyMatches.size(); j++){
                if(isDummyMatch(fantasyMatches.get(j))){
                    return new int[] {i,j};
                }
            }
        }
        return null;
    }

    private boolean isDummyMatch(FantasyMatch fantasyMatch) {
        return fantasyMatch.getHomeTeam().getTeamId() == -100 || fantasyMatch.getAwayTeam().getTeamId() == -100;
    }

    private void addFirstHalfOfSeason(int roundCounter) {
        for(int i = 0; i < roundCounter; i++){
            List<FantasyMatch> matchList = new ArrayList<FantasyMatch>();
            for(int j = 0; j < fantasyTeamList.size()/2; j++){
                FantasyMatch fantasyMatch = new FantasyMatch();
                if(i%2 ==0){
                    fantasyMatch.setHomeTeam(fantasyTeamArray[0][j]);
                    fantasyMatch.setAwayTeam(fantasyTeamArray[1][j]);
                }else{
                    fantasyMatch.setHomeTeam(fantasyTeamArray[1][j]);
                    fantasyMatch.setAwayTeam(fantasyTeamArray[0][j]);
                }
                matchList.add(fantasyMatch);

            }
            rotateClockWise();
            fantasyMatchList.add(matchList);
        }
    }

    private void addSecondHalfOfSeason(int roundCounter) {
        for(int i = 0; i < roundCounter; i++){
            List<FantasyMatch> secondHalfMatchList = new ArrayList<FantasyMatch>();
            for(int j = 0; j < fantasyTeamList.size()/2; j++){
                FantasyMatch fantasyMatch = new FantasyMatch();
                if(i%2 !=0){
                    fantasyMatch.setHomeTeam(fantasyTeamArray[0][j]);
                    fantasyMatch.setAwayTeam(fantasyTeamArray[1][j]);
                }else{
                    fantasyMatch.setHomeTeam(fantasyTeamArray[1][j]);
                    fantasyMatch.setAwayTeam(fantasyTeamArray[0][j]);
                }
                secondHalfMatchList.add(fantasyMatch);
            }
            rotateClockWise();
            fantasyMatchList.add(secondHalfMatchList);
        }
    }

    private void populateMatchWithRounds() {
        for(int i = 0; i < fantasyRoundList.size(); i ++){
            for(FantasyMatch fantasyMatch : fantasyMatchList.get(i)){
                fantasyMatch.setFantasyRound(fantasyRoundList.get(i));
            }
        }
    }

    /**
     * Creates an array based on Round-robin tournament
     * http://en.wikipedia.org/wiki/Round-robin_tournament
     * 
     */

    protected void createArray(){
        addDummyTeam();
        FantasyTeam[][] array = new FantasyTeam[2][fantasyTeamList.size()/2];
        for(int i = 0; i < fantasyTeamList.size(); i++){
            if(i%2==0){
                array[0][i/2] = fantasyTeamList.get(i);
            }
            else{
                array[1][i/2] = fantasyTeamList.get(i);
            }
        }
        fantasyTeamArray = array;
    }

    private void addDummyTeam() {
        if(fantasyTeamList.size()%2 != 0 && fantasyTeamList.size()> 2){
            fantasyTeamList.add(createDummyTeam());
        }
    }

    /**
     * Rotates the Round-robin array
     * First element in first row is fixed
     * the last element of the first row moves to last element of the secondrow
     * first element on second row moves to second element on first row
     * other elements on first row shifts rigth
     * other elements on second row shifts left
     */
    protected void rotateClockWise(){
        FantasyTeam[][] array = new FantasyTeam[fantasyTeamArray.length][fantasyTeamArray[0].length];
        array[0][0] = fantasyTeamArray[0][0];
        array[0][1] = fantasyTeamArray[1][0];
        array[1][array[1].length-1] = fantasyTeamArray[0][fantasyTeamArray[0].length-1];

        for(int i = 2; i < fantasyTeamArray[0].length;i++){
            array[0][i] = fantasyTeamArray[0][i-1];
        }
        for(int i = 0; i < fantasyTeamArray[1].length-1; i++){
            array[1][i] = fantasyTeamArray[1][i+1];
        }
        fantasyTeamArray = array;
    }

    private void printMatchArray(){
        System.out.println("******************");
        for(int i = 0; i < fantasyTeamArray.length; i++){
            System.out.print("[");
            for(int j = 0; j < fantasyTeamArray[i].length; j++){
                if(j!= 0){
                    System.out.print(",");
                }
                System.out.print(fantasyTeamArray[i][j]);
            }
            System.out.print("]");
            System.out.println("");
        }
    }

    private FantasyTeam createDummyTeam(){
        FantasyTeam dummyTeam = new FantasyTeam();
        dummyTeam.setTeamId(-100);
        dummyTeam.setName("Dummy");
        return dummyTeam;

    }
}
