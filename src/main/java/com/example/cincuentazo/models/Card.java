package com.example.cincuentazo.models;


public class Card {
    private final String suit;
    private final String rank;

    /**
     * Constructs a playing card with the given suit and rank.
     *
     * @param suit the suit of the card ("Hearts", "Spades", "ETC")
     * @param rank the rank of the card ("A", "2", ..., "10", "J", "Q", "K")
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the suit of this card.
     *
     * @return the suit
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the rank of this card.
     *
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the numeric value of this card according to the rules of "Cincuentazo".
     * - Number cards (2–8, 10) have their face value.
     * - 9 has no effect (value = 0).
     * - Face cards (J, Q, K) subtract 10.
     * - Ace (A) is temporarily treated as 1 (use getOptimalValue() for dynamic behavior).
     *
     * @return the card's fixed value
     */
    public int getValue() {
        switch (rank) {
            case "2", "3", "4", "5", "6", "7", "8", "10":
                return Integer.parseInt(rank);
            case "9":
                return 0;
            case "J", "Q", "K":
                return -10;
            case "A":
                return 1; // Default value; use getOptimalValue() for context-aware choice
            default:
                return 0; // Should not occur with valid input
        }
    }


    /**
     * Returns the optimal value of this card based on the current table sum.
     * For non-Ace cards, it returns the standard value.
     * For an Ace, it chooses 10 if adding 10 does not exceed 50; otherwise, it chooses 1.
     *
     * @param currentTableSum the current total sum on the table
     * @return the best possible value for this card in the current game context
     */
    /*
    public int getOptimalValue(int currentTableSum) {
        if (!rank.equals("A")) {
            return getValue();
        }
        // Choose 10 for Ace only if it doesn't make the total exceed 50
        return (currentTableSum + 10 <= 50) ? 10 : 1;
    }
    */
    /**
     * Returns a string representation of the card using rank and suit symbols.
     * Examples: "A♠", "10♥", "K♦"
     *
     * @return a human-readable string like "Q♣"
     */
    @Override
    public String toString() {
        String suitSymbol = switch (suit) {
            case "Spades"   -> "♠";
            case "Hearts"   -> "♥";
            case "Diamonds" -> "♦";
            case "Clubs"    -> "♣";
            default         -> "?";
        };
        return rank + suitSymbol;
    }
}