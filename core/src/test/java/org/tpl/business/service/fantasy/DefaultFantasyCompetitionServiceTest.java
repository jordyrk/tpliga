package org.tpl.business.service.fantasy;
/*
Created by jordyr, 04.02.11

*/

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.*;
import org.tpl.integration.dao.fantasy.FantasyCompetitionStandingDao;
import org.tpl.integration.dao.fantasy.JdbcFantasyCompetitionStandingDao;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultFantasyCompetitionServiceTest {
    FantasyService fantasyService;
    FantasyMatchService fantasyMatchService;
    JdbcFantasyCompetitionStandingDao fantasyCompetitionStandingDao;
    TestableFantasyLeagueService service;

    @Before
    public void setUp() throws Exception {
        fantasyService = mock(FantasyService.class);
        fantasyMatchService = mock(FantasyMatchService.class);
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        fantasyCompetitionStandingDao = new JdbcFantasyCompetitionStandingDao();
        fantasyCompetitionStandingDao.setDataSource(dataSource);
        service = new TestableFantasyLeagueService();
        service.setFantasyService(fantasyService);
        service.setFantasyMatchService(fantasyMatchService);
        service.setFantasyCompetitionStandingDao(fantasyCompetitionStandingDao);

    }
    @Test
    public void testGetLastUpdatedCompetitionStandingWhenRoundIsUpdated() throws Exception {
        when(fantasyService.getCurrentFantasyRoundBySeasonId(anyInt())).thenReturn(createFantasyRound(3));
        when(fantasyService.getFantasyRoundById(3)).thenReturn(createFantasyRound(3));
        List<FantasyCompetitionStanding> list = service.getLastUpdatedFantasyCompetitionStandingsByCompetition(1);
        assertEquals("list size = 2", 2, list.size());
    }

    @Test
    public void testGetLastUpdatedCompetitionStandingWhenRoundIsNotUpdated() throws Exception {
        when(fantasyService.getCurrentFantasyRoundBySeasonId(anyInt())).thenReturn(createFantasyRound(4));
        when(fantasyService.getFantasyRoundById(4)).thenReturn(createFantasyRound(4));
        when(fantasyService.getFantasyRoundById(3)).thenReturn(createFantasyRound(3));
        List<FantasyCompetitionStanding> list = service.getLastUpdatedFantasyCompetitionStandingsByCompetition(1);
        assertEquals("list size = 2", 2, list.size());
    }

     private FantasyRound createFantasyRound(int fantasyRoundNumber) {
        FantasyRound fantasyRound = new FantasyRound();
        fantasyRound.setFantasyRoundId(fantasyRoundNumber);
        fantasyRound.setRound(fantasyRoundNumber);
        FantasySeason fantasySeason = new FantasySeason();
        fantasySeason.setFantasySeasonId(1);
        fantasyRound.setFantasySeason(fantasySeason);
        return fantasyRound;

    }

    private class TestableFantasyLeagueService extends DefaultFantasyCompetitionService{



        public void setFantasyService(FantasyService fantasyService) {
            this.fantasyService = fantasyService;
        }

        public void setFantasyMatchService(FantasyMatchService fantasyMatchService) {
            this.fantasyMatchService = fantasyMatchService;
        }

        public void setFantasyCompetitionStandingDao(FantasyCompetitionStandingDao fantasyCompetitionStandingDao) {
        this.fantasyCompetitionStandingDao = fantasyCompetitionStandingDao;
    }

        public FantasyCompetition getFantasyCompetitionById(int fantasyCompetitionId) {
            FantasyCompetition fantasyCompetition = new FantasyCompetition();
            fantasyCompetition.setFantasyCompetitionId(fantasyCompetitionId);
            FantasySeason fantasySeason = new FantasySeason();
            fantasyCompetition.setFantasySeason(fantasySeason);
            return fantasyCompetition;
        }

    }
}
