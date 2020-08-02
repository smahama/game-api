package com.smahama.api.game.exception;

/**
 * Exception for not found deck
 * @author Salomon Mahama
 *
 */
public class DeckNotFoundException
    extends Exception {

    private static final long serialVersionUID = -8294891510357037635L;

    private static final String DECK_NOT_FOUND = "Deck id:[%d] not found";

    /**
     * Constructor
     * @param deckId the deck id
     */
    public DeckNotFoundException(final Integer deckId) {

        super(String.format(DECK_NOT_FOUND, deckId));
    }
}
