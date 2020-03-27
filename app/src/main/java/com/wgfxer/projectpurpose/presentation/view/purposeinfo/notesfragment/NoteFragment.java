package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.Note;
import com.wgfxer.projectpurpose.presentation.viewmodel.NoteViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.ViewModelFactory;

import java.util.UUID;

public class NoteFragment extends Fragment {
    private static final String EXTRA_NOTE_ID = "note_id";
    private static final String EXTRA_PURPOSE_ID = "purpose_id";
    private Note note;
    private NoteViewModel viewModel;
    private EditText noteTitleEditText;
    private EditText noteBodyEditText;
    private TextWatcher titleTextWatcher;
    private TextWatcher bodyTextWatcher;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.note_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        setupViews(view);

        setupTextWatchers();

        viewModel = ViewModelProviders.of(this,new ViewModelFactory(getContext())).get(NoteViewModel.class);
    }

    private void findViews(View view) {
        noteTitleEditText =  view.findViewById(R.id.note_title_edit_text);
        noteBodyEditText =  view.findViewById(R.id.note_body_edit_text);
    }

    private void setupViews(View view) {
        if(note == null){
            note = new Note();
            note.setPurposeId(getArguments().getLong(EXTRA_PURPOSE_ID));
        }else{
            noteTitleEditText.setText(note.getTitle());
            noteBodyEditText.setText(note.getBody());
        }
    }


    private void setupTextWatchers() {
        titleTextWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setTitle(noteTitleEditText.getText().toString());
                //viewModel.updateNote(note);
            }

            public void afterTextChanged(Editable editable) {
            }
        };
        bodyTextWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setBody(noteBodyEditText.getText().toString());
                //viewModel.updateNote(note);
            }

            public void afterTextChanged(Editable editable) {
            }
        };

        noteTitleEditText.addTextChangedListener(titleTextWatcher);
        noteBodyEditText.addTextChangedListener(bodyTextWatcher);
    }

    @Override
    public void onPause() {
        super.onPause();
        long purposeId = getArguments().getLong(EXTRA_PURPOSE_ID, -1);
        if(purposeId == -1){
            viewModel.updateNote(note);
        }else{
            viewModel.insertNote(note, null);
        }
    }

    public static NoteFragment editInstance(Note note) {
        Bundle args = new Bundle();
        NoteFragment fragment = new NoteFragment();
        fragment.note = note;
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteFragment createInstance(long purposeId) {
        Bundle args = new Bundle();
        args.putLong(EXTRA_PURPOSE_ID, purposeId);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
