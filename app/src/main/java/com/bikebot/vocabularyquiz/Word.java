package com.bikebot.vocabularyquiz;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by pablo on 10/02/18.
 */

@Entity
public class Word implements Comparable<Word>, Serializable {

    /*
        TODO: #12 find a consistent naming for the word in one language and in another. Possibly
          need to come up with a name for the two languages (native for the current language,
          foreign for the one we're learning?)
    */

    @PrimaryKey
    @NonNull
    public String learntWord; // The word in the language we are learning

    @ColumnInfo
    @NonNull
    public String translation; // The word in the language we know

    @ColumnInfo
    public int timesWrong; // #times the word was answered wrong in a quiz

    @ColumnInfo
    public int timesRight; // #times the word was answered right in a quiz

    public Word(@NonNull String learntWord, @NonNull String translation) {
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

    // Letter header for visualization purposes. Doesn't go into the DB.
    public boolean isHeader() {
        return translation.equals("") && learntWord.length() == 1;
    }

    static class CorrectnessComparator implements Comparator<Word> {
        public int compare(Word a, Word b) {
            return a.getCorrectness() - b.getCorrectness();
        }
    }
}
