package com.wgfxer.projectpurpose.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.wgfxer.projectpurpose.data.database.NoteDao;
import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.domain.INotesRepository;
import com.wgfxer.projectpurpose.models.Note;
import com.wgfxer.projectpurpose.presentation.viewmodel.NoteViewModel;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Реализация интерфейса INotesRepository для доступа к таблице с заметками
 * Обращается к базе данных через Dao
 */
public class NotesRepository implements INotesRepository {

    private NoteDao noteDao;
    private Executor executor;

    public NotesRepository(ProjectPurposeDatabase database,
                              Executor executor) {
        noteDao = database.noteDao();
        this.executor = executor;
    }

    /**
     * Получить все заметки
     * @param purposeId id цели для которой получаем все заметки
     * @return LiveData со списком всех заметок
     */
    @Override
    public LiveData<List<Note>> getNotes(int purposeId) {
        return noteDao.getNotes(purposeId);
    }

    /**
     * Получаем заметку по id
     * @param noteId id заметки
     * @return LiveData с заметкой
     */
    @Override
    public LiveData<Note> getNoteById(long noteId) {
        return noteDao.getNoteById(noteId);
    }

    /**
     * Вставить заметку
     * @param note заметка для вставки
     */
    @Override
    public void insertNote(final Note note,@Nullable final NoteViewModel.OnNoteInsertedListener onNoteInsertedListener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long noteId = noteDao.insertNote(note);
                if(onNoteInsertedListener != null){
                    onNoteInsertedListener.onNoteInserted(noteId);
                }
            }
        });
    }

    /**
     * Обновить заметку
     * @param note заметка для обновления
     */
    @Override
    public void updateNote(final Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.updateNote(note);
            }
        });
    }

    /**
     * Удалить заметку
     * @param note заметка для удаления
     */
    @Override
    public void deleteNote(final Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteNote(note);
            }
        });
    }
}
