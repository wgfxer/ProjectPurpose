package com.wgfxer.projectpurpose.presentation.viewmodel;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.wgfxer.projectpurpose.domain.INotesRepository;
import com.wgfxer.projectpurpose.models.Note;

import java.util.List;

/**
 * ВьюМодель для заметок
 */
public class NoteViewModel extends ViewModel {

    private INotesRepository repository;

    public NoteViewModel(INotesRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Note>> getNotes(int purposeId){
        return repository.getNotes(purposeId);
    }

    public LiveData<Note> getNoteById(long noteId){
        return repository.getNoteById(noteId);
    }

    public void insertNote(Note note, OnNoteInsertedListener onNoteInsertedListener ){
        repository.insertNote(note, onNoteInsertedListener);
    }

    public void updateNote(Note note){
        repository.updateNote(note);
    }

    public void deleteNote(Note note){
        repository.deleteNote(note);
    }

    public interface OnNoteInsertedListener{
        void onNoteInserted(long noteId);
    }

}
