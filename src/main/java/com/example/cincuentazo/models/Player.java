package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private String name;
    private boolean isMachine; // state, is it a machine or not
    private List<Card> hand; // hand of cards
    private int score; // score
    private boolean isEliminated = false;  //To know if a player has already lost

    public Player(String name, boolean isMachine)
    {
        this.name = name;
        this.isMachine = isMachine;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    public void receiveCard(Card card) { // "recibirCarta" -> "receiveCard", "carta" -> "card"
        hand.add(card);
    }

    public List<Card> getHand() { return hand; }

    public boolean isMachine() { return isMachine; }

    public int getScore() { return score; }

    public void addPoints(int points) { // add points to score
        score += points;
    }

    public void setEliminated(boolean eliminated)
    {
        this.isEliminated = eliminated;
    }

    public boolean isEliminated()
    {
        return isEliminated;
    }

    public String getName() { return name; }

    /** Checks the player's hand to see if they have at least one valid move.
     * @param currentTableSum The current sum on the table.
     * @return true if the player can play, false if they should be eliminated.
     */
    public boolean canPlay(int currentTableSum)
    {
        for (Card card : hand)
        {
            int cardValue = card.getOptimalValue(currentTableSum);
            if (currentTableSum + cardValue <= 50)
            {
                return true; // ¡found a playable card!
            }
        }
        return false; // has no cards to play
    }

    /** (AI Logic) Find the best card to play.
     * @param currentTableSum The current sum on the table.
     * @return The selected card to play, or null if it cannot play.
     */
    public Card findBestMove(int currentTableSum)
    {
        // Simple strategy: play the first valid card you find
        for (Card card : hand) {
            int cardValue = card.getOptimalValue(currentTableSum);
            if (currentTableSum + cardValue <= 50) {
                hand.remove(card); // Remove the card from the hand
                return card;
            }
        }
        return null; // can't play
    }

    // Method to remove a specific card (necessary for the human)
    public void playCard(Card card)
    {
        hand.remove(card);
    }


}