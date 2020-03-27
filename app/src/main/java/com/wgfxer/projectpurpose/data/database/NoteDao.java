package com.wgfxer.projectpurpose.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wgfxer.projectpurpose.models.domain.Task;

import java.util.List;

/**
 * DAO для таблицы с задачами
 */
@Dao
public interface TaskDao {
    /**
     * Запрос невыполненных задач для конкретной цели
     *
     * @return liveData с невыполненными задачами
     */
    @Query("SELECT * FROM tasks WHERE isDone = 0 AND purposeId = :purposeId")
    LiveData<List<Task>> getFutureTasks(int purposeId);

    /**
     * Запрос выполненных задач для конкретной цели
     *
     * @return liveData с выполненными задачами
     */
    @Query("SELECT * FROM tasks WHERE isDone = 1 AND purposeId = :purposeId")
    LiveData<List<Task>> getDoneTasks(int purposeId);

    /**
     * Получает задачу по id
     *
     * @param taskId id задачи
     * @return liveData с задачей
     */
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    LiveData<Task> getTaskById(int taskId);

    /**
     * Вставляет новую задачу
     *
     * @param task задача для вставки
     */
    @Insert
    void insertTask(Task task);

    /**
     * Обновляет задачу
     *
     * @param task задача для обновления
     */
    @Update
    void updateTask(Task task);

    /**
     * Удаляет задачу
     *
     * @param task задача для удаления
     */
    @Delete
    void deleteTask(Task task);
}
