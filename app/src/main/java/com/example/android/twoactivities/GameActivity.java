package com.example.android.twoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //If easy button is pressed create easy graph
        //If medium button is pressed create medium graph
        //If hard button is pressed create hard graph

        String difficulty = getIntent().getStringExtra("difficulty_button_press");

        switch (difficulty)
        {
            case "easy":
                Log.i(LOG_TAG, "Easy Button clicked!");
                break;

            case "medium":
                Log.i(LOG_TAG, "Medium Button clicked!");
                break;

            case "hard":
                Log.i(LOG_TAG, "Hard Button clicked!");
                break;
        }

    }

    public void launchPostGame(View view) {
        Intent intent = new Intent(this, PostGame.class);
        startActivity(intent);
    }
}
