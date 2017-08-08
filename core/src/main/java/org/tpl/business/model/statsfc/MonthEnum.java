package org.tpl.business.model.statsfc;

/**
 * Created by jordyr on 9/4/16.
 */
public enum MonthEnum {

    AUGUST("2017-08-01", "2017-08-31"),
    SEPTEMBER("2017-09-01", "2017-09-31"),
    OCTOBER("2017-10-01", "2017-10-31"),
    NOVEMBER("2017-11-01", "2017-11-31"),
    DECEMBER("2017-12-01", "2017-12-31"),
    JANUARY("2018-01-01", "2018-01-31"),
    FEBRUARY("2018-02-01", "2018-02-31"),
    MARCH("2018-03-01", "2018-03-31"),
    APRIL("2018-04-01", "2018-04-31"),
    MAY("2018-05-01", "2018-05-31");

    private static final String defaultQueryString = "?from=%s&to=%s";

    private String queryString;

    MonthEnum(String from, String to) {
        queryString = String.format(defaultQueryString, from, to);
    }

    public String getQueryString() {
        return queryString;
    }
}
