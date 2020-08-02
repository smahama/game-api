package com.smahama.api.game.controller;

import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smahama.api.game.dto.CreateDeckRequest;
import com.smahama.api.game.dto.CreateGameRequest;
import com.smahama.api.game.dto.CreatePlayerRequest;
import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Deal;
import com.smahama.api.game.model.Game;
import com.smahama.api.game.service.DealService;
import com.smahama.api.game.service.DeckService;
import com.smahama.api.game.service.GameService;
import com.smahama.api.game.service.PlayerService;

/**
 * REST API Entry point
 * @author Salomon Mahama
 *
 */
@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private DeckService deckService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private DealService dealService;

    public GameController() {

        //
    }

    /**
     * POST method to create a Game
     * @param createGameRequest data to create a new game
     * @return HTTP status 201, and newly created game
     */
    @PostMapping("/v1/games")
    public ResponseEntity<Game> createGame(
        @RequestBody final CreateGameRequest createGameRequest) {

        final var game = this.gameService.createGame(createGameRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    /**
     * DELETE method to delete a Game
     * @param gameId the game id
     * @return HTTP status 204 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @DeleteMapping("/v1/games/{gameId}")
    public ResponseEntity<String> deleteGame(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.deleteGame(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * POST method to create a Deck and add it to the Game
     * @param gameId the game id
     * @param createDeckRequest  data to create a new deck
     * @return HTTP status 201 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/v1/games/{gameId}/decks")
    public ResponseEntity<?> addDeck(
        @PathVariable final Integer gameId,
        @RequestBody final CreateDeckRequest createDeckRequest) {

        try {
            final var deck = this.deckService.createDeck(createDeckRequest);
            final var game = this.gameService.addDeck(gameId, deck);
            return ResponseEntity.status(HttpStatus.CREATED).body(game);
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }
    }

    /**
     * POST method to create a Player and add it to the Game
     * @param gameId the game id
     * @param createPlayerRequest   data to create a new Player
     * @return HTTP status 201 and the object Player if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/v1/games/{gameId}/players")
    public ResponseEntity<?> addPlayer(
        @PathVariable final Integer gameId,
        @RequestBody final CreatePlayerRequest createPlayerRequest) {

        try {
            final var player = this.playerService.createPlayer(createPlayerRequest);
            final var game = this.gameService.addPlayer(gameId, player);
            return ResponseEntity.status(HttpStatus.CREATED).body(game);
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }
    }

    /**
     * DELETE method to delete a Player from the Game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 204 if OK or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @DeleteMapping("/v1/games/{gameId}/players/{playerId}")
    public ResponseEntity<String> deletePlayer(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            final var player = this.playerService.getPlayer(playerId);
            this.gameService.removePlayer(gameId, player);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * PUT method to deal a card to a Player from the Game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 200 with there was a deal, 204 if no deal or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @PutMapping("/v1/games/{gameId}/players/{playerId}/cards")
    public ResponseEntity<?> dealCards(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            final Deal deal = this.dealService.dealCards(gameId, playerId);
            if (deal != null) {
                return ResponseEntity.status(HttpStatus.OK).body(deal);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * GET method to get all cards dealt for a player in game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 200 with there was a deal, 204 if no deal or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @GetMapping("/v1/games/{gameId}/players/{playerId}/cards")
    public ResponseEntity<?> getPlayerCards(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            final var playerCards = this.dealService.getPlayerCards(gameId, playerId);
            return ResponseEntity.status(HttpStatus.OK).body(playerCards);
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * GET method to get all Players from the Game
     * @param gameId the game id
     * @return HTTP status 200 with the list of Players if OK or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @GetMapping("/v1/games/{gameId}/players")
    public ResponseEntity<?> listPlayers(
        @PathVariable final Integer gameId) {

        try {
            final var playersCards = this.dealService.getPlayersCards(gameId);
            Collections.sort(playersCards, Comparator.reverseOrder());
            return ResponseEntity.status(HttpStatus.OK).body(playersCards);
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }
    }

    /**
     * GET method to list suits and their remainging cards count in the game
     * @param gameId the game id
     * @return HTTP status 200 with the list of cards if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @GetMapping("/v1/games/{gameId}/suits")
    public ResponseEntity<?> listSuits(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getCardCountBySuit(gameId));
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }
    }

    /**
     * GET method to list remaining cards in the game
     * @param gameId the game id
     * @return HTTP status 200 with the list of remaining cards if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @GetMapping("/v1/games/{gameId}/cards")
    public ResponseEntity<?> listCards(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getCardCount(gameId));
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }
    }

    /**
     * POST method to shuffle all cards in the game
     * @param gameId the game id
     * @return HTTP status 204 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/v1/games/{gameId}/shuffle")
    public ResponseEntity<?> shuffle(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.shuffle(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }
    }
}
