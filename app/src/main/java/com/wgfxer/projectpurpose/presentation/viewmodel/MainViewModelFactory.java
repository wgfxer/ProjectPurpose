package com.wgfxer.projectpurpose.presentation.viewmodel;

import android.content.Context;

import com.wgfxer.projectpurpose.data.repository.PurposesRepository;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Фабрика для создания вьюМодели
 * устанавливает реализацию репозитория
 */
public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context applicationContext;

    public MainViewModelFactory(@NonNull Context context) {
        applicationContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (MainViewModel.class.equals(modelClass)) {
            IPurposesRepository purposesRepository = new PurposesRepository(applicationContext);

            // noinspection unchecked
            return (T) new MainViewModel(purposesRepository);
        } else {
            return super.create(modelClass);
        }
    }
}
