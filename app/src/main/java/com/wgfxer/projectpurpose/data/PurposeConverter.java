package com.wgfxer.projectpurpose.data;


import com.wgfxer.projectpurpose.domain.Note;
import com.wgfxer.projectpurpose.domain.PurposeTheme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import androidx.room.TypeConverter;

public class PurposeConverter {
    private static final String KEY_IMAGE_PATH = "image_path";
    private static final String KEY_GRADIENT_ID = "gradient_id";
    private static final String KEY_GRADIENT_ALPHA = "gradient_alpha";
    private static final String KEY_IS_WHITE_FONT = "is_white_font";

    private static final String KEY_TITLE_RES_ID = "title_res_id";
    private static final String KEY_BODY = "body";
    private static final String KEY_HINT_RES_ID = "hint_res_id";

    @TypeConverter
    public String fromTheme(PurposeTheme theme) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (theme.getImagePath() != null) {
                jsonObject.put(KEY_IMAGE_PATH, theme.getImagePath());
            } else {
                jsonObject.put(KEY_IMAGE_PATH, "null");
            }
            jsonObject.put(KEY_GRADIENT_ID, theme.getGradientId());
            jsonObject.put(KEY_GRADIENT_ALPHA, theme.getGradientAlpha());
            jsonObject.put(KEY_IS_WHITE_FONT, theme.isWhiteFont());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @TypeConverter
    public PurposeTheme toTheme(String themeString) {
        PurposeTheme purposeTheme = new PurposeTheme();
        try {
            JSONObject themeJSON = new JSONObject(themeString);
            String imagePath = themeJSON.getString(KEY_IMAGE_PATH);
            if (!imagePath.equals("null")) {
                purposeTheme.setImagePath(themeJSON.getString(KEY_IMAGE_PATH));
            }
            purposeTheme.setGradientId(themeJSON.getInt(KEY_GRADIENT_ID));
            purposeTheme.setGradientAlpha((float) themeJSON.getDouble(KEY_GRADIENT_ALPHA));
            purposeTheme.setWhiteFont(themeJSON.getBoolean(KEY_IS_WHITE_FONT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return purposeTheme;
    }

    @TypeConverter
    public long fromDate(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(long dateLong) {
        return new Date(dateLong);
    }

    @TypeConverter
    public String fromArrayListNotes(ArrayList<Note> notesList) {
        JSONArray jsonArrayListNotes = new JSONArray();
        for (Note note : notesList) {
            JSONObject jsonObjectNote = new JSONObject();
            try {
                jsonObjectNote.put(KEY_TITLE_RES_ID, note.getTitleResourceId());
                if (note.getBody() == null || note.getBody().isEmpty()) {
                    jsonObjectNote.put(KEY_BODY, "null");
                } else {
                    jsonObjectNote.put(KEY_BODY, note.getBody());
                }
                jsonObjectNote.put(KEY_HINT_RES_ID, note.getHintResourceId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayListNotes.put(jsonObjectNote);
        }
        return jsonArrayListNotes.toString();
    }

    @TypeConverter
    public ArrayList<Note> toArrayListNotes(String source) {
        ArrayList<Note> notesList = new ArrayList<>();
        try {
            JSONArray jsonArrayListNotes = new JSONArray(source);
            for (int i = 0; i < jsonArrayListNotes.length(); i++) {
                JSONObject jsonObjectNote = (JSONObject) jsonArrayListNotes.get(i);
                Note note = new Note();
                note.setTitleResourceId((Integer) jsonObjectNote.get(KEY_TITLE_RES_ID));
                String body = (String) jsonObjectNote.get(KEY_BODY);
                if (!body.equals("null")) {
                    note.setBody(body);
                }
                note.setHintResourceId((Integer) jsonObjectNote.get(KEY_HINT_RES_ID));
                notesList.add(note);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notesList;
    }
}
