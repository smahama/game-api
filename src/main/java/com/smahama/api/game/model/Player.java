package com.smahama.api.game.model;

public class Player {

    private final Integer playerId;

    private String playerName;

    public Player(final Integer pPlayerId) {

        this.playerId = pPlayerId;
    }

    public String getPlayerName() {

        return this.playerName;
    }

    public void setPlayerName(
        final String pPlayerName) {

        this.playerName = pPlayerName;
    }

    public Integer getPlayerId() {

        return this.playerId;
    }
}
