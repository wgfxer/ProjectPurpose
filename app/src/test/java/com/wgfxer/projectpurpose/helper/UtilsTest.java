package com.wgfxer.projectpurpose.helper;


import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void getStringFromDateTest() {
        int year = 1984;
        int month = 3;
        int dayOfMonth = 25;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date date = new Date(calendar.getTimeInMillis());
        String dateText = Utils.getStringFromDate(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Calendar calendarResult = Calendar.getInstance();
        try {
            calendarResult.setTimeInMillis(dateFormat.parse(dateText).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(year, calendarResult.get(Calendar.YEAR));
        assertEquals(month, calendarResult.get(Calendar.MONTH));
        assertEquals(dayOfMonth, calendarResult.get(Calendar.DAY_OF_MONTH));
    }


    @Test
    public void getDaysFromDateTest() {
        Date date = new Date();
        assertEquals(0, Utils.getDaysFromDate(date));

        int days = 3;
        Date secondDate = new Date(System.currentTimeMillis() + days * 24 * 60 * 60 * 1000);
        assertEquals(days, Utils.getDaysFromDate(secondDate));
    }

    @Test
    public void getMillisUntilNextTest() {
        int hour = 21;
        int minute = 30;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        long millis = Utils.getMillisUntilNext(hour, minute);
        assertTrue(millis >= 0);
        assertTrue(millis <= Utils.ONE_DAY_IN_MILLIS);
        calendar.setTimeInMillis(System.currentTimeMillis() + millis);
        assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(minute, calendar.get(Calendar.MINUTE));
    }

    @Test
    public void getIndependentTimeTest() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        long time = Utils.getIndependentTime(calendar.getTimeInMillis());
        Calendar calendarResult = Calendar.getInstance(Locale.getDefault());
        calendarResult.setTimeInMillis(time);
        assertEquals(calendar.get(Calendar.YEAR), calendarResult.get(Calendar.YEAR));
        assertEquals(calendar.get(Calendar.MONTH), calendarResult.get(Calendar.MONTH));
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), calendarResult.get(Calendar.DAY_OF_MONTH));
    }
}