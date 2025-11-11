package com.example.cincuentazo.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    void deckStartsWith52Cards() {
        Deck deck = new Deck();
        assertEquals(52, deck.size());
    }
    @Test
    void deckIsEmptyWhenDealsAllCards() {
        Deck deck = new Deck();
        for(int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertTrue(deck.isEmpty());
    }
    @Test
    void testDeckAfterRemoveSomeCards1() {
        Deck deck = new Deck();
        for(int i = 0; i < 1; i++) {
            deck.dealCard();
        }
        assertEquals(51, deck.size());
    }
    @Test
    void testDeckAfterRemoveSomeCards5() {
        Deck deck = new Deck();
        for(int i = 0; i < 5; i++) {
            deck.dealCard();
        }
        assertEquals(47, deck.size());
    }
    @Test
    void testDeckAfterRemoveSomeCards10() {
        Deck deck = new Deck();
        for(int i = 0; i < 10; i++) {
            deck.dealCard();
        }
        assertEquals(42, deck.size());
    }
    @Test
    void testDeckAfterRemoveSomeCards32() {
        Deck deck = new Deck();
        for(int i = 0; i < 32; i++) {
            deck.dealCard();
        }
        assertEquals(20, deck.size());
    }
    @Test
    void dealCardAfterRemoveAllCardsReturnException() {
        Deck deck = new Deck();
        for(int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertThrows(CincuentazoException.class, () -> {
            deck.dealCard();
        });

    }
    @Test
    void deckSizeAfterAddCards() {
        Deck deck = new Deck();
        for(int i = 0; i < 3; i++) {
            deck.dealCard();
        }
        deck.addCardsToBottom(List.of(new Card("Hearts", "A")));
        deck.addCardsToBottom(List.of(new Card("Clubs", "7")));

        assertEquals(51, deck.size());

    }

}