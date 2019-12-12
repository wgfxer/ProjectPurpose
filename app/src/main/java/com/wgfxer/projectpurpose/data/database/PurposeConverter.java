package com.wgfxer.projectpurpose.data.database;


import com.wgfxer.projectpurpose.models.domain.Note;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;
import com.wgfxer.projectpurpose.models.domain.Report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.TypeConverter;

public class PurposeConverter {
    private static final String KEY_IMAGE_PATH = "image_path";
    private static final String KEY_GRADIENT_ID = "gradient_id";
    private static final String KEY_GRADIENT_ALPHA = "gradient_alpha";
    private static final String KEY_IS_WHITE_FONT = "is_white_font";

    private static final String KEY_TITLE_RES_ID = "title_res_id";
    private static final String KEY_BODY = "body";
    private static final String KEY_HINT_RES_ID = "hint_res_id";
    private static final String KEY_REPORT_DATE = "key_report_date";
    private static final String KEY_REPORT_TITLE = "key_report_title";
    private static final String KEY_REPORT_DESCRIPTION = "key_report_description";
    private static final String KEY_REPORT_DID_GOOD = "key_report_did_good";
    private static final String KEY_REPORT_COULD_BETTER = "key_report_could_better";

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
    public String fromListNotes(List<Note> notesList) {
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
    public List<Note> toListNotes(String source) {
        List<Note> notesList = new ArrayList<>();
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

    @TypeConverter
    public String fromArrayListReports(List<Report> reports) {
        JSONArray jsonArrayListReports = new JSONArray();
        for (Report report : reports) {
            JSONObject jsonObjectReport = new JSONObject();
            try {
                jsonObjectReport.put(KEY_REPORT_DATE, report.getDateReport());
                if (report.getTitleReport() == null || report.getTitleReport().isEmpty()) {
                    jsonObjectReport.put(KEY_REPORT_TITLE, "null");
                } else {
                    jsonObjectReport.put(KEY_REPORT_TITLE, report.getTitleReport());
                }

                if (report.getDescriptionReport() == null || report.getDescriptionReport().isEmpty()) {
                    jsonObjectReport.put(KEY_REPORT_DESCRIPTION, "null");
                } else {
                    jsonObjectReport.put(KEY_REPORT_DESCRIPTION, report.getDescriptionReport());
                }

                if (report.getWhatDidGood() == null || report.getWhatDidGood().isEmpty()) {
                    jsonObjectReport.put(KEY_REPORT_DID_GOOD, "null");
                } else {
                    jsonObjectReport.put(KEY_REPORT_DID_GOOD, report.getWhatDidGood());
                }

                if (report.getWhatCouldBetter() == null || report.getWhatCouldBetter().isEmpty()) {
                    jsonObjectReport.put(KEY_REPORT_COULD_BETTER, "null");
                } else {
                    jsonObjectReport.put(KEY_REPORT_COULD_BETTER, report.getWhatCouldBetter());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayListReports.put(jsonObjectReport);
        }
        return jsonArrayListReports.toString();
    }

    @TypeConverter
    public List<Report> toArrayListReports(String source) {
        List<Report> reportsList = new ArrayList<>();
        try {
            JSONArray jsonArrayListReports = new JSONArray(source);
            for (int i = 0; i < jsonArrayListReports.length(); i++) {
                JSONObject jsonObjectReport = (JSONObject) jsonArrayListReports.get(i);
                Report report = new Report();
                report.setDateReport(jsonObjectReport.getLong(KEY_REPORT_DATE));
                String title = (String) jsonObjectReport.get(KEY_REPORT_TITLE);
                String description = (String) jsonObjectReport.get(KEY_REPORT_DESCRIPTION);
                String didGood = (String) jsonObjectReport.get(KEY_REPORT_DID_GOOD);
                String couldBetter = (String) jsonObjectReport.get(KEY_REPORT_COULD_BETTER);
                if (!title.equals("null")) {
                    report.setTitleReport(title);
                }
                if (!description.equals("null")) {
                    report.setDescriptionReport(description);
                }
                if (!didGood.equals("null")) {
                    report.setWhatDidGood(didGood);
                }
                if (!couldBetter.equals("null")) {
                    report.setWhatCouldBetter(couldBetter);
                }
                reportsList.add(report);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reportsList;
    }
}