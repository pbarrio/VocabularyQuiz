package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class ConfigOptionsActivity extends Activity {

    DBAccessor dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_options);

        dba = Room.databaseBuilder(
                getApplicationContext(),
                VocabularyDB.class,
                "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();
    }

    public void deleteDB(View view) {

        dba.deleteAllWords();
    }

    // TODO - This shows up as "null" the first time that the app is open. Should be something like
    // "not set" or such.
    public void changeLanguage(View view) {

        dba.insertConfigOption(new ConfigOption(
                getString(R.string.language_learnt),
                "hindi"
        ));
    }

    public void gotoListConfigOptions(View view) {
        Intent intent = new Intent(this, ListConfigOptionsActivity.class);
        startActivity(intent);
    }
}
