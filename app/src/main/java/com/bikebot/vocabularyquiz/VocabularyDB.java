package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by pablo on 10/02/18.
 */

// TODO: #10 find a way to do backups of the data and recover from there

// TODO: #11 allow synonyms, i.e. multiple meanings for a word and one meaning to match several words

@Database(entities={Word.class, ConfigOption.class}, version=1, exportSchema=true)
public abstract class VocabularyDB extends RoomDatabase {

    public abstract DBAccessor getDBAccessor();
}
