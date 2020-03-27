package com.wgfxer.projectpurpose.data.repository;


import androidx.lifecycle.LiveData;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.database.TaskDao;
import com.wgfxer.projectpurpose.domain.ITasksRepository;
import com.wgfxer.projectpurpose.models.Task;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Реализация интерфейса ITasksRepository для доступа к таблице с задачами
 * Обращается к базе данных через Dao
 */
public class TasksRepository implements ITasksRepository {

    private TaskDao taskDao;
    private Executor executor;

    public TasksRepository(ProjectPurposeDatabase database,
                           Executor executor) {
        taskDao = database.taskDao();
        this.executor = executor;
    }

    /**
     * Получить невыполненные задачи
     * @param purposeId id цели для которой получаем невыполненные задачи
     * @return LiveData со списком невыполненных задач
     */
    @Override
    public LiveData<List<Task>> getFutureTasks(int purposeId) {
        return taskDao.getFutureTasks(purposeId);
    }

    /**
     * Получить выполненные задачи
     * @param purposeId id цели для которой получаем выполненные задачи
     * @return LiveData со списком выполненных задач
     */
    @Override
    public LiveData<List<Task>> getDoneTasks(int purposeId) {
        return taskDao.getDoneTasks(purposeId);
    }

    /**
     * Получить задачу по id
     * @param taskId id задачи
     * @return LiveData с задачей
     */
    @Override
    public LiveData<Task> getTaskById(int taskId) {
        return taskDao.getTaskById(taskId);
    }

    /**
     * Вставить задачу
     * @param task задача для вставки
     */
    @Override
    public void insertTask(final Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(task);
            }
        });
    }

    /**
     * Обновить задачу
     * @param task задача для обновления
     */
    @Override
    public void updateTask(final Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTask(task);
            }
        });
    }

    /**
     * Удалить задачу
     * @param task задача для удаления
     */
    @Override
    public void deleteTask(final Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }
}
