package com.wgfxer.projectpurpose.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.models.data.Purpose;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PurposesRepository implements IPurposesRepository {

    private static ProjectPurposeDatabase database;

    public PurposesRepository(Context context) {
        database = ProjectPurposeDatabase.getInstance(context.getApplicationContext());
    }

    @Override
    public LiveData<List<Purpose>> getAllPurposes() {
        return database.purposeDao().getAllPurposes();
    }

    @Override
    public LiveData<List<Purpose>> getDonePurposes() {
        return database.purposeDao().getDonePurposes();
    }

    @Override
    public LiveData<Purpose> getPurposeById(int id) {
        return database.purposeDao().getPurposeById(id);
    }

    public void insertPurpose(Purpose purpose) {
        new InsertPurposeTask().execute(purpose);
    }

    public void updatePurpose(Purpose purpose) {
        new UpdatePurposeTask().execute(purpose);
    }

    public void deletePurpose(Purpose purpose) {
        new DeletePurposeTask().execute(purpose);
    }

    private static class InsertPurposeTask extends AsyncTask<Purpose, Void, Void> {
        @Override
        protected Void doInBackground(Purpose... purposes) {
            if (purposes != null && purposes.length > 0) {
                database.purposeDao().insertPurpose(purposes[0]);
            }
            return null;
        }
    }

    private static class UpdatePurposeTask extends AsyncTask<Purpose, Void, Void> {
        @Override
        protected Void doInBackground(Purpose... purposes) {
            if (purposes != null && purposes.length > 0) {
                database.purposeDao().updatePurpose(purposes[0]);
            }
            return null;
        }
    }

    private static class DeletePurposeTask extends AsyncTask<Purpose, Void, Void> {
        @Override
        protected Void doInBackground(Purpose... purposes) {
            if (purposes != null && purposes.length > 0) {
                database.purposeDao().deletePurpose(purposes[0]);
            }
            return null;
        }
    }
}
