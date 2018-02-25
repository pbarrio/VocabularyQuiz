package com.bikebot.vocabularyquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoAddWord(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);
    }

    public void gotoQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void gotoOptions(View view) {
        Intent intent = new Intent(this, ConfigOptionsActivity.class);
        startActivity(intent);
    }

    public void gotoListDictionary(View view) {
        Intent intent = new Intent(this, ListDictionaryActivity.class);
        startActivity(intent);
    }
}
