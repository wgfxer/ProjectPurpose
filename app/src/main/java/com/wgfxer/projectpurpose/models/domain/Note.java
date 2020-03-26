package com.wgfxer.projectpurpose.models.domain;

import java.util.UUID;

/**
 * Модель заметки
 */
public class Note {

    private UUID id = UUID.randomUUID();
    private String body;
    private String title;

    public Note() {
    }

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != null ? !id.equals(note.id) : note.id != null) return false;
        if (body != null ? !body.equals(note.body) : note.body != null)
            return false;
        return title != null ? title.equals(note.title) : note.title == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
