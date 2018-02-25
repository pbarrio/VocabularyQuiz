package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class QuizActivity extends Activity {

    private static Random rand = new Random();
    private Word[] words;
    private Word chosen;

    // Score-related
    private int correct, incorrect;
    private boolean isCurrentWordChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        DBAccessor dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();

        words = dba.getAllWords();
        if (words.length == 0) {
            Intent intent = new Intent(this, ErrorMsgActivity.class);
            intent.putExtra(getString(R.string.param_error), getString(R.string.error_empty_dict));
            startActivity(intent);
            finish();
            return;
        }

        correct = 0;
        incorrect = 0;
        askWord();
    }

    public void askWord(View view) { askWord(); }

    public void askWord() {

        TextView cmpCheckMsg = (TextView) findViewById(R.id.cmpCheckMsg);
        EditText answer = (EditText) findViewById(R.id.answer);
        cmpCheckMsg.setText("");
        answer.setText("");

        chosen = words[rand.nextInt(words.length)];
        TextView foreignWord = (TextView) findViewById(R.id.foreignWord);
        foreignWord.setText(chosen.nativeWord);
        isCurrentWordChecked = false;
    }

    public void checkWord(View view) {

        if (isCurrentWordChecked)
            return;

        EditText answerBox = (EditText) findViewById(R.id.answer);
        TextView cmpCheck = (TextView) findViewById(R.id.cmpCheckMsg);

        String translation = chosen.translatedWord;
        isCurrentWordChecked = true;
        if (translation.equals(answerBox.getText().toString())) {
            cmpCheck.setText(getText(R.string.info_correct));
            ++correct;
        } else {
            cmpCheck.setText(getResources().getString(
                    R.string.info_incorrect,
                    chosen.nativeWord,
                    translation
            ));
            ++incorrect;
        }
    }

    public void gotoResultsActivity(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(getString(R.string.param_n_correct), correct);
        intent.putExtra(getString(R.string.param_n_incorrect), incorrect);
        startActivity(intent);
        finish();
    }
}
