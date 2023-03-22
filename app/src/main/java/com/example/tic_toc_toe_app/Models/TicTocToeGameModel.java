package com.example.tic_toc_toe_app.Models;

import android.graphics.Point;

/**
 * Implementation of the {@link TicTocToeGame TicTocToeGame interface}.
 */
public class TicTocToeGameModel implements TicTocToeGame {
    private static final int BOARD_ROWS = 3;
    private static final int BOARD_COLS = 3;

    private final Player[][] board = new Player[BOARD_ROWS][BOARD_COLS];
    private final ComputerMoveGenerator computerMoveGenerator;
    private Player playerWhoseTurnItIs = Player.NONE;
    private Player winner = Player.NONE;
    private boolean gameRunning = false;
    private boolean computerOpponent;

    public TicTocToeGameModel(ComputerMoveGenerator computerMoveGenerator) {
        this.computerMoveGenerator = computerMoveGenerator;
    }


    @Override
    public void startNewGame(int opponentType) {
        setOpponentType(opponentType);
        resetBoard();
        playerWhoseTurnItIs = Player.X;
        winner = Player.NONE;
        gameRunning = true;
    }


    /**
     * Sets the opponent for the new game to be either computer or human, depending on the input.
     * If the input attempts to select any other option, an exception is thrown.
     *
     * @param opponentType the desired type of opponent - computer or human.
     */
    private void setOpponentType(int opponentType){
        if(opponentType == HUMAN_OPPONENT){
            computerOpponent = false;
        }
        else if(opponentType == COMPUTER_OPPONENT){
            computerOpponent = true;
        }
        else{
            throw new IllegalArgumentException(
                    String.format("%d is not a valid opponent code.", opponentType));
        }
    }


    /**
     * Clears the contents of the game board.
     */
    private void resetBoard() {
        for(int i = 0; i < BOARD_ROWS; i++){
            for(int j = 0; j < BOARD_COLS; j++){
                board[i][j] = null;
            }
        }
    }


    @Override
    public boolean takeTurn(int row, int col) {
        if(!gameRunning){
            return false;
        }
        if(computerOpponent && playerWhoseTurnItIs != Player.X){
            return false;
        }
        if(row < 0 || row >= BOARD_ROWS){
            throw new IndexOutOfBoundsException(String.format("Row %d out of bounds for board length %d.", row, BOARD_ROWS));
        }
        if(col < 0 || col >= BOARD_COLS){
            throw new IndexOutOfBoundsException(String.format("Column %d out of bounds for board length %d.", col, BOARD_COLS));
        }
        if(board[row][col] != null){ //if a player already went there
            return false;
        }


        //Mark the chosen spot on the board with an X or O
        board[row][col] = playerWhoseTurnItIs;


        //Handel game over or next turn
        if(moveWinsGame(row, col)){
            endGame(playerWhoseTurnItIs);
        }
        else if(boardIsFull()){
            endGame(Player.NONE);
        }
        else{
            setNextTurn();
        }

        return true;
    }


    /**
     * Checks if the current player moving in the specified position cause that player to win.
     *
     * @param row the row the player chose.
     * @param col the column the player chose.
     * @return true if the player wins by going in the specified location, and false otherwise.
     */
    private boolean moveWinsGame(int row, int col) {
        return checkRowForWin(row) || checkColumnForWin(col) || checkDiagonalsForWin(row, col);
    }


    /**
     * Checks if a player won the game by having three of the same symbols in the specified row.
     *
     * @param row the row to check for wins.
     * @return true if there is a win in the specified row, and false otherwise.
     */
    private boolean checkRowForWin(int row){
        return board[row][0] != null
                && board[row][0] == board[row][1]
                && board[row][1] == board[row][2];
    }


    /**
     * Checks if a player won the game by having three of the same symbols in the specified column.
     *
     * @param col the column to check for wins.
     * @return true if there is a win in the specified column, and false otherwise.
     */
    private boolean checkColumnForWin(int col){
        return board[0][col] != null
                && board[0][col] == board[1][col]
                && board[1][col] == board[2][col];
    }


