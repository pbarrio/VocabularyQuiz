package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ListDictionaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dictionary);

        DBAccessor dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();

        // TODO: this functionality is duplicated in several classes. Create a superclass to access
        // the DB (e.g. WordDBUser), and encapsulate the use of DBAccessor.
        Word[] words = dba.getAllWords();
        if (words.length == 0) {
            Intent intent = new Intent(this, ErrorMsgActivity.class);
            intent.putExtra(getString(R.string.param_error), getString(R.string.error_empty_dict));
            startActivity(intent);
            finish();
            return;
        }

        LinearLayout wordList = (LinearLayout) this.findViewById(R.id.layout_list_dict);
        for (Word w : words) {

            TextView row = new TextView(getApplicationContext());
            row.setText(w.nativeWord);
            wordList.addView(row);
        }
    }
}
