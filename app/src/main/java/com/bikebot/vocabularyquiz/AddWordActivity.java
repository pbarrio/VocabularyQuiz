package com.bikebot.vocabularyquiz;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddWordActivity extends AppCompatActivity {

    private DBAccessor dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        dba = VocabularyDB.getDB(getApplicationContext()).getDBAccessor();
    }

    public void saveWord(View view) {

        EditText wordField = (EditText)findViewById(R.id.learnt_word);
        EditText meaningField = (EditText)findViewById(R.id.meaning);
        String word = wordField.getText().toString();
        String meaning = meaningField.getText().toString();

        String msg;
        if (word.equals(""))
            msg = getString(R.string.error_empty_string, "the new word");
        else if (meaning.equals(""))
            msg = getString(R.string.error_empty_string, "the meaning of the new word");
        else
            try {
                dba.insertNewWord(new Word(word, meaning));
                msg = getString(R.string.info_word_saved);
            }
            catch (SQLiteConstraintException e) {
                msg = getString(R.string.info_word_exists);
            }
        Toast msgDialog = Toast.makeText(
                getApplicationContext(), msg, Toast.LENGTH_LONG);
        msgDialog.show();

        // Prepare for next word
        wordField.setText("");
        meaningField.setText("");
        wordField.requestFocus(); // Set the focus back to the first text field
    }
}
