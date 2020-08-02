package com.smahama.api.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {

    private final List<Card> cards = Collections.synchronizedList(new ArrayList<>());

    public Shoe() {

        //
    }

    public void addDeck(
        final Deck deck) {

        this.cards.addAll(deck.getCards());
    }

    public List<Card> getCards() {

        return this.cards;
    }
}
