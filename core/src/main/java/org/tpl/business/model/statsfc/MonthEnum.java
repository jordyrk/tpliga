package org.tpl.business.model.statsfc;

import java.util.Date;

/**
 * Created by jordyr on 9/4/16.
 */
public enum MonthEnum {


    AUGUST("2020-08-01", "2020-08-31"),
    SEPTEMBER("2020-09-01", "2020-09-31"),
    OCTOBER("2020-10-01", "2020-10-31"),
    NOVEMBER("2020-11-01", "2020-11-31"),
    DECEMBER("2020-12-01", "2020-12-31"),
    JANUARY("2021-01-01", "2021-01-31"),
    FEBRUARY("2021-02-01", "2021-02-31"),
    MARCH("2021-03-01", "2021-03-31"),
    APRIL("2021-04-01", "2021-04-31"),
    MAY("2021-05-01", "2021-05-31"),
    JUNE("2020-06-01", "2020-06-31"),
    JULY("2020-07-01", "2020-07-31");

    private static final String defaultQueryString = "?from=%s&to=%s";

    private String queryString;

    MonthEnum(String from, String to) {
        queryString = String.format(defaultQueryString, from, to);
    }

    public String getQueryString() {
        return queryString;
    }
}
