package com.smahama.api.game.exception;

/**
 * Exception for not found player
 * @author Salomon Mahama
 *
 */
public class PlayerNotFoundException
    extends Exception {

    private static final long serialVersionUID = 3804015832591037762L;

    private static final String PLAYER_NOT_FOUND = "Player id:[%d] not found";

    /**
     * Constructor
     * @param playerId the player id
     */
    public PlayerNotFoundException(final Integer playerId) {

        super(String.format(PLAYER_NOT_FOUND, playerId));
    }
}
