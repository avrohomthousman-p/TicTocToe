package com.example.tic_toc_toe_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tic_toc_toe_app.Models.MediumDifficultyMovePicker;
import com.example.tic_toc_toe_app.Models.TicTocToeGame;
import com.example.tic_toc_toe_app.Models.TicTocToeGameModel;
import com.example.tic_toc_toe_app.R;

public class GameActivity extends AppCompatActivity {
    private boolean computerOpponent;
    private final TextView[][] board = new TextView[3][3];
    private TicTocToeGame gameModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Make a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        computerOpponent = getIntent().getBooleanExtra(MainActivity.OPPONENT_KEY, false);

        setupModel();

        setupBoard();

        setupBoardClickListeners();
    }


    /**
     * Sets up the tic toc toe game model.
     */
    private void setupModel() {
        gameModel = new TicTocToeGameModel(new MediumDifficultyMovePicker());
        gameModel.startNewGame((computerOpponent ? TicTocToeGame.COMPUTER_OPPONENT : TicTocToeGame.HUMAN_OPPONENT));
    }


    /**
     * Sets each element of the board to reference the appropriate text view.
     */
    private void setupBoard(){
        board[0][0] = findViewById(R.id.square00);
        board[0][1] = findViewById(R.id.square01);
        board[0][2] = findViewById(R.id.square02);

        board[1][0] = findViewById(R.id.square10);
        board[1][1] = findViewById(R.id.square11);
        board[1][2] = findViewById(R.id.square12);

        board[2][0] = findViewById(R.id.square20);
        board[2][1] = findViewById(R.id.square21);
        board[2][2] = findViewById(R.id.square22);
    }


    /**
     * Sets up event handlers to handle user clicks on the game board.
     */
    private void setupBoardClickListeners(){
        //TODO
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}