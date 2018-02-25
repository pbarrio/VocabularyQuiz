package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by pablo on 10/02/18.
 */

@Database(entities={Word.class}, version=1, exportSchema=false)
public abstract class VocabularyDB extends RoomDatabase {

    public abstract DBAccessor getDBAccessor();
}
