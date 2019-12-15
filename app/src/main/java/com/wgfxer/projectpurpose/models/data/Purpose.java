package com.wgfxer.projectpurpose.models.data;


import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.data.database.PurposeConverter;
import com.wgfxer.projectpurpose.models.domain.Note;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;
import com.wgfxer.projectpurpose.models.domain.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


/**
 * модель цели
 */
@Entity(tableName = "purposes")
@TypeConverters({PurposeConverter.class})
public class Purpose {

    @PrimaryKey(autoGenerate = true)
    private int id;
    /**
     * название цели
     */
    private String title;

    /**
     * дата окончания цели
     */
    private Date date;

    /**
     * лист с заметками по цели(план действий,мотивация,заметки)
     */
    private List<Note> notesList;

    /**
     * Тема цели
     */
    private PurposeTheme theme;

    /**
     * список отчетов по цели
     */
    private List<Report> reportsList;

    /**
     * Завершена ли цель
     */
    private boolean isDone;

    public Purpose() {
        this.theme = new PurposeTheme();
        this.notesList = new ArrayList<>();
        Note actionsPlan = new Note(R.string.actions_plan_title, R.string.actions_plan_hint);
        Note motivation = new Note(R.string.motivation_title, R.string.motivation_hint);
        Note notes = new Note(R.string.notes_title, R.string.notes_hint);
        notesList.add(actionsPlan);
        notesList.add(motivation);
        notesList.add(notes);
        reportsList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public PurposeTheme getTheme() {
        return theme;
    }

    public void setTheme(PurposeTheme theme) {
        this.theme = theme;
    }

    public List<Report> getReportsList() {
        return reportsList;
    }

    public void setReportsList(List<Report> reportsList) {
        this.reportsList = reportsList;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purpose purpose = (Purpose) o;

        if (id != purpose.id) return false;
        if (isDone != purpose.isDone) return false;
        if (title != null ? !title.equals(purpose.title) : purpose.title != null) return false;
        if (date != null ? !date.equals(purpose.date) : purpose.date != null) return false;
        if (notesList != null ? !notesList.equals(purpose.notesList) : purpose.notesList != null)
            return false;
        if (theme != null ? !theme.equals(purpose.theme) : purpose.theme != null) return false;
        return reportsList != null ? reportsList.equals(purpose.reportsList) : purpose.reportsList == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (notesList != null ? notesList.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (reportsList != null ? reportsList.hashCode() : 0);
        result = 31 * result + (isDone ? 1 : 0);
        return result;
    }


    @NonNull
    @Override
    public String toString() {
        return "Purpose{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", notesList=" + notesList +
                ", theme=" + theme +
                ", reportsList=" + reportsList +
                ", isDone=" + isDone +
                '}';
    }
}
