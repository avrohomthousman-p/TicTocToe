package com.example.tic_toc_toe_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.tic_toc_toe_app.Models.MediumDifficultyMovePicker;
import com.example.tic_toc_toe_app.Models.Player;
import com.example.tic_toc_toe_app.Models.TicTocToeGame;
import com.example.tic_toc_toe_app.Models.TicTocToeGameModel;
import com.example.tic_toc_toe_app.R;

import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {
    private boolean computerOpponent;
    private final TextView[][] board = new TextView[3][3];
    private TicTocToeGame gameModel;
    private Timer turnDelayer = new Timer();
    private TextView statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Make a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        computerOpponent = getIntent().getBooleanExtra(MainActivity.OPPONENT_KEY, false);

        setupModel();

        setupBoard();

        setupStatusBar();

        setupBoardClickListeners();
    }


    /**
     * Sets up the tic toc toe game model.
     */
    private void setupModel() {
        gameModel = new TicTocToeGameModel(new MediumDifficultyMovePicker());
        gameModel.startNewGame(
                (computerOpponent ?
                        TicTocToeGame.COMPUTER_OPPONENT :
                        TicTocToeGame.HUMAN_OPPONENT));

    }


    /**
     * Does initialization of status bar.
     */
    private void setupStatusBar(){
        findViewById(R.id.new_game_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });

        this.statusBar = findViewById(R.id.status_bar_text);
        updateStatusBar();
    }


    /**
     * Starts a new game.
     */
    private void startNewGame(){
        //Cancel pending computer turns
        if(computerOpponent){
            turnDelayer.cancel();
            turnDelayer = new Timer();
        }

        gameModel.startNewGame(computerOpponent ? TicTocToeGame.COMPUTER_OPPONENT : TicTocToeGame.HUMAN_OPPONENT);

        clearBoard();

        updateStatusBar();
    }


    /**
     * Removes all text from the game board.
     */
    private void clearBoard(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j].setText(" ");
            }
        }
    }


    /**
     * Updates the status bar to display the correct message.
     */
    private void updateStatusBar(){
        String text;

        if(gameModel.isGameOver()){
            if(gameModel.getWinner() == Player.NONE){
                text = "Game over: Draw";
            }
            else{
                text = "Game over: " + gameModel.getWinner().toString() + " wins!";
            }
        }
        else{
            text = "It is " + gameModel.getCurrentPlayer().toString() + "'s turn.";
        }

        runOnUiThread(() -> {statusBar.setText(text);});
    }


    /**
     * Sets each element of the board to reference the appropriate text view.
     */
    private void setupBoard(){
        GridLayout boardView = findViewById(R.id.board);


        for(int i = 0; i < boardView.getRowCount(); i++){
            for(int j = 0; j < boardView.getColumnCount(); j++){
                board[i][j] = (TextView) boardView.getChildAt(
                        convertMatrixIndexToLinear(i, j, boardView.getRowCount()));

            }
        }
    }


    /**
     * Converts the indexes of a 2 dimensional array into a single index for a linear array
     * with the same total capacity.
     *
     * @param x the X coordinate of the 2 dimensional array.
     * @param y the Y coordinate of the 2 dimensional array.
     * @param rowLength the length of each row in the 2 dimensional array.
     * @return the one dimensional index equivalent of the specified 2 dimensional index.
     */
    private int convertMatrixIndexToLinear(int x, int y, int rowLength){
        return y + (x * rowLength);
    }


    /**
     * Sets up event handlers to handle user clicks on the game board.
     */
    private void setupBoardClickListeners(){
        View.OnClickListener listener = new BoardClickListener();

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j].setOnClickListener(listener);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Manages clicks on the game board.
     */
    private class BoardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(gameModel.isGameOver()){
                return;
            }

            //Find out which part of the board was clicked
            String viewTag = (String) view.getTag();
            int x = Character.getNumericValue(viewTag.charAt(1));
            int y = Character.getNumericValue(viewTag.charAt(2));



            boolean moveWasLegal = gameModel.takeTurn(x, y);

            if(!moveWasLegal){
                return;
            }


            //Update the board to display the move that was just taken.
            board[x][y].setText(    gameModel.getValueAtBoardPosition(x, y).toString()   );


            updateStatusBar();


            if(computerOpponent){
                turnDelayer.schedule(new ComputerTurnTask(), 1500);
            }
        }
    }


    /**
     * Implementation of TimerTask that tells the model to take the computer turn, and then updates
     * the UI based on the results of that turn.
     */
    private class ComputerTurnTask extends TimerTask {
        @Override
        public void run() {
            Point computerMove = gameModel.takeComputerTurn();

            if(computerMove == null){
                return;
            }

            board[computerMove.x][computerMove.y].setText(
                    gameModel.getValueAtBoardPosition(computerMove.x, computerMove.y)
                            .toString());


            updateStatusBar();
        }
    }
}