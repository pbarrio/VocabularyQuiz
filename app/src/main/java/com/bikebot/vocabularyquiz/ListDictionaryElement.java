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

    protected DBAccessor dba;

    protected Word word;
    protected TextView foreignWord;
    protected TextView translatedWord;
    protected CheckBox select;

    public ListDictionaryElement(Context context) {

        super(context);

        // Layout of the element
        // TODO: try to use the layout object as much as possible instead of creating new ones
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        this.setLayoutParams(layout);

        // Layout of the first sub-element to the right of the parent element
        foreignWord = new TextView(context);
        foreignWord.setId(FOREIGN_WORD_ID);
        RelativeLayout.LayoutParams layout1 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layout1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        foreignWord.setLayoutParams(layout1);
        this.addView(foreignWord);

        // Layout of the sub-elements next to the first one
        translatedWord = new TextView(context);
        RelativeLayout.LayoutParams layout2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layout2.addRule(RelativeLayout.RIGHT_OF, FOREIGN_WORD_ID);
        layout2.leftMargin = 30;
        translatedWord.setLayoutParams(layout2);
        this.addView(translatedWord);

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
        foreignWord.setText(w.nativeWord);

        // TODO: do not concatenate string. Use resource string with placeholders.
        translatedWord.setText(getResources().getString(R.string.translated_word_in_list_dict, w.translatedWord));
    }

    public Word getWord() {return word;}

    public boolean isSelected() {return select.isChecked();}
}
