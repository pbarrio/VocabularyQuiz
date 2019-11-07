package com.bikebot.vocabularyquiz;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_msg);

        String msg = getIntent().getExtras().getString(getString(R.string.param_error));
        TextView errorBox = (TextView) findViewById(R.id.error_box);
        errorBox.setText(msg);
    }
}
