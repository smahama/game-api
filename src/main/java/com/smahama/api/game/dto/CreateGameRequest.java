package com.smahama.api.game.dto;

public class CreateGameRequest {

    private String gameName;

    public CreateGameRequest() {

        //
    }

    public String getGameName() {

        return this.gameName;
    }

    public void setGameName(
        final String pDeckName) {

        this.gameName = pDeckName;
    }
}
