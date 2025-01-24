/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modes;

import java.util.Random;
import javafx.scene.control.Button;

/**
 *
 * @author Ziad
 */
public class Easy extends Mode {
    private Random random = new Random();

    public Easy(Button[][] board, char computerSymbol, char playerSymbol) {
       
        super(board, computerSymbol, playerSymbol);
         
    }

    @Override
    public int[] getMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!isCellEmpty(row,col));
        return new int[]{row, col};
    }
}