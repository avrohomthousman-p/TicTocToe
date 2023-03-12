package com.example.tic_toc_toe_app.Models;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;


/**
 * Implementation of the {@link ComputerMoveGenerator ComputerMoveGenerator interface} that plays
 * the computer on medium difficulty. With this implementation, the computer will first look to
 * complete a row of 3. If it cannot, it will check if it can prevent the player from completing a
 * row of 3. If it cannot do that either it will pick a move at random.
 */
public class MediumDifficultyMovePicker implements ComputerMoveGenerator {
    private static final Random generator = new Random();
    private static final int BOARD_ROWS = 3;
    private static final int BOARD_COLS = 3;


    @Override
    public Point chooseMove(Player[][] gameBoard, Player computer) {
        Player human = (computer == Player.X ? Player.O : Player.X);

        Point chosenMove = null;


        //If there is a move that would let the computer win immediately, make that move.
        chosenMove = checkIfPlayerCanWin(gameBoard, computer);
        if(chosenMove != null){
            return chosenMove;
        }


        //If there is a move that would let the player win on his/her next turn, block that move.
        chosenMove = checkIfPlayerCanWin(gameBoard, human);
        if(chosenMove != null){
            return chosenMove;
        }



        //otherwise just pick something random
        return pickOtherMove(gameBoard);
    }


    /**
     * Checks if there is a move that the specified can take that would result in a win. If there
     * is, it will return a Point containing the coordinates of that move. Otherwise, null is
     * returned.
     *
     * @param gameBoard the current board state.
     * @param whoseTurnToTake the player that the computer is picking a move for.
     * @return the coordinates of the move that can win the game, or null if there is no
     *                  such move.
     */
    private Point checkIfPlayerCanWin(Player[][] gameBoard, Player whoseTurnToTake){
        BoardIterator iterator;
        Point chosenMove;

        //try each column
        for(int i = 0; i < BOARD_COLS; i++){
            iterator = new BoardIterator(gameBoard, new Point(0, 1), i, 0);
            chosenMove = checkLineForWin(gameBoard, whoseTurnToTake, iterator);

            if(chosenMove != null){
                return chosenMove;
            }
        }


        //try each row
        for(int i = 0; i < BOARD_ROWS; i++){
            iterator = new BoardIterator(gameBoard, new Point(1, 0), 0, i);
            chosenMove = checkLineForWin(gameBoard, whoseTurnToTake, iterator);

            if(chosenMove != null){
                return chosenMove;
            }
        }



        //try each diagonal
        iterator = new BoardIterator(gameBoard, new Point(1, 1), 0, 0);
        chosenMove = checkLineForWin(gameBoard, whoseTurnToTake, iterator);
        if(chosenMove != null){
            return chosenMove;
        }


        iterator = new BoardIterator(gameBoard, new Point(1, -1), 0, 2);
        chosenMove = checkLineForWin(gameBoard, whoseTurnToTake, iterator);
        if(chosenMove != null){
            return chosenMove;
        }


        return null;
    }



    /**
     * Checks if there is a winning move in the line that is checked by the board iterator.
     * If there is, the coordinates of that move are returned. If there isn't, null is returned.
     *
     * @param gameBoard the current board state.
     * @param whoseTurnToTake the player that the computer is picking a move for.
     * @param it an iterator that is set to check a column, row, or diagonal for winning moves.
     * @return the coordinates of the winning move, or null if there is no winning move.
     */
    private Point checkLineForWin(Player[][] gameBoard, Player whoseTurnToTake, BoardIterator it){
        int emptySpaces = 0;
        Point indexOfEmptySpace = null;

        while(it.hasNext()){
            Player current = it.next();

            if(current == null){                //nobody went in this spot
                if(emptySpaces == 1){           //if this isn't the first empty spot in this row
                    return null;                //no wins are possible here
                }
                emptySpaces++;
                indexOfEmptySpace = it.getIndex();
            }
            else if (current != whoseTurnToTake) {      //if the opponent has already went in this row
                return null;                            //no wins possible in this row
            }
        }


        return indexOfEmptySpace;
    }


    /**
     * Picks a move at random.
     *
     * @param board the game board.
     * @return a point containing the coordinates of the chosen move. Null if the board is full.
     */
    private Point pickOtherMove(Player[][] board){
        ArrayList<Point> emptySpots = new ArrayList<>();

        //fill the list
        for(int i = 0; i < BOARD_ROWS; i++){
            for(int j = 0; j < BOARD_COLS; j++){
                if(board[i][j] == null){
                    emptySpots.add(new Point(i, j));
                }
            }
        }



        if(emptySpots.isEmpty()){
            return null;
        }


        return emptySpots.get(generator.nextInt(emptySpots.size()));
    }
}
