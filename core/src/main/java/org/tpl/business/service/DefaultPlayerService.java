package org.tpl.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.Team;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.PlayerUsage;
import org.tpl.business.model.search.*;
import org.tpl.business.service.fantasy.FantasyService;
import org.tpl.business.service.fantasy.exception.PlayerDeleteException;
import org.tpl.integration.dao.PlayerDao;
import org.tpl.integration.dao.PlayerStatsDao;

import java.util.*;

public class DefaultPlayerService extends AbstractService implements PlayerService {
    @Autowired
    PlayerDao playerDao;

    @Autowired
    PlayerStatsDao playerStatsDao;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    LeagueService leagueService;

    public void saveOrUpdatePlayer(Player player) {
        playerDao.saveOrUpdatePlayer(player);
    }

    public void deletePlayer(int playerId) throws PlayerDeleteException {
        Player player = playerDao.getPlayerById(playerId);
        if(player == null){
            throw new PlayerDeleteException("Player does not exist, cannot be deleted");
        }

        boolean playerHasStats = playerStatsDao.playerHasStats(playerId);
        if(! playerHasStats){
            playerDao.deletePlayer(playerId);
        }
        else{
            throw new PlayerDeleteException("Player has stats, cannot be deleted");
        }

    }

    public Player getPlayerById(int id) {
        Player player =  playerDao.getPlayerById(id);
        updateDependencies(player);
        return player;
    }

    public PlayerSearchResult getPlayerBySearchTerm(SearchTerm term) {
        return playerDao.getPlayerBySearchTerm(term);
    }

    public List<Player> findPlayerByName(String firstName, String lastName){
        ComparisonTerm term1 = new ComparisonTerm("firstname", Operator.LIKE, firstName);
        ComparisonTerm term2 = new ComparisonTerm("lastname", Operator.LIKE, lastName);
        AndTerm andTerm = new AndTerm(term1,term2);
        List<Player> players = playerDao.getPlayerBySearchTerm(andTerm).getResults();
        updateDependenciesForPlayers(players);
        return players;
    }

    public List<Player> getPlayerByName(String firstName, String lastName, int teamId) {
        ComparisonTerm term1 = new ComparisonTerm("team_id", Operator.LIKE, teamId);
        ComparisonTerm term2 = new ComparisonTerm("lastname", Operator.LIKE, lastName);
        AndTerm andTerm;
        if(firstName != null){
            ComparisonTerm term3 = new ComparisonTerm("firstname", Operator.LIKE, firstName);
            AndTerm secondAndTerm = new AndTerm(term1,term2);
            andTerm = new AndTerm(term3,secondAndTerm);
        }else{
            andTerm = new AndTerm(term1,term2);
        }
        List<Player> players = playerDao.getPlayerBySearchTerm(andTerm).getResults();
        updateDependenciesForPlayers(players);
        return players;
    }

    public List<Player> searchForPlayer(String query) {
        OrTerm orTerm = new OrTerm();
        orTerm.addTerm(new ComparisonTerm("firstname", Operator.LIKE, query));
        orTerm.addTerm(new ComparisonTerm("lastname", Operator.LIKE, query));
        orTerm.addTerm(new ComparisonTerm("alias", Operator.LIKE, query));
        List<Player> players = playerDao.getPlayerBySearchTerm(orTerm).getResults();
        updateDependenciesForPlayers(players);
        return players;

    }

    public List<Player> getAllPlayers(){
        List<Player> players = playerDao.getAllPlayers();
        updateDependenciesForPlayers(players);
        return players;
    }

    public Player getPlayerByExternalId(int externalId) {
        ComparisonTerm term = new ComparisonTerm("externalId", Operator.EQ, externalId);
        PlayerSearchResult playerSearchResult = playerDao.getPlayerBySearchTerm(term);
        if(playerSearchResult.getResults().size()> 0){
            Player player =  playerSearchResult.getResults().get(0);
            updateDependencies(player);
            return player;
        }
        else return null;
    }

    public List<Player> getPlayersByTeamId(int teamId) {
        ComparisonTerm term = new ComparisonTerm("team_id", Operator.EQ, teamId);
        List<Player> players = playerDao.getPlayerBySearchTerm(term).getResults();
        updateDependenciesForPlayers(players);
        return players;
    }

    public List<Player> getPlayerByAlias(String alias, int teamId) {
        OrTerm orTerm = createOrTermFromAlias(alias, teamId);
        List<Player> playerList = getPlayerBySearchTerm(orTerm).getResults();
        updateDependenciesForPlayers(playerList);
        return playerList;
    }

    public List<PlayerUsage> getMostUsedPlayers(int limit, int fantasySeasonId) {
        List<FantasyRound> fantasyRounds = fantasyService.getFantasyRoundByFantasySeasonId(fantasySeasonId);
        int[] intArray = createIntArray(fantasyRounds);
        List<PlayerUsage> mostUsedPlayers = playerDao.getMostUsedPlayers(limit, intArray);
        updateDependenciesForPlayers(mostUsedPlayers);
        return mostUsedPlayers;
    }

