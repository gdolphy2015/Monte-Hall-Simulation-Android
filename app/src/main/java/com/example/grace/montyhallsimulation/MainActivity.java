package com.example.grace.montyhallsimulation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RandomDoorGenerator rdg;
    boolean doorHasBeenSelected = false;
    Door userSelectedDoor;
    Door removedDoor;
    int totalGames = 0;
    int switchWins = 0;
    int switchTotal = 0;
    int stayWins = 0;
    int stayTotal = 0;
    boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //used to generate which door (A, B , or C) is the prize door and the other 2 will be the
        //non desired doors.
        rdg = new RandomDoorGenerator();
    }

    public void newGame(View view) {
        Button newGame = findViewById(R.id.newGameButton);
        newGame.setVisibility(View.INVISIBLE);

        rdg = new RandomDoorGenerator();
        doorHasBeenSelected = false;
        userSelectedDoor = null;
        removedDoor = null;

        //Reset color and any missing letter.
        for (int i = 0; i < 3; i++) {
            Button btn = getDoor((char) ('A' + i));
            btn.setBackgroundColor(getResources().getColor(R.color.defaultButtonColor));
            String message = ((char) ('A' + i)) + "";
            btn.setText(message);
        }

        gameOver = false;

        TextView promptText = findViewById(R.id.prompt);
        String message = "Select a letter choice below:";
        promptText.setText(message);

    }

    public void selectA(View view) {
        if (!gameOver) {
            if (!doorHasBeenSelected) { //make sure user can select door only once.
                rdg.get('A').selectDoor();
                doorHasBeenSelected = true;
                userSelectedDoor = rdg.get('A');

                Button buttonA = findViewById(R.id.buttonA);
                buttonA.setBackgroundColor(getResources().getColor(R.color.buttonSelected));

                revealUndesiredDoor();
            } else if (removedDoor.doorLetter != 'A') { //It's an available option
                checkIfWon('A');
            }
        }
    }

    public void selectB(View view) {
        if (!gameOver) {
            if (!doorHasBeenSelected) { //make sure user can select door only once.
                rdg.get('B').selectDoor();
                doorHasBeenSelected = true;
                userSelectedDoor = rdg.get('B');

                Button buttonB = findViewById(R.id.buttonB);
                buttonB.setBackgroundColor(getResources().getColor(R.color.buttonSelected));

                revealUndesiredDoor();
            } else if (removedDoor.doorLetter != 'B') { //It's an available option
                checkIfWon('B');
            }
        }
    }

    public void selectC(View view) {
        if (!gameOver) {
            if (!doorHasBeenSelected) { //make sure user can select door only once.
                rdg.get('C').selectDoor();
                doorHasBeenSelected = true;
                userSelectedDoor = rdg.get('C');

                Button buttonC = findViewById(R.id.buttonC);
                buttonC.setBackgroundColor(getResources().getColor(R.color.buttonSelected));

                revealUndesiredDoor();
            } else if (removedDoor.doorLetter != 'C') { //It's an available option
                checkIfWon('C');
            }
        }
    }

    private void checkIfWon(char doorLetter) {
        gameOver = true;

        totalGames++;

        Button button = getDoor(doorLetter);

        if (rdg.getPrizeDoor().doorLetter == doorLetter) { //chose the winning door

            //set winning door to green color
            button.setBackgroundColor(getResources().getColor(R.color.winner));
            TextView promptText = findViewById(R.id.prompt);
            String message = "You win!";
            promptText.setText(message);

            if (userSelectedDoor.doorLetter == doorLetter) { //user stuck with their answer
                stayTotal++;
                stayWins++;

                //update stats
                TextView stayText = findViewById(R.id.stay);
                String numericalValue = String.format("%.2f", (stayWins * 1.0)/stayTotal*100.0);
                message = "Stay and Won Ratio: " + numericalValue + "%";
                stayText.setText(message);

            } else { //user switched answers
                switchTotal++;
                switchWins++;

                //update stats
                TextView switchText = findViewById(R.id.switched);
                String numericalValue = String.format("%.2f", (switchWins * 1.0)/switchTotal*100.0);
                message = "Switch and Won Ratio: " + numericalValue + "%";
                switchText.setText(message);

                //switch back the original choice, to default color
                getDoor(userSelectedDoor.doorLetter).setBackgroundColor(getResources().getColor(R.color.defaultButtonColor));
            }
        } else { // chose losing door
            button.setBackgroundColor(getResources().getColor(R.color.lose));
            TextView promptText = findViewById(R.id.prompt);
            String message = "You lose!";
            promptText.setText(message);

            if (userSelectedDoor.doorLetter == doorLetter) { //user stuck with their answer and lost
                stayTotal++;

                //update stats
                TextView stayText = findViewById(R.id.stay);
                String numericalValue = String.format("%.2f", (stayWins * 1.0)/stayTotal*100.0);
                message = "Stay and Won Ratio: " + numericalValue + "%";
                stayText.setText(message);

                //show the red box meaning the choice was losing.
                getDoor(doorLetter).setBackgroundColor(getResources().getColor(R.color.lose));
            } else { //user switched answers and lost
                switchTotal++;

                //update stats
                TextView switchText = findViewById(R.id.switched);
                String numericalValue = String.format("%.2f", (switchWins * 1.0)/switchTotal*100.0);
                message = "Switch and Won Ratio: " + numericalValue + "%";
                switchText.setText(message);

                //switch back the original choice, to default color
                getDoor(userSelectedDoor.doorLetter).setBackgroundColor(getResources().getColor(R.color.defaultButtonColor));
                //show user selected the new choice which lost.
                button.setBackgroundColor(getResources().getColor(R.color.lose));
            }
            //show winner
            getDoor(rdg.getPrizeDoor().doorLetter).setBackgroundColor(getResources().getColor(R.color.winner));
        }

        //update total wins stats
        TextView overallText = findViewById(R.id.overall);
        String numericalValue = String.format("%.2f", ((switchWins+stayWins)* 1.0)/totalGames*100.0);
        String message = "Overall Winnings: " + numericalValue + "%";
        overallText.setText(message);

        TextView gameCounter = findViewById(R.id.gameCounter);
        if (totalGames == 1) {
            message = "Overall Stats (" + totalGames + " game):";
        } else {
            message = "Overall Stats (" + totalGames + " games):";
        }
        gameCounter.setText(message);

        Button newGame = findViewById(R.id.newGameButton);
        newGame.setVisibility(View.VISIBLE);
    }


    //one that is not selected by the user and one that is not the prize door. (the user could have selected the prize door originally)
    public void revealUndesiredDoor() {
        //used to eliminate the door with no prize.
        Door undesired;

        Random rand = new Random();

        //generate random value between 0 and 1.
        double randomPrizeDoor = rand.nextDouble();

        if (randomPrizeDoor <= .50) { //first door in arraylist
            if (!rdg.doors.get(0).isUserSelected()) {
                undesired = rdg.doors.get(0);
            } else {
                undesired = rdg.doors.get(1);
            }
        } else { //second door in arraylist
            if (!rdg.doors.get(1).isUserSelected()) {
                undesired = rdg.doors.get(1);
            } else {
                undesired = rdg.doors.get(0);
            }
        }

        Button button = getDoor(undesired.doorLetter);
        button.setText("");

        TextView promptText = findViewById(R.id.prompt);
        String message = "To help you out, " + undesired.doorLetter + " has no prize. Choose " + userSelectedDoor.doorLetter + " again to confirm your original choice, or select the other door to switch your choice.";
        promptText.setText(message);

        removedDoor = undesired;
    }

    public Button getDoor(char doorLetter) {
        Button button;

        if (doorLetter == 'A') {
            button = findViewById(R.id.buttonA);
        } else if (doorLetter == 'B') {
            button = findViewById(R.id.buttonB);
        } else { //C
            button = findViewById(R.id.buttonC);
        }

        return button;
    }
}

