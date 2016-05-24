package org.tpl.integration.dao;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Season;
import org.tpl.business.model.Team;
import org.tpl.integration.dao.util.DateUtil;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
/*
Created by jordyr, 17.okt.2010

*/

public class JDBCSeasonDaoTest extends TestCase {

    JDBCSeasonDao jdbcSeasonDao = new JDBCSeasonDao();
    JDBCTeamDao jdbcTeamDao = new JDBCTeamDao();
    DateUtil dateUtil = new DateUtil();

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcSeasonDao = new JDBCSeasonDao();
        jdbcSeasonDao.setDataSource(dataSource);
        jdbcTeamDao = new JDBCTeamDao();
        jdbcTeamDao.setDataSource(dataSource);
    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcSeasonDao = null;

    }

    @Test
    public void testSimpleSaveSeason(){
        Season season = createSeason();
        jdbcSeasonDao.saveOrUpdateSeason(season);
        assertTrue("season.id > 0", season.getSeasonId() > 0 );
    }

    @Test
    public void testSaveSeason(){
        Season season =createSeason();
        season.setNumberOfTeams(20);
        season.setStartDate(new Date());
        season.setEndDate(new Date());
        jdbcSeasonDao.saveOrUpdateSeason(season);
        assertTrue("season.id > 0", season.getSeasonId() >0 );
        Season season2 = jdbcSeasonDao.getSeasonById(season.getSeasonId());
        assertEquals("season.id = season2.id",season.getSeasonId(),season2.getSeasonId());
        assertEquals("season.NumberOfTeams = season2.NumberOfTeams",season.getNumberOfTeams(),season2.getNumberOfTeams());
        assertEquals("season.StartDate.day = season2.StartDate.day",dateUtil.getDay(season.getStartDate()),dateUtil.getDay(season2.getStartDate()));
        assertEquals("season.StartDate.month = season2.StartDate.mont",dateUtil.getMonth(season.getStartDate()),dateUtil.getMonth(season2.getStartDate()));
        assertEquals("season.StartDate.year = season2.StartDate.day",dateUtil.getYear(season.getStartDate()),dateUtil.getYear(season2.getStartDate()));
        assertEquals("season.EndDate.day = season2.EndDate.day",dateUtil.getDay(season.getEndDate()),dateUtil.getDay(season2.getEndDate()));
        assertEquals("season.EndDate.month = season2.EndDate.mont",dateUtil.getMonth(season.getEndDate()),dateUtil.getMonth(season2.getEndDate()));
        assertEquals("season.EndDate.year = season2.EndDate.day",dateUtil.getYear(season.getEndDate()),dateUtil.getYear(season2.getEndDate()));
    }

    @Test
    public void testGetSeasonById(){
        Season season = jdbcSeasonDao.getSeasonById(1);
        assertTrue("season.id = 1", season.getSeasonId() == 1);
        assertTrue("season.leagueid= 1", season.getLeague().getLeagueId() == 1);
        assertTrue("season.NumberOfTeams = 20", season.getNumberOfTeams() == 20);

        assertTrue("season.startdate.day = 15", dateUtil.getDay(season.getStartDate()) == 15);
        assertTrue("season.startdate.month = 7", dateUtil.getMonth(season.getStartDate()) == 7);
        assertTrue("season.startdate.year = 2009", dateUtil.getYear(season.getStartDate()) == 2009);

        assertTrue("season.enddate.day = 15", dateUtil.getDay(season.getEndDate())== 15);
        assertTrue("season.enddate.month = 4", dateUtil.getMonth(season.getEndDate()) == 4);
        assertTrue("season.enddate.year = 2010", dateUtil.getYear(season.getEndDate()) == 2010);
        //TODO Test get teams as resolving dependencies are moved to the service
        //assertTrue("season.getTeams().size() ==2", season.getTeams().size() ==2);
    }

    @Test
    public void testGetSeasonsByLeagueId(){
        List<Season> seasons = jdbcSeasonDao.getSeasonsByLeagueId(1);
        assertTrue("seasons.size == 3",seasons.size() == 3);
    }

    @Test
    public void testAddLeagueToSeason(){
        Season season = jdbcSeasonDao.getSeasonById(1);
        //TODO Test get teams as resolving dependencies are moved to the service
        //assertTrue("season.getTeams().size() ==2", season.getTeams().size() ==2);
        Team team = jdbcTeamDao.getTeamById(4);
        season.getTeams().add(team);
        jdbcSeasonDao.saveOrUpdateSeason(season);
        Season season2 = jdbcSeasonDao.getSeasonById(1);
        //TODO Test get teams as resolving dependencies are moved to the service
        //assertTrue("season2.getTeams().size() ==3", season2.getTeams().size() ==3);
    }

    @Test
    public void testGetAllSeasons(){
        List<Season> seasons = jdbcSeasonDao.getAllSeasons();
        assertTrue("seasons.size == 5",seasons.size() == 5);
    }

    @Test
    public void testGetDefaultSeason() {
        Season season = jdbcSeasonDao.getDefaultSeason();
        assertTrue("season.id = 1", season.getSeasonId() == 1);
        assertTrue("season.leagueid= 1", season.getLeague().getLeagueId() == 1);
        assertTrue("season.NumberOfTeams = 20", season.getNumberOfTeams() == 20);

        assertTrue("season.startdate.day = 15", dateUtil.getDay(season.getStartDate()) == 15);
        assertTrue("season.startdate.month = 7", dateUtil.getMonth(season.getStartDate()) == 7);
        assertTrue("season.startdate.year = 2009", dateUtil.getYear(season.getStartDate()) == 2009);

        assertTrue("season.enddate.day = 15", dateUtil.getDay(season.getEndDate())== 15);
        assertTrue("season.enddate.month = 4", dateUtil.getMonth(season.getEndDate()) == 4);
        assertTrue("season.enddate.year = 2010", dateUtil.getYear(season.getEndDate()) == 2010);

    }

    @Test
    public void testSetDefaultSeason() {
        Season season =createSeason();
        season.setNumberOfTeams(18);
        jdbcSeasonDao.saveOrUpdateSeason(season);
        jdbcSeasonDao.setDefaultSeason(season);
        season = jdbcSeasonDao.getDefaultSeason();
        assertTrue("season.numberofteams = 18", season.getNumberOfTeams() == 18);
    }

    @Test
    public void testSimpleSaveLeagueRound(){
        LeagueRound leagueRound = new LeagueRound();
        leagueRound.setRound(9);
        leagueRound.setSeason(new Season());
        leagueRound.getSeason().setSeasonId(1);
        jdbcSeasonDao.saveOrUpdateLeagueRound(leagueRound);
        assertTrue("leagueRound.id > 0", leagueRound.getLeagueRoundId() >0 );
    }

    @Test
    public void testGetAllLeagueRounds(){
        List<LeagueRound> leagueRounds = jdbcSeasonDao.getAllLeagueRounds();
        assertTrue("leagueRounds.size == 9", leagueRounds.size() == 9);
    }

    @Test
    public void testGetLeagueRoundById(){
        LeagueRound leagueRound = jdbcSeasonDao.getLeagueRoundById(1);
        assertTrue("leagueRound.id = 1", leagueRound.getLeagueRoundId() == 1);
        assertTrue("leagueRound.roundnumber = 1", leagueRound.getRound() == 1);
        assertTrue("leagueRound.season.id = 1", leagueRound.getSeason().getSeasonId() == 1);
    }

    @Test
    public void testGetLeagueRoundBySeasonId(){
        List<LeagueRound> leagueRounds = jdbcSeasonDao.getLeagueRoundBySeasonId(1);
        assertTrue("leagueRounds.size == 7", leagueRounds.size() == 7);
        leagueRounds = jdbcSeasonDao.getLeagueRoundBySeasonId(2);
        assertTrue("leagueRounds.size == 2", leagueRounds.size() == 2);
    }

    @Test
    public void testGetLeagueRoundByRoundAndSeason(){
        LeagueRound leagueRound = jdbcSeasonDao.getLeagueRoundByRoundAndSeason(jdbcSeasonDao.getSeasonById(1), 1);
        assertTrue("leagueRound.id = 1", leagueRound.getLeagueRoundId() == 1);
        assertTrue("leagueRound.roundnumber = 1", leagueRound.getRound() == 1);
        assertTrue("leagueRound.season.id = 1", leagueRound.getSeason().getSeasonId() == 1);

    }

    @Test
    public void testGetFirstLeagueRoundForSeason(){
        LeagueRound leagueRound = jdbcSeasonDao.getFirstLeagueRoundForSeason(1);
        assertTrue("leagueRound.getRound == 1",leagueRound.getRound() == 1);
        assertTrue("leagueRound.getLeagueRoundId() == 1",leagueRound.getLeagueRoundId() == 1);
        leagueRound = jdbcSeasonDao.getFirstLeagueRoundForSeason(2);
        assertTrue("leagueRound.getRound == 1",leagueRound.getRound() == 1);
        assertTrue("leagueRound.getLeagueRoundId() == 1",leagueRound.getLeagueRoundId() == 8);
    }

   
    private Season createSeason(){
        Season season = new Season();
        season.setNumberOfTeams(20);
        season.setStartDate(new Date());
        season.setEndDate(new Date());
        return season;
    }
}
