package com.smahama.api.game.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.smahama.api.game.model.Card;

public class PlayerCards
    implements Comparable<PlayerCards> {

    private final Integer playerId;

    private List<Card> cards = new ArrayList<>();

    public PlayerCards(final Integer pPlayerId, final List<Card> pCards) {

        this.playerId = pPlayerId;
        this.cards = pCards;
    }

    public int getTotalValue() {

        return this.cards.stream().mapToInt(card -> card.getRank().getValue()).sum();
    }

    public Integer getPlayerId() {

        return this.playerId;
    }

    public List<Card> getCards() {

        return this.cards;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.playerId);
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
        final PlayerCards other = (PlayerCards) obj;
        return Objects.equals(this.playerId, other.playerId);
    }

    @Override
    public int compareTo(
        final PlayerCards pPlayerCards) {

        return Integer.valueOf(getTotalValue()).compareTo(pPlayerCards.getTotalValue());
    }
}
