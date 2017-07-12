package org.tpl.integration.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Match;
import org.tpl.business.model.Team;
import org.tpl.business.service.LeagueService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jordyr on 7/21/16.
 */
public class MatchParserNRKJsonImpl implements MatchConstructor {
    private String baseUrl = "http://snutt.nrk.no/sport_apps/resultat/backend/data/kasparov.api/idretter/501/turneringer/1503/sesonger/95191/terminliste";
    private SimpleDateFormat format = new SimpleDateFormat("HH.mm dd.MM.yy");
    protected LeagueService leagueService;

    @Override
    public List<Match> getMatches() throws MatchImportException {
        List<Match> matchList = new ArrayList<>();
        Map<String, Object> contentMap = getContent();
        Map<String, Team> teamMap = getTeamsFromDataBase((Map) contentMap.get("lag"));
        List<Map> terminliste = (List) contentMap.get("terminliste");
        for(Map map : terminliste){
            Match match = new Match();
            List celler = (List) map.get("celler");
            addTeamsToMatch(teamMap, match, celler);
            Date date = extractDate(celler);
            match.setMatchDate(date);
            Map kampinfo = (Map) celler.get(5);
            Integer homeGoal = (Integer) kampinfo.get("hjemme");
            match.setHomeGoals(homeGoal);
            Integer awayGoal = (Integer) kampinfo.get("borte");
            match.setAwayGoals(awayGoal);
            Map rundeinfo = (Map) celler.get(7);
            Integer runde = (Integer) rundeinfo.get("indeks");
            LeagueRound leagueRound = new LeagueRound();
            leagueRound.setRound(runde);
            match.setLeagueRound(leagueRound);
            matchList.add(match);
        }
        return matchList;
    }

    private Date extractDate(List celler) throws MatchImportException {
        String kampdato = (String) ((Map) celler.get(2)).get("tekst");
        String kamptidspunkt = ((String) celler.get(3)).replace(".",":");
        return formaterKamptidspunkt(kamptidspunkt + "," + kampdato);
    }

    private Date formaterKamptidspunkt(String kamptidspunkt) throws MatchImportException {
        kamptidspunkt = kamptidspunkt.replaceAll(" august ","08.");
        kamptidspunkt = kamptidspunkt.replaceAll(" september ","09.");
        kamptidspunkt = kamptidspunkt.replaceAll(" oktober ","10.");
        kamptidspunkt = kamptidspunkt.replaceAll(" november ","11.");
        kamptidspunkt = kamptidspunkt.replaceAll(" desember ","12.");
        kamptidspunkt = kamptidspunkt.replaceAll(" januar ","01.");
        kamptidspunkt = kamptidspunkt.replaceAll(" februar ","02.");
        kamptidspunkt = kamptidspunkt.replaceAll(" mars ","03.");
        kamptidspunkt = kamptidspunkt.replaceAll(" april ","04.");
        kamptidspunkt = kamptidspunkt.replaceAll(" mai ","05.");
        kamptidspunkt = kamptidspunkt.replaceAll(" juni ","06.");
        kamptidspunkt = kamptidspunkt.replaceAll(" juli ","07.");
        DateFormat formatter = new SimpleDateFormat("HH:mm,dd.MM.yyyy");
        try {
            return formatter.parse(kamptidspunkt);
        } catch (ParseException e) {
            throw new MatchImportException("Error parsing date");
        }


    }

    private void addTeamsToMatch(Map<String, Team> teamMap, Match match, List celler) {
        Map<String, Object> hjemmelagMap = (Map) celler.get(0);
        Map<String, Object> bortelagMap = (Map) celler.get(1);
        Team homeTeam = extractTeam(teamMap, hjemmelagMap);
        match.setHomeTeam(homeTeam);
        Team awayTeam = extractTeam(teamMap, bortelagMap);
        match.setAwayTeam(awayTeam);
    }

    private Team extractTeam(Map<String, Team> teamMap, Map<String, Object> lagMap) {
        Integer id = (Integer) lagMap.get("id");
        return teamMap.get(id.toString());
    }

    private Map<String, Team> getTeamsFromDataBase(Map lagMap) throws MatchImportException {
        Map<String, Team> teamMap = new HashMap<>();
        Set<String> set = lagMap.keySet();
        for (String lagKey : set) {
            Map<String, Object> lag = (Map) lagMap.get(lagKey);
            String navn = (String) lag.get("navn");
            Team team = findTeam(navn);
            teamMap.put(lagKey, team);
        }
        return teamMap;
    }

    private Map getContent() throws MatchImportException {
        Response response = doRequest();
        String s = response.readEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap hashMap = objectMapper.readValue(s, HashMap.class);
            return hashMap;
        } catch (IOException e) {
            throw new MatchImportException("Error reading data from api");
        }
    }

    protected Response doRequest() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl);
        Invocation.Builder innvocationBuilder = target.request();
        return innvocationBuilder.get();
    }

    protected Team findTeam(String teamName) throws MatchImportException {
        List<Team> teamByName = leagueService.findTeamByName(teamName);
        if (teamByName.size() == 1) {
            return teamByName.get(0);
        } else if (teamByName.size() == 0) {
            throw new MatchImportException("Found zero teams with name: " + teamName);
        } else {
            throw new MatchImportException("Found multiple teams with name: " + teamName);
        }
    }

    public void setLeagueService(LeagueService leagueService) {
        this.leagueService = leagueService;
    }
}
