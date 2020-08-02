package com.smahama.api.game.service;

import com.smahama.api.game.dto.CreateDeckRequest;
import com.smahama.api.game.model.Deck;

/**
 * Deck Service contract
 * @author Salomon Mahama
 *
 */
public interface DeckService {

    /**
     * method to create a Deck given deck create request parameter
     * @param createDeckRequest data to create a new deck
     * @return a newly created {@link Deck} with its id and its cards
     */
    Deck createDeck(
        CreateDeckRequest createDeckRequest);
}
