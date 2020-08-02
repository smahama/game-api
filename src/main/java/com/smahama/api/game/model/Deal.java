package com.smahama.api.game.model;

public class Deal {

    private final Integer playerId;

    private final Integer gameId;

    private final Card card;

    public Deal(final Integer pPlayerId, final Integer pGameId, final Card pCard) {

        this.playerId = pPlayerId;
        this.gameId = pGameId;
        this.card = pCard;
    }

    public Integer getPlayerId() {

        return this.playerId;
    }

    public Integer getGameId() {

        return this.gameId;
    }

    public Card getCard() {

        return this.card;
    }
}
