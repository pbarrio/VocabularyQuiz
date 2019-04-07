package com.bikebot.vocabularyquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyWordDialogFragment extends DialogFragment {

    Listener modifyWordListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle args = getArguments();
        builder.setTitle(
                getString(R.string.title_dialog_modify_word) +
                " " +
                args.getString(getString(R.string.param_foreign_word))
        );
        builder.setView(inflater.inflate(R.layout.dialog_modify_word, null));
        builder.setPositiveButton(
                R.string.button_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String translation =
                                ((TextView) getDialog().findViewById(R.id.modifyTranslation))
                                .getText().toString();

                        modifyWordListener.modifyTranslation(translation);
                    }
                }
        );
        builder.setNegativeButton(
                R.string.button_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }
        );
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        EditText translationBox = (EditText) getDialog().findViewById(R.id.modifyTranslation);
        translationBox.setText(getArguments().getString(getString(R.string.param_translation)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            modifyWordListener = (Listener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement the ModifyWord listener");
        }
    }

    public interface Listener {
        void modifyTranslation(String translation);
    }
}
