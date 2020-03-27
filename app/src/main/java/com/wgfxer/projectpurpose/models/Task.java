package com.wgfxer.projectpurpose.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Модель задачи
 */
@Entity(tableName = "tasks",foreignKeys = @ForeignKey(entity = Purpose.class,parentColumns = "id", childColumns = "purposeId", onDelete = CASCADE))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int taskId;
    private boolean isDone;
    private String title;
    private int purposeId;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(int purposeId) {
        this.purposeId = purposeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (taskId != task.taskId) return false;
        if (isDone != task.isDone) return false;
        if (purposeId != task.purposeId) return false;
        return title != null ? title.equals(task.title) : task.title == null;
    }

    @Override
    public int hashCode() {
        int result = taskId;
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + purposeId;
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", isDone=" + isDone +
                ", title='" + title + '\'' +
                ", purposeId=" + purposeId +
                '}';
    }
}

