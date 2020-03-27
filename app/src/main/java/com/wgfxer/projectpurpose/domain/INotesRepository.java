package com.wgfxer.projectpurpose.domain;

import androidx.lifecycle.LiveData;

import com.wgfxer.projectpurpose.models.Note;
import com.wgfxer.projectpurpose.presentation.viewmodel.NoteViewModel;

import java.util.List;

public interface INotesRepository {
    LiveData<List<Note>> getNotes(int purposeId);

    LiveData<Note> getNoteById(long noteId);

    void insertNote(Note note, NoteViewModel.OnNoteInsertedListener onNoteInsertedListener);

    void updateNote(Note note);

    void deleteNote(Note note);
}
