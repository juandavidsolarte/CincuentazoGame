package com.example.cincuentazo.controllers;

import com.example.cincuentazo.models.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import javafx.scene.control.Alert;

public class MenuController {

    @FXML
    private Button btn1Opponent, btn2Opponent, btn3Opponent, btnStartGame;

    private Button selectedButton = null;
    private int selectedOpponents = 0;


    @FXML
    private void initialize() {
        // Ensure all buttons start deselected
        btn1Opponent.getStyleClass().remove("selected");
        btn2Opponent.getStyleClass().remove("selected");
        btn3Opponent.getStyleClass().remove("selected");

        // Disable the Start Game button until an opponent is selected
        btnStartGame.setDisable(true);

        selectedButton = null;
    }


    @FXML
    public void selectOpponent(ActionEvent event) {
        Button clicked = (Button) event.getSource();

        // Remove "selected" style from the previously selected button
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("selected");
        }

        // Add "selected" style to the newly clicked button
        clicked.getStyleClass().add("selected");
        selectedButton = clicked;

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

            try {

                // 1. Create the Game instance HERE
                Game game = new Game();
                game.startGame(selectedOpponents);


                // 2. Load the new FXML scene
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/cincuentazo/views/game.fxml")
                );
                Parent gameRoot = loader.load();

                // 3.Get the controller of the new scene
                // and pass the number of selected opponents
                GameController gameController = loader.getController();
                gameController.setGame(game);
                //gameController.setNumberOfOpponents(selectedOpponents);

                // 4.Create a new window (Stage)
                Stage gameStage = new Stage();
                gameStage.setTitle("Cincuentazo");
                gameStage.setScene(new Scene(gameRoot, 809, 800));

                gameStage.getIcons().add(
                        new Image(getClass().getResourceAsStream("/com/example/cincuentazo/images/icono.png"))
                );

                gameStage.setResizable(false);

                // close the current menu window
                Stage menuStage = (Stage) btnStartGame.getScene().getWindow();
                menuStage.close(); // comment this line if you want to keep both windows open

                // Show the new game window
                gameStage.show();

            } catch (IOException e) {
                e.printStackTrace();

                // Show an error message if the FXML cannot be loaded
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load game");
                alert.setContentText("Could not load the game screen.");
                alert.showAndWait();
            }

        }
    }
}