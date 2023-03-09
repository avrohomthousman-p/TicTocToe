package com.example.tic_toc_toe_app.Models;
import android.graphics.Point;

/**
 * An object that decides the move the computer will make given a certain board state.
 */
public interface ComputerMoveGenerator {
    /**
     * Chooses a move for the computer to take, and returns the Point of the chosen move.
     *
     * @param gameBoard the current board state.
     * @param whoseTurnToTake the player that the computer should pick a move for.
     * @return a point with the indexes of the chosen move.
     */
    Point chooseMove(Player[][] gameBoard, Player whoseTurnToTake);
}
