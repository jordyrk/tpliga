package org.tpl.integration.parser;

import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerStats;
import org.w3c.dom.Document;
import org.w3c.tidy.DOMElementImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jordyr on 8/25/14.
 */
public class FantasyPremierLeaguePlayerStatsParser extends AbstractParser{
    private String baseUrl;
    protected Document document;
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<PlayerStats> importPlayerStatsForHomeTeam() {
        return importPlayerStats(0);

    }

    public List<PlayerStats> importPlayerStatsForAwayTeam() {
        return importPlayerStats(1);

    }

    private List<PlayerStats> importPlayerStats(int tableIndex){
        List<PlayerStats> playerStatsList = new ArrayList<PlayerStats>();
        List<DOMElementImpl> tableElementList = getNodeList(document, "table", "class", "ismJsStatsGrouped");
        DOMElementImpl tableTeamElement = tableElementList.get(tableIndex);
        playerStatsList.addAll(getPlayerStats(tableTeamElement));
        return playerStatsList;
    }

    private List<PlayerStats> getPlayerStats(DOMElementImpl tableHomeTeamElement) {
        List<PlayerStats> playerStatsList = new ArrayList<PlayerStats>();
        DOMElementImpl tbodyElement = nodeSearchFindFirstNode(tableHomeTeamElement, "tbody", null);
        List<DOMElementImpl> trElementList = nodeSearch(tbodyElement, "tr", null);
        for(DOMElementImpl tableRowElement: trElementList){
            PlayerStats playerStats = parsePlayerStatsFromNode(tableRowElement);
            playerStatsList.add(playerStats);

        }

        return playerStatsList;
    }

    private PlayerStats parsePlayerStatsFromNode(DOMElementImpl tableRowElement) {
        List<DOMElementImpl> cellElementList = nodeSearch(tableRowElement, "td", null);
        DOMElementImpl nameElement = cellElementList.get(0);
        String playerName = getText(nameElement).trim();
        playerName = replaceIllegalCharacters(playerName);
        DOMElementImpl minutesPlayedElement = cellElementList.get(1);
        int minutesPlayed = getIntFromElement(minutesPlayedElement);

        DOMElementImpl goalsScoredElement = cellElementList.get(2);
        int goalsScored = getIntFromElement(goalsScoredElement);

        DOMElementImpl assistsElement = cellElementList.get(3);
        int assists = getIntFromElement(assistsElement);

        DOMElementImpl ownGoalsElement = cellElementList.get(6);
        int owngoals = getIntFromElement(ownGoalsElement);

        DOMElementImpl penaltySavedElement = cellElementList.get(7);
        int penaltySaved = getIntFromElement(penaltySavedElement);

        DOMElementImpl penaltyMissedElement = cellElementList.get(8);
        int penaltyMissed = getIntFromElement(penaltyMissedElement);

        DOMElementImpl yellowCardElement = cellElementList.get(9);
        int yellowCards = getIntFromElement(yellowCardElement);

        DOMElementImpl redCardElement = cellElementList.get(10);
        int redCard = getIntFromElement(redCardElement);
        DOMElementImpl savesElement = cellElementList.get(11);
        int saves = getIntFromElement(savesElement);
        DOMElementImpl eaSportsPPIElement= cellElementList.get(14);
        int eaSportsPPI= getIntFromElement(eaSportsPPIElement);


        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(createPlayer(playerName));
        playerStats.setPlayedMinutes(minutesPlayed);
        if(minutesPlayed >= 90){
            playerStats.setWholeGame(true);
        }
        playerStats.setGoals(goalsScored);
        playerStats.setAssists(assists);
        playerStats.setOwnGoal(owngoals);
        playerStats.setSavedPenalty(penaltySaved);
        playerStats.setMissedPenalty(penaltyMissed);
        playerStats.setYellowCard(yellowCards);
        playerStats.setRedCard(redCard > 0 ? true: false);
        playerStats.setSaves(saves);
        playerStats.setEaSportsPPI(eaSportsPPI);

        return playerStats;


    }

    private String replaceIllegalCharacters(String playerName) {
        char illegalChar = 263;//c med toddler, får feil i databasen
        char legalChar = 99; //c
        String illegalString = "" + illegalChar;
        String legalString = "" + legalChar;
        return playerName.replaceAll(illegalString, legalString);
    }


    private int getIntFromElement(DOMElementImpl element){
        try{
            return Integer.parseInt(getText(element).trim());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    private Player createPlayer(String playerName) {
        Player player = new Player();
        player.setLastName(playerName);
        return player;

    }

    public void init(int fantasyPLMatchId){
        document = openHtmlDocument(String.format(baseUrl, fantasyPLMatchId));

    }



}
