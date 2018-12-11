package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends Activity {

    private static final int NWORDS = 10; // Number of words in a test

    private static Random rand = new Random();

    private Iterator<Word> words_it;
    Word current_word;
    private int correct, incorrect; // Score

    private boolean isCurrentWordChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DBAccessor dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Word[] allWords = dba.getAllWords();
        if (allWords.length < QuizActivity.NWORDS) {

            Intent intent = new Intent(this, ErrorMsgActivity.class);
            intent.putExtra(
                    getString(R.string.param_error),
                    getString(R.string.error_empty_dict, QuizActivity.NWORDS)
            );
            startActivity(intent);
            finish();
            return;
        }

        // TODO: pick up words that have been failed more frequently - this should help learn them

        // Pre-populate test to make sure words are not repeated
        Set<Word> words = new HashSet<Word>();
        while (words.size() < QuizActivity.NWORDS)
            words.add(allWords[rand.nextInt(allWords.length)]);

        correct = 0;
        incorrect = 0;
        words_it = words.iterator();
        askWord();
    }

    public void askWord(View view) { askWord(); }

    protected void askWord() {

        // Check if the test has finished
        if (! words_it.hasNext()) {
            gotoResultsActivity();
            return;
        }

        current_word = words_it.next();

        TextView cmpCheckMsg = (TextView) findViewById(R.id.cmpCheckMsg);
        EditText answer = (EditText) findViewById(R.id.answer);
        cmpCheckMsg.setText("");
        answer.setText("");

        TextView foreignWord = (TextView) findViewById(R.id.foreignWord);
        foreignWord.setText(current_word.learntWord);
        isCurrentWordChecked = false;
    }

    // TODO: update failures and successes in the words themselves for later use
    public void checkWord(View view) {

        if (isCurrentWordChecked)
            return;

        EditText answerBox = (EditText) findViewById(R.id.answer);
        TextView cmpCheck = (TextView) findViewById(R.id.cmpCheckMsg);

        String translation = current_word.translation;
        if (translation.equals(answerBox.getText().toString())) {
            cmpCheck.setText(getText(R.string.info_correct));
            ++correct;
        } else {
            cmpCheck.setText(getResources().getString(
                    R.string.info_incorrect,
                    current_word.learntWord,
                    translation
            ));
            ++incorrect;
        }
        isCurrentWordChecked = true;
    }

    public void gotoResultsActivity(View view) {gotoResultsActivity();}

    public void gotoResultsActivity() {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(getString(R.string.param_n_correct), correct);
        intent.putExtra(getString(R.string.param_n_incorrect), incorrect);
        startActivity(intent);
        finish();
    }
}
