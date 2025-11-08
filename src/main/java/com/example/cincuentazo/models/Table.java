package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Table
{
    private int currentSum = 0;
    private Card lastCard;

    public Table() {
        this.cardsOnTable = new ArrayList<>();
        this.currentSum = 0;

    }

    public void playCard(Card card) {
        int value = card.getOptimalValue(currentSum);
        currentSum = currentSum + value;
        this.lastCard = card;
    }

    public int getCurrentSum() {
        return currentSum;
    }

    public Card getLastCard() {
        return lastCard;
    }

    private List<Card> cardsOnTable;
    private List<Player> players;
    private int currentPlayerIndex;

    public Table(List<Player> players)
    {
        this.players = players;
        this.cardsOnTable = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    /**
     * Agrega una carta al montón de la mesa y actualiza la suma según las reglas del juego.
     */
    public void addCard(Card card) {
        cardsOnTable.add(card);
        currentSum += card.getValue();
        System.out.println("Added to table: " + card + " (Sum now: " + currentSum + ")");
    }



    public List<Card> getCardsOnTable()
    {
        return cardsOnTable;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public Player getCurrentPlayer()
    {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}