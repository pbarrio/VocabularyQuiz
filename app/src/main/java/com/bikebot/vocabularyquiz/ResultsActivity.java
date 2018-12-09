package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        TextView resultMsg = (TextView) findViewById(R.id.resultMsg);
        int correct = intent.getIntExtra(getString(R.string.param_n_correct), 0);
        int incorrect = intent.getIntExtra(getString(R.string.param_n_incorrect), 0);
        resultMsg.setText(getResources().getString(
                R.string.info_quiz_result,
                correct + incorrect,
                correct,
                (correct * 100) / (float) (correct + incorrect)
        ));
    }
}
