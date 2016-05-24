package org.tpl.integration.dao.fantasy;
/*
Created by jordyr, 23.01.11

*/

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.rule.*;
import org.tpl.integration.dao.JDBCSeasonDao;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class JDBCFantasyRuleDaoTest {
    JDBCFantasyRuleDao fantasyRuleDao;

    @Before
    public void setUp() throws Exception {

        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        fantasyRuleDao = new JDBCFantasyRuleDao();
        fantasyRuleDao.setDataSource(dataSource);

    }
    @Test
    public void testSaveOrUpdatePlayerRule() throws Exception {
        PlayerRule playerRule = new PlayerRule(PlayerRuleType.REDCARD, -5, 1,"Red card", PlayerRulePosition.ALL);
        fantasyRuleDao.saveOrUpdatePlayerRule(playerRule);
        assertTrue("id larger than 0", playerRule.getId() > 0);
        PlayerRule playerRule2 = fantasyRuleDao.getPlayerRuleById(playerRule.getId());
        assertEquals("id = id", playerRule.getId(), playerRule2.getId());
        assertEquals("name = name", playerRule.getName(), playerRule2.getName());
        assertEquals("QualifingNumber = QualifingNumber", playerRule.getQualifingNumber(), playerRule2.getQualifingNumber());
        assertEquals("points = points", playerRule.getPoints(), playerRule2.getPoints());
        assertEquals("playerPosition = playerPosition", playerRule.getPlayerRulePosition(), playerRule2.getPlayerRulePosition());
        assertEquals("ruletype = ruletype", playerRule.getRuleType(), playerRule2.getRuleType());
    }

    @Test
    public void testGetAllPlayerRules() throws Exception {
        List<PlayerRule> playerRuleList = fantasyRuleDao.getAllPlayerRules();
        assertTrue("numbers of rule == 4", playerRuleList.size() == 4);
    }

    @Test
    public void testDeleteRule(){
        PlayerRule playerRule = new PlayerRule(PlayerRuleType.REDCARD, -5, 1,"Red card", PlayerRulePosition.ALL);
        fantasyRuleDao.saveOrUpdatePlayerRule(playerRule);
        int rowsAffected = fantasyRuleDao.deleteRule(playerRule.getId());
        assertTrue("number of delted lines is 1", rowsAffected == 1);
    }

    @Test
    public void testSaveOrUpdateTeamRule() throws Exception {
        TeamRule teamRule = new TeamRule(TeamRuleType.GOALSCORED, 1, 1,"Teamgoal", PlayerRulePosition.ALL);
        fantasyRuleDao.saveOrUpdateTeamRule(teamRule);
        assertTrue("id larger than 0", teamRule.getId() > 0);
        TeamRule teamRule2 = fantasyRuleDao.getTeamRuleById(teamRule.getId());
        assertEquals("id = id", teamRule.getId(), teamRule2.getId());
        assertEquals("name = name", teamRule.getName(), teamRule2.getName());
        assertEquals("QualifingNumber = QualifingNumber", teamRule.getQualifingNumber(), teamRule2.getQualifingNumber());
        assertEquals("points = points", teamRule.getPoints(), teamRule2.getPoints());

        assertEquals("ruletype = ruletype", teamRule.getRuleType(), teamRule2.getRuleType());

    }

    @Test
    public void testGetAllTeamRules() throws Exception {
        List<TeamRule> teamRuleList = fantasyRuleDao.getAllTeamRules();
        assertTrue("numbers of rule == 2", teamRuleList.size() == 2);

    }
}
