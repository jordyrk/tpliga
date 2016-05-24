package org.tpl.business.model.fantasy;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class FantasyCupTest {

    @Test
    public void testGetNumberOfQualifiedTeamsWhenNumbersAreEven(){
        FantasyCup fantasyCup = new FantasyCup();
        fantasyCup.setNumberOfGroups(4);
        fantasyCup.setNumberOfTeams(20);
        fantasyCup.setNumberOfQualifiedTeamsFromGroups(16);
        int numberOf1PosTeams = fantasyCup.getNumberOfQualifiedTeams(1);
        assertEquals(4, numberOf1PosTeams);
        int numberOf5PosTeams = fantasyCup.getNumberOfQualifiedTeams(5);
        assertEquals(0,numberOf5PosTeams);
    }
    @Test
    public void testGetNumberOfQualifiedTeamsWhenNumbersAreOdd(){
        FantasyCup fantasyCup = new FantasyCup();
        fantasyCup.setNumberOfGroups(5);
        fantasyCup.setNumberOfTeams(25);
        fantasyCup.setNumberOfQualifiedTeamsFromGroups(16);
        int numberOf1PosTeams = fantasyCup.getNumberOfQualifiedTeams(1);
        assertEquals(5, numberOf1PosTeams);
        int numberOf2PosTeams = fantasyCup.getNumberOfQualifiedTeams(2);
        assertEquals(5, numberOf2PosTeams);
        int numberOf3PosTeams = fantasyCup.getNumberOfQualifiedTeams(3);
        assertEquals(5, numberOf3PosTeams);
        int numberOf4PosTeams = fantasyCup.getNumberOfQualifiedTeams(4);
        assertEquals(1, numberOf4PosTeams);
        int numberOf5PosTeams = fantasyCup.getNumberOfQualifiedTeams(5);
        assertEquals(0,numberOf5PosTeams);
    }

    public void testGetNumberOfQualifiedTeamsWhenNumbersAreObscure(){
        FantasyCup fantasyCup = new FantasyCup();
        fantasyCup.setNumberOfGroups(13);
        fantasyCup.setNumberOfTeams(13*5);
        fantasyCup.setNumberOfQualifiedTeamsFromGroups(16);
        int numberOf1PosTeams = fantasyCup.getNumberOfQualifiedTeams(1);
        assertEquals(13, numberOf1PosTeams);
        int numberOf2PosTeams = fantasyCup.getNumberOfQualifiedTeams(2);
        assertEquals(3, numberOf2PosTeams);
        int numberOf3PosTeams = fantasyCup.getNumberOfQualifiedTeams(3);
        assertEquals(0, numberOf3PosTeams);
        int numberOf4PosTeams = fantasyCup.getNumberOfQualifiedTeams(4);
        assertEquals(0, numberOf4PosTeams);
        int numberOf5PosTeams = fantasyCup.getNumberOfQualifiedTeams(5);
        assertEquals(0,numberOf5PosTeams);
    }




}
