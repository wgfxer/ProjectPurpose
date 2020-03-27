package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.Note;
import java.util.ArrayList;
import java.util.List;

class NotesAdapter extends Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notesList = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;
    private OnNoteDeleteClickListener onNoteDeleteClickListener;

    public class NotesViewHolder extends ViewHolder {
        private TextView noteBody;
        private TextView noteTitle;

        public NotesViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title_text_view);
            noteBody = itemView.findViewById(R.id.note_body_text_view);
        }

        public void bind(final Note note) {
            noteTitle.setText(note.getTitle());
            noteBody.setText(note.getBody());
            itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (onNoteClickListener != null) {
                        onNoteClickListener.onNoteClicked(note);
                    }
                }
            });
            itemView.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(itemView.getContext(), itemView);
                    popup.inflate(R.menu.note_item_menu);
                    popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.delete_note_item && onNoteDeleteClickListener != null) {
                                onNoteDeleteClickListener.onNoteDeleteClicked(note);
                            }
                            return false;
                        }
                    });
                    popup.show();
                    return true;
                }
            });
        }
    }

    public interface OnNoteClickListener {
        void onNoteClicked(Note note);
    }

    public interface OnNoteDeleteClickListener {
        void onNoteDeleteClicked(Note note);
    }

    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false));
    }

    public void onBindViewHolder(NotesViewHolder holder, int position) {
        holder.bind(notesList.get(position));
    }

    public int getItemCount() {
        return notesList.size();
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
        notifyDataSetChanged();
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public void setOnNoteDeleteClickListener(OnNoteDeleteClickListener onNoteDeleteClickListener) {
        this.onNoteDeleteClickListener = onNoteDeleteClickListener;
    }
}
