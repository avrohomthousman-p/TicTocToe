package com.example.tic_toc_toe_app.Models;

/**
 * A model for managing the backend of a tic toc toe game
 */
public interface TicTocToeGame {
    /**
     * Starts a new game
     *
     */
    void startNewGame();


    /**
     * Simulates the player whose turn it currently is, taking a turn.
     *
     * @param row the row the player wants to go on.
     * @param col the column the player wants to go on.
     */
    void takeTurn(int row, int col);


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
