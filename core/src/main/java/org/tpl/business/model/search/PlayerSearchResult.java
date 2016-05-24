package org.tpl.business.model.search;

import org.tpl.business.model.Player;


import java.util.List;

/**
 * User: Koren
 * Date: 21.jun.2010
 * Time: 20:36:12
 */
public class PlayerSearchResult {
    private List<Player> results;
    private int totalNumberOfResults;

    public List<Player> getResults() {
        return results;
    }

    public void setResults(List<Player> results) {
        this.results = results;
    }

    public int getTotalNumberOfResults() {
        return totalNumberOfResults;
    }

    public void setTotalNumberOfResults(int totalNumberOfResults) {
        this.totalNumberOfResults = totalNumberOfResults;
    }
}
