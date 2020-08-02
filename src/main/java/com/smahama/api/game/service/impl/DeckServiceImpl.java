package com.smahama.api.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smahama.api.game.dto.CreateDeckRequest;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.repository.DeckRepository;
import com.smahama.api.game.service.DeckService;

/**
 * Class implementation of {@link DeckService}
 * @author Salomon Mahama
 *
 */
@Service
public class DeckServiceImpl
    implements DeckService {

    @Autowired
    private DeckRepository deckRepository;

    private DeckServiceImpl() {

        //
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deck createDeck(
        final CreateDeckRequest createDeckRequest) {

        return this.deckRepository.createDeck(createDeckRequest.getDeckName());
    }
}
