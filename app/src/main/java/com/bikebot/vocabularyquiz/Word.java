package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by pablo on 10/02/18.
 */

@Entity
public class Word implements Comparable<Word>, Serializable {

    /*
        TODO: find a consistent naming for the word in one language and in another. Possibly need to
        come up with a name for the two languages (native for the current language, foreign for the
        one we're learning?)
    */

    @PrimaryKey
    @NonNull
    public String learntWord; // The word in the language we are learning

    @ColumnInfo
    public String translation; // The word in the language we know

    @ColumnInfo
    public int timesWrong; // #times the word was answered wrong in a quiz

    @ColumnInfo
    public int timesRight; // #times the word was answered right in a quiz

    public Word(String learntWord, String translation) {
        this.learntWord = learntWord;
        this.translation = translation;
        this.timesWrong = 0;
        this.timesRight = 0;
    }

    public int compareTo(Word word) {
        return this.learntWord.toLowerCase().compareTo(word.learntWord.toLowerCase());
    }

    public int getCorrectness() {
        try {
            return timesRight - timesWrong;
        }
        catch (ArithmeticException e) {
            return 0;
        }
    }

    static class CorrectnessComparator implements Comparator<Word> {
        public int compare(Word a, Word b) {
            return a.getCorrectness() - b.getCorrectness();
        }
    }
}
