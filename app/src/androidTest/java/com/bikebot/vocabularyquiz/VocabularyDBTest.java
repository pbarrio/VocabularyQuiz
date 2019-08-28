package com.bikebot.vocabularyquiz;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.room.testing.MigrationTestHelper;
import android.support.test.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;

public class VocabularyDBTest {
    private static final String TEST_DB_NAME = "TestAppDatabase.db";

    @Rule
    public MigrationTestHelper migrationTestHelper;

    public VocabularyDBTest() {
        migrationTestHelper = new MigrationTestHelper(
                InstrumentationRegistry.getInstrumentation(),
                VocabularyDB.class.getCanonicalName(),
                        new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void onMigration_1_2_WordWithNoTranslation() throws java.io.IOException {
        SupportSQLiteDatabase db = migrationTestHelper.createDatabase(TEST_DB_NAME, 1);
        db.execSQL(
                "INSERT INTO Word (learntWord, timesRight, timesWrong)" +
                "VALUES ('foo', 0, 0)");
        db.close();

        migrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, VocabularyDB.MIGRATION_1_2);
    }
}
