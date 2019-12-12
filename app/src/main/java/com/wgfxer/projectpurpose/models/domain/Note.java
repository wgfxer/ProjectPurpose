package com.wgfxer.projectpurpose.models.domain;

import androidx.annotation.NonNull;

public class Note {
    private int titleResourceId;
    private String body;
    private int hintResourceId;

    public Note() {
    }

    public Note(int titleResourceId, int hintResourceId) {
        this.titleResourceId = titleResourceId;
        this.hintResourceId = hintResourceId;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public void setTitleResourceId(int titleResourceId) {
        this.titleResourceId = titleResourceId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getHintResourceId() {
        return hintResourceId;
    }

    public void setHintResourceId(int hintResourceId) {
        this.hintResourceId = hintResourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (titleResourceId != note.titleResourceId) return false;
        if (hintResourceId != note.hintResourceId) return false;
        return body != null ? body.equals(note.body) : note.body == null;
    }

    @Override
    public int hashCode() {
        int result = titleResourceId;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + hintResourceId;
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "titleResourceId=" + titleResourceId +
                ", body='" + body + '\'' +
                ", hintResourceId=" + hintResourceId +
                '}';
    }
}
