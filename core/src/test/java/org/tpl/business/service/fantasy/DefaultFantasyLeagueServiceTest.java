package org.tpl.business.service.fantasy;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.tpl.business.model.fantasy.*;
import org.tpl.integration.dao.fantasy.FantasyLeagueStandingDao;
import org.tpl.integration.dao.fantasy.JdbcFantasyLeagueStandingDao;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DefaultFantasyLeagueServiceTest{
    FantasyService fantasyService;
    FantasyMatchService fantasyMatchService;
    JdbcFantasyLeagueStandingDao jdbcFantasyLeagueStandingDao;
    TestableFantasyLeagueService service;

    @Before
    public void setUp() throws Exception {
        fantasyService = mock(FantasyService.class);
        fantasyMatchService = mock(FantasyMatchService.class);
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyLeagueStandingDao = new JdbcFantasyLeagueStandingDao();
        jdbcFantasyLeagueStandingDao.setDataSource(dataSource);
        service = new TestableFantasyLeagueService();
        service.setFantasyService(fantasyService);
        service.setFantasyMatchService(fantasyMatchService);
        service.setFantasyLeagueStandingDao(jdbcFantasyLeagueStandingDao);
    }

    @Test
    public void testUpdateLeagueStandings() throws Exception {
        FantasyRound fantasyRound = createFantasyRound(1);
        when(fantasyService.getPreviousFantasyRound(anyInt(), anyInt())).thenReturn(null);
        when(fantasyService.getFantasyRoundById(anyInt())).thenReturn(fantasyRound);
        when(fantasyMatchService.getFantasyMatchByLeagueIdAndRoundId(anyInt(),anyInt())).thenReturn(createFantasyMatches());
        service.updateLeagueStandings(1);
    }

    @Test
    public void testGetLastUpdatedLeagueStandingWhenRoundIsUpdated() throws Exception {
        when(fantasyService.getCurrentFantasyRoundBySeasonId(anyInt())).thenReturn(createFantasyRound(2));
        when(fantasyService.getFantasyRoundById(2)).thenReturn(createFantasyRound(2));
        List<FantasyLeagueStanding> list = service.getLastUpdatedFantasyLeagueStandingsByLeague(3);
        assertEquals("list size is 2",2, list.size());
    }

    @Test
    public void testGetLastUpdatedLeagueStandingWhenRoundIsNotUpdated() throws Exception {
        when(fantasyService.getCurrentFantasyRoundBySeasonId(anyInt())).thenReturn(createFantasyRound(3));
        when(fantasyService.getFantasyRoundById(2)).thenReturn(createFantasyRound(2));
        when(fantasyService.getFantasyRoundById(3)).thenReturn(createFantasyRound(3));
        List<FantasyLeagueStanding> list = service.getLastUpdatedFantasyLeagueStandingsByLeague(3);
        assertEquals("list size is 2",2, list.size());
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

    private List<FantasyMatch> createFantasyMatches(){
        List<FantasyMatch> list = new ArrayList<FantasyMatch>();
        FantasyMatch fantasyMatch = new FantasyMatch();
        list.add(fantasyMatch);
        return list;


    }

    private class TestableFantasyLeagueService extends DefaultFantasyLeagueService{
        @Override
        public List<FantasyLeague> getAllFantasyLeagues(int fantasySeasonId) {
            List<FantasyLeague> fantasyLeagueList = new ArrayList<FantasyLeague>();
            FantasyLeague fantasyLeague = new FantasyLeague();
            fantasyLeague.setId(1);
            fantasyLeagueList.add(fantasyLeague);
            return fantasyLeagueList;
        }

        @Override
        public void saveOrUpdateFantasyLeagueStanding(FantasyLeagueStanding fantasyLeagueStanding) {

        }

        @Override
        public FantasyLeague getFantasyLeagueById(int id) {
            FantasyLeague fantasyLeague = new FantasyLeague();
            fantasyLeague.setId(id);
            fantasyLeague.setFantasySeason(new FantasySeason());

            return fantasyLeague;

        }

        @Override
        protected List<FantasyLeague> getAllFantasyLeaguesForFantasyRound(int fantasyRoundId) {
            return getAllFantasyLeagues(0);
        }

        public void setFantasyService(FantasyService fantasyService) {
            this.fantasyService = fantasyService;
        }

        public void setFantasyMatchService(FantasyMatchService fantasyMatchService) {
            this.fantasyMatchService = fantasyMatchService;
        }

        public void setFantasyLeagueStandingDao(FantasyLeagueStandingDao fantasyLeagueStandingDao) {
            this.fantasyLeagueStandingDao = fantasyLeagueStandingDao;
        }
    }

}
