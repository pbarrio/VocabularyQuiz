package com.bikebot.vocabularyquiz;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class ListDictionaryActivity extends AppCompatActivity implements ModifyDBValueDialogFragment.Listener{

    private DBAccessor dba;

    private WordAdapter adapter;
    private DictionaryViewModel viewModel;

    // State for the context menu
    private Word currentlySelectedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dictionary);

        // Create adapter to visualize the list
        ListView wordViewList = this.findViewById(R.id.layout_list_dict);
        adapter = new WordAdapter(this, new ArrayList<Word>());
        wordViewList.setAdapter(adapter);

        // Observe changes to the dictionary
        viewModel = ViewModelProviders.of(this).get(DictionaryViewModel.class);
        viewModel.getAllWords().observe(ListDictionaryActivity.this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> newWords) {
                adapter.add(newWords);
            }
        });

        // Allow context menu over the list of words (to delete elements, modify them, etc.)
        registerForContextMenu(wordViewList);
    }

    /* Show action menu for the currently selected word */
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentlySelectedWord = adapter.getWordAt(info.position);

        // If this is a header item, do not show the context menu
        if (currentlySelectedWord.isHeader())
            return;

        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wordlist_item, menu);

        menu.setHeaderTitle(currentlySelectedWord.learntWord);
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // TODO: #9 prompt for a double-check message before deleting the word
            case R.id.delete_button:
                adapter.remove(currentlySelectedWord);
                dba.deleteWord(currentlySelectedWord);
                adapter.notifyDataSetChanged();
                break;
            case R.id.modify_button:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                DialogFragment fragment = new ModifyDBValueDialogFragment();

                Bundle args = new Bundle();

                // Pass current word to the dialog
                args.putString(getString(R.string.param_foreign_word), currentlySelectedWord.learntWord);
                args.putString(getString(R.string.param_translation), currentlySelectedWord.translation);

                args.putString(
                        getString(R.string.param_title),
                        getString(R.string.title_dialog_modify_word)
                                + " "
                                + currentlySelectedWord.learntWord
                );

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
