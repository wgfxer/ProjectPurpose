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
import com.wgfxer.projectpurpose.models.Note;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NotesAdapter.OnNoteClickListener;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NotesAdapter.OnNoteDeleteClickListener;
import com.wgfxer.projectpurpose.presentation.viewmodel.NoteViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.ViewModelFactory;

import java.util.List;

public class NotesListFragment extends Fragment {
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";
    private ICanShowNote canShowNote;
    private Button newNoteButton;
    private NotesAdapter notesAdapter;
    private RecyclerView notesRecyclerView;
    private NoteViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getTargetFragment() instanceof ICanShowNote) {
            canShowNote = (ICanShowNote) getTargetFragment();
        }
        setupNewNoteButton(view);

        setupNotesRecyclerView(view);

        observeViewModel();
    }

    private void setupNotesRecyclerView(View view) {
        notesRecyclerView =  view.findViewById(R.id.notes_recycler_view);
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
                viewModel.deleteNote(note);
            }
        });
        notesRecyclerView.setAdapter(notesAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupNewNoteButton(View view) {
        newNoteButton = view.findViewById(R.id.new_note_button);
        newNoteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(canShowNote != null){
                    canShowNote.showNewNote(getArguments().getInt(KEY_PURPOSE_ID));
                }
            }
        });
    }

    private void observeViewModel() {
        int purposeId = getArguments().getInt(KEY_PURPOSE_ID);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(getContext())).get(NoteViewModel.class);
        viewModel.getNotes(purposeId).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(notes != null){
                    notesAdapter.setNotesList(notes);
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
