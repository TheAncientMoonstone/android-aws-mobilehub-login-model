package com.wtmimura.awsandroid;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // variable initialization
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        String who = savedValues.getString("userName", "who?" );

        TextView hello = findViewById(R.id.hello);
        hello.setText("Hello " + who + "!");
    }
}
