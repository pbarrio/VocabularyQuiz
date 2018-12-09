package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddWordActivity extends Activity {

    private DBAccessor dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        // TODO: do not allow main thread queries. This is done everywhere, so look for occurrences!
        dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();
    }

    public void saveWord(View view) {

        // TODO: style: don't use camelCase in resources. Use undrescores, such as info_msg
        EditText word = (EditText)findViewById(R.id.learntWord);
        EditText meaning = (EditText)findViewById(R.id.meaning);
        TextView infoMsg = (TextView) findViewById(R.id.info_msg);

        dba.insertNewWord(new Word(
                word.getText().toString(),
                meaning.getText().toString()));
        infoMsg.setText(getString(R.string.info_word_saved));

        // Prepare for next word
        word.setText("");
        meaning.setText("");
    }
}
