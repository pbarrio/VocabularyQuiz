package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        TextView resultMsg = (TextView) findViewById(R.id.resultMsg);
        int nQuestions = intent.getIntExtra(getString(R.string.param_n_answered), 0);

        // Receive incorrect words from the test to show the user as part of the results
        ArrayList<Word> incorrectWords =
                (ArrayList<Word>)intent.getSerializableExtra(getString(R.string.param_incorrect));
        int nCorrect = nQuestions - incorrectWords.size();
        resultMsg.setText(getResources().getString(
                R.string.info_quiz_result,
                nCorrect,
                nQuestions,
                (nCorrect * 100) / (float) nQuestions
        ));

        // Show a list with the failed words, if any, otherwise set message to congratulate the user
        if (incorrectWords.size() == 0) {
            TextView incorrectWordView = (TextView) this.findViewById(R.id.failedHeader);
            incorrectWordView.setText(getString(R.string.header_no_failed_words));
        }
        else {
            ListView incorrectWordView = (ListView) this.findViewById(R.id.view_list_incorrect);
            WordAdapter adapter = new WordAdapter(this, incorrectWords);
            incorrectWordView.setAdapter(adapter);
        }
    }
}
