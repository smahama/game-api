package com.smahama.api.game.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerRepositoryUnitTest {

    private static final String PLAYER_NAME = "PLAYER NAME";

    @InjectMocks
    private PlayerRepository playerRepository;

    public PlayerRepositoryUnitTest() {

        //
    }

    @Test
    public void testGetAllPlayers() {

        assertEquals(Collections.emptyList(), this.playerRepository.getAllPlayers());
    }

    @Test
    public void testCreatePlayer() {

        // when
        final var player = this.playerRepository.createPlayer(PLAYER_NAME);

        // then
        assertNotNull(player);
        assertNotNull(player.getPlayerId());
        assertEquals(PLAYER_NAME, player.getPlayerName());

        final var players = this.playerRepository.getAllPlayers();
        assertEquals(players.get(0), player);
        assertEquals(1, players.size());
    }

    @Test
    public void testDeletePlayer() {

        // given
        final var player = this.playerRepository.createPlayer(PLAYER_NAME);

        // when
        this.playerRepository.deletePlayer(player);

        // then
        assertEquals(Collections.emptyList(), this.playerRepository.getAllPlayers());
    }

    @Test
    public void testGetPlayer() {

        // given
        final var player = this.playerRepository.createPlayer(PLAYER_NAME);

        // when
        final var getPlayer = this.playerRepository.getPlayer(player.getPlayerId());

        // then
        assertEquals(player, getPlayer.get());
    }
}
