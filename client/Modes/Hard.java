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

public class Hard extends Mode {
      public Hard(Button[][] board, char computerSymbol, char playerSymbol) {
        super(board, computerSymbol, playerSymbol);
    }

      public static  final int Max_Depth=9;
     private int minimax(int depth,boolean isMaximizing) { //The isMaximizing flag indicates whether it is the maximizing player's (the computer's) turn or the minimizing player's (the player’s) turn.
        if (checkWin(computerSymbol))
            return 1;
        if (checkWin(playerSymbol)) 
            return -1;
        if (isBoardFull()||depth==Max_Depth) 
            return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE; // it is the maximizing player's (computer’s) turn or the minimizing player’s (player’s) turn. 

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isCellEmpty(i, j)) {
                    board[i][j].setText(isMaximizing ? String.valueOf(computerSymbol) : String.valueOf(playerSymbol));
                    int score = minimax(depth+1, !isMaximizing); //so it alternates between the computer’s and the player’s turn)
                    board[i][j].setText("");
                    bestScore = isMaximizing ? Math.max(bestScore, score) : Math.min(bestScore, score);
                }
            }
        }
        return bestScore;
    }
     
     
    @Override
    public int[] getMove() {
        //This value is used when you want to initialize a variable that will later store the maximum value in a comparison process.
        //When you set a variable to Integer.MIN_VALUE,
        //you are saying, "I'm starting with the smallest value, and later, I will update this value to something larger when I find a better option.
        int bestScore = Integer.MIN_VALUE; //constant that represents the smallest possible value an int can have. Its value is -2,147,483,648
        
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isCellEmpty(i, j)) {
                    board[i][j].setText(String.valueOf(computerSymbol));
                    int score = minimax(0,false);
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