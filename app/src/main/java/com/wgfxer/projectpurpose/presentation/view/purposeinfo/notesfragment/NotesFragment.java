package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.domain.ICanShowNote;

import com.wgfxer.projectpurpose.models.Note;
import com.wgfxer.projectpurpose.presentation.viewmodel.NoteViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.ViewModelFactory;

public class NotesFragment extends Fragment implements ICanShowNote {
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";
    private NoteViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int purposeId = getArguments().getInt(KEY_PURPOSE_ID);
        viewModel = ViewModelProviders.of(this,new ViewModelFactory(getContext()))
                .get(NoteViewModel.class);
        NotesListFragment notesListFragment = NotesListFragment.newInstance(purposeId);
        notesListFragment.setTargetFragment(this, 1);
        showFragment(notesListFragment, false);
    }

    public void showFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public static NotesFragment newInstance(int purposeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, purposeId);
        NotesFragment fragment = new NotesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showNote(Note note) {
        showFragment(NoteFragment.editInstance(note), true);
    }

    @Override
    public void showNewNote(long purposeId) {
        showFragment(NoteFragment.createInstance(purposeId), true);
    }
}
