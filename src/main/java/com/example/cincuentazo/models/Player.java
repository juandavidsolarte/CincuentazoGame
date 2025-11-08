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
    private boolean isEliminated = false;  //To know if a player has already lost

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

    /** Checks the player's hand to see if they have at least one valid move.
     * @param currentTableSum The current sum on the table.
     * @return true if the player can play, false if they should be eliminated.
     */
    public boolean canPlay(int currentTableSum) {
        for (Card card : hand) {
            int cardValue = card.getOptimalValue(currentTableSum);
            if (currentTableSum + cardValue <= 50) {
                return true; // Found a playable card
            }
        }
        return false; // Has no cards to play
    }

    /** (AI Logic) Find the best card to play.
     * @param currentTableSum The current sum on the table.
     * @return The selected card to play, or null if it cannot play.
     */
    public Card findBestMove(int currentTableSum) {
        Card bestMove = null;

        // Simple strategy: play the first valid card you find
        for (Card card : hand) {
            if (card.getRank().equals("J") || card.getRank().equals("Q") || card.getRank().equals("K")) {
                if (currentTableSum + card.getOptimalValue(currentTableSum) <= 50) {
                    bestMove = card;
                    hand.remove(bestMove); // The AI removes the card from its hand WHEN IT IS CHOSEN
                    return bestMove;
                }
            }
        }

        // 2. Try to play a 9 (value 0).
        for (Card card : hand) {
            if (card.getRank().equals("9")) {
                bestMove = card; // 9 is always valid if the sum is already <= 50
                hand.remove(bestMove);
                return bestMove;
            }
        }

        // 3. Play the first valid numbered card or Ace.
        for (Card card : hand) {
            if (currentTableSum + card.getOptimalValue(currentTableSum) <= 50) {
                bestMove = card;
                hand.remove(bestMove);
                return bestMove;
            }
        }

        // If it gets here, it has no play (although canPlay() should have prevented it)
        return null;
    }

    // Missing help method in Game.java (receiveCard)
    public void receiveCard(Card card) {
        addCard(card);
    }
// --- END OF THE NEW METHODS ---

    public boolean getIfIsEliminated() { return isEliminated;}

    public void setEliminated(boolean isEliminated){ this.isEliminated = isEliminated;}

}
