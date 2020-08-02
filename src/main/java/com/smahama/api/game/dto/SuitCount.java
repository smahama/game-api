package com.smahama.api.game.dto;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.smahama.api.game.model.Suit;

public class SuitCount {

    private Suit suit;

    private final AtomicInteger count = new AtomicInteger(0);

    public SuitCount() {

        //
    }

    public Suit getSuit() {

        return this.suit;
    }

    public void setSuit(
        final Suit pSuit) {

        this.suit = pSuit;
    }

    public AtomicInteger getCount() {

        return this.count;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.suit);
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
        final SuitCount other = (SuitCount) obj;
        return Objects.equals(this.suit, other.suit);
    }
}
