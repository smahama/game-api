package com.smahama.api.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smahama.api.game.dto.CreateDeckRequest;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.repository.DeckRepository;
import com.smahama.api.game.service.impl.DeckServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DeckServiceImplUnitTest {

    private static final String DECK_NAME = "DECK NAME";

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private DeckServiceImpl deckService;

    @Mock
    private Deck deck;

    public DeckServiceImplUnitTest() {

        //
    }

    @Test
    public void testCreateDeck() {

        // given
        final var createDeckRequest = new CreateDeckRequest();
        createDeckRequest.setDeckName(DECK_NAME);
        when(this.deckRepository.createDeck(anyString())).thenReturn(this.deck);

        // when
        final var newDeck = this.deckService.createDeck(createDeckRequest);

        // then
        assertEquals(this.deck, newDeck);
        verify(this.deckRepository).createDeck(eq(DECK_NAME));
    }

}
