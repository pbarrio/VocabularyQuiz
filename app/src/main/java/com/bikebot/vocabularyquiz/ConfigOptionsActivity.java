package com.bikebot.vocabularyquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigOptionsActivity extends AppCompatActivity {

    DBAccessor dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_options);

        dba = VocabularyDB.getDB(getApplicationContext()).getDBAccessor();
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

        // TODO: #6 be able to configure the language (now it can only be "hindi")
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
