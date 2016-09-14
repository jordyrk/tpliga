package org.tpl.integration.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Match;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.model.Team;
import org.tpl.business.model.statsfc.DataHolder;
import org.tpl.business.service.PlayerService;

import java.util.List;
import java.util.Map;

/**
 * Created by jordyr on 8/24/14.
 */
public class StatsFcPlayerStatsConstructor implements PlayerStatsConstructor {

    @Autowired
    StatsFcPlayerStatsParser statsFcPlayerStatsParser;

    @Autowired
    PlayerService playerService;


    @Override
    public List<PlayerStats> getPlayerStats(Match match) {
        Map<String, Object> stringObjectMap = DataHolder.get(match.getMatchId());

        List<PlayerStats> homeTeamPlayerStatsList = statsFcPlayerStatsParser.importPlayerStatsForTeam(stringObjectMap, TeamParserEnum.HOME);
        List<PlayerStats> awayTeamPlayerStatsList = statsFcPlayerStatsParser.importPlayerStatsForTeam(stringObjectMap, TeamParserEnum.AWAY);

        for (PlayerStats playerStats : homeTeamPlayerStatsList) {
            playerStats.setPlayer(findPlayer(match.getHomeTeam(), playerStats.getPlayer()));
            playerStats.setTeam(match.getHomeTeam());
            playerStats.setMatch(match);
        }
        for (PlayerStats playerStats : awayTeamPlayerStatsList) {
            playerStats.setPlayer(findPlayer(match.getAwayTeam(), playerStats.getPlayer()));
            playerStats.setTeam(match.getAwayTeam());
            playerStats.setMatch(match);
        }

        homeTeamPlayerStatsList.addAll(awayTeamPlayerStatsList);
        return homeTeamPlayerStatsList;
    }

    private Player findPlayer(Team team, Player player) {
        List<Player> playerListByAlias = playerService.getPlayerByName(player.getFirstName(),player.getLastName(), team.getTeamId());

        if (playerListByAlias != null && playerListByAlias.size() == 1) {

            return playerListByAlias.get(0);
        }
        return player;

    }
}
