package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.domain.ICanShowNote;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.models.domain.Note;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NotesAdapter.OnNoteClickListener;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NotesAdapter.OnNoteDeleteClickListener;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

public class NotesListFragment extends Fragment {
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";
    private ICanShowNote canShowNote;
    private Button newNoteButton;
    private NotesAdapter notesAdapter;
    private RecyclerView notesRecyclerView;
    public Purpose purpose;
    private MainViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getTargetFragment() instanceof ICanShowNote) {
            canShowNote = (ICanShowNote) getTargetFragment();
        }
        notesRecyclerView =  view.findViewById(R.id.notes_recycler_view);
        newNoteButton = view.findViewById(R.id.new_note_button);
        newNoteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Note newNote = new Note();
                purpose.getNotesList().add(newNote);
                viewModel.updatePurpose(purpose);
                if (canShowNote != null) {
                    canShowNote.showNote(newNote);
                }
            }
        });
        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new OnNoteClickListener() {
            public void onNoteClicked(Note note) {
                if (canShowNote != null) {
                    canShowNote.showNote(note);
                }
            }
        });
        notesAdapter.setOnNoteDeleteClickListener(new OnNoteDeleteClickListener() {
            public void onNoteDeleteClicked(Note note) {
                for (int i = 0; i < NotesListFragment.this.purpose.getNotesList().size(); i++) {
                    if ((purpose.getNotesList().get(i)).getId().equals(note.getId())) {
                        purpose.getNotesList().remove(i);
                        viewModel.updatePurpose(purpose);
                    }
                }
            }
        });
        notesRecyclerView.setAdapter(notesAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(getContext())).get(MainViewModel.class);
        viewModel.getPurposeById(getArguments().getInt(KEY_PURPOSE_ID)).observe(this, new Observer<Purpose>() {
            public void onChanged(Purpose purpose) {
                if (purpose != null) {
                    NotesListFragment.this.purpose = purpose;
                    notesAdapter.setNotesList(purpose.getNotesList());
                }
            }
        });
    }

    public static NotesListFragment newInstance(int purposeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, purposeId);
        NotesListFragment fragment = new NotesListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
