package org.tpl.business.model.search;

import java.util.List;

public interface SearchTerm {
    public String getQuery();

    public List<Object> getParameters();
}
