package com.wgfxer.projectpurpose.data.database;

import android.content.Context;

import com.wgfxer.projectpurpose.models.data.Purpose;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/**
 * БД для хранения целей
 */
@Database(entities = {Purpose.class}, version = 3, exportSchema = false)
public abstract class ProjectPurposeDatabase extends RoomDatabase {
    private static ProjectPurposeDatabase instance;
    private static final String DB_NAME = "project_purpose.db";
    private static final Object LOCK = new Object();


    /**
     * получение экземпляра бд
     *
     * @param context нужен для создания бд
     * @return экземпляр бд
     */
    public static ProjectPurposeDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, ProjectPurposeDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return instance;
    }

    public abstract PurposeDao purposeDao();
}
