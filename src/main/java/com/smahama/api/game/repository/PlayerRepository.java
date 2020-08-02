package com.smahama.api.game.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.smahama.api.game.model.Player;

/**
 * Repository for {@link Player}
 * @author Salomon Mahama
 *
 */
@Repository
public class PlayerRepository {

    private final List<Player> players = Collections.synchronizedList(new ArrayList<>());

    private final AtomicInteger playerIdsHolder = new AtomicInteger(1);

    private PlayerRepository() {

        //
    }

    public Player createPlayer(
        final String playerName) {

        final var player = new Player(this.playerIdsHolder.getAndIncrement());
        player.setPlayerName(playerName);
        this.players.add(player);

        return player;
    }

    public Optional<Player> getPlayer(
        final Integer playerId) {

        return this.players.stream().filter(player -> player.getPlayerId().equals(playerId)).findFirst();
    }

    public void deletePlayer(
        final Player player) {

        this.players.remove(player);
    }

    public List<Player> getAllPlayers() {

        return Collections.unmodifiableList(this.players);
    }
}
