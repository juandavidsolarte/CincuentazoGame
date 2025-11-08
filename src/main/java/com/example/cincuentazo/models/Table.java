package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int currentSum = 0;
    private List<Card> cardsOnTable; //Stores the pile of played cards
    private Card lastCard;

    public Table() {
        this.cardsOnTable = new ArrayList<>();
        this.currentSum = 0;
        this.lastCard = null;
    }

    /**
     * Add a card to the pile on the table and update the total according to the game rules.
     * THIS IS THE CORRECT LOGIC.
     */
    public void addCard(Card card) {
        // Use getOptimalValue to handle As (1 or 10)
        int value = card.getOptimalValue(currentSum);
        currentSum = currentSum + value;

        cardsOnTable.add(card);
        this.lastCard = card; // Updates the last card played

        System.out.println("Added to table: " + card + " (Sum now: " + currentSum + ")");
    }

    // This playCard(Card card) method was duplicated/conflicting, so it needs to remove.
    // Will use addCard(Card card), which is now correct.

    public int getCurrentSum() {
        return currentSum;
    }


    /**
     * Gets the last card played on the table.
     * (This was a duplicate and broken method before.)
     */
    public Card getLastCard() {
        return this.lastCard;
    }

    /** Take all the cards on the table except the last one
     * to refill the deck.
     * @return A list of cards to shuffle.
     */
    public List<Card> takeAllExceptLast() {
        if (cardsOnTable.size() <= 1) {
            return new ArrayList<>(); // There is nothing to pick up
        }

        //Copy all the letters except the last one
        List<Card> cardsToReshuffle = new ArrayList<>(cardsOnTable.subList(0, cardsOnTable.size() - 1));

        // Leave only the last card on the table
        Card last = getLastCard(); // Use the method that was just fixed
        cardsOnTable.clear();
        cardsOnTable.add(last);

        return cardsToReshuffle;
    }

    /**
     * Returns the list of cards on the table (for deck reset).
     * (This was the second duplicate method; now it's the only one.)
     */
    public List<Card> getCardsOnTable() {
        return cardsOnTable;
    }
}