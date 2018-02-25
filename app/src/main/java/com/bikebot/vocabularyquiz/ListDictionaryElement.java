package com.bikebot.vocabularyquiz;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListDictionaryElement extends RelativeLayout{

    protected static final int FOREIGN_WORD_ID = 10000;

    protected TextView foreignWord;
    protected TextView translatedWord;

    public ListDictionaryElement(Context context) {

        super(context);

        // Layout of the element
        // TODO: try to use the layout object as much as possible instead of creating new ones
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        this.setLayoutParams(layout);

        // Layout of the first sub-element with respect to the parent element
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

        Button deleteButton = new Button(context);
        RelativeLayout.LayoutParams deleteButtonLayout = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        deleteButtonLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        deleteButton.setLayoutParams(deleteButtonLayout);
        this.addView(deleteButton);
    }

    public void setForeignWord(String w) { foreignWord.setText(w); }

    // TODO: do not concatenate string. Use resource string with placeholders.
    public void setTranslatedWord(String w) { translatedWord.setText("(" + w + ")"); }
}
