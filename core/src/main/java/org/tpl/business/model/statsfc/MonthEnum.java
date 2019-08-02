package org.tpl.business.model.statsfc;

/**
 * Created by jordyr on 9/4/16.
 */
public enum MonthEnum {

    AUGUST("2019-08-01", "2019-08-31"),
    SEPTEMBER("2019-09-01", "2019-09-31"),
    OCTOBER("2019-10-01", "2019-10-31"),
    NOVEMBER("2019-11-01", "2019-11-31"),
    DECEMBER("2019-12-01", "2019-12-31"),
    JANUARY("2020-01-01", "2020-01-31"),
    FEBRUARY("2020-02-01", "2020-02-31"),
    MARCH("2020-03-01", "2020-03-31"),
    APRIL("2020-04-01", "2020-04-31"),
    MAY("2020-05-01", "2020-05-31");

    private static final String defaultQueryString = "?from=%s&to=%s";

    private String queryString;

    MonthEnum(String from, String to) {
        queryString = String.format(defaultQueryString, from, to);
    }

    public String getQueryString() {
        return queryString;
    }
}
