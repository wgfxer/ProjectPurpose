package com.wgfxer.projectpurpose.presentation.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.wgfxer.projectpurpose.domain.ITasksRepository;
import com.wgfxer.projectpurpose.models.Task;

import java.util.List;

/**
 * ВьюМодель для задач
 */
public class TaskViewModel extends ViewModel {

    private ITasksRepository repository;

    public TaskViewModel(ITasksRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Task>> getFutureTasks(int purposeId){
        return repository.getFutureTasks(purposeId);
    }

    public LiveData<List<Task>> getDoneTasks(int purposeId){
        return repository.getDoneTasks(purposeId);
    }

    public LiveData<Task> getTaskById(int taskId){
        return repository.getTaskById(taskId);
    }

    public void insertTask(Task task){
        repository.insertTask(task);
    }

    public void updateTask(Task task){
        repository.updateTask(task);
    }

    public void deleteTask(Task task){
        repository.deleteTask(task);
    }
}
