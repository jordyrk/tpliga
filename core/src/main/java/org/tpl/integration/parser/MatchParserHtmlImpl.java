package org.tpl.integration.parser;

import no.kantega.commons.log.Log;
import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Match;
import org.tpl.business.model.Team;
import org.w3c.dom.Document;
import org.w3c.tidy.DOMElementImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * User: Koren
 * Date: 27.jun.2010
 * Time: 08:51:05
 */
public class MatchParserHtmlImpl extends AbstractParser implements MatchConstructor{

    private String baseUrl = "http://snutt.nrk.no/sport_apps/legacy-results/?path=fotball-premier-league/&sesong=2015/2016&type=terminliste#runde-1";
    private  SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
    private Document document;
    public List<Match> getMatches() throws MatchImportException {
        List<Match> matches = new ArrayList<Match>();

        Document document = openHtmlDocument(baseUrl);

        List<DOMElementImpl> nodeList = getNodeList(document, "table", "class", "matches");

        for(DOMElementImpl table : nodeList){
            //TODO: TA ut runde
            List<DOMElementImpl> rows = nodeSearch(table, "tr", null);
            Date day = null;
            int roundNumber = getRoundNumber(table);
            LeagueRound leagueRound = new LeagueRound();
            leagueRound.setRound(roundNumber);
            for(DOMElementImpl row: rows){
                if(rowIsDateRow(row)){
                    DOMElementImpl element = nodeSearchFindFirstNode(row, "a", null);
                    if(element != null){
                        day = getDateFromElement(element);
                    }
                }
                if(rowIsMatchRow(row)){
                    List<DOMElementImpl> linkList = nodeSearch(row, "a", null);
                    Match match = new Match();
                    match.setLeagueRound(leagueRound);
                    String hometeamName = getText(linkList.get(0)).trim();
                    String awayTeamName = getText(linkList.get(1)).trim();
                    Team homeTeam = findTeam(hometeamName);
                    Team awayTeam = findTeam(awayTeamName);
                    match.setHomeTeam(homeTeam);
                    match.setAwayTeam(awayTeam);

                    if(linkList.size() == 4){
                        String score = getText(linkList.get(3)).trim();
                        String[] split = score.split("-");
                        match.setHomeGoals(parseInt(split[0]));
                        match.setAwayGoals(parseInt(split[1]));
                        match.setMatchDate(day);
                        
                    }else{
                        try{
                            Date exactDate = getDateFromString(day, getText(linkList.get(2)).trim());
                            match.setMatchDate(exactDate);
                        } catch (NullPointerException e){
                            Log.error(this.getClass().getName(),e);
                        }

                    }

                    matches.add(match);
                    //String matchScore = match.isMatchPlayed() ? " (" + match.getHomeGoals() + " - " + match.getAwayGoals()+") ": " - ";
                    //System.out.println(match.getLeagueRound().getRound() +": " + match.getMatchDate() + ": " + hometeamName + matchScore + awayTeamName);
                }
            }
        }
        return matches;
    }

    private Date getDateFromString(Date day, String matchTime) {
        matchTime = matchTime.substring(matchTime.length()-5,matchTime.length());
        int hour = parseInt(matchTime.substring(0,2));
        int minute = parseInt(matchTime.substring(3,matchTime.length()));
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(day);
        gc.add(GregorianCalendar.HOUR, hour);
        gc.add(GregorianCalendar.MINUTE, minute);
        return gc.getTime();
    }

    private int getRoundNumber(DOMElementImpl table) {
        List<DOMElementImpl> domElements = nodeSearch(table, "caption", "id");
        int roundNumber = -1;
        for(DOMElementImpl element: domElements){
            List<DOMElementImpl> spanList = nodeSearch(element, "span", null);
            if(spanList.size() == 1){
                String text = getText(spanList.get(0)).trim();

                text = text.substring(0,text.indexOf('.'));
                roundNumber = parseInt(text);
            }
        }
        return roundNumber;
    }

    protected Team findTeam(String teamName) throws MatchImportException {
        List<Team> teamByName = leagueService.findTeamByName(teamName);
        if(teamByName.size() == 1){
            return teamByName.get(0);
        }
        else if(teamByName.size() == 0){
            throw new MatchImportException("Found zero teams with name: " + teamName);
        }
        else {
            throw new MatchImportException("Found multiple teams with name: " + teamName);
        }
    }

    private Date getDateFromElement(DOMElementImpl element) {
        String text = getText(element).trim();
        if(text.length() == 8){
            return parseDate(text);
        }
        return null;
    }

    private boolean rowIsDateRow(DOMElementImpl row) {
        List<DOMElementImpl> domElements = nodeSearch(row, "th", "class", "inside");
        return  domElements.size() > 0;
    }

    private boolean rowIsMatchRow(DOMElementImpl row) {
        List<DOMElementImpl> domElements = nodeSearch(row, "a", "title", "Mer om");
        return  domElements.size() > 0;
    }


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private Date parseDate(String dateString){
        try{
            return format.parse(dateString);
        }   catch (ParseException e){
            return null;
        }
    }
}
