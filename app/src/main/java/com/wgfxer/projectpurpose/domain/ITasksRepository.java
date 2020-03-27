package com.wgfxer.projectpurpose.domain;

import androidx.lifecycle.LiveData;

import com.wgfxer.projectpurpose.models.Task;

import java.util.List;

/**
 * интерфейс репозитория для доступа к целям
 */
public interface ITasksRepository {
    LiveData<List<Task>> getFutureTasks(int purposeId);

    LiveData<List<Task>> getDoneTasks(int purposeId);

    LiveData<Task> getTaskById(int taskId);

    void insertTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);
}
