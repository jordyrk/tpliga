package org.tpl.integration.parser;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tpl.business.model.statsfc.MonthEnum;
import org.tpl.business.service.LeagueService;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by jordyr on 9/4/16.
 */
public class StatsFcPlayerStatsJsonTest {

    @Mock
    Response response;

    @Mock
    private LeagueService leagueService;

    private TestablePlayerStatsParser testablePlayerStatsParser;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        testablePlayerStatsParser = new TestablePlayerStatsParser("api-key");

        InputStream is = getClass().getClassLoader().getResourceAsStream("org/tpl/integration/parser/statsfc/stats.json");
        String json = IOUtils.toString(is);
        when(response.readEntity(String.class)).thenReturn(json);

    }

    @Test
    public void shouldParseIntoList() throws Exception {
        List matches = testablePlayerStatsParser.getMatches(MonthEnum.AUGUST);
        assertTrue(matches.size() == 30);

    }

    private class TestablePlayerStatsParser extends StatsFcPlayerStatsJsonImpl{
        public TestablePlayerStatsParser(String key) {
            super(key);
        }

        @Override
        protected Response doRequest(MonthEnum monthEnum) {

            return response;

        }

    }
}
