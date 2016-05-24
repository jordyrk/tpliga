package org.tpl.business.service.fantasy;
/*
Created by jordyr, 27.01.11

*/

import org.junit.Test;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.PlayerRuleType;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.model.fantasy.rule.TeamRuleType;
import org.tpl.business.service.DefaultLeagueService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultRuleServiceTest {
    @Test
    public void testUpdatePointsForMatch() throws Exception {

        DefaultRuleService defaultRuleService = getTestableRuleService();
        DefaultLeagueService defaultLeagueService = mock(DefaultLeagueService.class);
        defaultRuleService.leagueService = defaultLeagueService;
        List playerStatsList = new ArrayList<PlayerStats>();
        PlayerStats playerStats = mock(PlayerStats.class);
        Team team = mock(Team.class);

        Player player = mock(Player.class);
        when(playerStats.getPlayer()).thenReturn(player);
        when(playerStats.getTeam()).thenReturn(team);
        playerStatsList.add(playerStats);

        Match match = mock(Match.class);
        when(match.getHomeTeam()).thenReturn(team);
        when(match.getMatchResultForHomeTeam()).thenReturn(MatchResult.VICTORY);
        when(defaultLeagueService.getPlayerStatsByMatchId(anyInt())).thenReturn(playerStatsList);
        when(defaultLeagueService.queryPlayerStatsForInt(anyString(), anyInt(), anyInt())).thenReturn(2);
        when(defaultLeagueService.getMatchById(anyInt())).thenReturn(match);
        defaultRuleService.updatePointsForMatch(0);
    }

    private DefaultRuleService getTestableRuleService(){
        return new TestableRuleService();
    }

    private class TestableRuleService extends DefaultRuleService{

        @Override
        public List<PlayerRule> getAllPlayerRules() {
            List<PlayerRule> playerRuleList = new ArrayList<PlayerRule>();
            PlayerRule playerRule = mock(PlayerRule.class);
            when(playerRule.getRuleType()).thenReturn(PlayerRuleType.REDCARD);
            playerRuleList.add(playerRule);

            return playerRuleList;
        }
        @Override
        public List<TeamRule> getAllTeamRules() {
            List<TeamRule> teamRuleList = new ArrayList<TeamRule>();
            TeamRule teamRule = mock(TeamRule.class);
            when(teamRule.getRuleType()).thenReturn(TeamRuleType.VICTORY);
            teamRuleList.add(teamRule);
            return teamRuleList;
        }

    }
}
