package com.example.tic_toc_toe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
        findViewById(R.id.pvp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup model
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        findViewById(R.id.pvc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setup model
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}