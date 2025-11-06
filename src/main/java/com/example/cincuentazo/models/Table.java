package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Table
{
    private int currentSum = 0;

    private List<Card> cardsOnTable;       //Stores the pile of played cards



    public void playCard(Card card) {
        int value = card.getOptimalValue(currentSum);
        currentSum += value;
        cardsOnTable.add(card); // Add the card to the stack
    }

    public int getCurrentSum() {
        return currentSum;
    }

    public Table(){
        this.cardsOnTable = new ArrayList<>();
        this.currentSum = 0;
    }

    public Card getLastCard()
    {
        if (cardsOnTable.isEmpty())
        {
            return null;
        }
        // The last letter is the one that is at the end of the list
        return cardsOnTable.get(cardsOnTable.size() - 1);
    }

    /** Take all the cards on the table except the last one
     * to refill the deck.
     * @return A list of cards to shuffle.
     */
    public List<Card> takeAllExceptLast()
    {
        if (cardsOnTable.size() <= 1)
        {
            return new ArrayList<>(); // There is nothing to pick up
        }

        //Copy all the letters except the last one
        List<Card> cardsToReshuffle = new ArrayList<>(cardsOnTable.subList(0, cardsOnTable.size() - 1));

        // Leave only the last card on the table
        Card lastCard = getLastCard();
        cardsOnTable.clear();
        cardsOnTable.add(lastCard);

        return cardsToReshuffle;
    }

    public List<Card> getCardsOnTable()
    {
        return cardsOnTable;
    }
}