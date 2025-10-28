package com.example.cincuentazo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class MenuController {

    @FXML
    private Button btn1Opponent, btn2Opponent, btn3Opponent, btnStartGame;

    private int selectedOpponents = 0;

    @FXML
    public void selectOpponent(ActionEvent event) {
        // Remove "selected" class from all buttons
        btn1Opponent.getStyleClass().remove("selected");
        btn2Opponent.getStyleClass().remove("selected");
        btn3Opponent.getStyleClass().remove("selected");

        // Add "selected" to clicked button
        Button clicked = (Button) event.getSource();
        clicked.getStyleClass().add("selected");

        // Set selected opponent count
        if (clicked == btn1Opponent) {
            selectedOpponents = 1;
        } else if (clicked == btn2Opponent) {
            selectedOpponents = 2;
        } else if (clicked == btn3Opponent) {
            selectedOpponents = 3;
        }

        // Enable Start Game button
        btnStartGame.setDisable(false);
    }

    @FXML
    public void startGame() {
        if (selectedOpponents > 0) {
            System.out.println("Starting game against " + selectedOpponents + " opponent(s)...");
            //  Load game scene here
        }
    }
}