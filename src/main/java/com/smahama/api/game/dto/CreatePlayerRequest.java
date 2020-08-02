package com.smahama.api.game.dto;

public class CreatePlayerRequest {

    private String playerName;

    public CreatePlayerRequest() {

        //
    }

    public String getPlayerName() {

        return this.playerName;
    }

    public void setPlayerName(
        final String pDeckName) {

        this.playerName = pDeckName;
    }
}
