package com.example.cincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    private Table table;
    private Deck deck;

    private boolean gameActive; //determinant of whether there is an active game or not

    public Game(int numPlayers)
    {
        deck = new Deck();
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", false));

        for (int i = 2; i <= numPlayers; i++)
        {
            players.add(new Player("CPU " + i, true));
        }
        table = new Table(players);
        gameActive = true;
    }

    public void dealCards()
    {
        for (Player p : table.getPlayers()) {
            for (int i = 0; i < 4; i++) {
                p.receiveCard(deck.drawCard());
            }
        }
    }

    public void startTurns()
    {
        //we will later put the threads
    }

    public Table getTable() { return table; }
}
