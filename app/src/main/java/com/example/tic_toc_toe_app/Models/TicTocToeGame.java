package com.example.tic_toc_toe_app.Models;


import android.graphics.Point;

/**
 * A model for managing the backend of a tic toc toe game
 */
public interface TicTocToeGame {
    public static final int HUMAN_OPPONENT = 0;
    public static final int COMPUTER_OPPONENT = 1;


    /**
     * Starts a new game
     *
     * @param opponentType the opponent that the game will be played against. 0 for a human
     *                     opponent and 1 for a computer opponent.
     */
    void startNewGame(int opponentType);


    /**
     * Simulates the player whose turn it currently is, taking a turn. In a player vs computer
     * game, then this method will take the turn of the human player and the turn of the
     * computer player should be handled by a call to {@link TicTocToeGame#takeComputerTurn
     * takeComputerTurn}.
     *
     * @param row the row the player wants to go on.
     * @param col the column the player wants to go on.
     * @return true if the chosen move was legal, and false otherwise.
     */
    boolean takeTurn(int row, int col);


    /**
     * Simulates the computer turn in a player vs computer game. Throws an Exception in a
     * player vs player game.
     *
     * @return a point containing the coordinates of the move taken by the computer, or null if the
     * game is over.
     *
     * @throws RuntimeException if this method was called in a player vs player game.
     */
    Point takeComputerTurn();


    /**
     * Gets the player whose turn it is.
     *
     * @return the player whose turn it is, or NONE if the game is over.
     */
    Player getCurrentPlayer();


    /**
     * Returns the winner of the game.
     *
     * @return the winner of the game or NONE if the game is still going or ended in a draw.
     */
    Player getWinner();


    /**
     * Returns true if the game is over and false otherwise.
     *
     * @return true if the game is over and false otherwise.
     */
    boolean isGameOver();


    /**
     * Returns the player who has gone in the specified spot on the board, or NONE if nobody
     * has gone there.
     *
     * @param row the row the position is on.
     * @param col the column the position is on.
     * @return the value at that position.
     */
    Player getValueAtBoardPosition(int row, int col);
}
