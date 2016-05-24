package org.tpl.integration.dao.fantasy;

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.*;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;

import java.util.List;

import static junit.framework.Assert.*;

public class JdbcFantasyMatchDaoTest {
    JdbcFantasyMatchDao jdbcFantasyMatchDao;

    @Before
    public void setup(){
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyMatchDao = new JdbcFantasyMatchDao();
        jdbcFantasyMatchDao.setDataSource(dataSource);
    }

    @Test
    public void testSaveOrUpdateFantasyMatch() throws Exception {
        FantasyMatch fantasyMatch = createFantasyMatch();
        jdbcFantasyMatchDao.saveOrUpdateFantasyMatch(fantasyMatch);
        FantasyMatch savedFantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(fantasyMatch.getId());
        assertTrue(savedFantasyMatch.isPlayed());
        assertEquals(2,fantasyMatch.getHomegoals());
        assertEquals(1,fantasyMatch.getAwaygoals());
        assertFalse(savedFantasyMatch.isNew());
        jdbcFantasyMatchDao.saveOrUpdateFantasyMatch(savedFantasyMatch);
    }

    @Test
    public void testSaveOrUpdateFantasyCupMatch() throws Exception {
        FantasyMatch fantasyMatch = createFantasyMatch();
        fantasyMatch.setFantasyCup(new FantasyCup());
        jdbcFantasyMatchDao.saveOrUpdateFantasyMatch(fantasyMatch);
        FantasyMatch fantasyMatch2 = jdbcFantasyMatchDao.getFantasyMatchById(fantasyMatch.getId());
        assertEquals("CupMatch", FantasyMatchType.CUP, fantasyMatch2.getMatchType());
    }
    @Test
    public void testSaveOrUpdateFantasyCupGroupMatch() throws Exception {
        FantasyMatch fantasyMatch = createFantasyMatch();
        fantasyMatch.setFantasyCupGroup(new FantasyCupGroup());
        jdbcFantasyMatchDao.saveOrUpdateFantasyMatch(fantasyMatch);
        FantasyMatch fantasyMatch2 = jdbcFantasyMatchDao.getFantasyMatchById(fantasyMatch.getId());
        assertEquals("CupGroupMatch", FantasyMatchType.CUPGROUP, fantasyMatch2.getMatchType());
    }
    @Test
    public void testSaveOrUpdateFantasyLeagueMatch() throws Exception {
        FantasyMatch fantasyMatch = createFantasyMatch();
        fantasyMatch.setFantasyLeague(new FantasyLeague());
        jdbcFantasyMatchDao.saveOrUpdateFantasyMatch(fantasyMatch);
        FantasyMatch fantasyMatch2 = jdbcFantasyMatchDao.getFantasyMatchById(fantasyMatch.getId());
        assertEquals("LeagueMatch", FantasyMatchType.LEAGUE, fantasyMatch2.getMatchType());
    }

    @Test
    public void testGetMatchByRoundId(){
        List<FantasyMatch> list = jdbcFantasyMatchDao.getMatchByRoundId(1);
        assertEquals("list size = 4", 4, list.size());
        list = jdbcFantasyMatchDao.getMatchByRoundId(2);
        assertEquals("list size = 2", 2, list.size());
    }

    @Test
    public void testGetFantasyMatchById() throws Exception {
        FantasyMatch fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(1);
        assertEquals("hometeam id = 1", 1, fantasyMatch.getHomeTeam().getTeamId());
        assertEquals("awayteam id = 2", 2, fantasyMatch.getAwayTeam().getTeamId());
        assertEquals("round id = 1", 1, fantasyMatch.getFantasyRound().getFantasyRoundId());
        assertEquals("homegoals = 2", 2, fantasyMatch.getHomegoals());
        assertEquals("awaygoals = 1", 1, fantasyMatch.getAwaygoals());
        assertTrue(fantasyMatch.isPlayed());
        assertEquals("LeagueMatch", FantasyMatchType.LEAGUE, fantasyMatch.getMatchType());
        fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(2);
        assertEquals("LeagueMatch", FantasyMatchType.LEAGUE, fantasyMatch.getMatchType());
        fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(3);
        assertEquals("CupMatch", FantasyMatchType.CUP, fantasyMatch.getMatchType());
        fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(4);
        assertEquals("CupGroupMatch", FantasyMatchType.CUPGROUP, fantasyMatch.getMatchType());
        fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(5);
        assertEquals("CupGroupMatch", FantasyMatchType.CUPGROUP, fantasyMatch.getMatchType());
        fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(6);
        assertEquals("UnknownMatch", FantasyMatchType.UNKNOWN, fantasyMatch.getMatchType());

    }

    @Test
    public void testGetMatchByLeagueId(){
        List<FantasyMatch> list = jdbcFantasyMatchDao.getMatchByLeagueId(1);
        assertEquals("list size = 2", 2, list.size());
    }

