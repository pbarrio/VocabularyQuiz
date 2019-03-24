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
        if (listItem == null)
            listItem = LayoutInflater.from(adapterContext).inflate(
                    R.layout.list_dictionary_element, parent, false);

        Word w = wordList.get(position);

        TextView foreign = (TextView) listItem.findViewById(R.id.foreignWord);
        foreign.setText(w.learntWord);

        TextView translation = (TextView) listItem.findViewById(R.id.translation);
        translation.setText(w.translation);

        TextView extras = (TextView) listItem.findViewById(R.id.additionalData);
        extras.setText(adapterContext.getResources().getString(
                R.string.additional_word_data,
                w.getCorrectness()
        ));

        return listItem;
    }
}
