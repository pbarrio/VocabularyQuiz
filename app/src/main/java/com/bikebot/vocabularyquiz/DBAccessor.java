package com.bikebot.vocabularyquiz;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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
    List<Word> getAllWords();

    @Query("DELETE FROM Word")
    void deleteAllWords();

    /*
     * Configuration table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConfigOption(ConfigOption c);

    @Query("SELECT value FROM ConfigOption WHERE option = :optionName")
    String getConfigOption(String optionName);
}
