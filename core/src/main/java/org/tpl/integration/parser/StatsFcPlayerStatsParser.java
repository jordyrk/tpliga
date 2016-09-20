package org.tpl.integration.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.model.Team;
import org.tpl.business.service.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jordyr on 8/25/14.
 */
public class StatsFcPlayerStatsParser {

    @Autowired
    PlayerService playerService;


    public List<PlayerStats> importPlayerStatsForTeam(Map<String, Object> stringObjectMap, TeamParserEnum teamParserEnum) {
        List<Map<String, Object>> playerList = getList(getMap(stringObjectMap, "players"), teamParserEnum.getLocation());
        Team team = getTeam(getMap(stringObjectMap, "teams"), teamParserEnum);
        List<PlayerStats> playerStatsList = addPlayerStats(stringObjectMap, playerList, team);
        return playerStatsList;
    }

    private List<PlayerStats> addPlayerStats(Map<String, Object> stringObjectMap, List<Map<String, Object>> playerList, Team team) {
        List<PlayerStats> playerStatsList = new ArrayList<>();
        addStartingPlayers(playerList, playerStatsList, team);

        //Important that substituions are added before Card and Goal events
        addSubstitutionEvents(stringObjectMap, playerStatsList, team);
        addCardEvents(stringObjectMap, playerStatsList, team);
        addGoalEvents(stringObjectMap, playerStatsList, team);
        return playerStatsList;
    }


    private void addStartingPlayers(List<Map<String, Object>> playerList, List<PlayerStats> playerStatsList, Team team) {
        for (Map<String, Object> playerMap : playerList) {
            String role = getString(playerMap, "role");
            String name = getString(playerMap, "name");
            if ("starting".equalsIgnoreCase(role)) {

                Player player = createPlayerFromFullName(name);
                //All starting players are set at played 90 minutes. Substitutions will handle otherwise.
                PlayerStats playerStats = createPlayerStats(team, 90, player, true);
                playerStatsList.add(playerStats);
            }
        }
    }

    private void addSubstitutionEvents(Map<String, Object> stringObjectMap, List<PlayerStats> playerStatsList, Team team) {
        List<Map<String, Object>> substitutionsEvents = getList(getMap(stringObjectMap, "events"), "substitutions");
        for (Map<String, Object> substitutionsEvent : substitutionsEvents) {
            Map<String, Object> teamMap = (Map<String, Object>) substitutionsEvent.get("team");
            String teamName = getString(teamMap, "name");
            if (team.getFullName().equalsIgnoreCase(teamName)) {
                int matchTime = getMatchTime(substitutionsEvent);

                Map<String, Object> playerOffMap = getMap(substitutionsEvent, "playerOff");
                String namePlayerOff = getString(playerOffMap, "name");
                PlayerStats playerStatsPlayerOff = getPlayerStats(playerStatsList, namePlayerOff);
                playerStatsPlayerOff.setPlayedMinutes(matchTime);
                playerStatsPlayerOff.setWholeGame(false);

                Map<String, Object> playerOnMap = getMap(substitutionsEvent, "playerOn");
                String namePlayerOn = getString(playerOnMap, "name");
                Player playerFromName = createPlayerFromFullName(namePlayerOn);

                PlayerStats playerStats = createPlayerStats(team, 90 - matchTime, playerFromName, false);
                playerStatsList.add(playerStats);
            }
        }
    }

