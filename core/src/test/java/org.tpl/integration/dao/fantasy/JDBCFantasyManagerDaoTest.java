package org.tpl.integration.dao.fantasy;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyManager;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

public class JDBCFantasyManagerDaoTest extends TestCase {

    JDBCFantasyManagerDao jdbcFantasyManagerDao;
    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyManagerDao = new JDBCFantasyManagerDao();
        jdbcFantasyManagerDao.setDataSource(dataSource);
    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcFantasyManagerDao = null;
    }

    @Test
    public void testSimpleSaveFantasyManager(){
        FantasyManager fantasyManager = createFantasyManager();
        jdbcFantasyManagerDao.saveOrUpdateFantasyManager(fantasyManager);
        assertTrue("fantasyManager.id > 0", fantasyManager.getManagerId() > 0 );
    }

    @Test
    public void testSaveFantasyManager(){
        FantasyManager fantasyManager = createFantasyManager();

        jdbcFantasyManagerDao.saveOrUpdateFantasyManager(fantasyManager);
        assertTrue("fantasyManager.id > 0", fantasyManager.getManagerId() > 0 );
        FantasyManager fantasyManager2 = jdbcFantasyManagerDao.getFantasyManagerById(fantasyManager.getManagerId());
        assertEquals("fantasyManager.id = fantasyManager2.id",fantasyManager.getManagerId(),fantasyManager2.getManagerId());
        assertEquals("fantasyManager.managerName = fantasyManager2.managerName",fantasyManager.getManagerName(),fantasyManager2.getManagerName());
        assertEquals("fantasyManager.userid = fantasyManager2.userid",fantasyManager.getUserId(),fantasyManager2.getUserId());
    }

    @Test
    public void testGetFantasyManagerById(){
        FantasyManager fantasyManager = jdbcFantasyManagerDao.getFantasyManagerById(1);
        assertTrue(" fantasyManager.getManagerId() == 1", fantasyManager.getManagerId() == 1);
        assertTrue("fantasyManager.getManagerName() == Koren", fantasyManager.getManagerName().equals("Koren"));
        assertTrue("fantasyManager.getUserId()== koren@test.no", fantasyManager.getUserId().equals("koren@test.no"));
        assertTrue("fantasyManager.isActive", fantasyManager.isActive());
    }

    @Test
    public void testGetInActiveFantasyManagerById(){
        FantasyManager fantasyManager = jdbcFantasyManagerDao.getFantasyManagerById(3);
        assertFalse("fantasyManager.isActive", fantasyManager.isActive());
    }

    @Test
    public void testGetFantasyManagerByUserId(){
        FantasyManager fantasyManager = jdbcFantasyManagerDao.getFantasyManagerByUserId("koren@test.no");
        assertTrue(" fantasyManager.getManagerId() == 1", fantasyManager.getManagerId() == 1);
        assertTrue("fantasyManager.getManagerName() == Koren", fantasyManager.getManagerName().equals("Koren"));
        assertTrue("fantasyManager.getUserId()== koren@test.no", fantasyManager.getUserId().equals("koren@test.no"));
    }

    @Test
    public void testDeleteManager(){
        FantasyManager fantasyManager = createFantasyManager();
        jdbcFantasyManagerDao.saveOrUpdateFantasyManager(fantasyManager);
        fantasyManager = jdbcFantasyManagerDao.getFantasyManagerByUserId("test@test.no");
        assertNotNull(fantasyManager);
        jdbcFantasyManagerDao.deleteFantasyManager("test@test.no");
        FantasyManager deletedManager = jdbcFantasyManagerDao.getFantasyManagerByUserId("test@test.no");
        assertNull(deletedManager);
    }

    @Test
    public void testGetAllFantasyManagers() throws Exception {
        List<FantasyManager> managerList = jdbcFantasyManagerDao.getAllFantasyManagers();
        assertEquals("managerList size is 3", 3, managerList.size());
    }

    @Test
    public void testGetAllActiveFantasyManagers() throws Exception {
        List<FantasyManager> managerList = jdbcFantasyManagerDao.getAllActiveFantasyManagers();
        assertEquals("managerList size is 2", 2, managerList.size());
    }


    private FantasyManager createFantasyManager(){
        FantasyManager fantasyManager = new FantasyManager();
        fantasyManager.setManagerName("Kalle Klovn");
        fantasyManager.setUserId("test@test.no");
        fantasyManager.setActive(true);
        return fantasyManager;
    }
}