package org.tpl.integration.parser;

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.Match;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static org.junit.Assert.*;

public class FantasyPremierLeagueMatchHtmlParserTest {

    private TestableFantasyPremierLeagueMatchHtmlParser allMatchedFinishedParser;
    private TestableFantasyPremierLeagueMatchHtmlParser noneMatchedFinishedParser;
    private TestableFantasyPremierLeagueMatchHtmlParser someMatchedFinishedParser;
    @Before
    public void setUp() throws Exception {
        allMatchedFinishedParser = new TestableFantasyPremierLeagueMatchHtmlParser("org/tpl/integration/parser/fantasypremierleague/fixtures_round1.html");
        noneMatchedFinishedParser = new TestableFantasyPremierLeagueMatchHtmlParser("org/tpl/integration/parser/fantasypremierleague/fixtures_round_not_played.html");
        someMatchedFinishedParser = new TestableFantasyPremierLeagueMatchHtmlParser("org/tpl/integration/parser/fantasypremierleague/fixtures_round_some_played.html");
        allMatchedFinishedParser.init(1);
        noneMatchedFinishedParser.init(1);
        someMatchedFinishedParser.init(1);



    }

    @Test
    public void shouldContainCorrectNumberOfMatches() throws Exception {
        List<Match> matches = allMatchedFinishedParser.getMatches();
        assertEquals(10, matches.size());
        matches = noneMatchedFinishedParser.getMatches();
        assertEquals(10, matches.size());
        matches = someMatchedFinishedParser.getMatches();
        assertEquals(10, matches.size());

    }

    @Test
    public void matchShouldContainCorrectTeamsAndId() throws Exception {
        Match firstMatch = allMatchedFinishedParser.getMatches().get(0);
        assertEquals(3, firstMatch.getFantasyPremierLeagueId());
        assertEquals("Man Utd", firstMatch.getHomeTeam().getFullName());
        assertEquals("Swansea", firstMatch.getAwayTeam().getFullName());

        firstMatch = noneMatchedFinishedParser.getMatches().get(7);
        assertEquals(-1, firstMatch.getFantasyPremierLeagueId());
        assertEquals("Man City", firstMatch.getHomeTeam().getFullName());
        assertEquals("Southampton", firstMatch.getAwayTeam().getFullName());

        firstMatch = someMatchedFinishedParser.getMatches().get(6);
        assertEquals(17, firstMatch.getFantasyPremierLeagueId());
        assertEquals("Hull", firstMatch.getHomeTeam().getFullName());
        assertEquals("Stoke", firstMatch.getAwayTeam().getFullName());

        firstMatch = someMatchedFinishedParser.getMatches().get(9);
        assertEquals(-1, firstMatch.getFantasyPremierLeagueId());
        assertEquals("Man City", firstMatch.getHomeTeam().getFullName());
        assertEquals("Liverpool", firstMatch.getAwayTeam().getFullName());


    }

    private class TestableFantasyPremierLeagueMatchHtmlParser extends FantasyPremierLeagueMatchHtmlParser{
        private String url;

        public TestableFantasyPremierLeagueMatchHtmlParser(String url) {

            this.url = url;
        }

        @Override
        public void init(int round) {
            InputStream is = getClass().getClassLoader().getResourceAsStream(url);
            OutputStream outputStream = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setXHTML(false);
            document = tidy.parseDOM(is,outputStream);
        }

    }
}