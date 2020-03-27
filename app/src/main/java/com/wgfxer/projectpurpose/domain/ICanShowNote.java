package com.wgfxer.projectpurpose.domain;


import com.wgfxer.projectpurpose.models.Note;

public interface ICanShowNote {
    void showNote(Note note);
    void showNewNote(long purposeId);
}
