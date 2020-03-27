package com.wgfxer.projectpurpose.data.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wgfxer.projectpurpose.models.Note;

import java.util.List;


/**
 * DAO для таблицы с заметками
 */
@Dao
public interface NoteDao {
    /**
     * Получить все заметки для конкретной цели
     * @param purposeId id цели для которой получаем заметки
     * @return LiveData со списком заметок для конкретной цели
     */
    @Query("SELECT * FROM notes WHERE purposeId = :purposeId")
    LiveData<List<Note>> getNotes(int purposeId);

    /**
     * Получить заметку по id
     * @param noteId id заметки
     * @return LiveData с заметкой
     */
    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    LiveData<Note> getNoteById(long noteId);

    /**
     * Вставить заметку в базу данных
     * @param note заметка для вставки
     */
    @Insert
    long insertNote(Note note);

    /**
     * Обновить заметку в базе данных
     * @param note заметка для обновления
     */
    @Update
    void updateNote(Note note);

    /**
     * Удалить заметку из базы данных
     * @param note заметка для удаления
     */
    @Delete
    void deleteNote(Note note);
}
