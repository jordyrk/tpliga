package org.tpl.business.model.search;

import org.tpl.business.model.Match;
import org.tpl.business.model.Team;

import java.util.List;

/**
 * User: Koren
 * Date: 15.aug.2010
 * Time: 21:55:44
 */
public class MatchSearchResult {
    private List<Match> results;
    private int totalNumberOfResults;

    public List<Match> getResults() {
        return results;
    }

    public void setResults(List<Match> results) {
        this.results = results;
    }

    public int getTotalNumberOfResults() {
        return totalNumberOfResults;
    }

    public void setTotalNumberOfResults(int totalNumberOfResults) {
        this.totalNumberOfResults = totalNumberOfResults;
    }

}
