package org.tpl.business.model.statsfc;

/**
 * Created by jordyr on 9/4/16.
 */
public enum MonthEnum {

    AUGUST("2016-08-01", "2016-08-31"),
    SEPTEMBER("2016-09-01", "2016-09-31"),
    OCTOBER("2016-10-01", "2016-10-31"),
    NOVEMBER("2016-11-01", "2016-11-31"),
    DECEMBER("2016-12-01", "2016-12-31"),
    JANUARY("2017-01-01", "2017-01-31"),
    FEBRUARY("2017-02-01", "2017-02-31"),
    MARCH("2017-03-01", "2017-03-31"),
    APRIL("2017-04-01", "2017-04-31"),
    MAY("2017-05-01", "2017-05-31");

    private static final String defaultQueryString = "?from=%s&to=%s";

    private String queryString;

    MonthEnum(String from, String to) {
        queryString = String.format(defaultQueryString, from, to);
    }

    public String getQueryString() {
        return queryString;
    }
}
