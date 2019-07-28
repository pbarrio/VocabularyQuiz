package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends Activity {

    private static final int NWORDS = 10; // Number of words in a test

    private static Random rand = new Random();

    private DBAccessor dba;

    private Set<Word> words;
    private Iterator<Word> wordsIt;
    private Word currentWord;
    private int nAnswered = 0;
    private ArrayList<Word> incorrect;
    private boolean isCurrentWordChecked;
    private boolean reverse; // Are we currently asking for a reverse translation?

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        Arrays.sort(allWords, new Word.CorrectnessComparator());

        // Pre-populate test to make sure words are not repeated
        words = new HashSet<Word>();
        for (int i = 0; words.size() < QuizActivity.NWORDS && i < allWords.length; ++i)
            words.add(allWords[i]);

        incorrect = new ArrayList<Word>();
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

        TextView question = (TextView) findViewById(R.id.foreignWord);
        EditText answer = (EditText) findViewById(R.id.answer);

        currentWord = wordsIt.next();
        reverse = rand.nextBoolean();

        // Reset text boxes and messages since they are outdated with info from the previous word
        answer.setText("");

        if (reverse)
            question.setText(currentWord.translation);
        else
            question.setText(currentWord.learntWord);

        isCurrentWordChecked = false;
    }

    public void checkWord(View view) { checkWord(); }

    private void checkWord() {

        if (isCurrentWordChecked)
            return;

        EditText answerBox = (EditText) findViewById(R.id.answer);

        String correctAnswer = reverse ?
                currentWord.learntWord.toLowerCase() :
                currentWord.translation.toLowerCase();
        String providedAnswer = answerBox.getText().toString().toLowerCase();

        String msg;
        if (correctAnswer.equals(providedAnswer)) {
            msg = getString(R.string.info_correct);
            currentWord.timesRight++;
        } else {
            msg = getString(
                    R.string.info_incorrect,
                    reverse ? currentWord.translation : currentWord.learntWord,
                    correctAnswer
            );
            incorrect.add(currentWord);
            currentWord.timesWrong++;
        }
        nAnswered++;
        Toast msgDialog = Toast.makeText(
                getApplicationContext(), msg, Toast.LENGTH_LONG);
        msgDialog.show();
        isCurrentWordChecked = true;
    }

    public void gotoResultsActivity(View view) {gotoResultsActivity();}

    public void gotoResultsActivity() {

        // Update words in the DB
        wordsIt = words.iterator();
        while (wordsIt.hasNext())
            dba.updateWord(wordsIt.next());

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(getString(R.string.param_n_answered), nAnswered);
        intent.putExtra(getString(R.string.param_incorrect), incorrect);
        startActivity(intent);
        finish();
    }
}
