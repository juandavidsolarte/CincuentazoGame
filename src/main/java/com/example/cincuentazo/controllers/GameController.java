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
    private HBox playerHandBox; //  container for the human player's cards

    private Game game;

    /**
     * Called from MenuController to inject the fully initialized game.
     */
    public void setGame(Game game) {
        this.game = game;
        initializeUI(); // Update UI immediately
    }

    /**
     * Initialize UI based on the current game state.
     */
    private void initializeUI() {
        if (game == null) return;

        /*

        // Update opponent count
        int opponentCount = game.getPlayers().size() - 1; // exclude human
        lblOpponents.setText("Playing against " + opponentCount + " machine opponent(s)");

        // Update table sum
        lblTableSum.setText("Table Sum: " + game.getTable().getCurrentSum());

        //  Render player's hand in playerHandBox
        renderPlayerHand();
    }

         */
    /*
    private void renderPlayerHand() {
        // Clear previous cards
        playerHandBox.getChildren().clear();

        // Get human player (assume index 0)
        var humanPlayer = game.getPlayers().get(0);
        var hand = humanPlayer.getHand();

        // For now, just print to console (later: create Buttons for each card)
        System.out.println("Player hand: " + hand);

        // Create a Button for each card and add to playerHandBox
        // Example:
        // for (Card card : hand) {
        //     Button cardButton = new Button(card.toString());
        //     playerHandBox.getChildren().add(cardButton);
        // }
    }

     */





    }

}
