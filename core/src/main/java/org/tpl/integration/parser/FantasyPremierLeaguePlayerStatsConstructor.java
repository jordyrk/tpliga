package org.tpl.integration.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Match;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.model.Team;
import org.tpl.business.service.PlayerService;
import org.tpl.business.service.fantasy.exception.PlayerStatsCreationException;

import java.util.List;

/**
 * Created by jordyr on 8/24/14.
 */
public class FantasyPremierLeaguePlayerStatsConstructor implements PlayerStatsConstructor {

    @Autowired
    FantasyPremierLeaguePlayerStatsParser fantasyPremierLeaguePlayerStatsParser;

    @Autowired
    PlayerService playerService;



    @Override
    public List<PlayerStats> getPlayerStats(Match match) {
        fantasyPremierLeaguePlayerStatsParser.init(match.getFantasyPremierLeagueId());
        List<PlayerStats> homeTeamPlayerStatsList = fantasyPremierLeaguePlayerStatsParser.importPlayerStatsForHomeTeam();
        List<PlayerStats> awayTeamPlayerStatsList = fantasyPremierLeaguePlayerStatsParser.importPlayerStatsForAwayTeam();

        for(PlayerStats playerStats :homeTeamPlayerStatsList){

                playerStats.setPlayer(findPlayer(match.getHomeTeam(), playerStats.getPlayer()));
                playerStats.setTeam(match.getHomeTeam());
                playerStats.setMatch(match);

        }
        for(PlayerStats playerStats :awayTeamPlayerStatsList){

                playerStats.setPlayer(findPlayer(match.getAwayTeam(), playerStats.getPlayer()));
                playerStats.setTeam(match.getAwayTeam());
                playerStats.setMatch(match);

        }

        homeTeamPlayerStatsList.addAll(awayTeamPlayerStatsList);
        return homeTeamPlayerStatsList;
    }

    private Player findPlayer(Team team, Player player) {
        List<Player> playerListByAlias = playerService.getPlayerByLastName(player.getLastName(), team.getTeamId());
        if(playerListByAlias != null && playerListByAlias.size() == 1 ){

            return playerListByAlias.get(0);
        }
        return player;

    }
}