    /**
     * Checks if a player going in the specified board location triggered a win on
     * a diagonal on the board.
     *
     * @param row the row the player went in.
     * @param col the column the player went in.
     * @return true if moving in the specified location triggered a diagonal win.
     */
    private boolean checkDiagonalsForWin(int row, int col) {
        //All positions in the diagonals have an even sum when adding their row and column number.
        //So if the position given is not in a diagonal, there is no diagonal win.
        if((row + col) % 2 == 1){
            return false;
        }


        //This check ensures that this method does not return TRUE if all 3 values in a diagonal are null.
        if(board[1][1] == null){
            return false;
        }


        return (board[0][0] == board[1][1] && board[1][1] == board[2][2])             //Win in first diagonal
                || (board[0][2] == board[1][1] && board[1][1] == board[2][0]);        //Win in second diagonal
    }


    /**
     * Checks if the board is full and there are no more places to go.
     *
     * @return true if the board is full.
     */
    private boolean boardIsFull(){
        for(int i = 0; i < BOARD_ROWS; i++){
            for(int j = 0; j < BOARD_COLS; j++){
                if(board[i][j] == null){
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Marks the local variables to indicate that the game is no longer running.
     *
     * @param winner The player that won (None on a tie).
     */
    private void endGame(Player winner){
        this.winner = winner;
        playerWhoseTurnItIs = Player.NONE;
        gameRunning = false;
    }


    @Override
    public Point takeComputerTurn(){
        if(!gameRunning){
            return null;
        }
        if(!computerOpponent){
            throw new RuntimeException("Cannot take turn for computer in a player vs player game.");
        }
        if(playerWhoseTurnItIs != Player.O){ //computer is always O
            return null;
        }


        Point p = this.computerMoveGenerator.chooseMove(copyBoard(), Player.O);

        board[p.x][p.y] = Player.O;



        //Check for game over
        if(moveWinsGame(p.x, p.y)){
            endGame(Player.O);
        }
        else if(boardIsFull()){
            endGame(Player.NONE);
        }
        else{
            setNextTurn();
        }


        return p;
    }


    /**
     * Makes a deep copy of the game board.
     *
     * @return a deep copy of the game board.
     */
    private Player[][] copyBoard(){
        Player[][] copy = new Player[BOARD_ROWS][BOARD_COLS];

        for (int i = 0; i < BOARD_ROWS; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, BOARD_COLS);
        }

        return copy;
    }


    /**
     * Sets the next turn to be the player who did not just go.
     */
    private void setNextTurn() {
        if(playerWhoseTurnItIs == Player.X){
            playerWhoseTurnItIs = Player.O;
        }
        else if(playerWhoseTurnItIs == Player.O){
            playerWhoseTurnItIs = Player.X;
        }
    }


    @Override
    public Player getCurrentPlayer() {
        return playerWhoseTurnItIs;
    }


    @Override
    public Player getWinner(){
        return this.winner;
    }


    @Override
    public boolean isGameOver() {
        return !gameRunning;
    }


    @Override
    public Player getValueAtBoardPosition(int row, int col) {
        if(row < 0 || row >= BOARD_ROWS){
            throw new IndexOutOfBoundsException(String.format("Row %d out of bounds for board length %d.", row, BOARD_ROWS));
        }
        if(col < 0 || col >= BOARD_COLS){
            throw new IndexOutOfBoundsException(String.format("Column %d out of bounds for board length %d.", col, BOARD_COLS));
        }


        return board[row][col];
    }


    /**
     * Prints the game board to the console. This is just for testing.
     */
    void printGrid(){
        for(int i = 0; i < BOARD_ROWS; i++){
            System.out.print("|");
            for(int j = 0; j < BOARD_COLS; j++){
                System.out.print((board[i][j] == null ? " " : board[i][j]) + "|");
            }
            System.out.println();
        }
    }
}
