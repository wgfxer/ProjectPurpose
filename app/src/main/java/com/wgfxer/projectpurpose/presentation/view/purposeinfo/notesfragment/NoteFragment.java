package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.domain.Note;
import java.util.UUID;

public class NoteFragment extends Fragment {
    private static final String EXTRA_NOTE_BODY = "note_body";
    private static final String EXTRA_NOTE_ID = "note_id";
    private static final String EXTRA_NOTE_TITLE = "note_title";
    private UUID noteId;
    private OnNoteChangedListener onNoteChangedListener;

    public interface OnNoteChangedListener {
        void onNoteChanged(Note note);
    }

    private void setOnNoteChangedListener(OnNoteChangedListener onNoteChangedListener2) {
        this.onNoteChangedListener = onNoteChangedListener2;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteId = (UUID) getArguments().getSerializable(EXTRA_NOTE_ID);
        String title = getArguments().getString(EXTRA_NOTE_TITLE);
        String body = getArguments().getString(EXTRA_NOTE_BODY);
        final EditText noteTitleEditText =  view.findViewById(R.id.note_title_edit_text);
        final EditText noteBodyEditText =  view.findViewById(R.id.note_body_edit_text);
        noteTitleEditText.setText(title);
        noteBodyEditText.setText(body);
        TextWatcher textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onNoteChangedListener != null) {
                    Note note = new Note();
                    note.setId(NoteFragment.this.noteId);
                    note.setTitle(noteTitleEditText.getText().toString());
                    note.setBody(noteBodyEditText.getText().toString());
                    onNoteChangedListener.onNoteChanged(note);
                }
            }

            public void afterTextChanged(Editable editable) {
            }
        };
        noteTitleEditText.addTextChangedListener(textWatcher);
        noteBodyEditText.addTextChangedListener(textWatcher);
    }

    public static NoteFragment newInstance(Note note, OnNoteChangedListener onNoteChangedListener2) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NOTE_ID, note.getId());
        args.putString(EXTRA_NOTE_TITLE, note.getTitle());
        args.putString(EXTRA_NOTE_BODY, note.getBody());
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        fragment.setOnNoteChangedListener(onNoteChangedListener2);
        return fragment;
    }
}
