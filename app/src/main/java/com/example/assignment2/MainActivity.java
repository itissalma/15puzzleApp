package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons;
    private Button startButton;
    private CheckBox testBox;
    private Puzzle puzzle;
    private Ctrl gameCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[16];
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);
        buttons[6] = findViewById(R.id.button7);
        buttons[7] = findViewById(R.id.button8);
        buttons[8] = findViewById(R.id.button9);
        buttons[9] = findViewById(R.id.button10);
        buttons[10] = findViewById(R.id.button11);
        buttons[11] = findViewById(R.id.button12);
        buttons[12] = findViewById(R.id.button13);
        buttons[13] = findViewById(R.id.button14);
        buttons[14] = findViewById(R.id.button15);
        buttons[15] = findViewById(R.id.button16);
        startButton = findViewById(R.id.startButton);
        testBox = findViewById(R.id.testCheckbox);

        puzzle = new Puzzle();
        gameCtrl = new Ctrl(puzzle);
        updateButtons(puzzle.getBoard());

        for (int i = 0; i < 16; i++) {
            final int index = i + 1;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonClick(index);
                }
            });
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartClick();
            }
        });
    }

    private void onButtonClick(int index) {
        if (puzzle != null) {
            int row = (index - 1) / Puzzle.SIZE + 1;
            int col = (index - 1) % Puzzle.SIZE + 1;

            if (puzzle.validMove(row, col)) {
                puzzle.updateBoard(row, col);
                gameCtrl.incrementMoves(); // Increment the move count

                if (puzzle.playerWon()) {
                    showWinningDialog(gameCtrl.getNumOfMoves());
                    gameCtrl.resetNumOfMoves();
                }
            }
            updateButtons(puzzle.getBoard());
        }
    }

    private void onStartClick() {
        enableButtons();
        if (testBox.isChecked()) {
            // Display predefined test board
            puzzle.setTestBoard();
        } else {
            // Start a new game
            puzzle = new Puzzle();
        }
        gameCtrl = new Ctrl(puzzle);

        // Update UI with initial board state
        updateButtons(puzzle.getBoard());
    }

    private void updateButtons(int[][] board) {
        for (int row = 0; row < Puzzle.SIZE; row++) {
            for (int col = 0; col < Puzzle.SIZE; col++) {
                int index = row * Puzzle.SIZE + col;
                int value = board[row][col];
                buttons[index].setText(value != 0 ? String.valueOf(value) : "");
            }
        }
    }

    private void showWinningDialog(int numOfMoves) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!");
        builder.setMessage("You won in " + numOfMoves + " moves.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                disableButtons();
            }
        });
        builder.show();
    }
    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void enableButtons() {
        for (Button button : buttons) {
            button.setEnabled(true);
        }
    }

}
