package org.tpl.integration.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tpl.business.model.statsfc.MonthEnum;
import org.tpl.business.service.LeagueService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

/**
 * Created by jordyr on 7/21/16.
 */
public class StatsFcPlayerStatsJsonImpl {
    private String baseUrl = "https://dugout.statsfc.com/api/v2/results%s";
    private final String key;

    public StatsFcPlayerStatsJsonImpl(String key) {
        this.key = key;
    }

    protected LeagueService leagueService;

    public List<Map<String,Object>> getMatches(MonthEnum month) throws MatchImportException {
        Map content = getContent(month);
        return  (ArrayList) content.get("data");
    }



    private Map getContent(MonthEnum month) throws MatchImportException {
        Response response = doRequest(month);
        String s = response.readEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap hashMap = objectMapper.readValue(s, HashMap.class);
            return hashMap;
        } catch (IOException e) {
            throw new MatchImportException("Error reading data from api");
        }
    }


    protected Response doRequest(MonthEnum month) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(String.format(baseUrl,month.getQueryString()));

        Invocation.Builder innvocationBuilder = target.request().header("X-StatsFC-Key",key);
        return innvocationBuilder.get();
    }

    public void setLeagueService(LeagueService leagueService) {
        this.leagueService = leagueService;
    }


}
