package com.wgfxer.projectpurpose.models.data;


import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.data.database.PurposeConverter;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.models.domain.Note;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;
import com.wgfxer.projectpurpose.models.domain.Report;
import com.wgfxer.projectpurpose.models.domain.Task;

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
    private String title;
    private Date date;
    private List<Task> tasksList = new ArrayList<>();
    private List<Note> notesList = new ArrayList<>();
    private List<Report> reportsList = new ArrayList<>();
    private PurposeTheme theme = new PurposeTheme();
    private boolean isDone;

    public Purpose() {
        Note actionsPlan = new Note("План действий", "Напишите примерный план действий, например каждый день заниматься в зале или посетить какие-нибудь курсы");
        Note motivation = new Note("Мотивация", "Напишите, почему вам очень важно достичь этой цели, в тяжелые моменты это может помочь вам не опустить руки");
        this.notesList.add(actionsPlan);
        this.notesList.add(motivation);
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

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public List<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public List<Report> getReportsList() {
        return reportsList;
    }

    public void setReportsList(List<Report> reportsList) {
        this.reportsList = reportsList;
    }

    public PurposeTheme getTheme() {
        return theme;
    }

    public void setTheme(PurposeTheme theme) {
        this.theme = theme;
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
        if (tasksList != null ? !tasksList.equals(purpose.tasksList) : purpose.tasksList != null)
            return false;
        if (notesList != null ? !notesList.equals(purpose.notesList) : purpose.notesList != null)
            return false;
        if (reportsList != null ? !reportsList.equals(purpose.reportsList) : purpose.reportsList != null)
            return false;
        return theme != null ? theme.equals(purpose.theme) : purpose.theme == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (tasksList != null ? tasksList.hashCode() : 0);
        result = 31 * result + (notesList != null ? notesList.hashCode() : 0);
        result = 31 * result + (reportsList != null ? reportsList.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (isDone ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Purpose{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", tasksList=" + tasksList +
                ", notesList=" + notesList +
                ", reportsList=" + reportsList +
                ", theme=" + theme +
                ", isDone=" + isDone +
                '}';
    }
}
