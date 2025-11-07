package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a player in the "Cincuentazo" game.
 * Can be  human or machine.
 */
public class Player {
    private String name;
    private boolean isMachine; // state, is it a machine or not
    private List<Card> hand; // hand of cards
    private boolean active;             // true if not eliminated
    private int score; // score

    /**
     * Constructs a new player.
     */
    public Player(String name, boolean isMachine) {
        this.name = name;
        this.isMachine = isMachine;
        this.hand = new ArrayList<>();
        this.active = true; // All players start active
        this.score = 0;
    }

    /**
     * Adds a card to the player's hand.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes and returns a card at the given index.
     * Assumes the index is valid and the player has enough cards.
     */
    public Card playCard(int index) {
        return hand.remove(index);
    }

    public String getName() {
        return name;
    }

    public boolean isMachine() {
        return isMachine;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand); // return copy to preserve encapsulation
    }

    public int getHandSize() {
        return hand.size();
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Marks the player as eliminated.
     * Called when the player has no valid moves.
     */
    public void setInactive() {
        this.active = false;
    }

    public boolean removeCard(Card card) {
        System.out.println("Trying to remove: " + card);
        boolean removed = hand.remove(card);
        System.out.println("After remove: " + hand.size());
        return removed;
    }

}





