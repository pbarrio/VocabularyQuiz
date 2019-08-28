package com.bikebot.vocabularyquiz;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by pablo on 10/02/18.
 */

// TODO: #10 find a way to do backups of the data and recover from there

// TODO: #11 allow synonyms, i.e. multiple meanings for a word and one meaning to match several words

@Database(entities={Word.class, ConfigOption.class}, version=2, exportSchema=true)
public abstract class VocabularyDB extends RoomDatabase {

    private static VocabularyDB INSTANCE;

    /*
    DB migration from v.1 to v.2

    Changes: "translation" field cannot be null (@NonNull). Insert a message instead.
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS tmp_migration_1_2");
            db.execSQL(
                    "CREATE TABLE tmp_migration_1_2 (" +
                            "learntWord TEXT NOT NULL PRIMARY KEY, " +
                            "translation TEXT NOT NULL," +
                            "timesWrong INTEGER NOT NULL," +
                            "timesRight INTEGER NOT NULL)"
            );
            db.execSQL(
                    "UPDATE Word " +
                    "SET translation = '(add a meaning to this word)' " +
                            "WHERE translation IS NULL");
            db.execSQL(
                    "INSERT INTO " +
                            "tmp_migration_1_2(learntWord, translation, timesWrong, timesRight) " +
                            "SELECT learntWord, translation, timesWrong, timesRight FROM Word");
            db.execSQL("DROP TABLE Word");
            db.execSQL("ALTER TABLE tmp_migration_1_2 RENAME TO Word");
        }
    };

    public static VocabularyDB getDB(final Context context) {
        if (INSTANCE == null) {
            synchronized (VocabularyDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            VocabularyDB.class,
                            "vocabulary-db"
                    )
                    // TODO: #5 Do not allow main thread queries.
                    .allowMainThreadQueries().addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DBAccessor getDBAccessor();
}
