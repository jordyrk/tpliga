package org.tpl.integration.parser;

import org.tpl.business.model.Match;
import org.tpl.business.model.Team;
import org.w3c.dom.Document;
import org.w3c.tidy.DOMElementImpl;

import java.util.ArrayList;
import java.util.List;

public class FantasyPremierLeagueMatchHtmlParser extends AbstractParser{
    private String baseUrl;
    protected Document document;

    public void init(int round){
        document = openHtmlDocument(String.format(baseUrl, round));
    }

    public void tearDown(){
        document = null;
    }

    public List<Match> getMatches() {
        List<Match> matchList = new ArrayList<Match>();
        List<DOMElementImpl> tableList = getNodeList(document, "table", "class", "ismFixtureTable");
        DOMElementImpl tableElement = tableList.get(0);
        List<DOMElementImpl[]> teamAndSummaryRows = getTeamAndSummaryRows(tableElement);
        for(DOMElementImpl[] teamSummaryArray :teamAndSummaryRows){
            String homeTeam = getHomeTeamFromNode(teamSummaryArray[0]);
            String awayTeam = getAwayTeamFromNode(teamSummaryArray[0]);
            int id = -1;
            if(teamSummaryArray[1]!= null){
                id = getIdFromNode(teamSummaryArray[1]);
            }
            Match match = createMatchFromStrings(homeTeam, awayTeam, id);
            matchList.add(match);


        }

        return matchList;
    }

    private int getIdFromNode(DOMElementImpl domElement) {
        DOMElementImpl idElement = nodeSearchFindFirstNode(domElement, "a", "class", "ismFixtureStatsLink");
        return Integer.parseInt(getAttributeValue(idElement, "data-id"));
    }

    private String getHomeTeamFromNode(DOMElementImpl domElement) {
        DOMElementImpl teamNameElement = nodeSearchFindFirstNode(domElement, "td", "class", "ismHomeTeam");
        return getText(teamNameElement);
    }

    private String getAwayTeamFromNode(DOMElementImpl domElement) {
        DOMElementImpl teamNameElement = nodeSearchFindFirstNode(domElement, "td", "class", "ismAwayTeam");
        return getText(teamNameElement);
    }


    private Match createMatchFromStrings(String homeTeamName, String awayTeamName, int id) {
        Match match = new Match();
        Team homeTeam = new Team();
        homeTeam.setFullName(homeTeamName);
        Team awayTeam = new Team();
        awayTeam.setFullName(awayTeamName);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setFantasyPremierLeagueId(id);
        return match;

    }

    private List<DOMElementImpl[]> getTeamAndSummaryRows(DOMElementImpl tableElement){
        List<DOMElementImpl[]> teamAndSummaryRows = new ArrayList<DOMElementImpl[]>();
        List<DOMElementImpl> tr = nodeSearch(tableElement, "tr", null);
        for(int i = 0; i < tr.size(); i+=2){
            DOMElementImpl[] elementArray = new DOMElementImpl[2];
            DOMElementImpl rowNode = tr.get(i);
            DOMElementImpl summaryNode = null;
            if((i+1) < tr.size()){
                summaryNode = tr.get(i+1);
            }

            elementArray[0] = rowNode;
            if(summaryNode != null){
                String classValue = getAttributeValue(summaryNode, "class");
                if(classValue.toLowerCase().contains("ismfixturesummary")){
                    elementArray[1] = summaryNode;

                }else{
                    --i;
                }
            }
            teamAndSummaryRows.add(elementArray);
        }
        return teamAndSummaryRows;

    }


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
