package com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.domain.PositionKeeper;
import com.wgfxer.projectpurpose.models.domain.Task;
import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends Adapter<ViewHolder> {
    private static final int FOOTER_VIEW = 1;
    private boolean canCreateNewTasks;
    private boolean needToFocusLastTask = false;
    private boolean needToFocusNewTask = false;
    private OnNewTaskClickListener onNewTaskClickListener;
    private OnTaskDeleteClickListener onTaskDeleteClickListener;
    private OnTaskDoneClickListener onTaskDoneClickListener;
    private int positionToFocus = -1;
    private List<Task> tasksList = new ArrayList<>();

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW) {
            return new NewTaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_task_item, parent, false));
        }
        return new TasksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof TasksViewHolder) {
            ((TasksViewHolder) holder).bind(position);
            EditText editTextToFocus = ((TasksViewHolder) holder).getEditText();
            if (needToFocusNewTask && position == tasksList.size() - 1) {
                editTextToFocus.requestFocus();
                ((InputMethodManager) editTextToFocus.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(2, 1);
                needToFocusNewTask = false;
            } else if (needToFocusLastTask && (position == positionToFocus || position == tasksList.size() - 1)) {
                editTextToFocus.requestFocus();
                editTextToFocus.setSelection(editTextToFocus.getText().length());
                needToFocusLastTask = false;
            }
        }
    }

    public int getItemCount() {
        if (this.canCreateNewTasks) {
            return tasksList.size() + 1;
        }
        return tasksList.size();
    }

    public class NewTaskViewHolder extends ViewHolder {
        public NewTaskViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (onNewTaskClickListener != null) {
                        onNewTaskClickListener.onNewTaskClicked();
                        needToFocusNewTask = true;
                    }
                }
            });
        }
    }

    public interface OnNewTaskClickListener {
        void onNewTaskClicked();
    }

    public interface OnTaskDeleteClickListener {
        void onTaskDeleteClicked(Task task);
    }

    public interface OnTaskDoneClickListener {
        void onTaskDoneClicked(Task task);
    }

    private class TaskTextWatcher extends PositionKeeper implements TextWatcher {

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Task task = tasksList.get(position);
            if (task != null) {
                task.setTitle(charSequence.toString());
            }
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    public class TasksViewHolder extends ViewHolder {
        private CheckBox checkBoxIsDone;
        private EditText taskTitle;
        private ImageView iconDelete;
        private TaskDoneClickListener doneClickListener = new TaskDoneClickListener();
        private TaskTitleFocusChangeListener focusChangeListener = new TaskTitleFocusChangeListener();
        private TaskTextWatcher taskTitleTextWatcher = new TaskTextWatcher();

        class TaskDoneClickListener extends PositionKeeper implements OnClickListener {

            public void onClick(View v) {
                if (onTaskDoneClickListener != null) {
                    onTaskDoneClickListener.onTaskDoneClicked(tasksList.get(position));
                }
            }
        }

        class TaskTitleFocusChangeListener extends PositionKeeper implements OnFocusChangeListener {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iconDelete.setVisibility(View.VISIBLE);
                    iconDelete.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if (onTaskDeleteClickListener != null) {
                                onTaskDeleteClickListener.onTaskDeleteClicked(tasksList.get(position));
                                positionToFocus = position;
                                needToFocusLastTask = true;
                            }
                        }
                    });
                }else{
                    iconDelete.setOnClickListener(null);
                    iconDelete.setVisibility(View.INVISIBLE);
                }
            }
        }

        public TasksViewHolder(View itemView) {
            super(itemView);
            checkBoxIsDone = itemView.findViewById(R.id.check_box_done);
            taskTitle = itemView.findViewById(R.id.task_title_edit_text);
            iconDelete = itemView.findViewById(R.id.icon_delete);
            taskTitle.addTextChangedListener(taskTitleTextWatcher);
            taskTitle.setOnFocusChangeListener(focusChangeListener);
            checkBoxIsDone.setOnClickListener(doneClickListener);
        }


        public void bind(int position) {
            Task task = tasksList.get(position);
            taskTitleTextWatcher.updatePosition(position);
            focusChangeListener.updatePosition(position);
            doneClickListener.updatePosition(position);
            checkBoxIsDone.setChecked(task.isDone());
            taskTitle.setText(task.getTitle());
        }

        public EditText getEditText() {
            return this.taskTitle;
        }
    }

    public TasksAdapter(boolean canCreateNewTasks) {
        this.canCreateNewTasks = canCreateNewTasks;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    public void setOnNewTaskClickListener(OnNewTaskClickListener onNewTaskClickListener) {
        this.onNewTaskClickListener = onNewTaskClickListener;
    }

    public void setOnTaskDeleteClickListener(OnTaskDeleteClickListener onTaskDeleteClickListener) {
        this.onTaskDeleteClickListener = onTaskDeleteClickListener;
    }

    public void setOnTaskDoneClickListener(OnTaskDoneClickListener onTaskDoneClickListener) {
        this.onTaskDoneClickListener = onTaskDoneClickListener;
    }



    public int getItemViewType(int position) {
        if (position == tasksList.size()) {
            return 1;
        }
        return super.getItemViewType(position);
    }
}
