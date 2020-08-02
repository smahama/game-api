package com.smahama.api.game.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final Integer deckId;

    private String deckName;

    private final List<Card> cards = new ArrayList<>();

    public Deck(final Integer pDeckId) {

        this.deckId = pDeckId;
    }

    public String getDeckName() {

        return this.deckName;
    }

    public void setDeckName(
        final String pDeckName) {

        this.deckName = pDeckName;
    }

    public List<Card> getCards() {

        return this.cards;
    }

    public Integer getDeckId() {

        return this.deckId;
    }
}
