package org.tpl.integration.dao;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.Team;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;
/*
Created by jordyr, 17.okt.2010

*/

public class JDBCTeamDaoTest extends TestCase {
    JDBCTeamDao jdbcTeamDao = new JDBCTeamDao();

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcTeamDao = new JDBCTeamDao();
        jdbcTeamDao.setDataSource(dataSource);
    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcTeamDao = null;
    }

    @Test
    public void testSimpleSaveTeam(){
        Team team = new Team();
        team.setExternalId(100);
        team.setFullName("Bolton Wanderers");
        team.setShortName("Bolton");

        jdbcTeamDao.saveOrUpdateTeam(team);
        assertTrue("league.id > 0", team.getTeamId() >0 );
    }

    @Test
    public void testGetTeamById(){
        Team team = jdbcTeamDao.getTeamById(2);
        assertTrue("team.id = 2", team.getTeamId() == 2);
        assertTrue("team.externalId = 200", team.getExternalId() == 200);
        assertTrue("team.shortName = Aston Villa", team.getShortName().equals("Aston Villa"));
        assertTrue("team.fullName = Aston Villa", team.getFullName().equals("Aston Villa"));

    }

    @Test
    public void testSaveTeam(){
        Team team = new Team();
        team.setExternalId(999);
        team.setFullName("Swindon Town Football Club ");
        team.setShortName("Swindon");

        jdbcTeamDao.saveOrUpdateTeam(team);
        assertTrue("team.id > 0", team.getTeamId() >0 );
        Team team2 = jdbcTeamDao.getTeamById(team.getTeamId());
        assertEquals("team.id = team2.id",team.getTeamId(),team2.getTeamId());
        assertEquals("team.externalId = team2.externalId",team.getExternalId(),team2.getExternalId());
        assertEquals("team.FullName = team2.FullName",team.getFullName(),team2.getFullName());
        assertEquals("team.ShortName = team2.ShortName",team.getShortName(),team2.getShortName());

    }

    @Test
    public void testGetAllTeams(){
        List<Team> teams = jdbcTeamDao.getAllTeams();
        assertTrue("teams.size() == 20", teams.size() == 20);
    }

    @Test
    public void testGetTeamsInSeason() throws Exception {
        List<Team> teamBySeason = jdbcTeamDao.getTeamBySeason(2);
        assertEquals(1,teamBySeason.size());
    }

    @Test
    public void testGetTeamByAlias() throws Exception {
        Team tottenham = jdbcTeamDao.getTeamByAlias("Tottenham Hotspurs").get(0);
        assertEquals("Tottenham", tottenham.getFullName());
    }


}
