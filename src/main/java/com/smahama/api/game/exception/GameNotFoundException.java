package com.smahama.api.game.exception;

/**
 * Exception for not found game
 * @author Salomon Mahama
 *
 */
public class GameNotFoundException
    extends Exception {

    private static final long serialVersionUID = 24811436699702475L;

    private static final String GAME_NOT_FOUND = "Game id:[%d] not found";

    /**
     * Constructor
     * @param gameId the game id
     */
    public GameNotFoundException(final Integer gameId) {

        super(String.format(GAME_NOT_FOUND, gameId));
    }
}
