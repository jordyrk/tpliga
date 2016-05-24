package org.tpl.integration.dao;

import org.tpl.business.model.Player;
import org.tpl.business.model.fantasy.PlayerUsage;
import org.tpl.business.model.search.PlayerSearchResult;
import org.tpl.business.model.search.SearchTerm;

import java.util.List;

public interface PlayerDao {
    void saveOrUpdatePlayer(Player player);

    Player getPlayerById(int id);

    List<Player> getPlayerByAlias(String alias);

    PlayerSearchResult getPlayerBySearchTerm(SearchTerm term);

    List<Player> getAllPlayers();

    void deletePlayer(int playerId);

    List<PlayerUsage> getMostUsedPlayers(int limit, int[] fantasyRoundIdArray);

    List<PlayerUsage> getMostUsedPlayersForRound(int limit, int fantasyRoundId);

    List<PlayerUsage> getMostUsedPlayersForTeam(int limit, int fantasyTeamId, int[] fantasyRoundIdArray);


}


