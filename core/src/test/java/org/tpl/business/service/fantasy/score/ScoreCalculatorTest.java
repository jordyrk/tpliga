package org.tpl.business.service.fantasy.score;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class ScoreCalculatorTest {

    @Test
    public void testGetStandardDeviation(){
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        assertEquals("2 -> 0.5", 0.5, scoreCalculator.getStandardDeviation(2) );
        assertEquals("2.1 -> 0.5", 0.5, scoreCalculator.getStandardDeviation(2.1) );
        assertEquals("7.5 -> 0.35", 0.35, scoreCalculator.getStandardDeviation(7.5) );
        assertEquals("8 -> 0.35", 0.35, scoreCalculator.getStandardDeviation(8) );
        assertEquals("11 -> 0.35", 0.35, scoreCalculator.getStandardDeviation(11) );
        assertEquals("11.5 -> 0.34", 0.34, scoreCalculator.getStandardDeviation(11.5) );
        assertEquals("12 -> 0.34", 0.34, scoreCalculator.getStandardDeviation(12) );
        assertEquals("13 -> 0.32", 0.32, scoreCalculator.getStandardDeviation(13) );
        assertEquals("-1000 -> 0.5", 0.5, scoreCalculator.getStandardDeviation(-1000) );
        assertEquals("1000 -> 0.32", 0.32, scoreCalculator.getStandardDeviation(1000) );
    }
    @Test
    public void testBaseScore(){
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        assertEquals("20 -> 0,0", 0, scoreCalculator.calculateBaseScore(20)[0]);
        assertEquals("29 -> 0,0", 0, scoreCalculator.calculateBaseScore(29)[0]);
        assertEquals("30 -> 1,1", 1, scoreCalculator.calculateBaseScore(30)[0]);
        assertEquals("47 -> 1,1",1, scoreCalculator.calculateBaseScore(47)[0]);
        assertEquals("48 -> 2,2", 2, scoreCalculator.calculateBaseScore(48)[0]);
        assertEquals("70 -> 2,2", 2, scoreCalculator.calculateBaseScore(70)[0]);
        assertEquals("-1000 -> 0,0", 0, scoreCalculator.calculateBaseScore(-1000)[0]);
        assertEquals("1000 -> 2,2", 2, scoreCalculator.calculateBaseScore(1000)[0]);

    }

    @Test
    public void testDrawScenarioes(){
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        int[] score = scoreCalculator.calculateScore(20,20);
        assertEquals("homegoals = 0", 0, score[0]);
        assertEquals("awaygoals = 0", 0, score[1]);

        score = scoreCalculator.calculateScore(30,30);
        assertEquals("homegoals = 1", 1, score[0]);
        assertEquals("awaygoals = 1", 1, score[1]);

        score = scoreCalculator.calculateScore(50,50);
        assertEquals("homegoals = 2", 2, score[0]);
        assertEquals("awaygoals = 2", 2, score[1]);

        score = scoreCalculator.calculateScore(-20,-20);
        assertEquals("homegoals = 0", 0, score[0]);
        assertEquals("awaygoals = 0", 0, score[1]);

        score = scoreCalculator.calculateScore(110,110);
        assertEquals("homegoals = 2", 2, score[0]);
        assertEquals("awaygoals = 2", 2, score[1]);

    }
    @Test
    @Ignore
    public void testWinLossScenarioes(){
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        int[] score = scoreCalculator.calculateScore(30,20);
        assertEquals("homegoals = 2", 2, score[0]);
        assertEquals("awaygoals = 0", 0, score[1]);

        score = scoreCalculator.calculateScore(40,20);
        assertEquals("homegoals = 4", 4, score[0]);
        assertEquals("awaygoals = 1", 1, score[1]);

        score = scoreCalculator.calculateScore(80,20);
        assertEquals("homegoals = 7",7, score[0]);
        assertEquals("awaygoals = 2", 2, score[1]);

        score = scoreCalculator.calculateScore(23,20);
        assertEquals("homegoals = 0", 0, score[0]);
        assertEquals("awaygoals = 0", 0, score[1]);

        score = scoreCalculator.calculateScore(12,19);
        assertEquals("homegoals = 0", 0, score[0]);
        assertEquals("awaygoals = 1", 1, score[1]);
    }
}


