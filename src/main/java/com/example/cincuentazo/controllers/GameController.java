package com.example.cincuentazo.controllers;

import com.example.cincuentazo.models.Card;
import com.example.cincuentazo.models.Player;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
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

    @FXML
    private Label lblCurrentPlayer; //to reference in the threads

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
        updateUI();
        lblCurrentPlayer.setText("Tu Turno");
    }

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

    // A central method to update the entire UI
    private void updateUI()
    {
        updateTableUI();
        updatePlayerHandUI();
    }

    // Method to display the player's hand
    private void updatePlayerHandUI()
    {
        Player humanPlayer = game.getPlayers().get(0);
        playerHandBox.getChildren().clear();

        for (Card card : humanPlayer.getHand())
        {
            Label cardLabel = new Label(card.toString());
            cardLabel.getStyleClass().add("card-label");

            // Action of the human player
            cardLabel.setOnMouseClicked(event ->
            {
                // It only allows playing if it is the human's turn
                if (game.getCurrentPlayerIndex() == humanPlayer)
                {
                    handleHumanPlay(card);
                }
            });

            playerHandBox.getChildren().add(cardLabel);
        }
    }

    private void handleHumanPlay(Card card)
    {
        // Validate if the move is legal
        if (!game.isValidPlay(card))
        {
            System.out.println("Movimiento ilegal: " + card);
            return;
        }

        // Disable the hand while processing
        playerHandBox.setDisable(true);
        lblCurrentPlayer.setText("Procesando...");

        // Execute the turn
        game.executePlay(game.getHumanPlayer(), card);
        updateUI();

        // Start the machine sequence
        checkNextTurn();
    }

    /** REVIEW THE FOLLOWING TURN (Called after each move)
     * This is the main "Game Loop"
     */
    private void checkNextTurn()
    {
        // Advance to the next valid player in the model
        // ALL LOGIC (elimination, skip turn) happens in this method!
        Player nextPlayer = game.advanceToNextValidTurn();
        updateUI();

        // Check end of game
        if (game.isGameOver())
        {
            showGameOver(game.getWinner());
            return;
        }

        // Decide whether it is the AI's or the Human's turn
        if (nextPlayer.isMachine())
        {
            lblCurrentPlayer.setText("Turno de: " + nextPlayer.getName());
            runMachineTurn(nextPlayer);
        }
        else
        {
            // Human's turn
            lblCurrentPlayer.setText("¡Tu Turno!");
            playerHandBox.setDisable(false);
        }
    }

    /** MACHINE THREAD (AI)
     * This runs in the background so as not to freeze the UI.
     */
    private void runMachineTurn(Player machine)
    {

        Task<Card> machineTask = new Task<>()
        {
            @Override
            protected Card call() throws Exception
            {
                Thread.sleep(1500); // 1.5 seconds
                return machine.findBestMove(game.getTable().getCurrentSum());
            }
        };

        // When the thread finishes (setOnSucceeded)
        machineTask.setOnSucceeded(event ->
        {
            Card cardToPlay = machineTask.getValue(); // Get the chosen card

            // Execute the turn in the model
            System.out.println(machine.getName() + " juega: " + cardToPlay);
            game.executePlay(machine, cardToPlay);

            // Start the next turn (it could be another machine or the human)
            checkNextTurn();
        });

        // Start the thread
        new Thread(machineTask).start();
    }

    // --- UI UPDATE METHODS ---

    /** Displays the end-of-game alert */
    private void showGameOver(Player winner)
    {
        playerHandBox.setDisable(true);
        lblCurrentPlayer.setText("¡Juego Terminado!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Fin del Juego!");

        if (winner != null)
        {
            alert.setHeaderText("¡Felicidades, " + winner.getName() + "!");
            alert.setContentText("Has ganado la partida.");
        }
        else
        {
            alert.setHeaderText("¡Juego terminado!");
            alert.setContentText("No quedan ganadores.");
        }
        alert.showAndWait();
    }
}
