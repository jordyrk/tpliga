package org.tpl.business.model.search;

import org.tpl.business.model.Player;
import org.tpl.business.model.fantasy.FantasyMatch;

import java.util.List;

/**
 * User: Koren
 * Date: 21.jun.2010
 * Time: 20:36:12
 */
public class FantasyMatchSearchResult {
    private List<FantasyMatch> results;
    private int totalNumberOfResults;

    public List<FantasyMatch> getResults() {
        return results;
    }

    public void setResults(List<FantasyMatch> results) {
        this.results = results;
    }

    public int getTotalNumberOfResults() {
        return totalNumberOfResults;
    }

    public void setTotalNumberOfResults(int totalNumberOfResults) {
        this.totalNumberOfResults = totalNumberOfResults;
    }
}
