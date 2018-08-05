package org.tpl.business.model.statsfc;

/**
 * Created by jordyr on 9/4/16.
 */
public enum MonthEnum {

    AUGUST("2018-08-01", "2018-08-31"),
    SEPTEMBER("2018-09-01", "2018-09-31"),
    OCTOBER("2018-10-01", "2018-10-31"),
    NOVEMBER("2018-11-01", "2018-11-31"),
    DECEMBER("2018-12-01", "2018-12-31"),
    JANUARY("2019-01-01", "2019-01-31"),
    FEBRUARY("2019-02-01", "2019-02-31"),
    MARCH("2019-03-01", "2019-03-31"),
    APRIL("2019-04-01", "2019-04-31"),
    MAY("2019-05-01", "2019-05-31");

    private static final String defaultQueryString = "?from=%s&to=%s";

    private String queryString;

    MonthEnum(String from, String to) {
        queryString = String.format(defaultQueryString, from, to);
    }

    public String getQueryString() {
        return queryString;
    }
}
