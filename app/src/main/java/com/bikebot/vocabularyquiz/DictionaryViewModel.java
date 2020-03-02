package com.bikebot.vocabularyquiz;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DictionaryViewModel extends AndroidViewModel {

    private LiveData<List<Word>> wordList;
    private VocabularyDB db;

    public DictionaryViewModel(Application app) {
        super(app);
        db = VocabularyDB.getDB(this.getApplication());
        wordList = db.getDBAccessor().getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {return wordList;}

    public void insertWord(Word word) throws SQLiteConstraintException {
        new InsertAsyncTask(db.getDBAccessor()).execute(word);
    }

    public static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private DBAccessor dbAccessor;

        InsertAsyncTask(DBAccessor dao) {
            dbAccessor = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) throws SQLiteConstraintException {
            try {
                dbAccessor.insertNewWord(params[0]);
            }
            catch (SQLiteConstraintException e) {} // Don't care if the word could be added or not
            return null;
        }
    }
}
