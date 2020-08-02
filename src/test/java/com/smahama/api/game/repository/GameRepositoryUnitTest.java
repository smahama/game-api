package com.smahama.api.game.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smahama.api.game.model.Card;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.model.Player;

@ExtendWith(MockitoExtension.class)
public class GameRepositoryUnitTest {

    private static final String GAME_NAME = "GAME NAME";

    private static final Integer PLAYER_ID = 1;

    @InjectMocks
    private GameRepository gameRepository;

    @Mock
    private Deck deck;

    @Mock
    private Player player;

    @Mock
    private Card card;

    public GameRepositoryUnitTest() {

        //
    }

    @Test
    public void testCreateGame() {

        // when
        final var game = this.gameRepository.createGame(GAME_NAME);

        // then
        assertNotNull(game);
        assertNotNull(game.getGameId());
        assertNotNull(game.getShoe());
        assertEquals(GAME_NAME, game.getGameName());
        assertEquals(Collections.emptyList(), game.getShoe().getCards());

        final var games = this.gameRepository.getAllGames();
        assertEquals(games.get(0), game);
        assertEquals(1, games.size());
    }

    @Test
    public void testGetGame() {

        // given
        final var game = this.gameRepository.createGame(GAME_NAME);

        // when
        final var getGame = this.gameRepository.getGame(game.getGameId());

        // then
        assertEquals(game, getGame.get());
    }

    @Test
    public void testDeleteGame() {

        // given
        final var game = this.gameRepository.createGame(GAME_NAME);

        // when
        this.gameRepository.deleteGame(game.getGameId());

        // then
        assertEquals(Collections.emptyList(), this.gameRepository.getAllGames());
    }

    @Test
    public void testAddDeck() {

        // given
        final var game = this.gameRepository.createGame(GAME_NAME);
        when(this.deck.getCards()).thenReturn(List.of(this.card));
        // when
        this.gameRepository.addDeck(game, this.deck);

        // then
        final var shoe = game.getShoe();
        assertEquals(1, shoe.getCards().size());
        assertEquals(this.card, shoe.getCards().get(0));
    }

    @Test
    public void testAddPlayer() {

        // given
        final var game = this.gameRepository.createGame(GAME_NAME);

        // when
        this.gameRepository.addPlayer(game, this.player);

        // then
        final var gamePlayers = game.getPlayers();
        assertEquals(1, gamePlayers.size());
        assertTrue(gamePlayers.contains(this.player));
    }

    @Test
    public void testRemovePlayer() {

        // given
        when(this.player.getPlayerId()).thenReturn(PLAYER_ID);

        final var game = this.gameRepository.createGame(GAME_NAME);
        this.gameRepository.addPlayer(game, this.player);

        // when
        this.gameRepository.removePlayer(game, PLAYER_ID);

        // then
        final var gamePlayers = game.getPlayers();
        assertEquals(Collections.emptyList(), gamePlayers);
    }
}
