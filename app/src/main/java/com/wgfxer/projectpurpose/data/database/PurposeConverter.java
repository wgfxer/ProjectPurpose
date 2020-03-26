package com.wgfxer.projectpurpose.data.database;


import com.wgfxer.projectpurpose.models.domain.Note;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;
import com.wgfxer.projectpurpose.models.domain.Report;
import com.wgfxer.projectpurpose.models.domain.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.room.TypeConverter;


/**
 * TypeConverter для полей Purpose
 */
public class PurposeConverter {
    private static final String KEY_GRADIENT_ALPHA = "gradient_alpha";
    private static final String KEY_GRADIENT_ID = "gradient_id";
    private static final String KEY_IMAGE_PATH = "image_path";
    private static final String KEY_IS_WHITE_FONT = "is_white_font";
    private static final String KEY_NOTE_BODY = "note_body";
    private static final String KEY_NOTE_ID = "note_id";
    private static final String KEY_NOTE_TITLE = "note_title";
    private static final String KEY_REPORT_COULD_BETTER = "key_report_could_better";
    private static final String KEY_REPORT_DATE = "key_report_date";
    private static final String KEY_REPORT_DESCRIPTION = "key_report_description";
    private static final String KEY_REPORT_DID_GOOD = "key_report_did_good";
    private static final String KEY_REPORT_TITLE = "key_report_title";
    private static final String KEY_TASK_ID = "key_task_id";
    private static final String KEY_TASK_IS_DONE = "key_task_is_done";
    private static final String KEY_TASK_TITLE = "key_task_title";


    /**
     * Конвертирует из типа PurposeTheme в String
     *
     * @param theme конвертируемая тема
     * @return String представление темы(в формате JSON)
     */
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


    /**
     * Конвертирует из String(JSON) в PurposeTheme
     *
     * @param themeString строка в виде JSON, содержит в себе PurposeTheme
     * @return конвертированную PurposeTheme
     */
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

    /**
     * конвертирует лист объектов типа Note в String JSON
     *
     * @param notesList конвертируемый лист
     * @return конвертированный в String JSON лист
     */
    @TypeConverter
    public String fromListNotes(List<Note> notesList) {
        JSONArray jsonArrayListNotes = new JSONArray();
        for (Note note : notesList) {
            JSONObject jsonObjectNote = new JSONObject();
            try {
                jsonObjectNote.put(KEY_NOTE_ID,note.getId());
                if (note.getTitle() == null || note.getTitle().isEmpty()) {
                    jsonObjectNote.put(KEY_NOTE_TITLE, "null");
                } else {
                    jsonObjectNote.put(KEY_NOTE_TITLE, note.getTitle());
                }
                if (note.getBody() == null || note.getBody().isEmpty()) {
                    jsonObjectNote.put(KEY_NOTE_BODY, "null");
                } else {
                    jsonObjectNote.put(KEY_NOTE_BODY, note.getBody());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayListNotes.put(jsonObjectNote);
        }
        return jsonArrayListNotes.toString();
    }

    /**
     * Конвертирует из String JSON в List<Note
     *
     * @param source String представление List<Note> в формате JSON
     * @return конвертированный список с заметками
     */
    @TypeConverter
    public List<Note> toListNotes(String source) {
        List<Note> notesList = new ArrayList<>();
        try {
            JSONArray jsonArrayListNotes = new JSONArray(source);
            String str = "null";
            for (int i = 0; i < jsonArrayListNotes.length(); i++) {
                JSONObject jsonObjectNote = (JSONObject) jsonArrayListNotes.get(i);
                Note note = new Note();
                note.setId(UUID.fromString((String) jsonObjectNote.get(KEY_NOTE_ID)));
                String title = (String) jsonObjectNote.get(KEY_NOTE_TITLE);
                if (!title.equals(str)) {
                    note.setTitle(title);
                }
                String body = (String) jsonObjectNote.get(KEY_NOTE_BODY);
                if (!body.equals(str)) {
                    note.setBody(body);
                }
                notesList.add(note);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notesList;
    }

    /**
     * Конвертирует List отчетов в строку в формате JSON
     *
     * @param reports конвертируемый лист с отчетами
     * @return конвертированный в строку в формате JSON лист
     */
    @TypeConverter
    public String fromListReports(List<Report> reports) {
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

    /**
     * Конвертирует из строки формата JSON в List с отчетами
     *
     * @param source строка в формате JSON которая содержит лист с отчетами
     * @return конвертированный лист с отчетами
     */
    @TypeConverter
    public List<Report> toListReports(String source) {
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

    @TypeConverter
    public String fromListTasks(List<Task> tasksList) {
        JSONArray jsonArrayListTasks = new JSONArray();
        for (Task task : tasksList) {
            JSONObject jsonObjectTask = new JSONObject();
            try {
                jsonObjectTask.put(KEY_TASK_ID, task.getId().toString());
                jsonObjectTask.put(KEY_TASK_IS_DONE, task.isDone());
                if (task.getTitle() != null && !task.getTitle().isEmpty()) {
                    jsonObjectTask.put(KEY_TASK_TITLE, task.getTitle());
                }else{
                    jsonObjectTask.put(KEY_TASK_TITLE, "null");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArrayListTasks.put(jsonObjectTask);
        }
        return jsonArrayListTasks.toString();
    }

    @TypeConverter
    public List<Task> toListTasks(String source) {
        List<Task> tasksList = new ArrayList<>();
        try {
            JSONArray jsonArrayListTasks = new JSONArray(source);
            for (int i = 0; i < jsonArrayListTasks.length(); i++) {
                JSONObject jsonObjectTask = (JSONObject) jsonArrayListTasks.get(i);
                Task task = new Task();
                task.setId(UUID.fromString((String) jsonObjectTask.get(KEY_TASK_ID)));
                task.setDone(jsonObjectTask.getBoolean(KEY_TASK_IS_DONE));
                String title = (String) jsonObjectTask.get(KEY_TASK_TITLE);
                if (!title.equals("null")) {
                    task.setTitle(title);
                }
                tasksList.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tasksList;
    }


}
