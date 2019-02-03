package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashSet;

public class ResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        TextView resultMsg = (TextView) findViewById(R.id.resultMsg);
        int nQuestions = intent.getIntExtra(getString(R.string.param_n_answered), 0);
        HashSet<Word> incorrect =
                (HashSet<Word>)intent.getSerializableExtra(getString(R.string.param_incorrect));
        int nCorrect = nQuestions - incorrect.size();
        resultMsg.setText(getResources().getString(
                R.string.info_quiz_result,
                nCorrect,
                nQuestions,
                (nCorrect * 100) / (float) nQuestions
        ));

        // TODO: show a list with the failed words (already received into variable "incorrect")
    }
}
