package com.wgfxer.projectpurpose.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils {
    public static String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static int getDaysFromDate(Date date) {
        return (int) ((date.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
    }
}
