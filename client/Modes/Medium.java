/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import javafx.scene.control.Button;

/**
 *
 * @author Maryam Muhammad
 */

public class Medium extends Mode {
    private static final int MAX_DEPTH = 2;  

    public Medium(Button[][] board, char computerSymbol, char playerSymbol) {
        super(board, computerSymbol, playerSymbol);
    }

    private int minimax(int depth, boolean isMaximizing) {//isMaximizing: true if it's the AI's turn, false if it's the player's turn.
        //This recursive method simulates possible moves and returns a score based on the best outcome for the AI.
        if (checkWin(computerSymbol)) 
            return 1;
        if (checkWin(playerSymbol)) 
            return -1;
        if (isBoardFull()) 
            return 0;
        if (depth == MAX_DEPTH)  //Tracks how deep the recursion has gone. 
            return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//Integer.MIN_VALUE (-2,147,483,648) → The lowest possible integer.
//Integer.MAX_VALUE (2,147,483,647) → The highest possible integer.
//The AI wants to maximize its score, so it starts with the lowest possible value(Integer.MIN_VALUE). 
//This ensures that any valid move will be better than the starting value.
/*
If it's the AI's turn (isMaximizing = true), bestScore starts at Integer.MIN_VALUE because:
The AI is searching for the highest possible score.
Any possible move will be better than MIN_VALUE, so the AI keeps updating bestScore with better moves.
If it's the Player's turn (isMaximizing = false), bestScore starts at Integer.MAX_VALUE because:
The Player is trying to minimize AI's score.
Any move the Player makes will be lower than MAX_VALUE, so the Player keeps updating bestScore with worse moves for AI.
✅ AI starts with MIN_VALUE so it can only improve from there.
✅ The AI picks the move that gives the highest score.
✅ The Player starts with MAX_VALUE so it can only decrease from there.
✅ This ensures that both AI and Player play optimally.✅ AI starts with MIN_VALUE so it can only improve from there.
✅ The AI picks the move that gives the highest score.
✅ The Player starts with MAX_VALUE so it can only decrease from there.
✅ This ensures that both AI and Player play optimally.
*/
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isCellEmpty(i, j)) {
                    board[i][j].setText(isMaximizing ? String.valueOf(computerSymbol) : String.valueOf(playerSymbol));
                    int score = minimax(depth + 1, !isMaximizing); //it allows the AI to simulate future moves and decide the best possible move.
                    board[i][j].setText("");
                    bestScore = isMaximizing ? Math.max(bestScore, score) : Math.min(bestScore, score);
                }
            }
        }
        return bestScore;
    }

    @Override
    public int[] getMove() {
        if (checkWin(computerSymbol) || checkWin(playerSymbol)) {
            return new int[]{-1, -1}; //If there is already a winner, return {-1, -1}, meaning no valid moves left.
        }

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isCellEmpty(i, j)) {
                    board[i][j].setText(String.valueOf(computerSymbol));

                    if (checkWin(computerSymbol)) {
                        return new int[]{i, j}; 
                    }

                    int score = minimax(0, true); 
                    board[i][j].setText(""); 

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }

        return bestMove;
    }
}
/*
public class Medium extends Mode {
    private static final int MAX_DEPTH = 3; 

    public Medium(Button[][] board, char computerSymbol, char playerSymbol) {
        super(board, computerSymbol, playerSymbol);
    }

    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin(computerSymbol)) 
            return 1;
        if (checkWin(playerSymbol)) 
            return -1;
        if (isBoardFull()) 
            return 0;
        if (depth == MAX_DEPTH)  
            return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isCellEmpty(i, j)) {
                    board[i][j].setText(isMaximizing ? String.valueOf(computerSymbol) : String.valueOf(playerSymbol));
                    int score = minimax(depth + 1, !isMaximizing); // Increase depth with each recursive call
                    board[i][j].setText("");
                    bestScore = isMaximizing ? Math.max(bestScore, score) : Math.min(bestScore, score);
                }
            }
        }
        return bestScore;
    }

    @Override
public int[] getMove() {
    if (checkWin(computerSymbol) || checkWin(playerSymbol)) {
        return new int[]{-1, -1}; 
    }

    int bestScore = Integer.MIN_VALUE;
    int[] bestMove = new int[]{-1, -1};

    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
            if (isCellEmpty(i, j)) {
                board[i][j].setText(String.valueOf(computerSymbol));
                
                if (checkWin(computerSymbol)) {
                    return new int[]{i, j}; 
                }

                int score = minimax(0, false); 
                board[i][j].setText(""); 

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = new int[]{i, j};
                }
            }
        }
    }

    return bestMove;
}
}
*/