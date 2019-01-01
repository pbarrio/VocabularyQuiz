package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;

public class ListDictionaryActivity extends Activity {

    private View selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dictionary);

        DBAccessor dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();

        /* TODO: this functionality is duplicated in several classes. Create a superclass to access
           the DB (e.g. WordDBUser), and encapsulate the use of DBAccessor. */
        // TODO: show header with the current letter in the sorted list
        // TODO: alphabetical sort should be case-insensitive
        Word[] words = dba.getAllWords();
        Arrays.sort(words);

        if (words.length == 0) {
            Intent intent = new Intent(this, ErrorMsgActivity.class);
            intent.putExtra(getString(R.string.param_error), getString(R.string.error_empty_dict));
            startActivity(intent);
            finish();
            return;
        }

        LinearLayout wordList = (LinearLayout) this.findViewById(R.id.layout_list_dict);
        for (Word w : words) {

            ListDictionaryElement row = new ListDictionaryElement(
                    new ContextThemeWrapper(getApplicationContext(), R.style.AppTheme));
            row.setWord(w);
            wordList.addView(row);
        }
        registerForContextMenu(wordList);
    }

    public void deleteSelectedElements(View view) {

        LinearLayout wordList = (LinearLayout) this.findViewById(R.id.layout_list_dict);
        DBAccessor dba = Room.databaseBuilder(
                wordList.getContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();

        for (int iRow = 0; iRow < wordList.getChildCount(); iRow++) {

            ListDictionaryElement r = (ListDictionaryElement) wordList.getChildAt(iRow);
            if (r.isSelected()) {
                wordList.removeView(r);
                dba.deleteWord(r.getWord());
                iRow--;
            }
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wordlist_item, menu);
        selectedItem = view;
    }

    public boolean onContextItemSelected(MenuItem item) {

        LinearLayout wordList = (LinearLayout) this.findViewById(R.id.layout_list_dict);
        //TODO: finish logic and remove other ways of delete the words
        switch (item.getItemId()) {
            case R.id.delete_button:
                wordList.removeView(selectedItem);
                return true;
            case R.id.modify_button:
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