    public List<PlayerUsage> getMostUsedPlayersForRound(int limit, int fantasyRoundId) {
        List<PlayerUsage> mostUsedPlayersForRound = playerDao.getMostUsedPlayersForRound(limit, fantasyRoundId);
        updateDependenciesForPlayers(mostUsedPlayersForRound);
        return mostUsedPlayersForRound;
    }

    public List<PlayerUsage> getMostUsedPlayersForTeam(int limit, int fantasyTeamId, int fantasySeasonId) {
        List<FantasyRound> fantasyRounds = fantasyService.getFantasyRoundByFantasySeasonId(fantasySeasonId);
        int[] intArray = createIntArray(fantasyRounds);
        List<PlayerUsage> mostUsedPlayersForTeam = playerDao.getMostUsedPlayersForTeam(limit, fantasyTeamId, intArray);
        updateDependenciesForPlayers(mostUsedPlayersForTeam);
        return mostUsedPlayersForTeam;
    }

    public List<PlayerUsage> getMostUsedPlayersAsTeamForTeam(int fantasyTeamId, int fantasySeasonId) {
        List<FantasyRound> fantasyRounds = fantasyService.getFantasyRoundByFantasySeasonId(fantasySeasonId);
        int[] intArray = createIntArray(fantasyRounds);
        List<PlayerUsage> mostUsedPlayersForTeam = playerDao.getMostUsedPlayersForTeam(30, fantasyTeamId, intArray);
        updateDependenciesForPlayers(mostUsedPlayersForTeam);
        List<PlayerUsage> players = new ArrayList<PlayerUsage>();
        players.addAll(getPlayers(PlayerPosition.GOALKEEPER, 1, mostUsedPlayersForTeam));
        players.addAll(getPlayers(PlayerPosition.DEFENDER, 4, mostUsedPlayersForTeam));
        players.addAll(getPlayers(PlayerPosition.MIDFIELDER, 4, mostUsedPlayersForTeam));
        players.addAll(getPlayers(PlayerPosition.STRIKER, 2, mostUsedPlayersForTeam));


        return players;
    }


    private List<PlayerUsage> getPlayers(PlayerPosition position, int numberOfPlayers, List<PlayerUsage> mostUsedPlayersForTeam) {
        List<PlayerUsage> playerUsages = new ArrayList<PlayerUsage>();
        for(int i = 0; i < mostUsedPlayersForTeam.size(); i++){
            PlayerUsage playerUsage = mostUsedPlayersForTeam.get(i);
            if(playerUsage.getPlayerPosition().equals(position) && playerUsages.size() < numberOfPlayers){
                playerUsages.add(playerUsage);
            }
        }
        return playerUsages;
    }

    private void updateDependenciesForPlayers(List<? extends Player> players){
        for(Player player: players){
            updateDependencies(player);
        }
    }


    private void updateDependencies(Player player){
        if(player == null) return;
        if(player.getTeam() != null && ! player.getTeam().isNew()){
            Team team = leagueService.getTeamById(player.getTeam().getTeamId());
            player.setTeam(team);
        }
    }

    protected OrTerm createOrTermFromAlias(String alias, int teamId) {
        ComparisonTerm aliasTerm = new ComparisonTerm("alias", Operator.LIKE,"%"+alias+"%");
        ComparisonTerm teamTerm = new ComparisonTerm("team_id", Operator.EQ, teamId);
        AndTerm aliasAndTeamTerm = new AndTerm(aliasTerm, teamTerm);
        OrTerm orTerm = new OrTerm();
        orTerm.addTerm(aliasAndTeamTerm);
        String[] split = alias.trim().split(" ");
        if(split.length == 1){
            ComparisonTerm lastnameTerm = new ComparisonTerm("lastname", Operator.EQ , split[0]);
            AndTerm lastNameAndTeamTerm= new AndTerm(lastnameTerm, teamTerm);
            orTerm.addTerm(lastNameAndTeamTerm);

        }
        if(split.length == 2){
            ComparisonTerm firstnameTerm = new ComparisonTerm("firstname", Operator.EQ , split[0]);
            AndTerm nameAndTeamTerm= new AndTerm(firstnameTerm, teamTerm);
            ComparisonTerm lastnameTerm = new ComparisonTerm("lastname", Operator.EQ , split[1]);
            nameAndTeamTerm.addTerm(lastnameTerm);
            orTerm.addTerm(nameAndTeamTerm);
        }
        if(split.length > 2){
            ComparisonTerm firstnameTerm = new ComparisonTerm("firstname", Operator.EQ , alias.substring(0,alias.lastIndexOf(" ")));
            AndTerm nameAndTeamTerm= new AndTerm(firstnameTerm, teamTerm);
            ComparisonTerm lastnameTerm = new ComparisonTerm("lastname", Operator.EQ , split[split.length-1]);
            nameAndTeamTerm.addTerm(lastnameTerm);
            orTerm.addTerm(nameAndTeamTerm);
        }
        return orTerm;
    }



}
