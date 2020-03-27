package com.wgfxer.projectpurpose.presentation.viewmodel;

import android.content.Context;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.repository.NotesRepository;
import com.wgfxer.projectpurpose.data.repository.PurposesRepository;
import com.wgfxer.projectpurpose.data.repository.ReportsRepository;
import com.wgfxer.projectpurpose.data.repository.TasksRepository;
import com.wgfxer.projectpurpose.domain.INotesRepository;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.domain.IReportsRepository;
import com.wgfxer.projectpurpose.domain.ITasksRepository;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Фабрика для создания вьюМоделей
 * устанавливает реализацию репозитория
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context applicationContext;

    public ViewModelFactory(@NonNull Context context) {
        applicationContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (PurposeViewModel.class.equals(modelClass)) {
            ProjectPurposeDatabase database = ProjectPurposeDatabase.getInstance(applicationContext);
            IPurposesRepository purposesRepository = new PurposesRepository(database, Executors.newSingleThreadExecutor());
            // noinspection unchecked
            return (T) new PurposeViewModel(purposesRepository, applicationContext);
        } else if(TaskViewModel.class.equals(modelClass)){
            ProjectPurposeDatabase database = ProjectPurposeDatabase.getInstance(applicationContext);
            ITasksRepository tasksRepository = new TasksRepository(database, Executors.newSingleThreadExecutor());
            // noinspection unchecked
            return (T) new TaskViewModel(tasksRepository);
        } else if(NoteViewModel.class.equals(modelClass)){
            ProjectPurposeDatabase database = ProjectPurposeDatabase.getInstance(applicationContext);
            INotesRepository notesRepository = new NotesRepository(database, Executors.newSingleThreadExecutor());
            // noinspection unchecked
            return (T) new NoteViewModel(notesRepository);
        } else if(ReportViewModel.class.equals(modelClass)){
            ProjectPurposeDatabase database = ProjectPurposeDatabase.getInstance(applicationContext);
            IReportsRepository reportsRepository = new ReportsRepository(database, Executors.newSingleThreadExecutor());
            // noinspection unchecked
            return (T) new ReportViewModel(reportsRepository);
        }else {
            return super.create(modelClass);
        }
    }
}
