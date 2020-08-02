package com.smahama.api.game.dto;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.smahama.api.game.model.Card;

public class CardCount {

    private Card card;

    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * Default constructor
     */
    public CardCount() {

        super();
    }

    public Card getCard() {

        return this.card;
    }

    public void setCard(
        final Card card) {

        this.card = card;
    }

    public AtomicInteger getCount() {

        return this.count;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.card);
    }

    @Override
    public boolean equals(
        final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CardCount other = (CardCount) obj;
        return Objects.equals(this.card, other.card);
    }

}
