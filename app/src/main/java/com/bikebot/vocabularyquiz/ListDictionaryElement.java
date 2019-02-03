package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * One element in the dictionary viewer
 *
 * @author Pablo Barrio
 */

public class ListDictionaryElement extends RelativeLayout{

    protected static final int FOREIGN_WORD_ID = 10000;
    protected static final int TRANSLATED_WORD_ID = 10001;

    protected DBAccessor dba;

    protected Word word;
    protected TextView foreignWord;
    protected TextView translatedWord;
    protected TextView additionalData;
    protected CheckBox select;

    // TODO: add a slider in the word so that it hides the meaning but we can slide with the finger
    // to see the meaning. This is good to study with the dictionary.

    // TODO: additionally to the previous idea, it would be nice to have a button in
    // ListDictionaryActivity to switch between word and meaning visible, so that we can study in
    // both directions.

    public ListDictionaryElement(Context context) {

        super(context);

        // Layout of the element
        // TODO: try to use the layout object as much as possible instead of creating new ones
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        this.setLayoutParams(layout);

        // Layout of the first element (learnt word)
        foreignWord = new TextView(context);
        foreignWord.setId(FOREIGN_WORD_ID);
        RelativeLayout.LayoutParams layout1 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layout1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        foreignWord.setLayoutParams(layout1);
        this.addView(foreignWord);

        // Layout of the translation
        translatedWord = new TextView(context);
        translatedWord.setId(TRANSLATED_WORD_ID);
        RelativeLayout.LayoutParams layout2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layout2.addRule(RelativeLayout.RIGHT_OF, FOREIGN_WORD_ID);
        layout2.leftMargin = 30;
        translatedWord.setLayoutParams(layout2);
        this.addView(translatedWord);

        // Layout of the additional word information
        additionalData = new TextView(context);
        RelativeLayout.LayoutParams layout3 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layout3.addRule(RelativeLayout.RIGHT_OF, TRANSLATED_WORD_ID);
        layout3.leftMargin = 30;
        additionalData.setLayoutParams(layout3);
        this.addView(additionalData);

        // Layout of the tick box
        select = new CheckBox(context);
        RelativeLayout.LayoutParams selectLayout = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        selectLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        select.setLayoutParams(selectLayout);
        this.addView(select);

        // TODO: do not allow main thread queries
        dba = Room.databaseBuilder(
                context, VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();
    }

    public void setWord(Word w) {

        word = w;
        foreignWord.setText(word.learntWord);
        translatedWord.setText(getResources().getString(
                R.string.translated_word_in_list_dict,
                word.translation
        ));

        // Info about how many times the word was answered correctly
        additionalData.setText(getResources().getString(
                R.string.additional_word_data,
                word.getCorrectness()
        ));
    }

    public Word getWord() {return word;}

    public boolean isSelected() {return select.isChecked();}
}
