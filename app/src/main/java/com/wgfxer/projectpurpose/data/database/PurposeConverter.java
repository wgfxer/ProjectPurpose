package com.wgfxer.projectpurpose.data.database;


import java.util.Date;

import androidx.room.TypeConverter;


/**
 * TypeConverter для полей Purpose
 */
public class PurposeConverter {

    /**
     * конвертирует из date в long
     *
     * @param date дата в виде Date
     * @return дата в виде long
     */
    @TypeConverter
    public long fromDate(Date date) {
        return date.getTime();
    }

    /**
     * конвертирует из long timeMillis  в Date
     *
     * @param dateLong дата в виде long
     * @return дата в виде Date
     */
    @TypeConverter
    public Date toDate(long dateLong) {
        return new Date(dateLong);
    }
asdsadasd
}
