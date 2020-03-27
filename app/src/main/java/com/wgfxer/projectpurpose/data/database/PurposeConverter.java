package com.wgfxer.projectpurpose.data.database;


import com.wgfxer.projectpurpose.models.PurposeTheme;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import androidx.room.TypeConverter;


/**
 * TypeConverter для полей Purpose
 */
public class PurposeConverter {
    private static final String KEY_GRADIENT_ALPHA = "gradient_alpha";
    private static final String KEY_GRADIENT_NUMBER = "gradient_number";
    private static final String KEY_IMAGE_PATH = "image_path";
    private static final String KEY_IS_WHITE_FONT = "is_white_font";

    /**
     * Конвертирует из типа PurposeTheme в String
     *
     * @param theme конвертируемая тема
     * @return String представление темы(в формате JSON)
     */
    /*@TypeConverter
    public String fromTheme(PurposeTheme theme) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (theme.getImagePath() != null) {
                jsonObject.put(KEY_IMAGE_PATH, theme.getImagePath());
            } else {
                jsonObject.put(KEY_IMAGE_PATH, "null");
            }
            jsonObject.put(KEY_GRADIENT_NUMBER, theme.getGradientPosition());
            jsonObject.put(KEY_GRADIENT_ALPHA, theme.getGradientAlpha());
            jsonObject.put(KEY_IS_WHITE_FONT, theme.isWhiteFont());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
*/

    /**
     * Конвертирует из String(JSON) в PurposeTheme
     *
     * @param themeString строка в виде JSON, содержит в себе PurposeTheme
     * @return конвертированную PurposeTheme
     */
    /*@TypeConverter
    public PurposeTheme toTheme(String themeString) {
        PurposeTheme purposeTheme = new PurposeTheme();
        try {
            JSONObject themeJSON = new JSONObject(themeString);
            String imagePath = themeJSON.getString(KEY_IMAGE_PATH);
            if (!imagePath.equals("null")) {
                purposeTheme.setImagePath(themeJSON.getString(KEY_IMAGE_PATH));
            }
            purposeTheme.setGradientPosition(themeJSON.getInt(KEY_GRADIENT_NUMBER));
            purposeTheme.setGradientAlpha((float) themeJSON.getDouble(KEY_GRADIENT_ALPHA));
            purposeTheme.setWhiteFont(themeJSON.getBoolean(KEY_IS_WHITE_FONT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return purposeTheme;
    }
*/
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

}
