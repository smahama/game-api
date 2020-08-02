package com.smahama.api.game.service;

import java.util.List;

import com.smahama.api.game.dto.PlayerCards;
import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Deal;

/**
 * Deal Card Service contract
 * @author Salomon Mahama
 *
 */
public interface DealService {

    /**
     * method to deal a card to a player
     * @param gameId the game id
     * @param playerId the player id
     * @return the deal object, null return mean no deal happens
     * @throws GameNotFoundException if the game doesn't exist
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    Deal dealCards(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    /**
     * method to get all carts dealt for a player in the game
     * @param gameId  gameId the game id
     * @param playerId playerId the player id
     * @return PlayerCards object
     * @throws GameNotFoundException  GameNotFoundException if the game doesn't exist
     * @throws PlayerNotFoundException  PlayerNotFoundException if the player doesn't exist
     */
    PlayerCards getPlayerCards(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    /**
     * method to get all carts dealt for each player in the game
     * @param gameId the game id
     * @return the list of {@link PlayerCards}
     * @throws GameNotFoundException if the game doesn't exist
     */
    List<PlayerCards> getPlayersCards(
        Integer gameId)
        throws GameNotFoundException;
}
