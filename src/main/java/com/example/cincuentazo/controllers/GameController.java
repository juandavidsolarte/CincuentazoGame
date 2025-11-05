package com.example.cincuentazo.controllers;

import com.example.cincuentazo.models.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GameController {

    @FXML
    private Label lblOpponents;
    @FXML
    private Label lblTableSum;

    @FXML
    private Label lblTableCard; // Shows the actual card

    @FXML
    private HBox playerHandBox; //  container for the human player's cards

    // Reference to the active game
    private Game game;

    /**
     * Called by MenuController to inject the fully initialized game.
     * Immediately updates the UI to reflect current game state.
     *
     * @param game the active Game instance
     */
    public void setGame(Game game) {
        this.game = game;
        updateTableUI();
    }

    /**
     * Initialize UI based on the current game state.
     */
    /**
     * Updates the table display with the latest card and sum.
     * Called whenever the table state changes (e.g., after a move).
     */
    private void updateTableUI() {
        if (game == null || game.getTable() == null) return;

        var table = game.getTable();
        var lastCard = table.getLastCard();
        int sum = table.getCurrentSum();

        // Display card symbol
        if (lastCard != null) {
            lblTableCard.setText(lastCard.toString());
        } else {
            lblTableCard.setText(" ");
        }

        // Update sum label
        lblTableSum.setText("Sum: " + sum);
    }


}
