package org.tpl.business.model.search;

/**
 *
 */
public enum Operator {
    NOT("<>"),
    EQ("="),
    GE(">="),
    G(">"),
    LE("<="),
    L("<"),
    LIKE("LIKE"),
    IN ("IN");

    private String op;

    Operator(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }
}
