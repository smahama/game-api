package com.smahama.api.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smahama.api.game.dto.CreateGameRequest;
import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.helper.ListShuffleHelper;
import com.smahama.api.game.model.Card;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.model.Game;
import com.smahama.api.game.model.Player;
import com.smahama.api.game.model.Rank;
import com.smahama.api.game.model.Shoe;
import com.smahama.api.game.model.Suit;
import com.smahama.api.game.repository.DeckRepository;
import com.smahama.api.game.repository.GameRepository;
import com.smahama.api.game.service.impl.GameServiceImpl;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplUnitTest {

    private static final String GAME_NAME = "GAME NAME";

    private static final Integer GAME_ID = 123;

    private static final Integer PLAYER_ID = 1;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private ListShuffleHelper listShuffleHelper;

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private Game game;

    @Mock
    private Deck deck;

    @Mock
    private Shoe shoe;

    @Mock
    private Player player;

    public GameServiceImplUnitTest() {

        //
    }

    @Test
    public void testCreateGame() {

        // given
        when(this.gameRepository.createGame(anyString())).thenReturn(this.game);
        final var createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName(GAME_NAME);

        // when
        final var newGame = this.gameService.createGame(createGameRequest);

        // then
        assertEquals(this.game, newGame);
        verify(this.gameRepository).createGame(eq(GAME_NAME));
    }

    @Test
    public void testDeleteGame()
        throws GameNotFoundException {

        // given
        when(this.game.getGameId()).thenReturn(GAME_ID);

        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        // when
        this.gameService.deleteGame(GAME_ID);

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.gameRepository).deleteGame(eq(GAME_ID));
    }

    @Test
    public void testDeleteNotFoundGame() {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.empty());

        // when && then
        assertThrows(GameNotFoundException.class, () -> this.gameService.deleteGame(GAME_ID));

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.deckRepository, never()).deleteDeck(any(Deck.class));
        verify(this.gameRepository, never()).deleteGame(anyInt());
    }

    @Test
    public void testAddDeck()
        throws GameNotFoundException {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        // when
        this.gameService.addDeck(GAME_ID, this.deck);

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.gameRepository).addDeck(eq(this.game), eq(this.deck));
    }

    @Test
    public void testAddDeckToNotFoundGame() {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.empty());

        // when && then
        assertThrows(GameNotFoundException.class, () -> this.gameService.addDeck(GAME_ID, this.deck));

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.gameRepository, never()).addDeck(any(Game.class), any(Deck.class));
    }

    @Test
    public void testAddPlayer()
        throws GameNotFoundException {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));

        // when
        this.gameService.addPlayer(GAME_ID, this.player);

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.gameRepository).addPlayer(eq(this.game), eq(this.player));
    }

    @Test
    public void testAddPlayerToNotFoundGame() {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.empty());

        // when && then
        assertThrows(GameNotFoundException.class, () -> this.gameService.addPlayer(GAME_ID, this.player));

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.gameRepository, never()).addPlayer(any(Game.class), any(Player.class));
    }

    @Test
    public void testRemovePlayer()
        throws GameNotFoundException {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));
        when(this.player.getPlayerId()).thenReturn(PLAYER_ID);
        // when
        this.gameService.removePlayer(GAME_ID, this.player);

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.gameRepository).removePlayer(eq(this.game), eq(PLAYER_ID));
    }

    @Test
    public void testRemovePlayerFromNotFoundGame() {

        // given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.empty());

        // when && then
        assertThrows(GameNotFoundException.class, () -> this.gameService.removePlayer(GAME_ID, this.player));

        // then
        verify(this.gameRepository).getGame(eq(GAME_ID));
        verify(this.gameRepository, never()).removePlayer(any(Game.class), anyInt());
    }

    @Test
    public void testShuffle()
        throws GameNotFoundException {

        // Given
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(this.game));
        when(this.game.getShoe()).thenReturn(this.shoe);
        // When
        this.gameService.shuffle(this.game.getGameId());
        // Then
        verify(this.listShuffleHelper).shuffle(anyList());
    }

    @Test
    public void testGetCardCountBySuit()
        throws GameNotFoundException {

        // given
        final var sampleGame = createGame();
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(sampleGame));

        // when
        final var suitCounts = this.gameService.getCardCountBySuit(GAME_ID);

        // then
        assertEquals(2, suitCounts.size());
        assertTrue(suitCounts.stream()
            .anyMatch(suitCount -> suitCount.getSuit() == Suit.DIAMONDS && suitCount.getCount().get() == 2));
        assertTrue(suitCounts.stream()
            .anyMatch(suitCount -> suitCount.getSuit() == Suit.CLUBS && suitCount.getCount().get() == 1));
    }

    @Test

    public void testGetCardCount()
        throws GameNotFoundException {

        // given
        final var sampleGame = createGame();
        when(this.gameRepository.getGame(anyInt())).thenReturn(Optional.of(sampleGame));

        // when
        final var cardCounts = this.gameService.getCardCount(GAME_ID);

        // then
        assertEquals(3, cardCounts.size());
        assertTrue(cardCounts.stream()
            .anyMatch(cardCount -> cardCount.getCard().getRank() == Rank.KING
                && cardCount.getCard().getSuit() == Suit.DIAMONDS
                && cardCount.getCount().get() == 1));
        assertTrue(cardCounts.stream()
            .anyMatch(cardCount -> cardCount.getCard().getRank() == Rank.QUEEN
                && cardCount.getCard().getSuit() == Suit.CLUBS
                && cardCount.getCount().get() == 1));
        assertTrue(cardCounts.stream()
            .anyMatch(cardCount -> cardCount.getCard().getRank() == Rank.FIVE
                && cardCount.getCard().getSuit() == Suit.DIAMONDS
                && cardCount.getCount().get() == 1));
    }

    private Game createGame() {

        final Deck deck = new Deck(1);
        deck.getCards().add(new Card(1, Suit.CLUBS, Rank.QUEEN));
        deck.getCards().add(new Card(2, Suit.DIAMONDS, Rank.KING));

        final Deck deck2 = new Deck(2);
        deck2.getCards().add(new Card(3, Suit.DIAMONDS, Rank.FIVE));

        final Game game = new Game(GAME_ID);
        game.setShoe(new Shoe());
        game.getShoe().addDeck(deck);
        game.getShoe().addDeck(deck2);

        return game;
    }
}
