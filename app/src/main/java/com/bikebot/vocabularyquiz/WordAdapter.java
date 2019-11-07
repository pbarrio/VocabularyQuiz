package com.bikebot.vocabularyquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        Word w = wordList.get(position);
        int layout = w.isHeader() ?
                    R.layout.list_dictionary_header :
                    R.layout.list_dictionary_element;

        /* TODO: #13 This is not optimal. We should try to reuse convertView if possible, but we
            first should check if it is of the right type (i.e. whether it is inflated from a
            list_dictionary_element or from a list_dictionary_header) */
        View listItem = LayoutInflater.from(adapterContext).inflate(layout, parent,false);

        TextView foreign = listItem.findViewById(R.id.foreignWord);
        foreign.setText(w.learntWord);

        if (!w.isHeader()) {

            TextView translation = listItem.findViewById(R.id.translation);
            translation.setText(w.translation);

            TextView extras = listItem.findViewById(R.id.additionalData);
            extras.setText(adapterContext.getResources().getString(
                    R.string.additional_word_data,
                    w.getCorrectness()
            ));
        }

        return listItem;
    }
}
