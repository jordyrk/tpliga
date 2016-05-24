package org.tpl.integration.parser;

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.PlayerStats;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static org.junit.Assert.*;

public class FantasyPremierLeaguePlayerStatsParserTest {

    private TestableFantasyPremierLeaguePlayerStatsParser parser;
    @Before
    public void setUp() throws Exception {
        parser = new TestableFantasyPremierLeaguePlayerStatsParser();
        parser.init(0);


    }

    @Test
    public void shouldHaveCorrectNumberOfPlayerStats() throws Exception {
        List<PlayerStats> playerStatsList = parser.importPlayerStatsForAwayTeam();
        assertEquals(14, playerStatsList.size());
        playerStatsList = parser.importPlayerStatsForHomeTeam();
        assertEquals(14, playerStatsList.size());

    }

    @Test
    public void shouldHaveCorrectStat() throws Exception {
        List<PlayerStats> playerStatsList = parser.importPlayerStatsForHomeTeam();
        assertEquals(2,playerStatsList.get(0).getSaves());
        assertEquals(90,playerStatsList.get(0).getPlayedMinutes());
        assertEquals(10,playerStatsList.get(0).getEaSportsPPI());
        assertEquals(1,playerStatsList.get(2).getAssists());
        assertEquals(1,playerStatsList.get(8).getYellowCard());
        assertEquals(1,playerStatsList.get(13).getGoals());


    }

    private class TestableFantasyPremierLeaguePlayerStatsParser extends FantasyPremierLeaguePlayerStatsParser{
        @Override
        public void init(int fantasyPLMatchId) {
            InputStream is = getClass().getClassLoader().getResourceAsStream("org/tpl/integration/parser/fantasypremierleague/matchstats.html");
            OutputStream outputStream = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setXHTML(false);
            document = tidy.parseDOM(is,outputStream);
        }
    }

}