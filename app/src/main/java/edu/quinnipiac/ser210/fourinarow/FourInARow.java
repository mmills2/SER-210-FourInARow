package edu.quinnipiac.ser210.fourinarow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Random;

/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * @date 2/12/2022
 */
public class FourInARow implements IGame {

    // The game board and the game status
    private static final int ROWS = 6, COLS = 6; // number of rows and columns
    private int[][] board = new int[ROWS][COLS]; // game board in 2D array
    private boolean red;
    private Random random;

    /**
     * clear board and set current player
     */
    public FourInARow() {

        red = false;
        random = new Random();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = EMPTY;
            }
        }

    }

    public boolean checkIfEmpty(int location) {
        int r = location / 6;
        int c = location - (6 * r);
        return (board[r][c] == EMPTY);
    }

    @Override
    public void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = EMPTY;
            }
        }
    }

    @Override
    public void setMove(int player, int location) {
        int r = location / 6;
        int c = location - (6 * r);
        board[r][c] = player;
    }

    @Override
    public int getComputerMove() {
        // first move
        if (!red) {
            int firstMove = random.nextInt(36);
            int row = firstMove / 6;
            int col = firstMove - (6 * row);
            while (board[row][col] == BLUE) { // if random move happens to be players first move
                firstMove = random.nextInt(36);
            }
            red = true;
            return firstMove;
        }


        // create a new board that rates each cell which ever cell has the highest rating the computer will put a piece there
        // if multiple cells with highest rating then a random one of the highest rated cells will be chosen
        int highestValue = 0;
        ArrayList<Integer> bestMoves = new ArrayList<Integer>();
        int[][] ratedBoard = new int[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == EMPTY) {
                    ratedBoard[r][c] += 1;
                }

                // if red check increase rating of adjacent tiles
                if (board[r][c] == RED) {
                    int i = 0;

                    // top
                    i = 0;
                    if (r - 3 >= 0 || (r - 2 >= 0 && r + 1 < ROWS) || (r - 1 >= 0 && r + 2 < ROWS)) {
                        while (board[r - i][c] == RED && r - i > 0) {
                            i++;
                            if (board[r - i][c] == EMPTY) {
                                ratedBoard[r - i][c] += i;
                            }
                        }
                    }

                    // bottom
                    i = 0;
                    if (r + 3 < ROWS || (r + 2 < ROWS && r - 1 >= 0) || (r + 1 < ROWS && r - 2 >= 0)) {
                        while (board[r + i][c] == RED && r + i < ROWS - 1) {
                            i++;
                            if (board[r + i][c] == EMPTY) {
                                ratedBoard[r + i][c] += i;
                            }
                        }
                    }

                    // right
                    i = 0;
                    if (c + 3 < COLS || (c + 2 < COLS && c - 1 >= 0) || (c + 1 < COLS && c - 2 >= 0)) {
                        while (board[r][c + i] == RED && c + i < COLS - 1) {
                            i++;
                            if (board[r][c + i] == EMPTY) {
                                ratedBoard[r][c + i] += i;
                            }
                        }
                    }

                    // left
                    i = 0;
                    if (c - 3 >= 0 || (c - 2 >= 0 && c + 1 < COLS) || (c - 1 >= 0 && c + 2 < COLS)) {
                        while (board[r][c - i] == RED && c - i > 0) {
                            i++;
                            if (board[r][c - i] == EMPTY) {
                                ratedBoard[r][c - i] += i;
                            }
                        }
                    }

                    // top left
                    i = 0;
                    if ((r - 3 >= 0 && c - 3 >= 0) || (r - 2 >= 0 && c - 2 >= 0 && r + 1 < ROWS && c + 1 < COLS) || (r - 1 >= 0 && c - 1 >= 0 && r + 2 < ROWS && c + 2 < COLS)) {
                        while (board[r - i][c - i] == RED && r - i > 0 && c - i > 0) {
                            i++;
                            if (board[r - i][c - i] == EMPTY) {
                                ratedBoard[r - i][c - i] += i;
                            }
                        }
                    }

                    // top right
                    i = 0;
                    if ((r - 3 >= 0 && c + 3 < COLS) || (r - 2 >= 0 && c + 2 < COLS && r + 1 < ROWS && c - 1 >= 0) || (r - 1 >= 0 && c + 1 < COLS && r + 2 < ROWS && c - 2 >= 0)) {
                        while (board[r - i][c + i] == RED && r - i > 0 && c + i < COLS - 1) {
                            i++;
                            if (board[r - i][c + i] == EMPTY) {
                                ratedBoard[r - i][c + i] += i;
                            }
                        }
                    }

                    // bottom left
                    i = 0;
                    if ((r + 3 < ROWS && c - 3 >= 0) || (r + 2 < ROWS && c - 2 >= 0 && r - 1 >= 0 && c + 1 < COLS) || (r + 1 < ROWS && c - 1 >= 0 && r - 2 >= 0 && c + 2 < COLS)) {
                        while (board[r + i][c - i] == RED && r + i < ROWS - 1 && c - i > 0) {
                            i++;
                            if (board[r + i][c - i] == EMPTY) {
                                ratedBoard[r + i][c - i] += i;
                            }
                        }
                    }

                    // bottom right
                    i = 0;
                    if ((r + 3 < ROWS && c + 3 < COLS) || (r + 2 < ROWS && c + 2 < COLS && r - 1 >= 0 && c - 1 >= 0) || (r + 1 < ROWS && c + 1 < COLS && r - 2 >= 0 && c - 2 >= 0)) {
                        while (board[r + i][c + i] == RED && r + i < ROWS - 1 && c + i < COLS - 1) {
                            i++;
                            if (board[r + i][c + i] == EMPTY) {
                                ratedBoard[r + i][c + i] += i;
                            }
                        }
                    }
                }

                // if blue check if there are two in a row and block
                if (board[r][c] == BLUE) {

                    // top
                    int i = 0;
                    while (board[r - i][c] == BLUE && r - i > 0) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r - i][c] += i;
                        }
                    }

                    // bottom
                    i = 0;
                    while (board[r + i][c] == BLUE && r + i < ROWS - 1) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r + i][c] += i;
                        }
                    }

                    // right
                    i = 0;
                    while (board[r][c + i] == BLUE && c + i < COLS - 1) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r][c + i] += i;
                        }
                    }

                    // left
                    i = 0;
                    while (board[r][c - i] == BLUE && c - i > 0) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r][c - i] += i;
                        }
                    }

                    // top left
                    i = 0;
                    while (board[r - i][c - i] == BLUE && r - i > 0 && c - i > 0) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r - i][c - i] += i;
                        }
                    }

                    // top right
                    i = 0;
                    while (board[r - i][c + i] == BLUE && r - i > 0 && c + i < COLS - 1) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r - i][c + i] += i;
                        }
                    }

                    // bottom left
                    i = 0;
                    while (board[r + i][c - i] == BLUE && r + i < ROWS - 1 && c - i > 0) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r + i][c - i] += i;
                        }
                    }

                    // bottom right
                    i = 0;
                    while (board[r + i][c + i] == BLUE && r + i < ROWS - 1 && c + i < COLS - 1) {
                        i++;
                        if (i > 1) {
                            ratedBoard[r + i][c + i] += i;
                        }
                    }
                }

            }
        }
        // rates all non empty cells as 0 and finds the highest value on rated board
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] != EMPTY) {
                    ratedBoard[r][c] = 0;
                }
                if (ratedBoard[r][c] > highestValue) {
                    highestValue = ratedBoard[r][c];
                }
            }
        }
        // adds all cells with hgihest rating to list
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (ratedBoard[r][c] == highestValue) {
                    bestMoves.add((r * 6) + c);
                }

            }
        }

        // returns random cell from list of highest rated cells
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    @Override
    public int checkForWinner() {
        int empty = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == EMPTY) {
                    empty++;
                }

                // red win check
                if (board[r][c] == RED) {

                    // top
                    int i = 0;
                    while (r - i >= 0 && board[r - i][c] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }

                    // bottom
                    i = 0;
                    while (r + i < ROWS && board[r + i][c] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }

                    // right
                    i = 0;
                    while (c + i < COLS && board[r][c + i] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }

                    // left
                    i = 0;
                    while (c - i >= 0 && board[r][c - i] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }

                    // top left
                    i = 0;
                    while (r - i >= 0 && c - i >= 0 && board[r - i][c - i] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }

                    // top right
                    i = 0;
                    while (r - i >= 0 && c + i < COLS && board[r - i][c + i] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }

                    // bottom left
                    i = 0;
                    while (r + i < ROWS && c - i >= 0 && board[r + i][c - i] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }

                    // bottom right
                    i = 0;
                    while (r + i < ROWS && c + i < COLS && board[r + i][c + i] == RED) {
                        if (i == 3) {
                            return RED_WON;
                        }
                        i++;
                    }
                }

                // blue win check
                if (board[r][c] == BLUE) {

                    // top
                    int i = 0;
                    while (r - i >= 0 && board[r - i][c] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }

                    // bottom
                    i = 0;
                    while (r + i < ROWS && board[r + i][c] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }

                    // right
                    i = 0;
                    while (c + i < COLS && board[r][c + i] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }

                    // left
                    i = 0;
                    while (c - i >= 0 && board[r][c - i] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }

                    // top left
                    i = 0;
                    while (r - i >= 0 && c - i >= 0 && board[r - i][c - i] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }

                    // top right
                    i = 0;
                    while (r - i >= 0 && c + i < COLS && board[r - i][c + i] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }

                    // bottom left
                    i = 0;
                    while (r + i < ROWS && c - i >= 0 && board[r + i][c - i] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }

                    // bottom right
                    i = 0;
                    while (r + i < ROWS && c + i < COLS && board[r + i][c + i] == BLUE) {
                        if (i == 3) {
                            return BLUE_WON;
                        }
                        i++;
                    }
                }

            }
        }

        if (empty == 0) {
            return TIE;
        }
        return PLAYING;
    }
}