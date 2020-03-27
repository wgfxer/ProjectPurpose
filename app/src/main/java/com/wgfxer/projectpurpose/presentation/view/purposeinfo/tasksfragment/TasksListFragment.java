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
import com.wgfxer.projectpurpose.models.Task;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksAdapter.OnNewTaskClickListener;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksAdapter.OnTaskDeleteClickListener;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksAdapter.OnTaskDoneClickListener;
import com.wgfxer.projectpurpose.presentation.viewmodel.TaskViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.ViewModelFactory;
import java.util.List;

public class TasksListFragment extends Fragment {
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";
    private TasksAdapter doneTasksAdapter;
    private RecyclerView doneTasksRecyclerView;
    private boolean isDoneTasksShow = true;
    private TasksAdapter.OnTaskChangeListener onTaskChangeListener;
    private OnTaskDeleteClickListener onTaskDeleteClickListener;
    private OnTaskDoneClickListener onTaskDoneClickListener;
    private PreferencesHelper preferencesHelper;
    private Button showDoneTasksButton;
    private TasksAdapter tasksAdapter;
    private RecyclerView tasksRecyclerView;
    private TaskViewModel viewModel;

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
        viewModel =  ViewModelProviders.of( this,  new ViewModelFactory(getContext())).get(TaskViewModel.class);
        int purposeId = getArguments().getInt(KEY_PURPOSE_ID);
        viewModel.getFutureTasks(purposeId).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    tasksAdapter.setTasksList(tasks);
                }
            }
        });
        viewModel.getDoneTasks(purposeId).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if(tasks != null){
                    doneTasksAdapter.setTasksList(tasks);
                    setupShowDoneTasksButtonVisibility(tasks);
                }
            }
        });
    }

    private void setupShowDoneTasksButtonVisibility(List<Task> tasks) {
        if(tasks.size() > 0){
            showDoneTasksButton.setVisibility(View.VISIBLE);

        }else{
            showDoneTasksButton.setVisibility(View.GONE);
        }
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
        onTaskChangeListener = new TasksAdapter.OnTaskChangeListener() {
            @Override
            public void onTaskChange(Task task) {
                    viewModel.updateTask(task);
            }
        };
        onTaskDeleteClickListener = new OnTaskDeleteClickListener() {
            public void onTaskDeleteClicked(Task task) {
                viewModel.deleteTask(task);
            }
        };
        onTaskDoneClickListener = new OnTaskDoneClickListener() {
            public void onTaskDoneClicked(Task task) {
                task.setDone(!task.isDone());
                viewModel.updateTask(task);
            }
        };
    }

    private void setupTasksView(View view) {
        tasksRecyclerView =  view.findViewById(R.id.tasks_recycler_view);
        tasksAdapter = new TasksAdapter(true);
        tasksAdapter.setOnNewTaskClickListener(new OnNewTaskClickListener() {
            public void onNewTaskClicked() {
                Task task = new Task();
                task.setPurposeId(getArguments().getInt(KEY_PURPOSE_ID));
                viewModel.insertTask(task);
            }
        });
        tasksAdapter.setOnTaskChangeListener(onTaskChangeListener);
        tasksAdapter.setOnTaskDeleteClickListener(onTaskDeleteClickListener);
        tasksAdapter.setOnTaskDoneClickListener(onTaskDoneClickListener);
        tasksRecyclerView.setAdapter(tasksAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupDoneTasksView(View view) {
        doneTasksRecyclerView = view.findViewById(R.id.done_tasks_recycler_view);
        doneTasksAdapter = new TasksAdapter(false);
        doneTasksAdapter.setOnTaskChangeListener(onTaskChangeListener);
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

    public static TasksListFragment newInstance(int purposeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, purposeId);
        TasksListFragment fragment = new TasksListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
