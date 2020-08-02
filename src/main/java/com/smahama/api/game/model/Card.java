package com.smahama.api.game.model;

import java.util.Comparator;
import java.util.Objects;

public class Card
    implements Comparable<Card> {

    private final Integer cardId;

    private final Suit suit;

    private final Rank rank;

    public Card(final Integer pCardId, final Suit pSuit, final Rank pRank) {

        this.cardId = pCardId;
        this.rank = pRank;
        this.suit = pSuit;
    }

    public Integer getCardId() {

        return this.cardId;
    }

    public Suit getSuit() {

        return this.suit;
    }

    public Rank getRank() {

        return this.rank;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.cardId);
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
        final Card other = (Card) obj;
        return Objects.equals(this.cardId, other.cardId);
    }

    @Override
    public int compareTo(
        final Card pCard) {

        return Comparator.comparing(Card::getSuit)
            .thenComparing(card -> card.getRank().getValue(), Comparator.reverseOrder()).compare(this, pCard);
    }
}
