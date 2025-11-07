package com.example.cincuentazo.models;

import com.example.cincuentazo.models.Card;
import java.util.*;

/**
 * Represents a standard English deck of playing cards (52 cards).
 * The deck is generated with exactly:
 *  13 ranks per suit: A, 2–10, J, Q, K
 *  4 suits: Hearts, Diamonds, Clubs, Spades
 * → Total: 13 × 4 = 52 cards
 */
public class Deck
{

    private List<Card> cards;

    /**
     * Constructor: Initializes and shuffles a full 52-card deck.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        generateCards();  // Build the full deck
        shuffle();        // Randomize card order
    }

    /**
     * Generates all 52 standard playing cards.

     *  For each of the 4 suits, we create exactly 13 cards.
     *  This ensures the maximum number of cards per suit (13) is respected.
     *  Total cards = 4 suits × 13 ranks = 52 → Standard deck size.
     */
    private void generateCards() {
        // Define the 4 standard suits
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        // Define the 13 ranks in a standard suit

        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        // Iterate over each suit
        for (int i = 0; i < suits.length; i++) {
            String suit = suits[i];
            for (int j = 0; j < ranks.length; j++) { // ← int j + j++
                String rank = ranks[j];
                cards.add(new Card(suit, rank));
            }
        }
        // After all suits, total cards = 4 × 13 = 52

    }

    /**
     * Randomly shuffles the deck using Collections.shuffle().
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws (removes and returns) the top card from the deck.
     * Alias for dealCard(), for readability in some game contexts.
     */
    public Card drawCard() {
        return dealCard();
    }


    // TO PRINT IN CONSOLE
    public List<Card> getAllCards() {
        return new ArrayList<>(cards); // return a copy
    }

    /**
     * Deals (removes and returns) the top card from the deck.
     *
     * @return the top Card
     * @throws IllegalStateException if the deck is empty
     */
    public Card dealCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot deal from an empty deck.");
        }
        // Remove and return the last card (efficient for ArrayList)
        return cards.remove(cards.size() - 1);
    }

    /**
     * Checks if the deck has no remaining cards.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the current number of cards in the deck.
     *
     * @return number of cards
     */
    public int size() {
        return cards.size();
    }


    /** Adds cards (from an eliminated player) to the end of the deck.
     */
    public void addCardsToBottom(List<Card> cardsToAdd)
    {
        // I would add them at the beginning (background if taken from the end)
        cards.addAll(0, cardsToAdd);
    }

    /** Adds the cards from the table, shuffles them, and puts them into the deck.
     */
    public void reshuffleFromTable(List<Card> tableCards)
    {
        this.cards.addAll(tableCards);
        shuffle();
    }
}