package com.example.tic_toc_toe_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tic_toc_toe_app.R;

public class GameActivity extends AppCompatActivity {
    private final TextView[][] board = new TextView[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }


    private void setupBoard(){
        final String id = "square";

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                //board[i][j] = findViewById();
            }
        }
    }
}