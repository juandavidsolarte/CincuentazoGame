package com.example.cincuentazo.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void cardHeartNumber7EqualSeven(){
        Card card = new Card("Hearts","7");
        assertEquals(7, card.getValue());
    }
    @Test
    void cardSpadesNumber3EqualThree(){
        Card card = new Card("Spades","3");
        assertEquals(3, card.getValue());
    }
    @Test
    void cardDiamondNumber5EqualFive(){
        Card card = new Card("Diamonds","5");
        assertEquals(5, card.getValue());
    }
    @Test
    void cardNumber9EqualCero(){
        Card card = new Card("Diamonds","9");
        assertEquals(0, card.getValue());
    }
    @Test
    void cardJEqualNegativeTen(){
        Card card = new Card("Clubs","J");
        assertEquals(-10, card.getValue());
    }
    @Test
    void cardQEqualNegativeTen(){
        Card card = new Card("Hearts","Q");
        assertEquals(-10, card.getValue());
    }
    @Test
    void cardKEqualNegativeTen(){
        Card card = new Card("Diamonds","K");
        assertEquals(-10, card.getValue());
    }
    @Test
    void cardAEqualOne(){
        Card card = new Card("Clubs","A");
        assertEquals(1, card.getOptimalValue(45));
    }
    @Test
    void cardAEqualTen(){
        Card card = new Card("Clubs","A");
        assertEquals(10, card.getOptimalValue(30));
    }
    @Test
    void testCardSpades2String(){
        Card card = new Card("Spades","2");
        assertEquals("2♠", card.toString());
    }
    @Test
    void testCardHeart4String(){
        Card card = new Card("Hearts","4");
        assertEquals("4♥", card.toString());
    }
    @Test
    void testCardDiamond6String(){
        Card card = new Card("Diamonds","6");
        assertEquals("6♦", card.toString());
    }
    @Test
    void testCardClubs8String(){
        Card card = new Card("Clubs","8");
        assertEquals("8♣", card.toString());
    }
    @Test
    void testCardSpadeJString(){
        Card card = new Card("Spades","J");
        assertEquals("J♠", card.toString());
    }
    @Test
    void testCardHeartQString(){
        Card card = new Card("Hearts","Q");
        assertEquals("Q♥", card.toString());
    }
    @Test
    void testCardDiamondKString(){
        Card card = new Card("Diamonds","K");
        assertEquals("K♦", card.toString());
    }
    @Test
    void testCardClubAString(){
        Card card = new Card("Clubs","A");
        assertEquals("A♣", card.toString());
    }
    @Test
    void testCardUnknownSuitString(){
        Card card = new Card("Apple","A");
        assertEquals("A?", card.toString());
    }

}