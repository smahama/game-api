package com.smahama.api.game.dto;

public class CreateDeckRequest {

    private String deckName;

    public CreateDeckRequest() {

        //
    }

    public String getDeckName() {

        return this.deckName;
    }

    public void setDeckName(
        final String pDeckName) {

        this.deckName = pDeckName;
    }
}
