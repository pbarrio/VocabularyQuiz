package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    DBAccessor dba;

    private void resetTitle () {
        String title = getTitle().toString();
        String language = dba.getConfigOption(getString(R.string.language_learnt));
        if (language != null)
            setTitle(title + " - " + language);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();

        resetTitle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        resetTitle();
    }

    public void gotoAddWord(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);
    }

    public void gotoQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void gotoOptions(View view) {
        Intent intent = new Intent(this, ConfigOptionsActivity.class);
        startActivityForResult(intent, 1);
    }

    public void gotoListDictionary(View view) {
        Intent intent = new Intent(this, ListDictionaryActivity.class);
        startActivity(intent);
    }
}
