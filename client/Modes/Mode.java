
package Modes;

import javafx.scene.control.Button;


public abstract class Mode {
    protected Button[][] board;
    protected char computerSymbol;
    protected char playerSymbol;

    public Mode(Button[][] board, char computerSymbol, char playerSymbol) {
        this.board = board;
        this.computerSymbol = computerSymbol;
        this.playerSymbol = playerSymbol;
    }

    public abstract int[] getMove(); 
    
   protected boolean checkWin(char symbol) {
    for (int i = 0; i < 3; i++) {
        //All buttons in the row or column must contain the same symbol, and none of them should be empty.
        if ((board[i][0].getText().length() > 0 && board[i][0].getText().charAt(0) == symbol &&
             board[i][1].getText().length() > 0 && board[i][1].getText().charAt(0) == symbol &&
             board[i][2].getText().length() > 0 && board[i][2].getText().charAt(0) == symbol) ||

            (board[0][i].getText().length() > 0 && board[0][i].getText().charAt(0) == symbol &&
             board[1][i].getText().length() > 0 && board[1][i].getText().charAt(0) == symbol &&
             board[2][i].getText().length() > 0 && board[2][i].getText().charAt(0) == symbol)) {
            return true;
        }
    }    // Check diagonals
    return (board[0][0].getText().length() > 0 && board[0][0].getText().charAt(0) == symbol && 
            board[1][1].getText().length() > 0 && board[1][1].getText().charAt(0) == symbol && 
            board[2][2].getText().length() > 0 && board[2][2].getText().charAt(0) == symbol) ||
           (board[0][2].getText().length() > 0 && board[0][2].getText().charAt(0) == symbol && 
            board[1][1].getText().length() > 0 && board[1][1].getText().charAt(0) == symbol && 
            board[2][0].getText().length() > 0 && board[2][0].getText().charAt(0) == symbol);
}
   
   public boolean isComputerPlayerWon()
   {
    return checkWin(computerSymbol);
   }
    protected boolean isCellEmpty(int row, int col)
    {
        return board[row][col].getText().equals(""); 
    }
    
    protected boolean isBoardFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (isCellEmpty(i, j)) 
                    return false;
            }
        }
        return true;
    }
   
};
