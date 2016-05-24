package org.tpl.integration.dao.fantasy;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

public class JDBCFantasyRoundDaoTest extends TestCase {

    JDBCFantasyRoundDao jdbcFantasyRoundDao;

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyRoundDao = new JDBCFantasyRoundDao();
        jdbcFantasyRoundDao.setDataSource(dataSource);
    }

    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcFantasyRoundDao = null;
    }

    @Test
    public void testSimpleSaveFantasyRound(){
        FantasyRound fantasyRound = createFantasyRound();
        jdbcFantasyRoundDao.saveOrUpdateFantasyRound(fantasyRound);
        assertTrue("fantasyRound.id > 0", fantasyRound.getFantasyRoundId() > 0 );
    }

    @Test
    public void testSaveFantasyRound(){
        FantasyRound fantasyRound = createFantasyRound();
        jdbcFantasyRoundDao.saveOrUpdateFantasyRound(fantasyRound);
        assertTrue("fantasyRound.id > 0", fantasyRound.getFantasyRoundId() > 0 );
        FantasyRound fantasyRound2 = jdbcFantasyRoundDao.getFantasyRoundById(fantasyRound.getFantasyRoundId());
        assertEquals("fantasyRound.id = fantasyRound2.id",fantasyRound.getFantasyRoundId(),fantasyRound2.getFantasyRoundId());
        assertEquals("fantasyRound.round = fantasyRound2.round",fantasyRound.getRound(),fantasyRound2.getRound());
        assertEquals("fantasyRound.openForChange = fantasyRound2.openForChange",fantasyRound.isOpenForChange(),fantasyRound2.isOpenForChange());
        assertEquals("fantasyRound.fantasyseason.id = fantasyRound2.fantasyseason.id",fantasyRound.getFantasySeason().getFantasySeasonId() ,fantasyRound2.getFantasySeason().getFantasySeasonId());
        assertNotNull("fantasyRound2 closeDate is not null", fantasyRound2.getCloseDate());
    }

    @Test
    public void testGetFantasyRoundById(){
        FantasyRound fantasyRound = jdbcFantasyRoundDao.getFantasyRoundById(1);
        assertTrue("fantasyRound.getFantasySeasonId() == 1", fantasyRound.getFantasyRoundId() == 1);
        assertTrue("fantasyRound.round() == 1", fantasyRound.getRound() == 1);
        assertFalse("fantasyRound.isOpenForChange is false", fantasyRound.isOpenForChange());
        assertTrue("fantasyRound.getFantasySeason().getFantasySeasonId() == 1", fantasyRound.getFantasySeason().getFantasySeasonId() == 1);
    }

    @Test
    public void testAddMatchToRound(){
        FantasyRound fantasyRound = createFantasyRound();
        jdbcFantasyRoundDao.saveOrUpdateFantasyRound(fantasyRound);
        boolean value = jdbcFantasyRoundDao.addMatchToRound(1,fantasyRound.getFantasyRoundId());
        assertTrue("matchAdded", value);
        value = jdbcFantasyRoundDao.addMatchToRound(1,fantasyRound.getFantasyRoundId());
        assertFalse("matchAdded", value);
    }

    @Test
    public void testRemoveMatchFromRound(){
        boolean value = jdbcFantasyRoundDao.removeMatchFromRound(1,1);
        assertTrue("matchRemoved", value);
        value = jdbcFantasyRoundDao.removeMatchFromRound(1,1);
        assertFalse("matchNotRemoved", value);
    }

    @Test
    public void testGetMatchIdsForRound(){
        List<Integer> matchIdList = jdbcFantasyRoundDao.getMatchIdsForRound(1);
        assertTrue("matchList.size() == 3",matchIdList.size() == 3 );

    }

    @Test
    public void testGetFantasyRoundByNumberAndSeason(){
        FantasyRound fantasyRound = jdbcFantasyRoundDao.getFantasyRoundByNumberAndSeason(1,1);
        assertEquals("round is 1", 1, fantasyRound.getRound());
        fantasyRound = jdbcFantasyRoundDao.getFantasyRoundByNumberAndSeason(0,1);
        assertNull("round is null", fantasyRound);
    }

    private FantasyRound createFantasyRound(){
        FantasyRound fantasyRound = new FantasyRound();
        fantasyRound.setRound(1);
        fantasyRound.setFantasySeason(new FantasySeason());
        fantasyRound.getFantasySeason().setFantasySeasonId(0);
        fantasyRound.setCloseDate(new Date());
        return fantasyRound;
    }
}