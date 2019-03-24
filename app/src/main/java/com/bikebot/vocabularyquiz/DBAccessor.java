package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by pablo on 10/02/18.
 */

@Dao
public interface DBAccessor {

    /*
     * Word table
     */
    @Insert
    void insertNewWord(Word w);

    @Update
    void updateWord(Word word);

    @Delete()
    void deleteWord(Word word);

    @Query("SELECT * FROM Word")
    Word[] getAllWords();

    @Query("DELETE FROM Word")
    void deleteAllWords();

    /*
     * Configuration table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConfigOption(ConfigOption c);

    @Query("SELECT * FROM ConfigOption")
    ConfigOption[] getAllConfigOptions();

    @Query("SELECT value FROM ConfigOption WHERE option = :optionName")
    String getConfigOption(String optionName);
}
