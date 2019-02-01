package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends Activity {

    private static final int NWORDS = 10; // Number of words in a test

    private static Random rand = new Random();

    DBAccessor dba;

    private Set<Word> words;
    private Iterator<Word> wordsIt;
    private Word current_word;
    private int correct; // Score
    HashSet<Word> incorrect;
    private boolean isCurrentWordChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: allow changing the type of test (from native to translated or vice-versa)
        dba = Room.databaseBuilder(
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

        // TODO: make sure that even words that have 100% correctness have a chance to be chosen
        // Idea: make it dependent on the number of times that it showed up on a test
        // Another idea: sort words by #failed, then by #correct descending
        Arrays.sort(allWords, new Word.CorrectnessComparator());

        // Pre-populate test to make sure words are not repeated
        words = new HashSet<Word>();
        for (int i = 0; words.size() < QuizActivity.NWORDS && i < allWords.length; ++i)
            words.add(allWords[i]);

        correct = 0;
        incorrect = new HashSet<Word>();
        wordsIt = words.iterator();
        askWord();
    }

    public void askWord(View view) {
        checkWord();
        askWord();
    }

    protected void askWord() {

        // Check if the test has finished
        if (! wordsIt.hasNext()) {
            gotoResultsActivity();
            return;
        }

        current_word = wordsIt.next();

        TextView cmpCheckMsg = (TextView) findViewById(R.id.cmpCheckMsg);
        EditText answer = (EditText) findViewById(R.id.answer);
        cmpCheckMsg.setText("");
        answer.setText("");

        TextView foreignWord = (TextView) findViewById(R.id.foreignWord);
        foreignWord.setText(current_word.learntWord);
        isCurrentWordChecked = false;
    }

    public void checkWord(View view) { checkWord(); }

    private void checkWord() {

        if (isCurrentWordChecked)
            return;

        EditText answerBox = (EditText) findViewById(R.id.answer);
        TextView cmpCheck = (TextView) findViewById(R.id.cmpCheckMsg);

        // TODO: make correctness test case-insensitive
        String translation = current_word.translation;
        if (translation.equals(answerBox.getText().toString())) {
            // TODO: display result message in a "Snackbar" instead of a text field
            cmpCheck.setText(getText(R.string.info_correct));
            ++correct;
            current_word.timesRight++;
        } else {
            // TODO: display result message in a "Snackbar" instead of a text field
            cmpCheck.setText(getResources().getString(
                    R.string.info_incorrect,
                    current_word.learntWord,
                    translation
            ));
            incorrect.add(current_word);
            current_word.timesWrong++;
        }
        isCurrentWordChecked = true;
    }

    public void gotoResultsActivity(View view) {gotoResultsActivity();}

    public void gotoResultsActivity() {

        // Update words in the DB
        wordsIt = words.iterator();
        while (wordsIt.hasNext())
            dba.updateWord(wordsIt.next());

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(getString(R.string.param_n_correct), correct);
        intent.putExtra(getString(R.string.param_n_incorrect), incorrect.toArray().length);
        startActivity(intent);
        finish();
    }
}