    @Test
    public void testGetMatchByLeagueIdAndRoundId(){
        List<FantasyMatch> list = jdbcFantasyMatchDao.getMatchByLeagueIdAndRoundId(1,1);
        assertEquals("list size = 1", 1, list.size());
        list = jdbcFantasyMatchDao.getMatchByLeagueIdAndRoundId(1,2);
        assertEquals("list size = 1", 1, list.size());
    }
    @Test
    public void testGetMatchByCupId(){
        List<FantasyMatch> list = jdbcFantasyMatchDao.getMatchByCupId(1);
        assertEquals("list size = 1", 1, list.size());
    }

    @Test
    public void testGetMatchByCupIdAndRoundId(){
        List<FantasyMatch> list = jdbcFantasyMatchDao.getMatchByCupIdAndRoundId(1,1);
        assertEquals("list size = 1", 1, list.size());

    }
    @Test
    public void testGetMatchByCupGroupId(){
        List<FantasyMatch> list = jdbcFantasyMatchDao.getMatchByCupGroupId(1);
        assertEquals("list size = 2", 2, list.size());
    }

    @Test
    public void testGetMatchByCupGroupIdAndRoundId(){
        List<FantasyMatch> list = jdbcFantasyMatchDao.getMatchByCupGroupIdAndRoundId(1,1);
        assertEquals("list size = 1", 1, list.size());
        list = jdbcFantasyMatchDao.getMatchByCupGroupIdAndRoundId(1,2);
        assertEquals("list size = 1", 1, list.size());

    }

    @Test
    public void fantasyLeagueShouldHaveMatches(){
        assertTrue(jdbcFantasyMatchDao.fantasyLeagueHasMatches(1));
    }
    @Test
    public void fantasyLeagueShouldNotHaveMatches(){
        assertFalse(jdbcFantasyMatchDao.fantasyLeagueHasMatches(77));
    }
    @Test
    public void fantasyCupShouldHaveMatches(){
        assertTrue(jdbcFantasyMatchDao.fantasyCupHasMatches(1));
    }
    @Test
    public void fantasyCupShouldNotHaveMatches(){
        assertFalse(jdbcFantasyMatchDao.fantasyCupHasMatches(77));
    }

    @Test
    public void fantasyCupGroupShouldHaveMatches(){
        assertTrue(jdbcFantasyMatchDao.fantasyCupGroupHasMatches(1));
    }
    @Test
    public void fantasyCupGroupShouldNotHaveMatches(){
        assertFalse(jdbcFantasyMatchDao.fantasyCupGroupHasMatches(77));
    }


    @Test
    public void testGetMaxRoundIdForCupInMatch() throws Exception {
        int maxRoundIdForCupInMatch = jdbcFantasyMatchDao.getMaxRoundIdForCupInMatch(1);
        assertEquals(1,maxRoundIdForCupInMatch);
    }

    @Test
    public void testDeleteFantastyMatch() throws Exception {
        FantasyMatch fantasyMatch = createFantasyMatch();

        jdbcFantasyMatchDao.saveOrUpdateFantasyMatch(fantasyMatch);
        fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(fantasyMatch.getId());
        assertNotNull("fantasymatch exists", fantasyMatch);

        jdbcFantasyMatchDao.deleteFantasyMatch(fantasyMatch.getId());
        fantasyMatch = jdbcFantasyMatchDao.getFantasyMatchById(fantasyMatch.getId());
        assertNull("fantasymatch is deleted", fantasyMatch);
    }

    @Test
    public void testGetFantasyMatchByRoundsAndIsPlayed() throws Exception {
        List<FantasyMatch> fantasyMatchByRoundsAndIsPlayed = jdbcFantasyMatchDao.getFantasyMatchByRoundsAndIsPlayed("1,2", true);
        assertEquals(6,fantasyMatchByRoundsAndIsPlayed.size());
        fantasyMatchByRoundsAndIsPlayed = jdbcFantasyMatchDao.getFantasyMatchByRoundsAndIsPlayed("2", true);
        assertEquals(2,fantasyMatchByRoundsAndIsPlayed.size());
        fantasyMatchByRoundsAndIsPlayed = jdbcFantasyMatchDao.getFantasyMatchByRoundsAndIsPlayed("1", true);
        assertEquals(4,fantasyMatchByRoundsAndIsPlayed.size());


    }

    private FantasyMatch createFantasyMatch(){
        FantasyMatch fantasyMatch = new FantasyMatch();
        fantasyMatch.setHomeTeam(new FantasyTeam());
        fantasyMatch.setAwayTeam(new FantasyTeam());
        fantasyMatch.setFantasyRound(new FantasyRound());
        fantasyMatch.setHomegoals(2);
        fantasyMatch.setAwaygoals(1);
        fantasyMatch.setPlayed(true);
        return fantasyMatch;

    }
}
