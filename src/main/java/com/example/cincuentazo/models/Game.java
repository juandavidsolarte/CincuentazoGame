package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the main game logic for "Cincuentazo".
 * Manages players, deck, table, turns, and game state.
 */
public class Game
{
    private List<Player> players;
    private Deck deck;
    private Table table;
    private int currentPlayerIndex;
    private boolean gameOver;
    private Player winner;

    // Empty constructor
    public Game() {

    }

    // Deck is created HERE — only when game starts
    public void startGame(int numOpponents) {
        // Reset state
        this.players = new ArrayList<>();
        //this.table = new Table();
        this.currentPlayerIndex = 0;
        this.gameOver = false;
        this.winner = null;

        // Create and shuffle deck
        this.deck = new Deck();
        this.table = new Table();

        //  PRINT THE DECK TO CONSOLE (for debugging)
        System.out.println("Size : " + deck.size());
        System.out.println("=== DECK: Card and Value ===");
        for (int i = 0; i < deck.getAllCards().size(); i++) {
            Card card = deck.getAllCards().get(i);
            System.out.println((i + 1) + ". " + card + " → value = " + card.getValue());
        }
        System.out.println("================================");

        // add Players
        // Human player is on index 0
        players.add(new Player(" YOU ", false));
        // Add machine players
        for (int i = 1; i <= numOpponents; i++) {
            players.add(new Player("Machine " + i, true));
        }
        //  4 RANDOM CARDS TO EACH PLAYER
        for (int j = 0; j < players.size() ;j++) {
            Player player = players.get(j);
            for (int i = 0; i < 4; i++) {
                Card card = deck.dealCard();
                player.addCard(card); //  Takes a random card from the shuffled deck
                System.out.println(player.getName() + " received: " + card);
            }
        }

        System.out.println("=== MANOS DE JUGADORES ===");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getHand());
        }
        System.out.println("==========================");

        // Draw the first card and place it on the table
        Card initialCard = deck.dealCard();
        table.addCard(initialCard); // Updates sum and stores card

        System.out.println("Intialized table with card: " + initialCard +
                "  Table sum: " + table.getCurrentSum());
        // Show how many cards remain in deck
        System.out.println("Cards left in deck: " + deck.size()); // 52 - (4 * totalPlayers) - 1 (initial card)

    }//END METHOD START GAME

    // Logic to play a card
    /**
     * Plays a card from the given player's hand, updates the table sum,
     * and draws a replacement card from the deck if available.
     *
     * <p>This method:
     * <ul>
     *   <li>Removes the card from the player's hand
     *   <li>Adds the card's value to the table sum via
     *   <li>Draws a new card from the deck and adds it to the player's hand, if the deck is not empty.
     * </ul>
     *
     */
    public Card playCard(Player player, Card card) {

        Table table = getTable();
        Deck deck = getDeck();

        boolean removed = player.removeCard(card);
        if (!removed) {
            System.err.println(" Card " + card + " not found in hand.");
            return null;
        }

        table.addCard(card);
        System.out.println(" Played: " + card + " → Sum: " + table.getCurrentSum());

        // Draw new card if available
        if (!deck.isEmpty()) {
            Card newCard = deck.dealCard();
            player.addCard(newCard);
            System.out.println(" Drew: " + newCard);
            return newCard;
        }

        return null; // deck empty
    }//END METHOD PLAYCARD



    // Method to count cards left in deck
    public int getCardsLeftInDeck() {
        return deck.size();
    }


    /** Checks if a specific card is a legal move.
     * @param card The card to check.
     * @return true if the card does not exceed 50.
     */
    public boolean isValidPlay(Card card)
    {
        return table.getCurrentSum() + card.getOptimalValue(table.getCurrentSum()) <= 50;
    }

    /** Checks if a player has valid moves.
     * @param player The player to check.
     * @return true if they have at least one playable card.
     */
    public boolean canPlayerPlay(Player player)
    {
        return player.canPlay(table.getCurrentSum());
    }

    /** Executes a player's turn (human or computer).
     * @param player The player who is taking the turn.
     * @param card The card being played.
     */
    public void executePlay(Player player, Card card)
    {
        // Remove the card from the hand (if it is human. The AI already did it).
        if (!player.isMachine())
        {
            player.removeCard(card);
        }

        // Play the card on the table (updates the total)
        table.addCard(card);

        // Draw a card (and play an empty deck)
        checkDeckAndDraw(player);
    }

    /** Moves to the next player, skipping eliminated ones
     * and handling elimination if someone cannot play.
     * @return The next player who CAN play.
     */
    public Player advanceToNextValidTurn()
    {
        if (isGameOver())
        {
            return winner; // End of the game
        }

        // Move to the next index
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player nextPlayer = getCurrentPlayer();

        // --- Logic Loop ---
        // Did the game end?
        List<Player> activePlayers = getActivePlayers();
        if (activePlayers.size() <= 1)
        {
            this.gameOver = true;
            this.winner = activePlayers.isEmpty() ? null : activePlayers.get(0);
            return winner;
        }

        // Is it disqualified? If yes, skip
        if (nextPlayer.getIfIsEliminated())
        {
            return advanceToNextValidTurn(); //Recursion to jump to the next
        }

        // Can play? If not, remove him and skip
        if (!canPlayerPlay(nextPlayer))
        {
            handleElimination(nextPlayer);
            return advanceToNextValidTurn();
        }

        // This player is valid and can play.
        return nextPlayer;
    }

    /** Handles the removal of a player.
     * @param player The player to remove.
     */
    private void handleElimination(Player player)
    {
        System.out.println("¡JUGADOR ELIMINADO: " + player.getName() + "!");
        player.setEliminated(true);
        // The eliminated player's cards are sent to the bottom of the deck
        deck.addCardsToBottom(player.getHand());
        player.getHand().clear(); // Empty the hand
    }

    /** Checks if the deck is empty and draws a card for the player.
     * @param player The player who draws.
     */
    private void checkDeckAndDraw(Player player)
    {
        if (deck.isEmpty())
        {
            System.out.println("¡Mazo vacío! Rellenando desde la mesa...");
            List<Card> tableCards = table.takeAllExceptLast();
            deck.reshuffleFromTable(tableCards);
        }

        if (!deck.isEmpty()) {
            player.receiveCard(deck.dealCard());
        }
    }

    // ===== Getters =====

    public Table getTable() { return table; }
    public List<Player> getPlayers() { return players; }
    public Player getCurrentPlayer() {return players.get(currentPlayerIndex);}
    public Player getHumanPlayer() {return players.get(0);}
    public Deck getDeck() { return deck; }
    public boolean isGameOver() { return gameOver; }
    public Player getWinner() { return winner; }

    /** Helper to know how many players are left */
    public List<Player> getActivePlayers()
    {
        return players.stream()
                .filter(p -> !p.getIfIsEliminated())
                .collect(Collectors.toList());
    }

    /*

    private boolean gameActive; //determinant of whether there is an active game or not

    public Game(int numPlayers)
    {
        deck = new Deck();
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", false));

        for (int i = 2; i <= numPlayers; i++)
        {
            players.add(new Player("CPU " + i, true));
        }
        table = new Table(players);
        gameActive = true;
    }

    public void dealCards()
    {
        for (Player p : table.getPlayers()) {
            for (int i = 0; i < 4; i++) {
                p.receiveCard(deck.drawCard());
            }
        }
    }

    public void startTurns()
    {
        //we will later put the threads
    }

    public Table getTable() { return table; }

     */
}
