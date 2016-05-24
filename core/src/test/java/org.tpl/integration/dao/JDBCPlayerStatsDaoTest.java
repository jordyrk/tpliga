package org.tpl.integration.dao;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.model.search.Operator;
import org.tpl.integration.dao.fantasy.JDBCFantasyRuleDao;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;
/*
Created by jordyr, 06.nov.2010

*/

public class JDBCPlayerStatsDaoTest extends TestCase{
    JDBCPlayerStatsDao playerStatsDao;
    JDBCFantasyRuleDao fantasyRuleDao;
    JDBCPlayerDao playerDao;


    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        playerStatsDao = new JDBCPlayerStatsDao();
        playerStatsDao.setDataSource(dataSource);
        fantasyRuleDao = new JDBCFantasyRuleDao();
        fantasyRuleDao.setDataSource(dataSource);
         playerDao = new JDBCPlayerDao();
        playerDao.setDataSource(dataSource);
    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        playerStatsDao = null;
        fantasyRuleDao = null;
        playerDao = null;
    }


    @Test
    public void testSimpleSavePlayerStats(){
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(new Player());
        playerStats.getPlayer().setPlayerId(1);
        playerStats.setTeam(new Team());
        playerStats.getTeam().setTeamId(1);
        playerStats.setMatch(new Match());
        playerStats.getMatch().setMatchId(1);
        playerStats.setGoals(1);
        playerStats.setAssists(2);
        playerStats.setOwnGoal(3);
        playerStats.setMissedPenalty(4);
        playerStats.setSavedPenalty(5);
        playerStats.setYellowCard(6);
        playerStats.setRedCard(true);
        playerStats.setWholeGame(true);
        playerStats.setStartedGame(true);
        playerStats.setPlayedMinutes(60);
        playerStats.setPoints(7);
        playerStats.setShots(4);
        playerStats.setShotsOnGoal(2);
        playerStats.setFoulsCommited(2);
        playerStats.setFoulsDrawn(2);
        playerStats.setOffsides(2);
        playerStats.setSaves(1);

        playerStatsDao.saveOrUpdatePlayerStats(playerStats);

    }

    @Test
    public void testGetPlayerStatsById(){
        //INSERT INTO player_stats  (matchid ,playerid ,goals ,assists ,owngoal ,missedpenalty ,savedpenalty ,yellowcard ,redcard ,wholegame ,startedgame ,points ,teamid) values (2,2,1,2,3,4,5,6,1,1,1,7,1 );
        PlayerStats playerStats = playerStatsDao.getPlayerStatsByMatchAndPlayer(2,2);

        assertTrue("playerStats.goals = 1", playerStats.getGoals() == 1);
        assertTrue("playerStats.assists = 2", playerStats.getAssists() == 2);
        assertTrue("playerStats.owngoal = 3", playerStats.getOwnGoal() == 3);
        assertTrue("playerStats.missedpenalty = 4", playerStats.getMissedPenalty() == 4);
        assertTrue("playerStats.savedpenalty = 5", playerStats.getSavedPenalty() == 5);

        assertTrue("playerStats.yellowcard = 6", playerStats.getYellowCard() == 6);
        assertTrue("playerStats.redcard = true", playerStats.isRedCard());
        assertTrue("playerStats.wholegame = true", playerStats.isWholeGame());
        assertTrue("playerStats.startedgame = true", playerStats.isStartedGame());
        assertTrue("playerStats.getPlayedMinutes = 60", playerStats.getPlayedMinutes() == 60);
        assertTrue("playerStats.points = 7", playerStats.getPoints() == 7);
        //4,3,2,1,1,1
        assertTrue("playerStats.shots = 4", playerStats.getShots() == 4);
        assertTrue("playerStats.shotsOnGoal = 3", playerStats.getShotsOnGoal() == 3);
        assertTrue("playerStats.offsides = 2", playerStats.getOffsides() == 2);
        assertTrue("playerStats.foulsCommited = 7", playerStats.getFoulsCommited() == 1);
        assertTrue("playerStats.foulsDrawn = 1", playerStats.getFoulsDrawn() == 1);
        assertTrue("playerStats.saves = 1", playerStats.getSaves() == 1);
        assertTrue("playerStats.teamid = 1", playerStats.getTeam().getTeamId() == 1);
        assertTrue("playerStats.matchid = 2", playerStats.getMatch().getMatchId() == 2);
        assertTrue("playerStats.playerid = 2", playerStats.getPlayer().getPlayerId() == 2);
        assertEquals("playerStats.easportsppi = 5", playerStats.getEaSportsPPI(),5);
        assertEquals("playerStats.manofthematch = false", playerStats.isManOfTheMatch(),false);

    }

    @Test
    public void testSavePlayerStats(){
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(new Player());
        playerStats.getPlayer().setPlayerId(4);
        playerStats.setTeam(new Team());
        playerStats.getTeam().setTeamId(4);
        playerStats.setMatch(new Match());
        playerStats.getMatch().setMatchId(4);
        playerStats.setGoals(1);
        playerStats.setAssists(2);
        playerStats.setOwnGoal(3);
        playerStats.setMissedPenalty(4);
        playerStats.setSavedPenalty(5);
        playerStats.setYellowCard(6);
        playerStats.setRedCard(true);
        playerStats.setWholeGame(true);
        playerStats.setStartedGame(true);
        playerStats.setPlayedMinutes(60);
        playerStats.setPoints(7);
        playerStats.setEaSportsPPI(10);
        playerStats.setManOfTheMatch(true);
        playerStatsDao.saveOrUpdatePlayerStats(playerStats);
        PlayerStats playerStats2 = playerStatsDao.getPlayerStatsByMatchAndPlayer(4,4);
        assertEquals("playerStats.match.id = playerStats2.match.id", playerStats.getMatch().getMatchId(),playerStats2.getMatch().getMatchId());
        assertEquals("playerStats.player.id = playerStats2.player.id", playerStats.getPlayer().getPlayerId(),playerStats2.getPlayer().getPlayerId());
        assertEquals("playerStats.team.id = playerStats2.team.id", playerStats.getTeam().getTeamId(),playerStats2.getTeam().getTeamId());
        assertEquals("playerStats.goals = playerStats2.goals", playerStats.getGoals(),playerStats2.getGoals());
        assertEquals("playerStats.assists = playerStats2.assists", playerStats.getAssists(),playerStats2.getAssists());

        assertEquals("playerStats.owngoal = playerStats2.owngoal", playerStats.getOwnGoal(),playerStats2.getOwnGoal());
        assertEquals("playerStats.missedpenalty = playerStats2.missedpenalty", playerStats.getMissedPenalty(),playerStats2.getMissedPenalty());
        assertEquals("playerStats.savedpenalty = playerStats2.savedpenalty", playerStats.getSavedPenalty(),playerStats2.getSavedPenalty());
        assertEquals("playerStats.yellowcard = playerStats2.yellowcard", playerStats.getYellowCard(),playerStats2.getYellowCard());
        assertEquals("playerStats.redcard = playerStats2.redcard", playerStats.isRedCard(),playerStats2.isRedCard());

        assertEquals("playerStats.wholegame = playerStats2.wholegame", playerStats.isWholeGame(),playerStats2.isWholeGame());
        assertEquals("playerStats.startedgame = playerStats2.startedgame", playerStats.isStartedGame(),playerStats2.isStartedGame());
        assertEquals("playerStats.playedMinutes = playerStats2.playedMinutes", playerStats.getPlayedMinutes(),playerStats2.getPlayedMinutes());
        assertEquals("playerStats.points = playerStats2.points", playerStats.getPoints(),playerStats2.getPoints());
        assertEquals("playerStats.easportsppi = playerStats2.easportsppi", playerStats.getEaSportsPPI(),playerStats2.getEaSportsPPI());
        assertEquals("playerStats.manofthematch = playerStats2.manofthematch", playerStats.isManOfTheMatch(),playerStats2.isManOfTheMatch());
    }

    @Test
    public void testGetPlayerStatsByMatchId(){

        List<PlayerStats> playerStatsList1 = playerStatsDao.getPlayerStatsByMatchId(1);
        List<PlayerStats> playerStatsList2 = playerStatsDao.getPlayerStatsByMatchId(2);

        assertTrue("playerStatsList1.size == 3", playerStatsList1.size() == 3);
        assertTrue("playerStatsList2.size == 3", playerStatsList2.size() == 3);

    }

    @Test
    public void testGetSimplePlayerStats(){
        List<SimplePlayerStats> list = playerStatsDao.getSimplePlayerStatsBySearchTerm(SimplePlayerStatsAttribute.GOALS, null, true,10,null).getResults();
        assertTrue("list size is 4", list.size() == 4);

    }

@Test
    public void testGetSimplePlayerStatsWithSearchTerm(){
        ComparisonTerm comparisonTerm = new ComparisonTerm("position", Operator.EQ,PlayerPosition.DEFENDER.toString());
        List<SimplePlayerStats> list = playerStatsDao.getSimplePlayerStatsBySearchTerm(SimplePlayerStatsAttribute.GOALS, comparisonTerm, true,10,null).getResults();
        assertTrue("list size is 1", list.size() == 1);

    }


}

