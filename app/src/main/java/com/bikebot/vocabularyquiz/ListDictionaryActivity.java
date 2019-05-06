package com.bikebot.vocabularyquiz;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
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

public class ListDictionaryActivity extends Activity implements ModifyWordDialogFragment.Listener{

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

        // Insert letter headers into the list
        char currentHeader = 0;
        for (int i = 0; i < words.size(); ++i) {
            char firstCharOfWord = words.get(i).learntWord.charAt(0);
            if (firstCharOfWord > currentHeader) {
                currentHeader = firstCharOfWord;
                words.add(
                        i,
                        new Word(String.valueOf(Character.toUpperCase(currentHeader)), "")
                );
            }
        }

        // Create adapter to visualize the list
        ListView wordViewList = (ListView) this.findViewById(R.id.layout_list_dict);
        adapter = new WordAdapter(this, words);
        wordViewList.setAdapter(adapter);

        // Allow context menu over the list of words (to delete elements, modify them, etc.)
        registerForContextMenu(wordViewList);
    }

    /* Show action menu for the currently selected word */
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentlySelectedWord = words.get(info.position);

        // If this is a header item, do not show the context menu
        if (currentlySelectedWord.translation.equals(""))
            return;

        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wordlist_item, menu);

        menu.setHeaderTitle(currentlySelectedWord.learntWord);
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // TODO: prompt for a double-check message before deleting the word
            case R.id.delete_button:
                adapter.remove(currentlySelectedWord);
                dba.deleteWord(currentlySelectedWord);
                adapter.notifyDataSetChanged();
                break;
            case R.id.modify_button:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                DialogFragment fragment = new ModifyWordDialogFragment();

                // Pass current word to the dialog
                Bundle args = new Bundle();
                args.putString(getString(R.string.param_foreign_word), currentlySelectedWord.learntWord);
                args.putString(getString(R.string.param_translation), currentlySelectedWord.translation);
                fragment.setArguments(args);

                fragment.show(transaction, "");
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void modifyTranslation(String translation) {
        currentlySelectedWord.translation = translation;
        dba.updateWord(currentlySelectedWord);
        adapter.notifyDataSetChanged();
    }
}
