package com.wgfxer.projectpurpose.presentation.view.purpose_info;

import com.wgfxer.projectpurpose.domain.Note;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


class NotesAdapter extends FragmentPagerAdapter {

    private ArrayList<Note> notesList;

    NotesAdapter(FragmentManager fm) {
        super(fm);
        this.notesList = new ArrayList<>();
    }

    public void setNotesList(ArrayList<Note> notesList) {
        this.notesList = notesList;
        notifyDataSetChanged();
    }

    ArrayList<Note> getNotesList() {
        return notesList;
    }

    @Override
    public Fragment getItem(final int position) {
        return NoteFragment.newInstance(notesList.get(position), new NoteFragment.OnNoteChangedListener() {
            @Override
            public void onNoteChanged(String body) {
                notesList.get(position).setBody(body);
            }
        });
    }

    @Override
    public int getCount() {
        return notesList.size();
    }
}
