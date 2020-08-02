package com.smahama.api.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Card;
import com.smahama.api.game.model.Deal;
import com.smahama.api.game.model.Game;
import com.smahama.api.game.model.Player;
import com.smahama.api.game.repository.DealRepository;
import com.smahama.api.game.repository.GameRepository;
import com.smahama.api.game.service.impl.DealServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DealServiceImplUnitTest {

    private static final Integer PLAYER_ID = 1;

    private static final Integer GAME_ID = 123;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private DealServiceImpl dealService;

    @Mock
    private Game game;

    @Mock
    private Card card;

    @Mock
    private Deal deal;

    @Mock
    private Player player;

    public DealServiceImplUnitTest() {

        //
    }

    @Test
    public void testDealCards()
        throws GameNotFoundException,
        PlayerNotFoundException {

        // given
        when(this.player.getPlayerId()).thenReturn(PLAYER_ID);
        when(this.game.getPlayers()).thenReturn(List.of(this.player));
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));
        when(this.gameRepository.getNextCard(any())).thenReturn(this.card);
        when(this.dealRepository.createDeal(anyInt(), anyInt(), any(Card.class))).thenReturn(this.deal);

        // when
        final var newDeal = this.dealService.dealCards(GAME_ID, PLAYER_ID);

        // then
        assertEquals(this.deal, newDeal);
        verify(this.gameRepository).getNextCard(eq(this.game));
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.dealRepository).createDeal(eq(GAME_ID), eq(PLAYER_ID), eq(this.card));
    }

    @Test
    public void testDealCardsGameNotFound()
        throws GameNotFoundException,
        PlayerNotFoundException {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.empty());

        // when
        assertThrows(GameNotFoundException.class, () -> this.dealService.dealCards(GAME_ID, PLAYER_ID));

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.dealRepository, never()).createDeal(anyInt(), anyInt(), any(Card.class));
    }

    @Test
    public void testDealCardsPlayerNotFound() {

        // given
        when(this.game.getPlayers()).thenReturn(Collections.emptyList());
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        // when
        assertThrows(PlayerNotFoundException.class, () -> this.dealService.dealCards(GAME_ID, PLAYER_ID));

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.dealRepository, never()).createDeal(anyInt(), anyInt(), any(Card.class));
    }

    @Test
    public void testDealCardsNoDeal()
        throws GameNotFoundException,
        PlayerNotFoundException {

        // given
        when(this.player.getPlayerId()).thenReturn(PLAYER_ID);
        when(this.game.getPlayers()).thenReturn(List.of(this.player));
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));
        when(this.gameRepository.getNextCard(any())).thenReturn(null);

        // when
        final var newDeal = this.dealService.dealCards(GAME_ID, PLAYER_ID);

        // then
        assertNull(newDeal);
        verify(this.gameRepository).getNextCard(eq(this.game));
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.dealRepository, never()).createDeal(anyInt(), anyInt(), any(Card.class));
    }

    @Test
    public void testGetPlayerCards()
        throws GameNotFoundException,
        PlayerNotFoundException {

        // given
        when(this.player.getPlayerId()).thenReturn(PLAYER_ID);
        when(this.game.getPlayers()).thenReturn(List.of(this.player));
        when(this.game.getGameId()).thenReturn(GAME_ID);
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        when(this.deal.getGameId()).thenReturn(GAME_ID);
        when(this.deal.getPlayerId()).thenReturn(PLAYER_ID);
        when(this.deal.getCard()).thenReturn(this.card);

        when(this.dealRepository.getAllDeals()).thenReturn(List.of(this.deal));

        // when
        final var playerCards = this.dealService.getPlayerCards(GAME_ID, PLAYER_ID);

        // then
        assertEquals(1, playerCards.getCards().size());
        assertEquals(this.card, playerCards.getCards().get(0));
    }

    @Test
    public void testGetPlayerCardsNoDealForPlayer()
        throws GameNotFoundException,
        PlayerNotFoundException {

        // given
        when(this.player.getPlayerId()).thenReturn(PLAYER_ID);
        when(this.game.getPlayers()).thenReturn(List.of(this.player));
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        when(this.dealRepository.getAllDeals()).thenReturn(List.of(this.deal));

        // when
        final var playerCards = this.dealService.getPlayerCards(GAME_ID, PLAYER_ID);

        // then
        assertEquals(Collections.emptyList(), playerCards.getCards());
    }

    @Test
    public void testGetPlayerCardsGameNotFound()
        throws GameNotFoundException,
        PlayerNotFoundException {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.empty());

        // when && Then
        assertThrows(GameNotFoundException.class, () -> this.dealService.getPlayerCards(GAME_ID, PLAYER_ID));

    }

    @Test
    public void testGetPlayerCardsPlayerNotFound() {

        // given
        when(this.game.getPlayers()).thenReturn(Collections.emptyList());
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        // when && Then
        assertThrows(PlayerNotFoundException.class, () -> this.dealService.getPlayerCards(GAME_ID, PLAYER_ID));
    }

    @Test
    public void testGetPlayersCards()
        throws GameNotFoundException {

        // given
        when(this.player.getPlayerId()).thenReturn(PLAYER_ID);
        when(this.game.getPlayers()).thenReturn(List.of(this.player));
        when(this.game.getGameId()).thenReturn(GAME_ID);
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        when(this.deal.getGameId()).thenReturn(GAME_ID);
        when(this.deal.getPlayerId()).thenReturn(PLAYER_ID);
        when(this.deal.getCard()).thenReturn(this.card);

        when(this.dealRepository.getAllDeals()).thenReturn(List.of(this.deal));

        // when
        final var playersCards = this.dealService.getPlayersCards(GAME_ID);

        // then
        assertEquals(1, playersCards.size());
        assertEquals(1, playersCards.get(0).getCards().size());
        assertEquals(PLAYER_ID, playersCards.get(0).getPlayerId());
        assertEquals(this.card, playersCards.get(0).getCards().get(0));
    }

    @Test
    public void testGetPlayersCardsGameNotFound()
        throws GameNotFoundException,
        PlayerNotFoundException {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.empty());

        // when && Then
        assertThrows(GameNotFoundException.class, () -> this.dealService.getPlayersCards(GAME_ID));
    }

}
