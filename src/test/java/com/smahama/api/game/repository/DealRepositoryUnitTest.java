package com.smahama.api.game.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smahama.api.game.model.Card;

@ExtendWith(MockitoExtension.class)
public class DealRepositoryUnitTest {
    private static final Integer GAME_ID = 123;

    private static final Integer PLAYER_ID = 1;

    @InjectMocks
    private DealRepository dealRepository;

    @Mock
    private Card card;

    public DealRepositoryUnitTest() {

        //
    }

    @Test
    void testCreateDeal() {

        // when
        final var deal = this.dealRepository.createDeal(GAME_ID, PLAYER_ID, this.card);

        // then
        assertNotNull(deal);
        assertEquals(GAME_ID, deal.getGameId());
        assertEquals(PLAYER_ID, deal.getPlayerId());
        assertEquals(this.card, deal.getCard());

        final var deals = this.dealRepository.getAllDeals();
        assertEquals(deals.get(0), deal);
        assertEquals(1, deals.size());
    }

    @Test
    public void testGetAllDeals() {

        assertEquals(Collections.emptyList(), this.dealRepository.getAllDeals());
    }

}
