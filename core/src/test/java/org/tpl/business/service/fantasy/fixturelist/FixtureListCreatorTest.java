package org.tpl.business.service.fantasy.fixturelist;

import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyMatch;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasyTeam;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;


public class FixtureListCreatorTest {
    int counter = 0;
    @Test
    public void testCreateFixtureList() throws Exception {
        List<FantasyRound> fantasyRoundList = createFantasyRounds(38);
        List<FantasyTeam> fantasyTeamList = createFantasyTeams(20);
        FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRoundList,fantasyTeamList,true);
        List<List<FantasyMatch>> fantasyMatchList = fixtureListCreator.createFixtureList();
        assertEquals(38,fantasyMatchList.size());
        assertEquals(10,fantasyMatchList.get(0).size());
    }
    @Test
    public void testCreateFixtureListWithOddNumberOfTeams() throws Exception {
        List<FantasyRound> fantasyRoundList = createFantasyRounds(38);
        List<FantasyTeam> fantasyTeamList = createFantasyTeams(19);
        FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRoundList,fantasyTeamList,true);
        List<List<FantasyMatch>> fantasyMatchList = fixtureListCreator.createFixtureList();
        assertEquals(38,fantasyMatchList.size());
        assertEquals(9,fantasyMatchList.get(0).size());
    }

    @Test
    public void testCreateFixtureListWithSmallOddNumberOfTeams() throws Exception {
        List<FantasyRound> fantasyRoundList = createFantasyRounds(3);
        List<FantasyTeam> fantasyTeamList = createFantasyTeams(3);
        FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRoundList,fantasyTeamList,false);
        List<List<FantasyMatch>> fantasyMatchList = fixtureListCreator.createFixtureList();
        assertEquals(3,fantasyMatchList.size());
        assertEquals(1,fantasyMatchList.get(0).size());
    }


    @Test
    public void testCreateArray(){
        List<FantasyRound> fantasyRoundList = createFantasyRounds(10);
        List<FantasyTeam> fantasyTeamList = createFantasyTeams(10);
        FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRoundList,fantasyTeamList,true);
        fixtureListCreator.createArray();
        for(int i = 0; i < 8; i ++){
            fixtureListCreator.rotateClockWise();
        }
    }

    @Test
    public void teamShouldHaveCorrectNumberOfHomeMatches(){
        int numberOfTeams = 20;
        int numberOfRounds = numberOfTeams*2-2;
        List<FantasyRound> fantasyRoundList = createFantasyRounds(numberOfRounds);
        List<FantasyTeam> fantasyTeamList = createFantasyTeams(numberOfTeams);
        FixtureListCreator fixtureListCreator = new FixtureListCreator(fantasyRoundList,fantasyTeamList,true);
        List<List<FantasyMatch>> fantasyMatchList = fixtureListCreator.createFixtureList();
        for(FantasyTeam fantasyTeam : fantasyTeamList){
            assertEquals("number of homematches is " + numberOfRounds/2, numberOfRounds/2, countNumberOfHomeMatches(fantasyTeam, fantasyMatchList));
            assertEquals("number of awaymatches is " + numberOfRounds/2, numberOfRounds/2, countNumberOfAwayMatches(fantasyTeam, fantasyMatchList));
        }
    }

    private List<FantasyRound> createFantasyRounds(int numberOfRounds){
        List<FantasyRound> fantasyRoundList = new ArrayList<FantasyRound>();
        for(int i = 1; i <= numberOfRounds; i++){
            fantasyRoundList.add(createFantasyRound(i));
        }
        return fantasyRoundList;
    }

    private List<FantasyTeam> createFantasyTeams(int numberOfTeams){
        List<FantasyTeam> fantasyTeamList = new ArrayList<FantasyTeam>();
        for(int i = 1; i <= numberOfTeams; i++){
            fantasyTeamList.add(createFantasyTeam());
        }
        return fantasyTeamList;
    }

    private FantasyRound createFantasyRound(int roundNumber){
        FantasyRound fantasyRound = new FantasyRound();
        fantasyRound.setRound(roundNumber);
        fantasyRound.setFantasyRoundId(counter++);
        return fantasyRound;
    }

    private FantasyTeam createFantasyTeam(){
        FantasyTeam fantasyTeam= new FantasyTeam();
        fantasyTeam.setTeamId(counter++);
        return fantasyTeam;
    }

    private int countNumberOfMatches(FantasyTeam fantasyTeam, List<List<FantasyMatch>> list){
        return countNumberOfHomeMatches(fantasyTeam,list) + countNumberOfAwayMatches(fantasyTeam,list);
    }
    private int countNumberOfHomeMatches(FantasyTeam fantasyTeam, List<List<FantasyMatch>> list){
        int matchCounter = 0;
        for(List<FantasyMatch> fantasyMatchList: list){
            for(FantasyMatch fantasyMatch: fantasyMatchList){
                if(fantasyMatch.getHomeTeam().equals(fantasyTeam)){
                    matchCounter++;
                }
            }
        }
        return matchCounter;
    }
    private int countNumberOfAwayMatches(FantasyTeam fantasyTeam, List<List<FantasyMatch>> list){
        int matchCounter = 0;
        for(List<FantasyMatch> fantasyMatchList: list){
            for(FantasyMatch fantasyMatch: fantasyMatchList){
                if(fantasyMatch.getAwayTeam().equals(fantasyTeam)){
                    matchCounter++;
                }
            }
        }
        return matchCounter;
    }
}
