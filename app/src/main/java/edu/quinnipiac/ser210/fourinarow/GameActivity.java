package edu.quinnipiac.ser210.fourinarow;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

/**
 * FourInARow
 * @author MacArthur Mills
 * @date 2/20/2022
 */

public class GameActivity extends AppCompatActivity {

    // instance variables
    FourInARow FIRboard;
    TextView winMessage;
    Button resetButton;
    boolean stillPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // set intances variable
        FIRboard = new FourInARow();
        winMessage = (TextView) findViewById(R.id.winMessage);
        resetButton = (Button) findViewById(R.id.resetButton);
        stillPlaying = true;

        // disable reset button
        resetButton.setEnabled(false);

        // setting player's name
        String playerName = getIntent().getStringExtra("playerName");
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(playerName);

        // setting turn to player name
        TextView playerTurnLabel = (TextView) findViewById(R.id.whoseTurn);
        playerTurnLabel.setText(playerName + "'s Turn");

        // retrieves screen width and height
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        // creates 36 buttons on game board
        GridLayout thisGridLayout = (GridLayout) findViewById(R.id.gameBoard);
        int col = 0;
        for(int i = 0; i<36; i++) {
            // parameter object that sets parameters for buttons -- only needed for setting width and height
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();

            // sets width and height of buttons relative to screen size
            param.width = width / 6 - 3;
            param.height = width / 6 - 3;

            // button attributes
            Button button = new Button(this);
            button.setLayoutParams(param);
            button.setId(i);
            button.setBackgroundResource(R.drawable.black_square);

            // button click functionality
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // sets turn text to computer ----- computer makes move instantaneously so impossible to see text ever display this
                    playerTurnLabel.setText("Computer's Turn");

                    // update button image
                    button.setBackgroundResource(R.drawable.blue_o);

                    // disable button
                    button.setEnabled(false);

                    // player set move
                    FIRboard.setMove(FourInARow.BLUE, button.getId());

                    // check game status
                    checkGameStatus();

                    // computer move
                    if(stillPlaying) {
                        int computerMove = FIRboard.getComputerMove();
                        FIRboard.setMove(FourInARow.RED, computerMove);
                        findViewById(computerMove).setBackgroundResource(R.drawable.red_x);
                        findViewById(computerMove).setEnabled(false);
                        playerTurnLabel.setText(playerName + "'s Turn");

                        // check game status
                        checkGameStatus();
                    }
                }
            });

            // adds button to gridlayout
            thisGridLayout.addView(button);
        }
    }

    // reset button functionality
    public void onResetButton(View view) {
        // clears game board and enables buttons
        FIRboard.clearBoard();
        for(int i = 0; i < 36; i++){
            findViewById(i).setEnabled(true);
            findViewById(i).setBackgroundResource(R.drawable.black_square);
        }

        // disables reset button
        findViewById(R.id.resetButton).setEnabled(false);

        // clears win message
        winMessage.setText("");

        // resets stillPlaying boolean
        stillPlaying = true;
    }

    // check game status
    public void checkGameStatus(){
        int gameStatus = FIRboard.checkForWinner();
        // if player wins
        if(gameStatus == FourInARow.BLUE_WON) {
            winMessage.setText("You won!");
            resetButton.setEnabled(true);
            disableButtons();
            stillPlaying = false;
        }
        // if computer wins
        else if(gameStatus == FourInARow.RED_WON) {
            winMessage.setText("You lost!");
            resetButton.setEnabled(true);
            disableButtons();
            stillPlaying = false;
        }
        // if it's a tie
        else if(gameStatus == FourInARow.TIE) {
            winMessage.setText("It's a tie!");
            resetButton.setEnabled(true);
            disableButtons();
            stillPlaying = false;
        }
    }

    // disables all buttons
    public void disableButtons(){
        for(int i = 0; i < 36; i++){
            findViewById(i).setEnabled(false);
        }
    }
}
