package org.tpl.business.service.fantasy;
/*
Created by jordyr, 23.01.11

*/

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Match;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.service.LeagueService;
import org.tpl.integration.dao.fantasy.FantasyRuleDao;

import java.util.List;

public class DefaultRuleService implements RuleService{

    @Autowired
    FantasyRuleDao fantasyRuleDao;

    @Autowired
    LeagueService leagueService;


    public void saveOrUpdatePlayerRule(PlayerRule playerRule) {
        fantasyRuleDao.saveOrUpdatePlayerRule(playerRule);
    }

    public PlayerRule getPlayerRuleById(int playerRuleId) {
        return fantasyRuleDao.getPlayerRuleById(playerRuleId);
    }

    public List<PlayerRule> getAllPlayerRules() {
        return fantasyRuleDao.getAllPlayerRules();
    }

    public int deleteRule(int ruleId) {
        return fantasyRuleDao.deleteRule(ruleId);
    }

    public void saveOrUpdateTeamRule(TeamRule teamRule) {
        fantasyRuleDao.saveOrUpdateTeamRule(teamRule);
    }

    public TeamRule getTeamRuleById(int teamRuleId) {
        return fantasyRuleDao.getTeamRuleById(teamRuleId);
    }

    public List<TeamRule> getAllTeamRules() {
        return fantasyRuleDao.getAllTeamRules();
    }

    public void updatePointsForMatch(int matchId) {

        List<PlayerStats> playerStatsList = leagueService.getPlayerStatsByMatchId(matchId);
        Match match = leagueService.getMatchById(matchId);
        updatePointsForPlayerRules(match,playerStatsList);
        updatePointsForTeamRules(match,playerStatsList);
        for(PlayerStats playerStats: playerStatsList){
            leagueService.saveOrUpdatePlayerStats(playerStats);
        }
    }
    private void updatePointsForPlayerRules(Match match, List<PlayerStats> playerStatsList){
        List<PlayerRule> playerRuleList = this.getAllPlayerRules();
        for(PlayerStats playerStats: playerStatsList){
            int sumPoints = 0;
            for(PlayerRule rule : playerRuleList){

                int numberOf = leagueService.queryPlayerStatsForInt(rule.getRuleType().getField(), playerStats.getPlayer().getPlayerId(), match.getMatchId());
                sumPoints += rule.calculatePoints(numberOf,playerStats.getPlayer().getPlayerPosition());
            }
            playerStats.setPoints(sumPoints);
        }
    }

    private void updatePointsForTeamRules(Match match, List<PlayerStats> playerStatsList){
        List<TeamRule> teamRuleList = this.getAllTeamRules();

        for(PlayerStats playerStats: playerStatsList){
            int sumPoints = 0;
            boolean homeTeam = playerStats.getTeam().getTeamId() == match.getHomeTeam().getTeamId();

            for(TeamRule rule : teamRuleList){

                String field = null;
                int numberOf = 0;
                if(homeTeam){
                    field = rule.getRuleType().getHomeField();

                }
                else{
                    field = rule.getRuleType().getAwayField();
                }

                if(field == null){
                    field = rule.getRuleType().getPlayerField();
                    numberOf = leagueService.queryPlayerStatsForInt(field, playerStats.getPlayer().getPlayerId(), match.getMatchId());
                    if(homeTeam){
                        if(match.getMatchResultForHomeTeam().toString().equals(rule.getRuleType().toString())){
                            sumPoints += rule.calculatePoints(numberOf,playerStats.getPlayer().getPlayerPosition());
                        }
                    }else{
                        if(match.getMatchResultForAwayTeam().toString().equals(rule.getRuleType().toString())){
                            sumPoints += rule.calculatePoints(numberOf,playerStats.getPlayer().getPlayerPosition());
                        }
                    }

                }else{
                    numberOf = leagueService.queryMatchForInt(field, match.getMatchId());
                    sumPoints += rule.calculatePoints(numberOf,playerStats.getPlayer().getPlayerPosition());
                }


            }
            playerStats.setPoints(sumPoints + playerStats.getPoints());
        }
    }
}
