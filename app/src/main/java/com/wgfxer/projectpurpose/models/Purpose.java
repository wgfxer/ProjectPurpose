package com.wgfxer.projectpurpose.models;


import com.wgfxer.projectpurpose.data.database.PurposeConverter;

import java.util.Date;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


/**
 * Модель цели
 */
@Entity(tableName = "purposes")
@TypeConverters({PurposeConverter.class})
public class Purpose {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date date;
    private boolean isDone;
    @Embedded
    private PurposeTheme theme = new PurposeTheme();

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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public PurposeTheme getTheme() {
        return theme;
    }

    public void setTheme(PurposeTheme theme) {
        this.theme = theme;
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
        return theme != null ? theme.equals(purpose.theme) : purpose.theme == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Purpose{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", isDone=" + isDone +
                ", theme=" + theme +
                '}';
    }
}
