package com.wgfxer.projectpurpose.presentation.view.purpose_info;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.domain.Note;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {
    private static final String EXTRA_NOTE_TITLE_RES_ID = "note_title_res_id";
    private static final String EXTRA_NOTE_BODY = "note_body";
    private static final String EXTRA_NOTE_HINT_RES_ID = "note_hint_res_id";

    private OnNoteChangedListener onNoteChangedListener;

    public void setOnNoteChangedListener(OnNoteChangedListener onNoteChangedListener) {
        this.onNoteChangedListener = onNoteChangedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int titleResId = getArguments().getInt(EXTRA_NOTE_TITLE_RES_ID);
        String body = getArguments().getString(EXTRA_NOTE_BODY);
        int hintResId = getArguments().getInt(EXTRA_NOTE_HINT_RES_ID);
        TextView noteTitleTextView = view.findViewById(R.id.note_title_text_view);
        EditText noteBodyEditText = view.findViewById(R.id.note_body_edit_text);
        noteTitleTextView.setText(titleResId);
        noteBodyEditText.setText(body);
        noteBodyEditText.setHint(hintResId);

        noteBodyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (onNoteChangedListener != null) {
                    onNoteChangedListener.onNoteChanged(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static NoteFragment newInstance(Note note, OnNoteChangedListener onNoteChangedListener) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_NOTE_TITLE_RES_ID, note.getTitleResourceId());
        args.putString(EXTRA_NOTE_BODY, note.getBody());
        args.putInt(EXTRA_NOTE_HINT_RES_ID, note.getHintResourceId());
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        fragment.setOnNoteChangedListener(onNoteChangedListener);
        return fragment;
    }

    interface OnNoteChangedListener {
        void onNoteChanged(String body);
    }
}
