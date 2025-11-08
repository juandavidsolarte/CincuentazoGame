package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;
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

        // Draw the first card and place it on the table
        Card initialCard = deck.dealCard();
        table.playCard(initialCard); // Updates sum and stores card

        System.out.println("Intialized table with card: " + initialCard +
                "  Table sum: " + table.getCurrentSum());
        // Show how many cards remain in deck
        System.out.println("Cards left in deck: " + deck.size()); // 52 - (4 * totalPlayers) - 1 (initial card)

    }//END METHOD STAR GAMGE

    public Deck getDeck() {
        return deck;
    }

    // Logic to play a card
    /**
     * Plays a card from the given player's hand, updates the table sum,
     * and draws a replacement card from the deck if available.
     *
     * <p>This method:
     * <ul>
     *   <li>Removes the card from the player's hand (using {@link Card#equals(Object)}).</li>
     *   <li>Adds the card's value to the table sum via {@link Table#addCard(Card)}.</li>
     *   <li>Draws a new card from the deck and adds it to the player's hand, if the deck is not empty.</li>
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


    // ===== Getters =====

    public Table getTable() {
        return table;
    }

    public List<Player> getPlayers() {
        return players;
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
