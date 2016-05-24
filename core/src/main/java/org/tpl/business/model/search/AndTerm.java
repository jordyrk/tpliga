package org.tpl.business.model.search;

/**
 *
 */
public class AndTerm extends AndOrTerm {
    public AndTerm() {
    }

    public AndTerm(SearchTerm term1, SearchTerm term2) {
        super(term1, term2);
    }

    protected Object getSqlOperator() {
        return "AND";
    }
}
