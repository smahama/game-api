package com.smahama.api.game.service;

import com.smahama.api.game.dto.CreatePlayerRequest;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Player;

/**
 * Player Service contract
 * @author Salomon Mahama
 *
 */
public interface PlayerService {

    /**
     * method to create a Player given player create request parameter
     * @param createPlayerRequest data to create a new Player
     * @return a newly created {@link Player} with its id
     */
    Player createPlayer(
        CreatePlayerRequest createPlayerRequest);

    /**
     * method to return a player matching id
     * @param playerId  the player id
     * @return matching {@link Player} 
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    Player getPlayer(
        Integer playerId)
        throws PlayerNotFoundException;
}
