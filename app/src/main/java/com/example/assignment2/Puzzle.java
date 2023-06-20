package com.example.assignment2;

import java.util.ArrayList;
import java.util.Collections;

public class Puzzle {
//    public static void main(String[] args) {
//        Ctrl ctrl = new Ctrl();
//        ctrl.play();
//    }

    public static final int SIZE = 4;
    private int[][] board;
    private int newRow = -1;
    private int newCol = -1;
    private Ctrl gameCtrl;

    public Puzzle() {
        gameCtrl = new Ctrl(this);
        board = new int[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 16; i++) list.add(i);
        Collections.shuffle(list);

        int num = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = list.get(num);
                num++;
            }
        }
    }

    public boolean playerWon() {
        int num = 1;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (num == SIZE * SIZE) {
                    // Skip the last cell (empty cell)
                    break;
                }
                if (board[row][col] != num++) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validMove(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;

        if (rowIndex - 1 >= 0 && board[rowIndex - 1][colIndex] == 0) {
            newRow = row - 1;
            newCol = col;
            return true;
        } else if (rowIndex + 1 < SIZE && board[rowIndex + 1][colIndex] == 0) {
            newRow = row + 1;
            newCol = col;
            return true;
        } else if (colIndex - 1 >= 0 && board[rowIndex][colIndex - 1] == 0) {
            newRow = row;
            newCol = col - 1;
            return true;
        } else if (colIndex + 1 < SIZE && board[rowIndex][colIndex + 1] == 0) {
            newRow = row;
            newCol = col + 1;
            return true;
        }

        return false;
    }

    public void updateBoard(int row, int col) {
        int temp;
        temp = board[row - 1][col - 1];
        board[row - 1][col - 1] = board[newRow - 1][newCol - 1];
        board[newRow - 1][newCol - 1] = temp;
    }

    public int getRowNum(char row) {
        if (row == 'a')
            return 1;
        else if (row == 'b')
            return 2;
        else if (row == 'c')
            return 3;
        else if (row == 'd')
            return 4;
        else return -1;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setTestBoard(){
        int[][] testBoard = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {0, 13, 14, 15}
        };
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = testBoard[row][col];
            }
        }
    }
}

class Ctrl {
    private Puzzle puzzle;
    public static final int SIZE = 4;
    private int numOfMoves = 0;

    public Ctrl(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void resetNumOfMoves() {
        numOfMoves = 0;
    }

    public void incrementMoves(){
        numOfMoves++;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }
}
