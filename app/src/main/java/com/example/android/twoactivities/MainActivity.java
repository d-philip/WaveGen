package com.example.android.twoactivities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchGameHard(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty_button_press", "hard");
        startActivity(intent);
    }

    public void launchGameMedium(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty_button_press", "medium");
        startActivity(intent);
    }

    public void launchGameEasy(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty_button_press", "easy");
        startActivity(intent);
    }

    public void launchInstructions(View view) {
        Intent intent = new Intent(this, InstructionActivity.class);
        startActivity(intent);
    }
}
