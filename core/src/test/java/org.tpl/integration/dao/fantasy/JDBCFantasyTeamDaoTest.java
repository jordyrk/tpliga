package org.tpl.integration.dao.fantasy;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.FantasyTeamRound;
import org.tpl.business.model.fantasy.Formation;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

public class JDBCFantasyTeamDaoTest extends TestCase {

    JDBCFantasyTeamDao jdbcFantasyTeamDao;
    JDBCFantasyManagerDao jdbcFantasyManagerDao;
    JDBCFantasyRoundDao jdbcFantasyRoundDao;

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyTeamDao = new JDBCFantasyTeamDao();
        jdbcFantasyTeamDao.setDataSource(dataSource);
        jdbcFantasyManagerDao = new JDBCFantasyManagerDao();
        jdbcFantasyManagerDao.setDataSource(dataSource);
        jdbcFantasyRoundDao = new JDBCFantasyRoundDao();
        jdbcFantasyRoundDao.setDataSource(dataSource);
    }

    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcFantasyTeamDao = null;
        jdbcFantasyManagerDao = null;
        jdbcFantasyRoundDao = null;
    }

    @Test
    public void testSimpleSaveFantasyTeam(){
        FantasyTeam fantasyTeam = createFantasyTeam();
        jdbcFantasyTeamDao.saveOrUpdateFantasyTeam(fantasyTeam);
        assertTrue("fantasyTeam.id > 0", fantasyTeam.getTeamId() > 0 );
    }

    @Test
    public void testSaveFantasyTeam(){
        FantasyTeam fantasyTeam = createFantasyTeam();

        jdbcFantasyTeamDao.saveOrUpdateFantasyTeam(fantasyTeam);
        assertTrue("fantasyTeam.id > 0", fantasyTeam.getTeamId() > 0 );
        FantasyTeam fantasyTeam2 = jdbcFantasyTeamDao.getFantasyTeamById(fantasyTeam.getTeamId());
        assertEquals("fantasyTeam.id = fantasyTeam2.id",fantasyTeam.getTeamId(),fantasyTeam2.getTeamId());
        assertEquals("fantasyTeam.name = fantasyTeam2.name",fantasyTeam.getName(),fantasyTeam2.getName());
        assertEquals("fantasyTeam.stadium = fantasyTeam2.stadium",fantasyTeam.getStadium(),fantasyTeam2.getStadium());
        assertEquals("fantasyTeam.supporterclub = fantasyTeam2.supporterclub",fantasyTeam.getSupporterClub(),fantasyTeam2.getSupporterClub());
        assertEquals("fantasyTeam.TeamSpirit = fantasyTeam2.TeamSpirit",fantasyTeam.getTeamSpirit(),fantasyTeam2.getTeamSpirit());
        assertEquals("fantasyTeam.LatestNews = fantasyTeam2.LatestNews",fantasyTeam.getLatestNews(),fantasyTeam2.getLatestNews());
        assertEquals("fantasyTeam.manager.id = fantasyTeam2.manager.id",fantasyTeam.getFantasyManager().getManagerId(),fantasyTeam2.getFantasyManager().getManagerId());
    }

    @Test
    public void testGetFantasyTeamById(){
        FantasyTeam fantasyTeam = jdbcFantasyTeamDao.getFantasyTeamById(1);
        assertTrue("fantasyTeam.getTeamId() == 1", fantasyTeam.getTeamId() == 1);
        assertTrue("fantasyTeam.getLatestNews() == Latest news", fantasyTeam.getLatestNews().equals("latestNews"));
        assertTrue("fantasyTeam.getName() == Name", fantasyTeam.getName().equals("Teamname"));
        assertTrue("fantasyTeam.getStadium() == Stadium", fantasyTeam.getStadium().equals("Stadium"));
        assertTrue("fantasyTeam.getSupporterClub() == Supporterclub", fantasyTeam.getSupporterClub().equals("supporterClub"));
        assertTrue("fantasyTeam.getTeamSpirit() == TeamSpirit", fantasyTeam.getTeamSpirit().equals("teamSpirit"));
        assertTrue("fantasyTeam.getFantasyManager().getManagerId() == 1", fantasyTeam.getFantasyManager().getManagerId() == 1);
    }

    @Test
    public void testSaveOrUpdateFantasyTeamRound(){
        FantasyTeamRound fantasyTeamRound = createFantasyTeamRound();
        jdbcFantasyTeamDao.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
        FantasyTeamRound fantasyTeamRound2= jdbcFantasyTeamDao.getFantasyTeamRoundByIds(2,2);
        assertTrue("fantasyTeamRound.getFantasyTeam().getTeamId() == fantasyTeamRound2.getFantasyTeam().getTeamId()", fantasyTeamRound.getFantasyTeam().getTeamId() == fantasyTeamRound2.getFantasyTeam().getTeamId());
        assertTrue("fantasyTeamRound.getFantasyRound().getFantasyRoundId() == fantasyTeamRound2.getFantasyRound().getFantasyRoundId()", fantasyTeamRound.getFantasyRound().getFantasyRoundId() == fantasyTeamRound2.getFantasyRound().getFantasyRoundId());
        assertTrue("fantasyTeamRound.isOfficial() == fantasyTeamRound2.isOfficial()", fantasyTeamRound.isOfficial() == fantasyTeamRound2.isOfficial());
        assertTrue("fantasyTeamRound.getPoints() == fantasyTeamRound2.getPoints()", fantasyTeamRound.getPoints() == fantasyTeamRound2.getPoints());
        assertTrue("fantasyTeamRound2.getPlayerList().size() == 11", fantasyTeamRound2.getPlayerList().size() == 11);
        assertTrue("fantasyTeamRound2.formation == fantasyTeamRound.formation", fantasyTeamRound2.getFormation() == fantasyTeamRound.getFormation());
        jdbcFantasyTeamDao.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
    }

    @Test
    public void testGetFantasyTeamRoundByIds(){
        //INSERT INTO fantasy_team_round(fantasyteamid, fantasyroundid, official, points, player_1_id, player_2_id, player_3_id, player_4_id, player_5_id, player_6_id, player_7_id, player_8_id, player_9_id, player_10_id, player_11_id) values (1,1,1,10, 1,2,3,4,5,6,7,8,9,10,11);
        FantasyTeamRound fantasyTeamRound = jdbcFantasyTeamDao.getFantasyTeamRoundByIds(1,1);
        assertTrue("fantasyTeamRound.getFantasyTeam().getTeamId() == 1", fantasyTeamRound.getFantasyTeam().getTeamId() == 1);
        assertTrue("fantasyTeamRound.getFantasyRound().getFantasyRoundId() == 1", fantasyTeamRound.getFantasyRound().getFantasyRoundId() == 1);
        assertTrue("fantasyTeamRound.isOfficial()", fantasyTeamRound.isOfficial());
        assertFalse("fantasyTeamRound.isOfficialWhenRoundIsClosed()", fantasyTeamRound.isOfficialWhenRoundIsClosed());
        assertTrue("fantasyTeamRound.getPoints() == 10", fantasyTeamRound.getPoints() == 10);

    }
    @Test
    public void testGetFantasyTeamRoundByTeamId(){
        List<FantasyTeamRound> fantasyTeamRoundList = jdbcFantasyTeamDao.getFantasyTeamRoundByTeamId(1);
        assertTrue("fantasyTeamRoundList.size() == 4", fantasyTeamRoundList.size() == 4);
    }
    /* TODO: Will fail in hsql
    public void testGetFantasyTeamRoundByRange(){
        String range = "(1,2,3)";
        List<FantasyTeamRound> fantasyTeamRoundList = jdbcFantasyTeamDao.getFantasyTeamRoundByRoundIdRange(range);
        assertTrue("fantasyTeamRoundList.size() == 3", fantasyTeamRoundList.size() == 3);
    }*/

    @Test
    public void testGetFantasyTeamRoundsByFantasyRoundId(){
        List<FantasyTeamRound> fantasyTeamRoundList = jdbcFantasyTeamDao.getFantasyTeamRoundsByFantasyRoundId(1);
        assertTrue("fantasyTeamRoundList.size() == 2", fantasyTeamRoundList.size() == 2);
        fantasyTeamRoundList = jdbcFantasyTeamDao.getFantasyTeamRoundsByFantasyRoundId(2);
        assertTrue("fantasyTeamRoundList.size() == 1", fantasyTeamRoundList.size() == 1);
    }

    @Test
    public void testGetOfficialFantasyTeamRounds(){
        List<FantasyTeamRound> fantasyTeamRoundList =  jdbcFantasyTeamDao.getOfficialFantasyTeamRounds(1);

        assertTrue("# offical teams = 1", fantasyTeamRoundList.size() == 1);
        fantasyTeamRoundList =  jdbcFantasyTeamDao.getOfficialFantasyTeamRounds(2);
        assertTrue("# offical teams = 0", fantasyTeamRoundList.size() == 0);
        jdbcFantasyTeamDao.updateFantasyTeamRoundsWhenRoundIsClosed(1);
        fantasyTeamRoundList =  jdbcFantasyTeamDao.getOfficialFantasyTeamRounds(1);
        assertTrue("# offical teams = 2", fantasyTeamRoundList.size() == 2);


    }

    @Test
    public void shouldContainCorrectNumberOfPlayers(){
        FantasyTeamRound fantasyTeamRound = createFantasyTeamRound();
        fantasyTeamRound.setFormation(Formation.FIVETHREETWO);
        jdbcFantasyTeamDao.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
        FantasyTeamRound savedFantasyReamRound = jdbcFantasyTeamDao.getFantasyTeamRoundByIds(2, 2);
        assertEquals(5, savedFantasyReamRound.getDefenders().size());
        assertEquals(3, savedFantasyReamRound.getMidfielders().size());
        assertEquals(2, savedFantasyReamRound.getStrikers().size());
    }

    @Test
    public void testDeleteFantasyTeam(){
        FantasyTeam fantasyTeam = createFantasyTeam();
        jdbcFantasyTeamDao.saveOrUpdateFantasyTeam(fantasyTeam);
        assertNotNull(fantasyTeam);
        jdbcFantasyTeamDao.deleteFantasyTeam(fantasyTeam.getTeamId());
        FantasyTeam deletedTeam = jdbcFantasyTeamDao.getFantasyTeamById(fantasyTeam.getTeamId());
        assertNull(deletedTeam);
    }

    @Test
    public void testDeleteFantasyTeamRound(){
        List<FantasyTeamRound> fantasyTeamRoundList = jdbcFantasyTeamDao.getFantasyTeamRoundByTeamId(1);
        assertTrue("fantasyTeamRoundList.size() == 4", fantasyTeamRoundList.size() == 4);
        jdbcFantasyTeamDao.deleteFantasyTeamRound(1);
        fantasyTeamRoundList = jdbcFantasyTeamDao.getFantasyTeamRoundByTeamId(1);
        assertEquals(0, fantasyTeamRoundList.size());

    }

    public void testGetNumberOfOfficialTeams(){
        assertTrue(jdbcFantasyTeamDao.getNumberOfOfficialTeams(1) == 1);
        assertTrue(jdbcFantasyTeamDao.getNumberOfOfficialTeams(2) == 0);
    }

    public void testGetNumberOfOfficialWhenRoundIsClosedTeams(){
        assertTrue(jdbcFantasyTeamDao.getNumberOfOfficialWhenRoundIsClosedTeams(1) == 1);
        assertTrue(jdbcFantasyTeamDao.getNumberOfOfficialWhenRoundIsClosedTeams(2) == 0);
    }

    private FantasyTeam createFantasyTeam(){
        FantasyTeam fantasyTeam = new FantasyTeam();
        fantasyTeam.setLatestNews("Latest news");
        fantasyTeam.setName("Name");
        fantasyTeam.setStadium("Stadium");
        fantasyTeam.setSupporterClub("Supporterclub");
        fantasyTeam.setTeamSpirit("TeamSpirit");
        fantasyTeam.setFantasyManager(jdbcFantasyManagerDao.getFantasyManagerById(1));
        return fantasyTeam;
    }

    private FantasyTeamRound createFantasyTeamRound(){
        FantasyTeamRound fantasyTeamRound = new FantasyTeamRound();
        fantasyTeamRound.setOfficial(true);
        fantasyTeamRound.setOfficialWhenRoundIsClosed(true);
        fantasyTeamRound.setPoints(19);
        fantasyTeamRound.setFantasyTeam(createFantasyTeam());
        FantasyRound fantasyRound = jdbcFantasyRoundDao.getFantasyRoundById(2);
        fantasyTeamRound.setFantasyRound(fantasyRound);
        FantasyTeam fantasyTeam = jdbcFantasyTeamDao.getFantasyTeamById(2);
        fantasyTeamRound.setFantasyTeam(fantasyTeam);

        return fantasyTeamRound;
    }
}