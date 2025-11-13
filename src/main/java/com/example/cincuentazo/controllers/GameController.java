package com.example.cincuentazo.controllers;

import com.example.cincuentazo.models.Card;
import com.example.cincuentazo.models.Game;
import com.example.cincuentazo.models.Player;
import com.example.cincuentazo.models.Table;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import com.example.cincuentazo.models.CincuentazoException;
import javafx.util.Duration;

public class GameController {
    @FXML
    public Label lblDeck;
    @FXML
    private Label lblOpponents; // It is not in use yet, but we will leave it.
    @FXML
    private Label lblTableSum;
    @FXML
    private Label lblTableCard; //Shows the actual card
    @FXML
    private HBox playerHandBox; // The container for the human player's cards
    @FXML
    private Label lblCurrentPlayer; //to reference in the threads
    @FXML
    private Label lblError;

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
        // Start the UI
        updateUI();
        // Start the game (the human always starts)
        lblCurrentPlayer.setText("Your Turn!");
        playerHandBox.setDisable(false);
        lblError.setVisible(false);
    }

    /**
     * Updates the table display with the latest card and sum.
     * Called whenever the table state changes (e.g., after a move).
     */
    private void updateTableUI() {
        if (game == null || game.getTable() == null) return;

        Table table = game.getTable();
        Card lastCard = table.getLastCard();
        int sum = table.getCurrentSum();

        if (lastCard != null) {
            lblTableCard.setText(lastCard.toString());
        } else {
            lblTableCard.setText("");
        }
        // Update sum label
        lblTableSum.setText(""+sum);
    }

    /**
     * Method to display the player's hand (with kickable labels).
     */
    private void updatePlayerHandUI() {
        Player humanPlayer = game.getHumanPlayer();
        playerHandBox.getChildren().clear();

        for (Card card : humanPlayer.getHand()) {
            Label cardLabel = new Label(card.toString());
            cardLabel.getStyleClass().add("card-button");

            // Action of the human player
            cardLabel.setOnMouseClicked(event -> {
                // // It only allows playing if it is the human's turn
                if (!playerHandBox.isDisabled()) {
                    handleHumanPlay(card);
                }
            });

            playerHandBox.getChildren().add(cardLabel);
        }
    }

    /**
     * A central method to update the entire UI
     */
    private void updateUI() {
        updateTableUI();
        updatePlayerHandUI();
        updateDeckLabel();
        updateOpponentsLabel();

    }


    private void updateDeckLabel() {
        int remaining = game.getCardsLeftInDeck();
        lblDeck.setText("Deck: " + remaining + " cards");
    }

    private void updateOpponentsLabel() {
        if (game == null) {
            throw new IllegalStateException("The has not been initialized yet.");
        }

        // Obtiene los jugadores activos
        int totalActives = game.getActivePlayers().size();

        // Restamos 1 para no contar al jugador
        int opponentsLeft= totalActives - 1;

        lblOpponents.setText("Active Oponnents : " + opponentsLeft);
    }


    /**
     * It is executed when the human clicks on a card.
     * @throws CincuentazoException if the last play exceeds 50
     */
    private void handleHumanPlay(Card card) {
        try
        {
            // Validate if the move is legal
            if (!game.isValidPlay(card))
            {
                throw new CincuentazoException("Illegal Move! " + card + " exceeds the limit of 50.");
            }

            // Disable the hand while processing
            playerHandBox.setDisable(true);
            lblCurrentPlayer.setText("Processing...");

            // Execute the turn
            game.executePlay(game.getHumanPlayer(), card);
            updateUI();

            // Start the machine sequence
            checkNextTurn();
        }
        catch (CincuentazoException e)
        {
            System.err.println("Error de juego: " + e.getMessage());
            lblError.setText(e.getMessage());
            lblError.setVisible(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));

            delay.setOnFinished(event ->
            {
                lblError.setText("");
                lblError.setVisible(false);
            });

            delay.play();
        }
    }

    /** REVIEW THE FOLLOWING TURN (Called after each move)
     * This is the main "Game Loop"
     */
    private void checkNextTurn() {
        // Advance to the next valid player in the model
        // ALL LOGIC (elimination, skip turn) happens in this method
        Player nextPlayer = game.advanceToNextValidTurn();
        updateUI();

        // Check end of game
        if (game.isGameOver()) {
            showGameOver(game.getWinner());
            return;
        }

        // Decide whether it is the AI's or the Human's turn
        if (nextPlayer.isMachine()) {
            lblCurrentPlayer.setText("Shift of: " + nextPlayer.getName());
            playerHandBox.setDisable(true); // Disable human hand
            runMachineTurn(nextPlayer); // Start the AI thread
        } else {
            // Human's turn
            lblCurrentPlayer.setText("Your Turn!");
            playerHandBox.setDisable(false); // Enable human hand
        }
    }

    /**
     * Displays the end-of-game alert
     */
    private void showGameOver(Player winner) {
        playerHandBox.setDisable(true);
        if (winner != null) {
            lblCurrentPlayer.setText("GAME OVER! Winner:" + winner.getName());
        } else {
            lblCurrentPlayer.setText("GAME OVER! (Tie/No one wins)");
        }
    }

    /** MACHINE THREAD (AI)
     * This runs in the background so as not to freeze the UI.
     */
    private void runMachineTurn(Player machine) {

        // Task is JavaFX's way of handling threads.
        Task<Card> machineTask = new Task<>() {
            @Override
            protected Card call() throws Exception {
                // 1. Pause to simulate that the AI thinks
                Thread.sleep(2000); // 2 seconds

                // 2. Calls the AI logic that we added in Player.java
                return machine.findBestMove(game.getTable().getCurrentSum());
            }
        };

        // When the thread finishes (setOnSucceeded)
        machineTask.setOnSucceeded(event -> {
            Card cardToPlay = machineTask.getValue(); // Get the chosen card

            if (cardToPlay == null) {
                // This shouldn't happen if the elimination logic works, but it's a safety net.
                System.err.println("ERROR: La máquina " + machine.getName() + " no pudo jugar y no fue eliminada.");
            } else {
                // Execute the turn in the model
                // (The AI has already removed the card from its hand in findBestMove)
                game.executePlay(machine, cardToPlay);
            }

            // 5. Move on to the next turn
            // Platform.runLater ensures this runs on the main UI thread
            Platform.runLater(this::checkNextTurn);
        });

        // Start the thread
        new Thread(machineTask).start();
    }

}
