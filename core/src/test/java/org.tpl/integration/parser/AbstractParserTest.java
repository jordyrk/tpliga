package org.tpl.integration.parser;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.tpl.business.service.DefaultLeagueService;
import org.tpl.integration.dao.*;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;
import javax.sql.DataSource;
/*
Created by jordyr, 17.okt.2010

*/

public abstract class AbstractParserTest extends TestCase{

    protected JDBCLeagueDao jdbcLeagueDao;
    protected JDBCMatchDao jdbcMatchDao;
    protected JDBCTeamDao jdbcTeamDao;
    protected JDBCPlayerDao jdbcPlayerDao;
    protected JDBCSeasonDao jdbcSeasonDao;
    protected DefaultLeagueService defaultLeagueService;

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcLeagueDao = new JDBCLeagueDao();
        jdbcMatchDao = new JDBCMatchDao();
        jdbcTeamDao = new JDBCTeamDao();
        jdbcPlayerDao = new JDBCPlayerDao();
        jdbcSeasonDao = new JDBCSeasonDao();
        jdbcLeagueDao.setDataSource(dataSource);
        jdbcMatchDao.setDataSource(dataSource);
        jdbcTeamDao.setDataSource(dataSource);
        jdbcPlayerDao.setDataSource(dataSource);
        jdbcSeasonDao.setDataSource(dataSource);
        defaultLeagueService = new DefaultLeagueService();
        defaultLeagueService.setLeagueDao(jdbcLeagueDao);
        defaultLeagueService.setMatchDao(jdbcMatchDao);
        defaultLeagueService.setPlayerDao(jdbcPlayerDao);
        defaultLeagueService.setSeasonDao(jdbcSeasonDao);
        defaultLeagueService.setTeamDao(jdbcTeamDao);

    }

    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcLeagueDao = null;
        jdbcMatchDao = null;
        jdbcTeamDao = null;
        jdbcPlayerDao = null;
        jdbcSeasonDao = null;
        defaultLeagueService = null;
    }

}
