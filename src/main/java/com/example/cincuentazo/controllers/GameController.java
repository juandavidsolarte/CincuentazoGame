package com.example.cincuentazo.controllers;

import com.example.cincuentazo.models.Card;
import com.example.cincuentazo.models.Game;
import com.example.cincuentazo.models.Player;
import com.example.cincuentazo.models.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;

public class GameController {

    @FXML
    private Label lblOpponents;
    @FXML
    private Label lblTableSum;

    @FXML
    private Label lblTableCard; // Shows the actual card

    @FXML
    private HBox playerHandBox; //  container for the human player's cards
    private List<Card> playerCards = new ArrayList<>();

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
        renderPlayerHand();   // Muestra las cartas del jugador

    }

    /**
     * Initialize UI based on the current game state.
     */
    /**
     * Updates the table display with the latest card and sum.
     * Called whenever the table state changes.
     */
    private void updateTableUI() {
        System.out.println("Updating table UI...");
        if (game == null || game.getTable() == null)
            return;

        Table table = game.getTable();
        Card lastCard = table.getLastCard();
        int sum = table.getCurrentSum();

        // Display card symbol
        if (lastCard != null) {
            lblTableCard.setText(lastCard.toString());
        } else {
            lblTableCard.setText("");
        }

        // Update sum label
        lblTableSum.setText("Sum: " + sum);
    }



    /**
     * Renders the human player's hand as 4 interactive card buttons.
     * Assumes the human player is at index 0 in the players list.
     */
    private void renderPlayerHand() {
        System.out.println("Rendering player hand...");
        // Clear any existing cards
        playerHandBox.getChildren().clear();

        // Get human player on index 0
        Player humanPlayer = game.getPlayers().get(0);
        List<Card> hand = humanPlayer.getHand();

        // Create a button for each card in hand
        for (int visualIndex = 0; visualIndex < hand.size(); visualIndex++) {
            Card card = hand.get(visualIndex);
            Button cardButton = new Button(card.toString());
            cardButton.getStyleClass().add("card-button");

            // Store the actual Card object in the button's properties
            cardButton.getProperties().put("card", card);
            cardButton.getProperties().put("visualIndex", visualIndex);

            //cardButton.setOnAction(event -> selectCard(event));

            cardButton.setOnAction(event -> {
                Button btn = (Button) event.getSource();
                Card selectedCard = (Card) btn.getProperties().get("card");
                int index = (Integer) btn.getProperties().get("visualIndex");
                selectCard(selectedCard, index, btn); // ← Pass index + button
            });


            playerHandBox.getChildren().add(cardButton);
        }

    }

    // Method to handle event
    /**
     * Handles the selection of a card by the human player.
     *
     * <p>The visual position of the card is preserved: only the clicked button is updated,
     * ensuring the other 3 cards remain in their original positions for usability.
     *
     * @param selectedCard the card selected by the player
     * @param visualIndex  the visual position (0–3) of the card in the hand UI
     */
    private void selectCard(Card selectedCard, int visualIndex, Button button) {
        try {
            System.out.println("Playing card: " + selectedCard);
            /*
            // obtain the card selected
            Button clickedButton = (Button) event.getSource();
            String cardText = clickedButton.getText();

            System.out.println("Selected card: " + cardText);
             */

            // Get human player
            Player humanPlayer = game.getPlayers().get(0);

            // Play the card and get the new one
            Card newCard = game.playCard(humanPlayer, selectedCard);

            if (newCard != null) {
                // Update ONLY this button with the NEW card
                button.setText(newCard.toString());
                button.getProperties().put("card", newCard);
                // ← visualIndex stays the same (no need to update)
            } else {
                // Deck empty: remove the button (or disable it)
                playerHandBox.getChildren().remove(button);
            }

            // Update the UI tabl,player,hand and sum
            lblTableCard.setText(selectedCard.toString());
            lblTableSum.setText("Sum: " + game.getTable().getCurrentSum());


            System.out.println(" UI updated: position " + visualIndex + " now shows " + newCard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }









}
