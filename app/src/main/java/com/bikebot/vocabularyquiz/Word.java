package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by pablo on 10/02/18.
 */

@Entity
public class Word {

    /*
        TODO: find a consistent naming for the word in one language and in another. Possibly need to
        come up with a name for the two languages (native for the current language, foreign for the
        one we're learning?)
    */

    @PrimaryKey
    @NonNull
    public String nativeWord;

    @ColumnInfo
    public String translatedWord;

    public Word(String nativeWord, String translatedWord) {
        this.nativeWord = nativeWord;
        this.translatedWord = translatedWord;
    }
}
