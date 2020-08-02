package com.smahama.api.game.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Integer gameId;

    private String gameName;

    private Shoe shoe;

    private final List<Player> players = new ArrayList<>();

    public Game(final Integer pGameId) {

        this.gameId = pGameId;
    }

    public Integer getGameId() {

        return this.gameId;
    }

    public String getGameName() {

        return this.gameName;
    }

    public void setGameName(
        final String pGameName) {

        this.gameName = pGameName;
    }

    public Shoe getShoe() {

        return this.shoe;
    }

    public void setShoe(
        final Shoe pShoe) {

        this.shoe = pShoe;
    }

    public List<Player> getPlayers() {

        return this.players;
    }
}
