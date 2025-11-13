package com.example.cincuentazo.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    void addCardsIncreaseSum(){
        Table table = new Table();
        Card card1 = new Card("Clubs", "8");
        Card card2 = new Card("Spades", "2");
        table.addCard(card1);
        table.addCard(card2);

        assertEquals(10, table.getCurrentSum());
    }
    @Test
    void addCardsIncreaseSum2(){
        Table table = new Table();
        Card card1 = new Card("Clubs", "9");
        Card card2 = new Card("Spades", "2");
        table.addCard(card1);
        table.addCard(card2);

        assertEquals(2, table.getCurrentSum());
    }
    @Test
    void addCardsIncreaseSum3(){
        Table table = new Table();
        Card card1 = new Card("Clubs", "J");
        Card card2 = new Card("Spades", "4");
        table.addCard(card1);
        table.addCard(card2);

        assertEquals(-6, table.getCurrentSum());
    }
    @Test
    void addCardsIncreaseSum4(){
        Table table = new Table();
        Card card1 = new Card("Clubs", "8");
        Card card2 = new Card("Spades", "2");
        Card card3 = new Card("Hearts", "A");
        table.addCard(card1);
        table.addCard(card2);
        table.addCard(card3);

        assertEquals(20, table.getCurrentSum());
    }
    @Test
    void addCardsIncreaseSum5(){
        Table table = new Table();
        Card card1 = new Card("Clubs", "J");
        Card card2 = new Card("Hearts", "A");
        table.addCard(card1);
        table.addCard(card2);

        assertEquals(0, table.getCurrentSum());
    }
    @Test
    void saveCorrectlyCardsOnList(){
        Table table = new Table();
        Card card1 = new Card("Clubs", "8");
        Card card2 = new Card("Spades", "2");
        table.addCard(card1);
        table.addCard(card2);
        assertEquals(2,table.getCardsOnTable().size());
    }
    @Test
    void saveCorrectlyCardsOnList2(){
        Table table = new Table();
        Card card1 = new Card("Clubs", "3");
        Card card2 = new Card("Spades", "2");
        Card card3 = new Card("Hearts", "A");
        table.addCard(card1);
        table.addCard(card2);
        table.addCard(card3);
        assertEquals(3,table.getCardsOnTable().size());
    }
    @Test
    void addCardBecomesLastCard(){
        Table table = new Table();
        Card card1 = new Card("Diamonds", "7");
        table.addCard(card1);
        assertEquals(card1, table.getLastCard());
    }
    @Test
    void addCardBecomesLastCard2(){
        Table table = new Table();
        Card card1 = new Card("Diamonds", "7");
        Card card2 = new Card("Clubs", "A");
        Card card3 = new Card("Spades", "9");
        table.addCard(card1);
        table.addCard(card2);
        table.addCard(card3);
        assertEquals(card3, table.getLastCard());
    }
    @Test
    void takeAllCardsExceptLast(){
        Table table = new Table();
        Card card1 = new Card("Hearts", "4");
        Card card2 = new Card("Diamonds", "3");
        Card card3 = new Card("Clubs", "7");
        table.addCard(card1);
        table.addCard(card2);
        table.addCard(card3);
        List<Card> remove = table.takeAllExceptLast();
        assertEquals(1, table.getCardsOnTable().size());
        assertEquals(card3, table.getLastCard());
    }



}