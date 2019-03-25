package com.bikebot.vocabularyquiz;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    private Context adapterContext;
    private List<Word> wordList;

    public WordAdapter(@NonNull Context context, @LayoutRes ArrayList<Word> list) {
        super(context, 0 , list);
        adapterContext = context;
        wordList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        Word w = wordList.get(position);

        if (listItem == null) {
            // Default is to assume that the list item is a dictionary word
            int layout = R.layout.list_dictionary_element;

            // If the item doesng't have a translation, it's a list header
            if (w.translation.equals(""))
                layout = R.layout.list_dictionary_header;

            listItem = LayoutInflater.from(adapterContext).inflate(
                    layout, parent, false);
        }

        TextView foreign = (TextView) listItem.findViewById(R.id.foreignWord);
        if (foreign != null) foreign.setText(w.learntWord);

        TextView translation = (TextView) listItem.findViewById(R.id.translation);
        if (translation != null) translation.setText(w.translation);

        TextView extras = (TextView) listItem.findViewById(R.id.additionalData);
        if (extras != null) extras.setText(adapterContext.getResources().getString(
                R.string.additional_word_data,
                w.getCorrectness()
        ));

        return listItem;
    }
}
