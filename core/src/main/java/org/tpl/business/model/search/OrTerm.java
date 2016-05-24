package org.tpl.business.model.search;

/**
 *
 */
public class OrTerm extends AndOrTerm {
    public OrTerm() {
    }

    public OrTerm(SearchTerm term1, SearchTerm term2) {
        super(term1, term2);
    }

    protected Object getSqlOperator() {
        return "OR";
    }
}
