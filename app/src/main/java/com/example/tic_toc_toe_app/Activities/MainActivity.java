package com.example.tic_toc_toe_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tic_toc_toe_app.R;

public class MainActivity extends AppCompatActivity {
    static final String OPPONENT_KEY = "computerOpponent";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtonListeners();
    }


    /**
     * Creates the click handlers for the two buttons on the main activity screen.
     */
    private void setupButtonListeners(){
        findViewById(R.id.pvp).setOnClickListener(new ButtonListener());
        findViewById(R.id.pvc).setOnClickListener(new ButtonListener());
    }


    /**
     * Event handler for buttons that bring you to the game activity.
     */
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);

            intent.putExtra(OPPONENT_KEY, view.getId() == R.id.pvc);

            MainActivity.this.startActivity(intent);
        }
    }
}