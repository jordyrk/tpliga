package org.tpl.business.model.search;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class ComparisonTerm implements SearchTerm {
    private String property;
    private Operator operator;
    private Object value;

    public ComparisonTerm(String property, Operator operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getQuery() {
        return property + " " + operator.getOp() +  " ?";
    }

    public List<Object> getParameters() {
        List<Object> params = new ArrayList<Object>();

        Object v = value;
        if (v != null && v instanceof String) {
            String str = (String)value;
            str = str.replace('*', '%');
            if (operator == Operator.LIKE && !str.contains("%")) {
                str = "%" + str + "%";
            }
            v = str;
        }
        params.add(v);
        return params;
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}
