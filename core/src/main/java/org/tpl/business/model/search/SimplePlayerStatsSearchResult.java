package org.tpl.business.model.search;

import org.tpl.business.model.SimplePlayerStats;

import java.util.List;

/**
 * User: Koren
 * Date: 21.jun.2010
 * Time: 20:36:12
 */
public class SimplePlayerStatsSearchResult {
    private List<SimplePlayerStats> results;
    private int totalNumberOfResults;

    public List<SimplePlayerStats> getResults() {
        return results;
    }

    public void setResults(List<SimplePlayerStats> results) {
        this.results = results;
    }

    public int getTotalNumberOfResults() {
        return totalNumberOfResults;
    }

    public void setTotalNumberOfResults(int totalNumberOfResults) {
        this.totalNumberOfResults = totalNumberOfResults;
    }
}
