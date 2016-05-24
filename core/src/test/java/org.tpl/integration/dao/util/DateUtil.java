package org.tpl.integration.dao.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/*
Created by jordyr, 17.okt.2010

*/

public class DateUtil {

     public int getDay(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }

    public int getMonth(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.MONTH);
    }
    public int getYear(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.YEAR);
    }
}
