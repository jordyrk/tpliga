package org.tpl.business.model.search;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public abstract class AndOrTerm implements SearchTerm {
    protected List<SearchTerm> terms = new ArrayList<SearchTerm>();

    protected AndOrTerm() {
    }

    public AndOrTerm(SearchTerm term1, SearchTerm term2) {
        terms.add(term1);
        terms.add(term2);
    }

    public void addTerm(SearchTerm searchTerm) {
        terms.add(searchTerm);
    }

    public List<Object> getParameters() {
        List<Object> params = new ArrayList<Object>();
        for (SearchTerm t : terms) {
            params.addAll(t.getParameters());
        }
        return params;
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();
        if (terms.size() == 0) {
            return "";
        }

        query.append(" (");
        for (int i = 0; i < terms.size(); i++) {
            if (i > 0) {
                query.append(" ");
                query.append(getSqlOperator());
                query.append(" ");
            }
            query.append(terms.get(i).getQuery());
        }
        query.append(")");

        return query.toString();
    }

    protected abstract Object getSqlOperator();
}
