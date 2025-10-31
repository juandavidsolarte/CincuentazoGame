package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck
{
    private List<Card> cards; //

    public Deck()
    {
        cards = new ArrayList<>();
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits)
        {
            for (int i = 0; i < values.length; i++)
            {
                int points = i + 1;
                cards.add(new Card(suit, values[i], points));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard()
    {
        return cards.remove(0);
    }
}