    private void addGoalEvents(Map<String, Object> stringObjectMap, List<PlayerStats> playerStatsList, Team team) {
        List<Map<String, Object>> goalEvents = getList(getMap(stringObjectMap, "events"), "goals");
        for (Map<String, Object> goalEvent : goalEvents) {

            Map<String, Object> teamMap = getMap(goalEvent, "team");
            String teamName = getString(teamMap, "name");
            String subType = getString(goalEvent, "subType");
            if (team.getFullName().equalsIgnoreCase(teamName) && !"own-goal".equals(subType)) {
                Map<String, Object> goalPlayerMap = getMap(goalEvent, "player");
                /*  This should'nt be the case, but the data provided from StatsFC does sometimes have goalevents without players.
                    It seems to be a case the goal is not approved.
                */
                if (goalPlayerMap != null) {
                    String name = getString(goalPlayerMap, "name");
                    PlayerStats goalPlayerStats = getPlayerStats(playerStatsList, name);
                    goalPlayerStats.setGoals(goalPlayerStats.getGoals() + 1);

                    Map<String, Object> assistPlayerMap = getMap(goalEvent, "assist");
                    if (assistPlayerMap != null) {
                        String assistName = getString(assistPlayerMap, "name");
                        PlayerStats assistPlayerStats = getPlayerStats(playerStatsList, assistName);
                        assistPlayerStats.setAssists(assistPlayerStats.getAssists() + 1);
                    }
                }

            } else if (!team.getFullName().equalsIgnoreCase(teamName) && "own-goal".equals(subType)) {
                Map<String, Object> goalPlayerMap = getMap(goalEvent, "player");
                String name = getString(goalPlayerMap, "name");
                PlayerStats goalPlayerStats = getPlayerStats(playerStatsList, name);
                goalPlayerStats.setOwnGoal(goalPlayerStats.getOwnGoal() + 1);

            }
        }
    }

    private void addCardEvents(Map<String, Object> stringObjectMap, List<PlayerStats> playerStatsList, Team team) {
        List<Map<String, Object>> cardEvents = getList(getMap(stringObjectMap, "events"), "cards");

        for (Map<String, Object> cardEvent : cardEvents) {

            Map<String, Object> teamMap = getMap(cardEvent, "team");
            String teamName = getString(teamMap, "name");
            if (team.getFullName().equalsIgnoreCase(teamName)) {

                String subType = getString(cardEvent, "subType");

                Map<String, Object> cardPlayerMap = getMap(cardEvent, "player");

                String fullName = getString(cardPlayerMap, "name");
                if ("red".equalsIgnoreCase(subType)) {
                    PlayerStats playerStats = getPlayerStats(playerStatsList, fullName);
                    playerStats.setRedCard(true);
                }

                if ("first-yellow".equalsIgnoreCase(subType)) {
                    PlayerStats playerStats = getPlayerStats(playerStatsList, fullName);
                    playerStats.setYellowCard(1);

                }
            }

        }
    }


    private PlayerStats createPlayerStats(Team team, int playedMinutes, Player player, boolean wholeMatch) {
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(player);
        playerStats.setTeam(team);
        playerStats.setPlayedMinutes(playedMinutes);
        playerStats.setWholeGame(wholeMatch);
        return playerStats;
    }

    private int getMatchTime(Map<String, Object> substitutionsEvent) {
        String matchTimeAsString = getString(substitutionsEvent, "matchTime");
        matchTimeAsString = matchTimeAsString.replaceAll("'", "");
        if (matchTimeAsString.contains("+")) {
            matchTimeAsString = matchTimeAsString.substring(0, matchTimeAsString.indexOf("+"));
        }
        return Integer.parseInt(matchTimeAsString);
    }

    private PlayerStats getPlayerStats(List<PlayerStats> playerStatsList, String fullName) {
        Player tempPlayer = new Player();
        tempPlayer.setFullName(fullName);

        for (PlayerStats playerStats : playerStatsList) {
            if (tempPlayer.getLastName().equals(playerStats.getPlayer().getLastName()) && (tempPlayer.getFirstName() == null || (tempPlayer.getFirstName().equals(playerStats.getPlayer().getFirstName())))) {
                return playerStats;
            }
        }
        return null;
    }

    private Team getTeam(Map<String, Object> teams,TeamParserEnum teamParserEnum) {
        Map<String, Object> teamMap = getMap(teams, teamParserEnum.getLocation());
        String teamName = (String) teamMap.get("name");
        String shortName = (String) teamMap.get("shortName");
        Team team = new Team();
        team.setFullName(teamName);
        team.setShortName(shortName);
        return team;
    }


    private Player createPlayerFromFullName(String fullName) {
        Player player = new Player();
        player.setFullName(fullName);
        return player;

    }

    private String getString(Map<String, Object> map, String name) {
        return (String) map.get(name);
    }

    private Map<String, Object> getMap(Map<String, Object> map, String name) {
        return (Map<String, Object>) map.get(name);
    }

    private List<Map<String, Object>> getList(Map<String, Object> map, String name) {
        return (List<Map<String, Object>>) map.get(name);
    }

}
