package com.smahama.api.game.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smahama.api.game.model.Card;

@ExtendWith(MockitoExtension.class)
public class DeckRepositoryUnitTest {

    private static final String DECK_NAME = "DECK NAME";

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private DeckRepository deckRepository;

    @Mock
    private Card card;

    public DeckRepositoryUnitTest() {

        //
    }

    @Test
    public void testGetAllDecks() {

        assertEquals(Collections.emptyList(), this.deckRepository.getAllDecks());
    }

    @Test
    public void testCreateDeck() {

        // given
        givenCardRepository();

        // when
        final var deck = this.deckRepository.createDeck(DECK_NAME);

        // then
        assertNotNull(deck);
        assertNotNull(deck.getDeckId());
        assertEquals(DECK_NAME, deck.getDeckName());
        assertEquals(List.of(this.card), deck.getCards());
        verify(this.cardRepository).getAllCards();

        final var decks = this.deckRepository.getAllDecks();
        assertEquals(decks.get(0), deck);
        assertEquals(1, decks.size());
    }

    @Test
    public void testDeleteDeck() {

        // given
        final var deck = this.deckRepository.createDeck(DECK_NAME);

        // when
        this.deckRepository.deleteDeck(deck);

        // then
        assertEquals(Collections.emptyList(), this.deckRepository.getAllDecks());
    }

    private void givenCardRepository() {

        when(this.cardRepository.getAllCards()).thenReturn(List.of(this.card));
    }
}
