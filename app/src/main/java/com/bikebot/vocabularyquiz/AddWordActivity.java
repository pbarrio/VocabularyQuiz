package com.bikebot.vocabularyquiz;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddWordActivity extends AppCompatActivity {

    private DictionaryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        viewModel = ViewModelProviders.of(this).get(DictionaryViewModel.class);
    }

    public void saveWord(View view) {

        EditText wordField = findViewById(R.id.learnt_word);
        EditText meaningField = findViewById(R.id.meaning);
        String word = wordField.getText().toString();
        String meaning = meaningField.getText().toString();

        String msg = getString(R.string.info_word_saved);
        if (word.equals(""))
            msg = getString(R.string.error_empty_string, "the new word");
        else if (meaning.equals(""))
            msg = getString(R.string.error_empty_string, "the meaning of the new word");
        else
            viewModel.insertWord(new Word(word, meaning));

        Toast msgDialog = Toast.makeText(
                getApplicationContext(), msg, Toast.LENGTH_LONG);
        msgDialog.show();

        // Prepare for next word
        wordField.setText("");
        meaningField.setText("");
        wordField.requestFocus(); // Set the focus back to the first text field
    }
}
