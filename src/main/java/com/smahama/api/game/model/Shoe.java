package com.smahama.api.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {

    private final List<Deck> decks = new ArrayList<>();

    private final List<Card> cards = Collections.synchronizedList(new ArrayList<>());

    public Shoe() {

        //
    }

    public void addDeck(
        final Deck deck) {

        this.decks.add(deck);
        this.cards.addAll(deck.getCards());
    }

    public List<Deck> getDecks() {

        return this.decks;
    }

    public List<Card> getCards() {

        return this.cards;
    }
}
