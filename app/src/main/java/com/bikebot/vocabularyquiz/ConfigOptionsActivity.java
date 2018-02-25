package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class ConfigOptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_options);
    }

    public void deleteDB(View view) {

        DBAccessor dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();
        dba.deleteAllWords();
    }
}
