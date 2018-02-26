package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by pablo on 10/02/18.
 */

@Dao
public interface DBAccessor {

    @Insert
    public void insertNewWord(Word w);

    @Query("SELECT * FROM Word")
    public Word[] getAllWords();

    @Query("DELETE FROM Word")
    public void deleteAllWords();

    @Delete()
    public void deleteWord(Word word);
}
