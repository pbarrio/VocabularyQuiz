package com.bikebot.vocabularyquiz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Collections;
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

    //public void insertWord(Word word) {db.getDBAccessor().insertNewWord(word);}
}
