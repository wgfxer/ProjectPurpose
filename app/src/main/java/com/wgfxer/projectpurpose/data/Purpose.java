package com.wgfxer.projectpurpose.data;


import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.domain.Note;
import com.wgfxer.projectpurpose.domain.PurposeTheme;
import com.wgfxer.projectpurpose.domain.Report;

import java.util.ArrayList;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


@Entity(tableName = "purposes")
@TypeConverters({PurposeConverter.class})
public class Purpose {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date date;
    private ArrayList<Note> notesList;
    private PurposeTheme theme;
    private ArrayList<Report> reportsList;
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

    public ArrayList<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(ArrayList<Note> notesList) {
        this.notesList = notesList;
    }

    public PurposeTheme getTheme() {
        return theme;
    }

    public void setTheme(PurposeTheme theme) {
        this.theme = theme;
    }

    public ArrayList<Report> getReportsList() {
        return reportsList;
    }

    public void setReportsList(ArrayList<Report> reportsList) {
        this.reportsList = reportsList;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
