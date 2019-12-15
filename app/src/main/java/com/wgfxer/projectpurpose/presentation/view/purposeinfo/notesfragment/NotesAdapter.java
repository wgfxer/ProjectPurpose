package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import com.wgfxer.projectpurpose.models.domain.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * Адаптер для ViewPager с заметками
 */
class NotesAdapter extends FragmentPagerAdapter {

    private List<Note> notesList;

    NotesAdapter(FragmentManager fm) {
        super(fm);
        this.notesList = new ArrayList<>();
    }


    void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
        notifyDataSetChanged();
    }

    List<Note> getNotesList() {
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
