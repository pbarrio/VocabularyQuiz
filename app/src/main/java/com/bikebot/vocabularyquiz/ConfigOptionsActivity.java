package com.bikebot.vocabularyquiz;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class ConfigOptionsActivity extends Activity {

    DBAccessor dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_options);

        dba = Room.databaseBuilder(
                getApplicationContext(),
                VocabularyDB.class,
                "vocabulary-db"
        ).allowMainThreadQueries().build().getDBAccessor();
    }

    public void deleteDB(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage(getString(R.string.textbox_confirm_delete_db))
                .setPositiveButton(
                        getString(R.string.button_yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dba.deleteAllWords();
                                Toast msgDialog = Toast.makeText(
                                        getApplicationContext(),
                                        getString(R.string.info_db_deleted),
                                        Toast.LENGTH_LONG);
                                msgDialog.show();

                            }
                        })
                .setNegativeButton(
                        getString(R.string.button_no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .show();
    }

    public void changeLanguage(View view) {

        // TODO: be able to configure the language (now it can only be "hindi")
        dba.insertConfigOption(new ConfigOption(
                getString(R.string.language_learnt),
                "hindi"
        ));
    }

    public void gotoListConfigOptions(View view) {
        Intent intent = new Intent(this, ListConfigOptionsActivity.class);
        startActivity(intent);
    }
}
