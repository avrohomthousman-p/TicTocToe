package com.example.tic_toc_toe_app.Models;

/**
 * Implementation of the {@link TicTocToeGame TicTocToeGame interface}.
 */
public class TicTocToeGameModel implements TicTocToeGame {
    private static final int BOARD_ROWS = 3;
    private static final int BOARD_COLS = 3;

    private final Player[][] board = new Player[BOARD_ROWS][BOARD_COLS];
    private Player playerWhoseTurnItIs = Player.NONE;
    private Player winner = Player.NONE;
    private boolean gameRunning = false;


    @Override
    public void startNewGame() {
        resetBoard();
        playerWhoseTurnItIs = Player.X;
        winner = Player.NONE;
        gameRunning = true;
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
    public void takeTurn(int row, int col) {
        if(!gameRunning){
            return;
        }
        if(row < 0 || row >= BOARD_ROWS){
            throw new IndexOutOfBoundsException(String.format("Row %d out of bounds for board length %d.", row, BOARD_ROWS));
        }
        if(col < 0 || col >= BOARD_COLS){
            throw new IndexOutOfBoundsException(String.format("Column %d out of bounds for board length %d.", col, BOARD_COLS));
        }
        if(board[row][col] != null){ //if a player already went there
            return;
        }


        board[row][col] = playerWhoseTurnItIs;


        if(moveWinsGame(row, col)){
            winner = playerWhoseTurnItIs;
            playerWhoseTurnItIs = Player.NONE;
            gameRunning = false;
        }
        else if(boardIsFull()){
            winner = Player.NONE;
            playerWhoseTurnItIs = Player.NONE;
            gameRunning = false;
        }
        else{
            setNextTurn();
        }
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
