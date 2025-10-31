package com.example.cincuentazo.models;

public class Card
{
    private String suit;
    private String value;  // A, 2, 3, 4, J, Q, K
    private int points;    //J = 10, Q = 10, K = 10, A = 1

    public Card(String suit, String value, int points)
    {
        this.suit = suit;
        this.value = value;
        this.points = points;
    }

    public String getSuit() { return suit; }
    public String getValue() { return value; }
    public int getPoints() { return points; }

    @Override
    public String toString()
    {
        return value + " of " + suit;
    }
}
