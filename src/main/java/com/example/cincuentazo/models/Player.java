package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private String name;
    private boolean isMachine; // state, is it a machine or not
    private List<Card> hand; // hand of cards
    private int score; // score

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

    public String getName() { return name; }
}