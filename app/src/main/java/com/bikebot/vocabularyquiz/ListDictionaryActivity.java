package com.bikebot.vocabularyquiz;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListDictionaryActivity extends Activity {

    private DBAccessor dba;

    private ArrayList<Word> words;
    private WordAdapter adapter;

    // State for the context menu
    private Word currentlySelectedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dictionary);

        /* TODO: this functionality is duplicated in several classes. Create a superclass to access
           the DB (e.g. WordDBUser), and encapsulate the use of DBAccessor. */
        // TODO: show header with the current letter in the sorted list
        dba = Room.databaseBuilder(
                getApplicationContext(), VocabularyDB.class, "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();

        words = new ArrayList<>(Arrays.asList(dba.getAllWords()));
        Collections.sort(words);

        if (words.size() == 0) {
            Intent intent = new Intent(this, ErrorMsgActivity.class);
            intent.putExtra(getString(R.string.param_error), getString(R.string.error_empty_dict, "1"));
            startActivity(intent);
            finish();
            return;
        }

        ListView wordViewList = (ListView) this.findViewById(R.id.layout_list_dict);
        adapter = new WordAdapter(this, words);
        wordViewList.setAdapter(adapter);
        registerForContextMenu(wordViewList);
    }

    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wordlist_item, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentlySelectedWord = words.get(info.position);
        menu.setHeaderTitle(currentlySelectedWord.learntWord);
    }

    public boolean onContextItemSelected(MenuItem item) {

        //TODO: finish logic and remove other ways of delete the words
        switch (item.getItemId()) {
            case R.id.delete_button:
                adapter.remove(currentlySelectedWord);
                dba.deleteWord(currentlySelectedWord);
                return true;
            case R.id.modify_button:
                return true;
        }
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }
}
