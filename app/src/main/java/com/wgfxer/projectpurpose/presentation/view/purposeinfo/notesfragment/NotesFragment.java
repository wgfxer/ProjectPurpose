package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelProviders;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.domain.ICanShowNote;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.models.domain.Note;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NoteFragment.OnNoteChangedListener;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

public class NotesFragment extends Fragment implements ICanShowNote {
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";
    private Purpose purpose;
    private MainViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this,new MainViewModelFactory(getContext()))
                .get(MainViewModel.class);
        viewModel.getPurposeById(getArguments().getInt(KEY_PURPOSE_ID)).observe(this, new Observer<Purpose>() {
            public void onChanged(Purpose purpose) {
                if (purpose != null) {
                    NotesFragment.this.purpose = purpose;
                }
            }
        });
        NotesListFragment notesListFragment = NotesListFragment.newInstance(getArguments().getInt(KEY_PURPOSE_ID));
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

    public void showNote(Note note) {
        showFragment(NoteFragment.newInstance(note, new OnNoteChangedListener() {
            public void onNoteChanged(Note note) {
                for (Note n : purpose.getNotesList()) {
                    if (n.getId().equals(note.getId())) {
                        n.setTitle(note.getTitle());
                        n.setBody(note.getBody());
                        viewModel.updatePurpose(purpose);
                    }
                }
            }
        }), true);
    }

    public static NotesFragment newInstance(int purposeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, purposeId);
        NotesFragment fragment = new NotesFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
