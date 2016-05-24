package org.tpl.business.model.search;



import org.tpl.business.model.Team;

import java.util.List;

/**
 *
 */
public class TeamSearchResult {
    private List<Team> results;
    private int totalNumberOfResults;

    public List<Team> getResults() {
        return results;
    }

    public void setResults(List<Team> results) {
        this.results = results;
    }

    public int getTotalNumberOfResults() {
        return totalNumberOfResults;
    }

    public void setTotalNumberOfResults(int totalNumberOfResults) {
        this.totalNumberOfResults = totalNumberOfResults;
    }
}
