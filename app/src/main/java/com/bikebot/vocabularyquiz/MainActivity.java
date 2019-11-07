package com.bikebot.vocabularyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    DBAccessor dba;

    private void resetTitle () throws ExecutionException, InterruptedException {
        String title = getResources().getString(R.string.app_name);

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return dba.getConfigOption(getString(R.string.language_learnt));
            }
        };

        Future<String> future = Executors.newSingleThreadExecutor().submit(callable);
        String language = future.get();
        if (language != null)
            setTitle(title + " - " + language);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = VocabularyDB.getDB(getApplicationContext()).getDBAccessor();

        try {
            resetTitle();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            resetTitle();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        startActivityForResult(intent, 1);
    }

    public void gotoListDictionary(View view) {
        Intent intent = new Intent(this, ListDictionaryActivity.class);
        startActivity(intent);
    }
}
