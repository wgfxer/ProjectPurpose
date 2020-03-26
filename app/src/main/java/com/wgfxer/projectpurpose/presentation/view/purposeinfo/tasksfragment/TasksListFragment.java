package com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment;

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
import com.wgfxer.projectpurpose.helper.PreferencesHelper;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.models.domain.Task;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksAdapter.OnNewTaskClickListener;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksAdapter.OnTaskDeleteClickListener;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksAdapter.OnTaskDoneClickListener;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;
import java.util.ArrayList;
import java.util.List;

public class TasksListFragment extends Fragment {
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";
    private TasksAdapter doneTasksAdapter;
    private RecyclerView doneTasksRecyclerView;
    private boolean isDoneTasksShow = true;
    private OnTaskDeleteClickListener onTaskDeleteClickListener;
    private OnTaskDoneClickListener onTaskDoneClickListener;
    private PreferencesHelper preferencesHelper;
    private Purpose purpose;
    private Button showDoneTasksButton;
    private TasksAdapter tasksAdapter;
    private RecyclerView tasksRecyclerView;
    private MainViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.tasks_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferencesHelper = new PreferencesHelper(getContext());
        setUpListeners();
        setupTasksView(view);
        setupDoneTasksView(view);
        setupShowTasksButton(view);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel =  ViewModelProviders.of( this,  new MainViewModelFactory(getContext())).get(MainViewModel.class);
        viewModel.getPurposeById(getArguments().getInt(KEY_PURPOSE_ID)).observe(this, new Observer<Purpose>() {
            public void onChanged(Purpose purpose) {
                if (purpose != null) {
                    TasksListFragment.this.purpose = purpose;
                    updateUI();
                }
            }
        });
    }

    private void updateUI() {
        tasksAdapter.setTasksList(getTasksIfDone(purpose.getTasksList(), false));
        doneTasksAdapter.setTasksList(getTasksIfDone(purpose.getTasksList(), true));
        if (getTasksIfDone(purpose.getTasksList(), true).size() == 0) {
            showDoneTasksButton.setVisibility(View.GONE);
        } else {
            showDoneTasksButton.setVisibility(View.VISIBLE);
        }
    }

    private List<Task> getTasksIfDone(List<Task> tasks, boolean isDone) {
        List<Task> listTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDone() == isDone) {
                listTasks.add(task);
            }
        }
        return listTasks;
    }

    private void setIsDoneTasksShow(boolean isShow) {
        if (isShow) {
            showDoneTasksButton.setText(R.string.hide_done_tasks);
            doneTasksRecyclerView.setVisibility(View.VISIBLE);
        } else {
            showDoneTasksButton.setText(R.string.show_done_tasks);
            doneTasksRecyclerView.setVisibility(View.GONE);
        }
        isDoneTasksShow = isShow;
    }

    private void setUpListeners() {
        onTaskDeleteClickListener = new OnTaskDeleteClickListener() {
            public void onTaskDeleteClicked(Task task) {
                if (purpose.getTasksList().remove(task)) {
                    updateUI();
                }
            }
        };
        onTaskDoneClickListener = new OnTaskDoneClickListener() {
            public void onTaskDoneClicked(Task task) {
                task.setDone(!task.isDone());
                updateUI();
            }
        };
    }

    private void setupTasksView(View view) {
        tasksRecyclerView =  view.findViewById(R.id.tasks_recycler_view);
        tasksAdapter = new TasksAdapter(true);
        tasksAdapter.setOnNewTaskClickListener(new OnNewTaskClickListener() {
            public void onNewTaskClicked() {
                purpose.getTasksList().add(new Task());
                tasksAdapter.setTasksList(getTasksIfDone(purpose.getTasksList(),false));
            }
        });
        tasksAdapter.setOnTaskDeleteClickListener(onTaskDeleteClickListener);
        tasksAdapter.setOnTaskDoneClickListener(onTaskDoneClickListener);
        tasksRecyclerView.setAdapter(tasksAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupDoneTasksView(View view) {
        doneTasksRecyclerView = view.findViewById(R.id.done_tasks_recycler_view);
        doneTasksAdapter = new TasksAdapter(false);
        doneTasksAdapter.setOnTaskDeleteClickListener(onTaskDeleteClickListener);
        doneTasksAdapter.setOnTaskDoneClickListener(onTaskDoneClickListener);
        doneTasksRecyclerView.setAdapter(doneTasksAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        doneTasksRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupShowTasksButton(View view) {
        showDoneTasksButton =  view.findViewById(R.id.show_done_tasks_button);
        setIsDoneTasksShow(preferencesHelper.getIsDoneTasksShow());
        showDoneTasksButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                setIsDoneTasksShow(!isDoneTasksShow);
                preferencesHelper.setIsDoneTasksShow(isDoneTasksShow);
            }
        });
    }

    public void onPause() {
        super.onPause();
        viewModel.updatePurpose(purpose);
    }

    public static TasksListFragment newInstance(int purposeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, purposeId);
        TasksListFragment fragment = new TasksListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
