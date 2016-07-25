package org.tpl.integration.parser;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tpl.business.model.Match;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class MatchParserNRKJsonImplTest {
    @Mock
    Response response;

    @Mock
    LeagueService leagueService;


    TestableMatchParser testableMatchParser;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testableMatchParser = new TestableMatchParser();
        testableMatchParser.setLeagueService(leagueService);
        mockTeam("Bournemouth");
        mockTeam("Burnley");
        mockTeam("Swansea City");
        mockTeam("Manchester United");
        InputStream is = getClass().getClassLoader().getResourceAsStream("org/tpl/integration/parser/fantasypremierleague/fixturelist_nrk.json");
        String json = IOUtils.toString(is);
        when(response.readEntity(String.class)).thenReturn(json);

    }

    private void mockTeam(String name) {
        List<Team> list = new ArrayList<>();
        Team team = new Team();
        team.setShortName(name);
        list.add(team);
        when(leagueService.findTeamByName(name)).thenReturn(list);
    }

    @Test
    public void shouldParseMatchesCorrectly() throws Exception {


        List<Match> matches = testableMatchParser.getMatches();
        assertTrue(matches.size() == 2);
        Match match = matches.get(0);
        assertTrue(match.getAwayGoals() == 0);
        assertTrue(match.getHomeGoals() == 0);
        assertTrue(match.getLeagueRound().getRound() == 1);
        assertEquals(match.getHomeTeam().getShortName(), "Bournemouth");
        assertEquals(match.getAwayTeam().getShortName(), "Manchester United");

    }

    private class TestableMatchParser extends MatchParserNRKJsonImpl{
        @Override
        protected Response doRequest() {

            return response;

        }

    }
}