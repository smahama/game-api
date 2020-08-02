package com.smahama.api.game.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardRepositoryUnitTest {

    @InjectMocks
    private CardRepository cardRepository;

    public CardRepositoryUnitTest() {

        //
    }

    @Test
    public void testGetAllCards() {

        final var cards = this.cardRepository.getAllCards();
        assertNotNull(cards);
        assertEquals(52, cards.size());
    }
}
