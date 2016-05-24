package org.tpl.business.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Player;
import org.tpl.business.model.fantasy.PlayerUsage;
import org.tpl.business.model.search.PlayerSearchResult;
import org.tpl.business.model.search.SearchTerm;
import org.tpl.business.service.fantasy.exception.PlayerDeleteException;
import org.tpl.integration.dao.PlayerDao;

import java.util.List;

public interface PlayerService {

void saveOrUpdatePlayer(Player player);

    void deletePlayer(int playerId) throws PlayerDeleteException;

    Player getPlayerById(int id);

    PlayerSearchResult getPlayerBySearchTerm(SearchTerm term);

    List<Player> findPlayerByName(String firstName, String lastName);

    List<Player> searchForPlayer(String query);

    List<Player> getAllPlayers();

    Player getPlayerByExternalId(int externalId);

    List<Player> getPlayersByTeamId(int teamId);

    List<Player> getPlayerByAlias(String alias, int teamId);

    List<PlayerUsage> getMostUsedPlayers(int limit, int fantasySeasonId);

    List<PlayerUsage> getMostUsedPlayersForRound(int limit, int fantasyRoundId);

    List<PlayerUsage> getMostUsedPlayersForTeam(int limit, int fantasyTeamId, int fantasySeasonId);

    List<PlayerUsage> getMostUsedPlayersAsTeamForTeam(int fantasyTeamId, int fantasySeasonId);


    List<Player> getPlayerByLastName(String lastName, int teamId);
}
