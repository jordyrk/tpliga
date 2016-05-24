package org.tpl.integration.dao.fantasy;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.Season;
import org.tpl.integration.dao.JDBCSeasonDao;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
/*
Created by jordyr, 26.nov.2010

*/

public class JDBCFantasySeasonDaoTest extends TestCase {

    JDBCFantasySeasonDao jdbcFantasySeasonDao;
    JDBCFantasyCompetitionDao jdbcFantasyCompetitionDao;
    JDBCSeasonDao jdbcSeasonDao;
    JDBCFantasyRoundDao jdbcFantasyRoundDao;


    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasySeasonDao = new JDBCFantasySeasonDao();
        jdbcFantasySeasonDao.setDataSource(dataSource);
        jdbcSeasonDao = new JDBCSeasonDao();
        jdbcSeasonDao.setDataSource(dataSource);
        jdbcFantasyCompetitionDao = new JDBCFantasyCompetitionDao();
        jdbcFantasyCompetitionDao.setDataSource(dataSource);
        jdbcFantasyRoundDao = new JDBCFantasyRoundDao();
        jdbcFantasyRoundDao.setDataSource(dataSource);

    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcFantasySeasonDao = null;
        jdbcFantasyCompetitionDao  = null;
        jdbcFantasyRoundDao = null;
    }
    @Test
    public void testSimpleSaveFantasySeason(){
        FantasySeason fantasySeason = createFantasySeason();
        jdbcFantasySeasonDao.saveOrUpdateFantasySeason(fantasySeason);
        assertTrue("fantasySeason.id > 0", fantasySeason.getFantasySeasonId() > 0 );
    }

    @Test
    public void testSaveFantasySeason(){
        FantasySeason fantasySeason =createFantasySeason();

        jdbcFantasySeasonDao.saveOrUpdateFantasySeason(fantasySeason);
        assertTrue("fantasySeason.id > 0", fantasySeason.getFantasySeasonId() >0 );
        FantasySeason fantasySeason2 = jdbcFantasySeasonDao.getFantasySeasonById(fantasySeason.getFantasySeasonId());
        assertEquals("fantasySeason.id = fantasySeason2.id",fantasySeason.getFantasySeasonId(),fantasySeason2.getFantasySeasonId());
        assertEquals("fantasySeason.isActiveSeason", fantasySeason.isActiveSeason(),fantasySeason.isActiveSeason());
        assertEquals("fantasySeason.getMaxTeamPrice", fantasySeason.getMaxTeamPrice(),fantasySeason.getMaxTeamPrice());
        assertEquals("fantasySeason.getNumberOfTransfersPerRound", fantasySeason.getNumberOfTransfersPerRound(),fantasySeason.getNumberOfTransfersPerRound());
        assertEquals("fantasySeason.getStartingNumberOfTransfers", fantasySeason.getStartingNumberOfTransfers(),fantasySeason.getStartingNumberOfTransfers());
        assertEquals("fantasySeason.Name = fantasySeason2.Name",fantasySeason.getName(),fantasySeason2.getName());
        assertEquals("fantasySeason.registrationActive = fantasySeason2.registrationActive",fantasySeason.isRegistrationActive(),fantasySeason2.isRegistrationActive());
        assertEquals("fantasySeason.Season.id = fantasySeason2.Season.id",fantasySeason.getSeason().getSeasonId(),fantasySeason2.getSeason().getSeasonId());

    }

    @Test
    public void testGetFantasySeasonById(){
        FantasySeason fantasySeason = jdbcFantasySeasonDao.getFantasySeasonById(1);
        assertTrue("fantasySeason.getFantasySeasonId() == 1", fantasySeason.getFantasySeasonId() == 1);
        assertTrue("fantasySeason.getSeason().getSeasonId() == 1", fantasySeason.getSeason().getSeasonId() == 1);
        assertTrue("fantasySeason.getName() == My season", fantasySeason.getName().equals("My season"));
        assertTrue("fantasySeason.isActiveSeason", fantasySeason.isActiveSeason());
        assertTrue("fantasySeason.getDefaultFantasyCompetition().getFantasyCompetitionId() == 1", fantasySeason.getDefaultFantasyCompetition().getFantasyCompetitionId() == 1);
        assertTrue("fantasySeason.getCurrentRound().getFantasyRoundId()== 4", fantasySeason.getCurrentRound().getFantasyRoundId()== 4);
    }

    @Test
    public void testActiveSeason(){
        FantasySeason fantasySeason = jdbcFantasySeasonDao.getFantasySeasonById(1);
        assertTrue("activeSeason", fantasySeason.isActiveSeason());

        FantasySeason fantasySeason2 =createFantasySeason();
        jdbcFantasySeasonDao.saveOrUpdateFantasySeason(fantasySeason2);
        assertTrue("activeSeason", fantasySeason2.isActiveSeason());

        fantasySeason = jdbcFantasySeasonDao.getFantasySeasonById(1);
        assertFalse("activeSeason", fantasySeason.isActiveSeason());

    }

    private FantasySeason createFantasySeason(){
        FantasySeason fantasySeason = new FantasySeason();
        fantasySeason.setName("test");
        fantasySeason.setActiveSeason(true);
        fantasySeason.setMaxTeamPrice(70000);
        fantasySeason.setNumberOfTransfersPerRound(7);
        fantasySeason.setStartingNumberOfTransfers(7);
        Season season = jdbcSeasonDao.getSeasonById(1);
        fantasySeason.setSeason(season);
        FantasyCompetition fantasyCompetition = new FantasyCompetition();
        fantasyCompetition.setName("Standard");
        fantasyCompetition.setFantasySeason(fantasySeason);
        fantasyCompetition.setFantasyCompetitionId(19);
        fantasySeason.setDefaultFantasyCompetition(fantasyCompetition);
        FantasyRound currentRound = jdbcFantasyRoundDao.getFantasyRoundById(1);
        fantasySeason.setCurrentRound(currentRound);
        return fantasySeason;
    }
}