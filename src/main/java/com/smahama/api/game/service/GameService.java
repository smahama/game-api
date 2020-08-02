package com.smahama.api.game.service;

import java.util.List;

import com.smahama.api.game.dto.CardCount;
import com.smahama.api.game.dto.CreateGameRequest;
import com.smahama.api.game.dto.SuitCount;
import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.model.Game;
import com.smahama.api.game.model.Player;

/**
 * Game service contract
 *
 * @author Salomon Mahama
 *
 */
public interface GameService {

    /**
     * method to create a game
     * @param createGameRequest data to create a new game
     * @return created {@link Game} object with its id
     */
    Game createGame(
        CreateGameRequest createGameRequest);

    /**
     * method to delete a game
     * @param gameId the game id
     * @throws GameNotFoundException if the game doesn't exist
     */
    void deleteGame(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to add deck to the game
     * @param gameId the game id
     * @param deck a {@link Deck} object to add to the game
     * @return updated {@link Game} object with deck
     * @throws GameNotFoundException if the game doesn't exist
     */
    Game addDeck(
        Integer gameId,
        Deck deck)
        throws GameNotFoundException;

    /**
     * method to add player to the game
     * @param gameId the game id
     * @param player a {@link Player} object to add to the game
     * @return updated {@link Game} object with deck
     * @throws GameNotFoundException if the game doesn't exist
     */
    Game addPlayer(
        Integer gameId,
        Player player)
        throws GameNotFoundException;

    /**
     * method to remove player from game
     * @param gameId  the game id
     * @param player a {@link Player} object to remove from the game
     * @throws GameNotFoundException if the game doesn't exist
     */
    void removePlayer(
        Integer gameId,
        Player player)
        throws GameNotFoundException;

    /**
     * method to return quantity of cards not dealt grouped by suit
     * @param gameId the game id
     * @return a List of {@link SuitCount} objects with the Suit and the quantity of cards for that Suit
     * @throws GameNotFoundException if the game doesn't exist
     */
    List<SuitCount> getCardCountBySuit(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to return the remaining ( not dealt) cards of the game
     * @param gameId the game id
     * @return a List of cards and it quantities
     * @throws GameNotFoundException if the game doesn't exist
     */
    List<CardCount> getCardCount(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to shuffle all cards of the game
     * @param gameId the game id
     * @throws GameNotFoundException if the game doesn't exist
     */
    void shuffle(
        Integer gameId)
        throws GameNotFoundException;
}
