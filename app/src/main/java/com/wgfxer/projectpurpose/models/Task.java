package com.wgfxer.projectpurpose.models.domain;

import java.util.UUID;

public class Task {

    private UUID id = UUID.randomUUID();
    private boolean isDone;
    private String title;

    public Task(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    public Task() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (isDone != task.isDone) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        return title != null ? title.equals(task.title) : task.title == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", isDone=" + isDone +
                ", title='" + title + '\'' +
                '}';
    }
}
