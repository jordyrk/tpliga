package org.tpl.integration.dao;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.fantasy.PlayerUsage;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
/*
Created by jordyr, 17.okt.2010

*/

public class JDBCPlayerDaoTest extends TestCase {
    JDBCPlayerDao jdbcPlayerDao;
    JDBCTeamDao jdbcTeamDao;

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcPlayerDao = new JDBCPlayerDao();
        jdbcPlayerDao.setDataSource(dataSource);
        jdbcTeamDao = new JDBCTeamDao();
        jdbcTeamDao.setDataSource(dataSource);
    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcPlayerDao = null;
        jdbcTeamDao = null;
    }
    
    @Test
    public void testSimpleSavePlayer(){
        Player player = createPlayer();
        jdbcPlayerDao.saveOrUpdatePlayer(player);
        assertTrue("player.id > 0", player.getPlayerId() >0 );
    }

    @Test
    public void testGetPlayerByAlias() throws Exception {
        Player player = createPlayer();
        jdbcPlayerDao.saveOrUpdatePlayer(player);
        Player bjornen = jdbcPlayerDao.getPlayerByAlias("Bjornen").get(0);
        assertNotNull(bjornen);
    }

    @Test
    public void testGetPlayerById(){

        Player player = jdbcPlayerDao.getPlayerById(1);
        assertTrue("player.id = 1", player.getPlayerId() == 1);

        assertTrue("player.externalId = 100", player.getExternalId() == 100);
        assertTrue("player.FirstName = Chris", player.getFirstName().equals("Chris"));
        assertTrue("player.LastName = Basham", player.getLastName().equals("Basham"));
        assertTrue("player.PlayerPosition = PlayerPosition.DEFENDER", player.getPlayerPosition() == PlayerPosition.DEFENDER);
        assertTrue("player.price = 100 000", player.getPrice() == 100000);
        assertTrue("player.ShirtNumber = 10", player.getShirtNumber()== 10);
        assertTrue("player.soccernetID = 108", player.getSoccerNetId()== 108);
        assertTrue("player.team.id = 1", player.getTeam().getTeamId()== 1);
    }

    @Test
    public void testSavePlayer(){
        Player player = new Player();
        player.setExternalId(999);
        player.setFirstName("Ole");
        player.setLastName("Brumm");
        player.setPlayerPosition(PlayerPosition.GOALKEEPER);
        player.setPrice(10);
        player.setShirtNumber(11);
        player.setTeam(jdbcTeamDao.getTeamById(1));

        jdbcPlayerDao.saveOrUpdatePlayer(player);
        assertTrue("player.id > 0", player.getPlayerId() >0 );
        Player player2 = jdbcPlayerDao.getPlayerById(player.getPlayerId());
        assertEquals("player.id = player2.id",player.getPlayerId(),player2.getPlayerId());
        assertEquals("player.externalId = player2.externalId",player.getExternalId(),player2.getExternalId());
        assertEquals("player.FirstName = player2.FirstName",player.getFirstName(),player2.getFirstName());
        assertEquals("player.lastname = player2.lastname",player.getLastName(),player2.getLastName());
        assertEquals("player.position = player2.position",player.getPlayerPosition(),player2.getPlayerPosition());
        assertEquals("player.price = player2.price",player.getPrice(),player2.getPrice());
        assertEquals("player.shirtnumber = player2.shirtnumber",player.getShirtNumber(),player2.getShirtNumber());
        assertEquals("player.soccerNetId = player2.soccerNetId",player.getSoccerNetId(),player2.getSoccerNetId());
        assertEquals("player.team.id = player2.team.id",player.getTeam().getTeamId(),player2.getTeam().getTeamId());
    }

    @Test
    public void testGetAllPlayers(){
        List<Player> players= jdbcPlayerDao.getAllPlayers();
        assertTrue("players.size() == 12", players.size() == 12);
    }

    @Test
    public void testGetMostUsedPlayers() throws Exception {
        List<PlayerUsage> mostUsedPlayers = jdbcPlayerDao.getMostUsedPlayers(10, new int[]{1, 2});
        assertEquals(10, mostUsedPlayers.size());
    }

    @Test
    public void testGetMostUsedPlayersInRound() throws Exception {
        List<PlayerUsage> mostUsedPlayers = jdbcPlayerDao.getMostUsedPlayersForRound(12, 2);
        assertEquals(11, mostUsedPlayers.size());
    }

    @Test
    public void testGetMostUsedPlayersForTeam() throws Exception {
        List<PlayerUsage> mostUsedPlayers = jdbcPlayerDao.getMostUsedPlayersForTeam(10, 1, new int[]{1, 2});
        assertEquals(10, mostUsedPlayers.size());
        PlayerUsage firstPlayerUsage = mostUsedPlayers.get(0);
        assertEquals(2,firstPlayerUsage.getNumberOfUsages());
    }

    private Player createPlayer() {
        Player player = new Player();
        player.setFirstName("Ole");
        player.setLastName("Brumm");
        player.setPlayerPosition(PlayerPosition.GOALKEEPER);
        player.setPrice(10);
        player.setShirtNumber(11);
        player.setExternalId(209);
        player.setSoccerNetId(133);
        List<String> alias = new ArrayList<String>();
        alias.add("Bjornen");
        player.setAlias(alias);
        return player;
    }
}
