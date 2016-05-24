package org.tpl.integration.dao;

import org.tpl.business.model.*;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class JDBCLeagueDaoTest extends TestCase {
    JDBCLeagueDao jdbcplDao = new JDBCLeagueDao();

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcplDao = new JDBCLeagueDao();
        jdbcplDao.setDataSource(dataSource);
    }
    @After
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcplDao = null;
    }

    @Test
    public void testSimpleSaveLeague(){
        League league= new League();

        league.setFullName("Premier League");
        league.setShortName("PL");
        jdbcplDao.saveOrUpdateLeague(league);
        assertTrue("league.id > 0", league.getLeagueId() >0 );
    }

    @Test
    public void testGetAllLeagues(){
          List<League> leagues = jdbcplDao.getAllLeagues();
        assertTrue("leagues.size == 2",leagues.size() == 2);
    }

    @Test
    public void testGetLeagueById(){
        League league = jdbcplDao.getLeagueById(1);
        assertTrue("league.id = 1", league.getLeagueId() == 1);

        assertTrue("league.shortName = PL", league.getShortName().equals("PL"));
        assertTrue("league.fullName = Premier League", league.getFullName().equals("Premier League"));
    }

    @Test
    public void testSaveLeague(){
        League league= new League();

        league.setFullName("Premier League");
        league.setShortName("PL");
        jdbcplDao.saveOrUpdateLeague(league);
        assertTrue("league.id > 0", league.getLeagueId() >0 );
        League league2 = jdbcplDao.getLeagueById(league.getLeagueId());
        assertEquals("league.id = league2.id",league.getLeagueId(),league2.getLeagueId());

        assertEquals("league.FullName = league2.FullName",league.getFullName(),league2.getFullName());
        assertEquals("league.ShortName = league2.ShortName",league.getShortName(),league2.getShortName());
    }

    @Test
    public void testDeleteLeague(){
        League league = createLeague();
        jdbcplDao.saveOrUpdateLeague(league);
        List<League> leagues = jdbcplDao.getAllLeagues();
        assertTrue("leagues.size == 3",leagues.size() == 3);
        jdbcplDao.deleteLeague(league.getLeagueId());

        List<League> leagues2 = jdbcplDao.getAllLeagues();
        assertTrue("leagues2.size == 2",leagues2.size() == 2);
    }

    @Test
    public void testSimpleSaveTeamStats(){
        TeamStats teamStats = new TeamStats();
        teamStats.setTeam(new Team());
        teamStats.getTeam().setTeamId(9);
        teamStats.setSeason(new Season());
        teamStats.getSeason().setSeasonId(9);

        teamStats.setPosition(1);
        teamStats.setGamesPlayed(2);
        teamStats.setWins(3);
        teamStats.setDraws(4);
        teamStats.setLosses(5);
        teamStats.setGoalsScored(6);
        teamStats.setGoalsAgainst(7);
        teamStats.setPoints(8);

        jdbcplDao.saveOrUpdateTeamStats(teamStats);
    }

    @Test
    public void testGetTeamStatsById(){
        TeamStats teamStats = jdbcplDao.getTeamStatsByTeamAndSeason(1,1);

        assertTrue("teamStats.seasonid = 1", teamStats.getSeason().getSeasonId() == 1);
        assertTrue("teamStats.teamid = 1", teamStats.getTeam().getTeamId() == 1);
        assertTrue("teamStats.position = 1", teamStats.getPosition() == 1);
        assertTrue("teamStats.gamesplayed = 2", teamStats.getGamesPlayed() == 2);
        assertTrue("teamStats.wins = 3", teamStats.getWins() == 3);

        assertTrue("teamStats.draws = 4", teamStats.getDraws() == 4);
        assertTrue("teamStats.losses = 5", teamStats.getLosses() == 5);
        assertTrue("teamStats.goalsscored = 6", teamStats.getGoalsScored() == 6);
        assertTrue("teamStats.goalsagainst = 7", teamStats.getGoalsAgainst() == 7);
        assertTrue("teamStats.points = 8", teamStats.getPoints() == 8);
    }

    @Test
    public void testSaveTeamStats(){
        TeamStats teamStats = new TeamStats();
        teamStats.setTeam(new Team());
        teamStats.getTeam().setTeamId(9);
        teamStats.setSeason(new Season());
        teamStats.getSeason().setSeasonId(9);

        teamStats.setPosition(1);
        teamStats.setGamesPlayed(2);
        teamStats.setWins(3);
        teamStats.setDraws(4);
        teamStats.setLosses(5);
        teamStats.setGoalsScored(6);
        teamStats.setGoalsAgainst(7);
        teamStats.setPoints(8);

        jdbcplDao.saveOrUpdateTeamStats(teamStats);

        TeamStats teamStats2 = jdbcplDao.getTeamStatsByTeamAndSeason(9,9);
        assertEquals("teamStats.team.id = teamStats2.team.id", teamStats.getTeam().getTeamId(),teamStats2.getTeam().getTeamId());
        assertEquals("teamStats.season.id = teamStats2.season.id", teamStats.getSeason().getSeasonId(),teamStats2.getSeason().getSeasonId());
        assertEquals("teamStats.position = teamStats2.position", teamStats.getPosition(),teamStats2.getPosition());
        assertEquals("teamStats.gamesplayed = teamStats2.gamesplayed", teamStats.getGamesPlayed(),teamStats2.getGamesPlayed());
        assertEquals("teamStats.wins = teamStats2.wins", teamStats.getWins(),teamStats2.getWins());

        assertEquals("teamStats.draws = teamStats2.draws", teamStats.getDraws(),teamStats2.getDraws());
        assertEquals("teamStats.losses = teamStats2.losses", teamStats.getLosses(),teamStats2.getLosses());
        assertEquals("teamStats.goalsscored = teamStats2.goalsscored", teamStats.getGoalsScored(),teamStats2.getGoalsScored());
        assertEquals("teamStats.goalsagainst = teamStats2.goalsagainst", teamStats.getGoalsAgainst(),teamStats2.getGoalsAgainst());
        assertEquals("teamStats.points = teamStats2.points", teamStats.getPoints() ,teamStats2.getPoints());
    }

    private League createLeague(){
        League league = new League();
        league.setFullName("tull");
        return league;
    }

}

