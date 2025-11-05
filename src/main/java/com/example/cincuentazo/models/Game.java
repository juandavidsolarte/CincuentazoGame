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
        // Add player (index 0)
        players.add(new Player("Human", true));
        // Add machine players
        for (int i = 1; i <= numOpponents; i++) {
            players.add(new Player("Machine " + i, false));
        }

        // Draw the first card and place it on the table
        Card initialCard = deck.dealCard();
        table.playCard(initialCard); // Updates sum and stores card

        // Optional: Log to console for debugging
        System.out.println("Intialized table with card: " + initialCard +
                " → Table sum: " + table.getCurrentSum());


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
