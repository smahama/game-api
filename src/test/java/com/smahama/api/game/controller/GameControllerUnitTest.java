package com.smahama.api.game.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smahama.api.game.dto.CreateDeckRequest;
import com.smahama.api.game.dto.CreateGameRequest;
import com.smahama.api.game.dto.CreatePlayerRequest;
import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Deal;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.model.Player;
import com.smahama.api.game.service.DealService;
import com.smahama.api.game.service.DeckService;
import com.smahama.api.game.service.GameService;
import com.smahama.api.game.service.PlayerService;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerUnitTest {

    private static final Integer GAME_ID = 123;

    private static final Integer PLAYER_ID = 1;

    private static final String GAME_NAME = "GAME NAME";

    private static final String DECK_NAME = "DECK NAME";

    private static final String PLAYER_NAME = "PLAYER NAME";

    private static final String GAME_1_NOT_FOUND = "Game id:[1] not found";

    private static final String PLAYER_1_NOT_FOUND = "Player id:[1] not found";

    private static final String BASE_V1_PATH = "/api/v1";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private DeckService deckService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private DealService dealService;

    @Autowired
    private ObjectMapper objectMapper;

    public GameControllerUnitTest() {

        //
    }

    @Test
    public void testCreateGame()
        throws Exception {

        final var createGameRequest = givenCreateGameRequest();
        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.post(BASE_V1_PATH + "/games").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createGameRequest));
        this.mvc.perform(accept).andExpect(status().isCreated());
    }

    @Test
    public void testDeleteGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_V1_PATH + "/games/1");
        doNothing().when(this.gameService).deleteGame(anyInt());
        this.mvc.perform(accept).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteGameForGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_V1_PATH + "/games/1");
        doThrow(new GameNotFoundException(1)).when(this.gameService).deleteGame(anyInt());
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testAddDeckToGame()
        throws Exception {

        final var createDeckRequest = givenCreateDeckRequest();
        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.post(BASE_V1_PATH + "/games/1/decks").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createDeckRequest));
        this.mvc.perform(accept).andExpect(status().isCreated());
    }

    @Test
    public void testAddDeckToGameNotFound()
        throws Exception {

        final var createDeckRequest = givenCreateDeckRequest();
        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.post(BASE_V1_PATH + "/games/1/decks").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createDeckRequest));

        when(this.gameService.addDeck(anyInt(), any(Deck.class))).thenThrow(new GameNotFoundException(1));
        when(this.gameService.addDeck(anyInt(), isNull())).thenThrow(new GameNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testAddPlayerToGame()
        throws Exception {

        final var createPlayerRequest = givenCreatePlayerRequest();
        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.post(BASE_V1_PATH + "/games/1/players").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createPlayerRequest));
        this.mvc.perform(accept).andExpect(status().isCreated());
    }

    @Test
    public void testAddPlayerToGameNotFound()
        throws Exception {

        final var createPlayerRequest = givenCreatePlayerRequest();
        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.post(BASE_V1_PATH + "/games/1/players").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createPlayerRequest));

        when(this.gameService.addPlayer(anyInt(), any(Player.class))).thenThrow(new GameNotFoundException(1));
        when(this.gameService.addPlayer(anyInt(), isNull())).thenThrow(new GameNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testDeletePlayerFromGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_V1_PATH + "/games/1/players/1");
        this.mvc.perform(accept).andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePlayerFromNotFoundGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_V1_PATH + "/games/1/players/1");

        doThrow(new GameNotFoundException(1)).when(this.gameService).removePlayer(anyInt(), any(Player.class));
        doThrow(new GameNotFoundException(1)).when(this.gameService).removePlayer(anyInt(), isNull());
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testDeleteUnknownPlayerFromGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_V1_PATH + "/games/1/players/1");
        when(this.playerService.getPlayer(anyInt())).thenThrow(new PlayerNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(PLAYER_1_NOT_FOUND));
    }

    @Test
    public void testDealCards()
        throws Exception {

        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.put(BASE_V1_PATH + "/games/1/players/1/cards");
        when(this.dealService.dealCards(anyInt(), anyInt())).thenReturn(givenDeal());
        this.mvc.perform(accept).andExpect(status().isOk());
    }

    @Test
    public void testDealCardsNoDeal()
        throws Exception {

        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.put(BASE_V1_PATH + "/games/1/players/1/cards");
        when(this.dealService.dealCards(anyInt(), anyInt())).thenReturn(null);
        this.mvc.perform(accept).andExpect(status().isNoContent());
    }

    @Test
    public void testDealCardsFromNotFoundGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.put(BASE_V1_PATH + "/games/1/players/1/cards");
        when(this.dealService.dealCards(anyInt(), anyInt())).thenThrow(new GameNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testDealCardsForUnknownPlayer()
        throws Exception {

        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.put(BASE_V1_PATH + "/games/1/players/1/cards");
        when(this.dealService.dealCards(anyInt(), anyInt())).thenThrow(new PlayerNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(PLAYER_1_NOT_FOUND));
    }

    @Test
    public void testGetPlayerCards()
        throws Exception {

        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/players/1/cards");
        this.mvc.perform(accept).andExpect(status().isOk());
    }

    @Test
    public void testGetPlayerCardsFromNotFoundGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/players/1/cards");
        when(this.dealService.getPlayerCards(anyInt(), anyInt())).thenThrow(new GameNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testGetPlayerCardsForUnknownPlayer()
        throws Exception {

        final MockHttpServletRequestBuilder accept =
            MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/players/1/cards");
        when(this.dealService.getPlayerCards(anyInt(), anyInt())).thenThrow(new PlayerNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(PLAYER_1_NOT_FOUND));
    }

    @Test
    public void testListPlayers()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/players");
        this.mvc.perform(accept).andExpect(status().isOk());
    }

    @Test
    public void testListPlayersFromNotFoundGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/players");
        when(this.dealService.getPlayersCards(anyInt())).thenThrow(new GameNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testListSuits()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/suits");
        this.mvc.perform(accept).andExpect(status().isOk());
    }

    @Test
    public void testListSuitsFromNotFoundGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/suits");
        when(this.gameService.getCardCountBySuit(anyInt())).thenThrow(new GameNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void testListCards()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/cards");
        this.mvc.perform(accept).andExpect(status().isOk());
    }

    @Test
    public void testListCardsFromNotFoundGame()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_V1_PATH + "/games/1/cards");
        when(this.gameService.getCardCount(anyInt())).thenThrow(new GameNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().isNotFound()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    private CreateGameRequest givenCreateGameRequest() {

        final var createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName(GAME_NAME);

        return createGameRequest;
    }

    private CreateDeckRequest givenCreateDeckRequest() {

        final var createDeckRequest = new CreateDeckRequest();
        createDeckRequest.setDeckName(DECK_NAME);

        return createDeckRequest;
    }

    private CreatePlayerRequest givenCreatePlayerRequest() {

        final var createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setPlayerName(PLAYER_NAME);

        return createPlayerRequest;
    }

    private Deal givenDeal() {

        return new Deal(PLAYER_ID, GAME_ID, null);
    }
}
