package com.example.tic_toc_toe_app.Models;

import android.graphics.Point;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An implementation if iterator that iterates over the game board moving in a single
 * direction. This direction is specified by the Point argument in the constructor. This
 * class takes the game board as a read only parameter, so no deep copy is needed.
 */
public class BoardIterator implements Iterator<Player> {
    private final Player[][] board;
    private final Point shift;
    private int x;
    private int y;
    private boolean calledNext = false;



    /**
     * Constructs an iterator over the specified board that moves in the direction specified by the
     * Point argument, until it reaches the end. The iterator moves to the next spot by incrementing
     * its internal coordinates with the X and Y values provided in the Point argument. So a Point
     * containing the values (+1, +1), and starting positions of 0,0 will cause the iterator to move
     * south east to position 1,1.
     *
     * @param board the game board that we are iterating over.
     * @param shiftDirection the direction the iterator should move.
     * @param startingX the x coordinate of the first value that should be returned by the iterator.
     * @param startingY the Y coordinate of the first value that should be returned by the iterator.
     */
    public BoardIterator(Player[][] board, Point shiftDirection, int startingX, int startingY) {
        this.board = board;
        this.shift = shiftDirection;
        this.x = startingX;
        this.y = startingY;
    }



    @Override
    public boolean hasNext() {
        return this.x < board.length && this.y < board[x].length;
    }



    @Override
    public Player next() {
        calledNext = true;
        Player currentValue = board[this.x][this.y];

        this.x += shift.x;
        this.y += shift.y;

        return currentValue;
    }


    /**
     * Gets the coordinates of the element most recently returned by the iterators next method.
     *
     * @return the coordinates of the element just returned by a call to next.
     * @throws NoSuchElementException if this method was called before any call to next was made.
     */
    public Point getIndex(){
        if(!calledNext){
            throw new NoSuchElementException();
        }

        return getPreviousPosition();
    }


    /**
     * Creates and returns a point with the coordinates of the element last returned by a call to
     * next.
     *
     * @return a point with the coordinates of the last element returned by next.
     */
    private Point getPreviousPosition(){
        return new Point(x - shift.x, y - shift.y);
    }
}
