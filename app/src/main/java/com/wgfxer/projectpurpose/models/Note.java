package com.wgfxer.projectpurpose.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


/**
 * Модель заметки
 */
@Entity(tableName = "notes",foreignKeys = @ForeignKey(entity = Purpose.class,parentColumns = "id", childColumns = "purposeId", onDelete = CASCADE))
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String title;
    private String body;
    private long purposeId;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(long purposeId) {
        this.purposeId = purposeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (noteId != note.noteId) return false;
        if (purposeId != note.purposeId) return false;
        if (title != null ? !title.equals(note.title) : note.title != null) return false;
        return body != null ? body.equals(note.body) : note.body == null;
    }

    @Override
    public int hashCode() {
        int result = noteId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (int) (purposeId ^ (purposeId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", purposeId=" + purposeId +
                '}';
    }
}
