package com.wgfxer.projectpurpose.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Utils {
    public static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;


    /**
     * Форматирует Date в строку
     *
     * @param date форматируемая date
     * @return Строку вида 28 дек 2019
     */
    public static String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Из date получает количество дней до этой date
     *
     * @param date до какой даты хотим посчитать дни
     * @return количество дней
     */
    public static int getDaysFromDate(Date date) {
        return (int) ((date.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * возвращает миллисекунды до следующего уведомления, нужно для запуска работы
     *
     * @param notificationHours   час уведомления
     * @param notificationMinutes минута уведомления
     */
    public static long getMillisUntilNext(int notificationHours, int notificationMinutes) {
        long currentTimeInMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, notificationHours);
        calendar.set(Calendar.MINUTE, notificationMinutes);
        if (calendar.getTimeInMillis() - currentTimeInMillis > 0) {
            return calendar.getTimeInMillis() - currentTimeInMillis;
        } else {
            return calendar.getTimeInMillis() - currentTimeInMillis + ONE_DAY_IN_MILLIS;
        }
    }

    /**
     * возвращает время независимое от миллисекунд
     * нужно для поиска отчета по дате в списке отчетов у цели
     *
     * @param time дата для которой нужно получить независимое время
     * @return независимое время
     */
    public static long getIndependentTime(long time) {
        Calendar calendarResult = Calendar.getInstance();
        calendarResult.setTimeInMillis(0);
        Calendar calendarFromView = Calendar.getInstance();
        calendarFromView.setTimeInMillis(time);
        calendarResult.set(Calendar.YEAR, calendarFromView.get(Calendar.YEAR));
        calendarResult.set(Calendar.MONTH, calendarFromView.get(Calendar.MONTH));
        calendarResult.set(Calendar.DAY_OF_MONTH, calendarFromView.get(Calendar.DAY_OF_MONTH));
        return calendarResult.getTimeInMillis();
    }
}
